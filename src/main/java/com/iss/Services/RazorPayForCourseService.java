package com.iss.Services;



import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.iss.Dto.UserCourseDto;
import com.iss.Repos.CourseRepository;
import com.iss.Repos.PaymentRepository;
import com.iss.Repos.UserRepository;
import com.iss.models.AccessStatus;
import com.iss.models.Course;
import com.iss.models.CourseReportData;
import com.iss.models.Payment;
import com.iss.models.PaymentStatus;
import com.iss.models.SubscriptionStatus;
import com.iss.models.TaskProgress;
import com.iss.models.Tasks;
import com.iss.models.User;
import com.iss.models.UserCourse;
import com.iss.models.UserTask;
import com.iss.models.UserVedio;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;

import jakarta.annotation.PostConstruct;



@Service
public class RazorPayForCourseService {

    private RazorpayClient client;
    @Autowired
    private UserRepository userrepos;
    @Autowired
    private  CourseRepository repos;
    
    @Autowired
    private PaymentRepository paymentRepos;
    
    @Autowired
    private  UserCourseService usercourseService;
    
    @Autowired
    private JapserReportService jasperReportService;

    @Value("${razorpay.key}")
    private String apiKey;

    @Value("${razorpay.secret}")
    private String apiSecret;
    
    @Value("${razorpay.currency}")
    private String currency;
    
    @Value("${razorpay.companyname}")
    private String companyname;

    @PostConstruct
    public void init() throws Exception {
        this.client = new RazorpayClient(apiKey, apiSecret);
    }

    public double fetchCoursePrice(int courseId)
    {
    	return repos.getCoursePriceByCourseId(courseId).get();
    }
    public Course fetchCourse(int courseId)
    {
    	return repos.findById(courseId).get();
    }
    public User fetchUser(String email)
    {
    	return userrepos.findByUsernameOrEmail(email, email).get();
    }
    public Map<String,Object> createCourseSubscriptionOrder(int courseId,String email) throws Exception {
    	double amount=fetchCoursePrice(courseId);
        int amountInPaise = (int) (amount * 100);
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amountInPaise); 
        orderRequest.put("currency", currency);
        orderRequest.put("receipt", "txn_123456");

        JSONObject metadata = new JSONObject();
        metadata.put("user_email", email);
        metadata.put("course_id", courseId);
        orderRequest.put("notes", metadata);
        
        Order order = client.orders.create(orderRequest);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("orderId", order.get("id"));
        map.put("amount", amount);
        return map;
    }
	public UserCourseDto verifyPayment(Map<String, String> paymentDetails) throws Exception {
		try {
            String razorpayOrderId = paymentDetails.get("orderId");
            String razorpayPaymentId = paymentDetails.get("paymentId");
            String razorpaySignature = paymentDetails.get("signature");
            String signatureData = razorpayOrderId + "|" + razorpayPaymentId;
            boolean isValidSignature = Utils.verifySignature(signatureData, razorpaySignature, apiSecret);
            if(isValidSignature)
            {
            	com.razorpay.Payment payment=client.payments.fetch(razorpayPaymentId);
            	return processPaymentAsync(payment.toJson());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
		return null;		
	}
	private UserCourseDto processPaymentAsync(JSONObject paymentData) {
        JSONObject metaData = paymentData.getJSONObject("notes");
        String email = metaData.getString("user_email");
        int courseid = (int) metaData.get("course_id");

        User user = fetchUser(email);
        Course course = fetchCourse(courseid);
        UserCourse usercourse = UserCourse.builder()
                .course(course)
                .user(user)
                .subscriptionStatus(SubscriptionStatus.ACTIVE)
                .build();

        List<UserVedio> userVedios = course.getVedios().stream()
                .map(v -> {
                    UserVedio userVedio = UserVedio.builder()
                            .vedio(v)
                            .usercourse(usercourse)
                            .paymentStatus(PaymentStatus.PENDING).accessStatus(AccessStatus.LOCKED)
                            .build();

                    List<UserTask> userTasks = new ArrayList<>();
                    for (Tasks t : v.getTasks()) {
                        userTasks.add(UserTask.builder().task(t).uservedio(userVedio).paymentStatus(PaymentStatus.PENDING).taskProgress(TaskProgress.PENDING).accessStatus(AccessStatus.LOCKED).build());
                    }
                    userVedio.setUsertask(userTasks);
                    return userVedio;
                })
                .collect(Collectors.toList());
        if(userVedios!=null && !userVedios.isEmpty())
        {
        	userVedios.getFirst().setAccessStatus(AccessStatus.UNLOCKED);
        }
        usercourse.setUserVedios(userVedios);
        UserCourseDto newusercourse=this.usercourseService.add(usercourse);
        long createdAt = paymentData.getLong("created_at");
        LocalDateTime paymentDateTime = LocalDateTime.ofEpochSecond(createdAt, 0, ZoneOffset.UTC);

        BigDecimal amount = new BigDecimal(paymentData.getInt("amount")).divide(BigDecimal.valueOf(100));
        BigDecimal fee = new BigDecimal(paymentData.getInt("fee")).divide(BigDecimal.valueOf(100));
        BigDecimal tax = new BigDecimal(paymentData.getInt("tax")).divide(BigDecimal.valueOf(100));

        Payment payment = Payment.builder()
                .amount(amount)
                .currency(paymentData.getString("currency"))
                .fee(fee)
                .tax(tax)
                .usercourse(usercourse)
                .paymentStatus(PaymentStatus.COMPLETED)
                .paymentFor(Payment.PaymentType.COURSE)
                .paymentDate(paymentDateTime)
                .paymentIdRazorpay(paymentData.getString("id"))
                .orderId(paymentData.optString("order_id"))
                .paymentMethod(paymentData.optString("method"))
                .vpa(paymentData.optString("vpa"))
                .contact(paymentData.optString("contact"))
                .email(paymentData.optString("email"))
                .acquirerTransactionId(paymentData.getJSONObject("acquirer_data").optString("upi_transaction_id"))
                .rrn(paymentData.getJSONObject("acquirer_data").optString("rrn"))
                .description(paymentData.optString("description"))
                .user(user) // Use your service method
                .build();


        paymentRepos.save(payment);
        CourseReportData courseReport = CourseReportData.builder().coursecategory(course.getCourseCategory().getCategory()).email(email).name(user.getUsername())
        		.enrolledcourse(course.getCoursename()).gst(payment.getTax().doubleValue()).invoiceno("").paymentid(payment.getPaymentMethod())
        		.paymentid(""+payment.getPaymentId()).paymentdate(Timestamp.valueOf(payment.getPaymentDate())).transactionid(payment.getAcquirerTransactionId())
        		.subscriptionfee(payment.getAmount().doubleValue()).subtotal(payment.getAmount().doubleValue()).upi(payment.getVpa()).build();
        this.jasperReportService.createAndSendCoursePaymentInvoiceReport(courseReport);
        return newusercourse;
    }

}
