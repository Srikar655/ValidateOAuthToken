package com.iss.Services;



import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.iss.Dto.UserTaskDto;
import com.iss.Mappers.UserTaskMapper;
import com.iss.Repos.PaymentRepository;
import com.iss.Repos.UserRepository;
import com.iss.Repos.UserTaskRepsitory;
import com.iss.models.Payment;
import com.iss.models.PaymentStatus;
import com.iss.models.TaskReprotData;
import com.iss.models.User;
import com.iss.models.UserTask;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;

import jakarta.annotation.PostConstruct;



@Service
public class RazorPayServiceForTask {

    private RazorpayClient client;
    
    @Autowired
    private UserTaskRepsitory userTaskRepso;
    @Autowired
    private PaymentRepository paymentRepos;
    
    @Autowired
    private UserRepository userRepos;
    
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

    public double fetchUserTaskPrice(int userVideoId)
    {
    	Optional<Double> price= this.userTaskRepso.getUserTaskPriceById(userVideoId);
    	if(price.isPresent())
    	{
    		return price.get();
    	}
    	return 0;
    }
    public Map<String,Object> createTaskPaymentOrderId(int userTaskId,String email) throws Exception {
    	double amount=fetchUserTaskPrice(userTaskId);
        Map<String,Object> map=new HashMap<String,Object>();
    	if(amount!=0)
    	{
	        int amountInPaise = (int) (amount * 100);
	        JSONObject orderRequest = new JSONObject();
	        orderRequest.put("amount", amountInPaise); 
	        orderRequest.put("currency", currency);
	        orderRequest.put("receipt", "txn_123456");
	
	        JSONObject metadata = new JSONObject();
	        metadata.put("userTaskId",  userTaskId);
	        metadata.put("email",  email);
	        orderRequest.put("notes", metadata);
	        
	        Order order = client.orders.create(orderRequest);
	        map.put("orderId", order.get("id"));
	        map.put("amount", amount);
	        return map;
    	}
    	return null;
    }
	public UserTaskDto verifyPayment(Map<String, String> paymentDetails) throws Exception {
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
	private UserTaskDto processPaymentAsync(JSONObject paymentData) {
		try
		{
	        JSONObject metaData = paymentData.getJSONObject("notes");
	        int userVideoId = metaData.getInt("userTaskId");
	        String useremail=metaData.getString("email");
	        Optional<User> optionalUser=userRepos.findByUsernameOrEmail(useremail, useremail);
	        if(optionalUser.isPresent())
	        {
		        Optional<UserTask> optionalusertask = this.userTaskRepso.findById(userVideoId);
		        if(optionalusertask.isPresent())
		        {
		        	UserTask usertask=optionalusertask.get();
		        	User user=optionalUser.get();
		        	usertask.setPaymentStatus(PaymentStatus.COMPLETED);
		        	UserTask newusertask=this.userTaskRepso.save(usertask);
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
		                     .usertask(newusertask)
		                    .paymentStatus(PaymentStatus.COMPLETED)
		                    .paymentFor(Payment.PaymentType.TASK)
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
		        	this.paymentRepos.save(payment);
		        	UserTaskDto usertaskdto= UserTaskMapper.Instance.toDto(newusertask);
		        	BeanUtils.copyProperties(newusertask, usertaskdto,"uservedio","payments");
		        	TaskReprotData reportData = TaskReprotData.builder()
		        	        .name(user.getUsername())
		        	        .email(useremail)
		        	        .invoiceno("INV-20250422")
		        	        .video(newusertask.getUservedio().getVedio().getVediotitle())
		        	        .taskno(newusertask.getId())
		        	        .subscriptionfee(newusertask.getTask().getTaskprice())
		        	        .subtotal(newusertask.getTask().getTaskprice())
		        	        .gst(89.82)
		        	        .paymentdate(Timestamp.valueOf(payment.getPaymentDate()))
		        	        .paymentid(payment.getPaymentId()+"")
		        	        .paymentmethod(payment.getPaymentMethod())
		        	        .upi(payment.getVpa())
		        	        .transactionid(payment.getAcquirerTransactionId())
		        	        .course(newusertask.getUservedio().getVedio().getCourse().getCoursename())
		        	        .task(newusertask.getTask().getTask())
		        	        .build();
		        	this.jasperReportService.createAndSendTaskPaymentInvoiceReport(reportData);
		        	return usertaskdto;
		        }
		        return null;
	        }
	        return null;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
    }

}
