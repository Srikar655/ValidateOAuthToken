package com.iss.Services;



import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.iss.Repos.CourseRepository;
import com.iss.Repos.UserRepository;
import com.iss.models.Course;
import com.iss.models.User;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;

import jakarta.annotation.PostConstruct;



@Service
public class RazorPayService {

    private RazorpayClient client;
    @Autowired
    private UserRepository userrepos;
    @Autowired
    private  CourseRepository repos;

    // Inject API Key and Secret from application.properties
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
    public Map<String,Object> createOrder(int courseId,String email) throws Exception {
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

	public boolean verifyPayment(Map<String, String> paymentDetails) throws Exception {
		try {
			System.out.println("verify");
            String razorpayOrderId = paymentDetails.get("orderId");
            String razorpayPaymentId = paymentDetails.get("paymentId");
            String razorpaySignature = paymentDetails.get("signature");
            String signatureData = razorpayOrderId + "|" + razorpayPaymentId;
            boolean isValidSignature = Utils.verifySignature(signatureData, razorpaySignature, apiSecret);
            return isValidSignature;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }


		
	}
}
