package com.iss.Services;



import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.iss.Dto.UserVedioDto;
import com.iss.Mappers.UserVedioMapper;
import com.iss.Repos.PaymentRepository;
import com.iss.Repos.UserRepository;
import com.iss.Repos.UserVideosRepository;
import com.iss.models.Payment;
import com.iss.models.PaymentStatus;
import com.iss.models.User;
import com.iss.models.UserVedio;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;

import jakarta.annotation.PostConstruct;



@Service
public class RazorPayForVideoPaymentService {

    private RazorpayClient client;
    
    @Autowired
    private UserVideosRepository userVedioRepos;
    @Autowired
    private PaymentRepository paymentRepos;
    
    @Autowired
    private UserRepository userRepos;
    
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

    public double fetchUserVideoPrice(int userVideoId)
    {
    	Optional<Double> price= this.userVedioRepos.getUserVideoPriceById(userVideoId);
    	if(price.isPresent())
    	{
    		return price.get();
    	}
    	return 0;
    }
    public Map<String,Object> createVideoPaymentOrderId(int userVideoId,String email) throws Exception {
    	double amount=fetchUserVideoPrice(userVideoId);
        Map<String,Object> map=new HashMap<String,Object>();
    	if(amount!=0)
    	{
	        int amountInPaise = (int) (amount * 100);
	        JSONObject orderRequest = new JSONObject();
	        orderRequest.put("amount", amountInPaise); 
	        orderRequest.put("currency", currency);
	        orderRequest.put("receipt", "txn_123456");
	
	        JSONObject metadata = new JSONObject();
	        metadata.put("userVideoId",  userVideoId);
	        metadata.put("email",  email);
	        orderRequest.put("notes", metadata);
	        
	        Order order = client.orders.create(orderRequest);
	        map.put("orderId", order.get("id"));
	        map.put("amount", amount);
	        return map;
    	}
    	return null;
    }
	public UserVedioDto verifyPayment(Map<String, String> paymentDetails) throws Exception {
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
	private UserVedioDto processPaymentAsync(JSONObject paymentData) {
		try
		{
	        JSONObject metaData = paymentData.getJSONObject("notes");
	        int userVideoId = metaData.getInt("userVideoId");
	        String useremail=metaData.getString("email");
	        Optional<User> optionalUser=userRepos.findByUsernameOrEmail(useremail, useremail);
	        if(optionalUser.isPresent())
	        {
		        Optional<UserVedio> optionaluservideo = this.userVedioRepos.findById(userVideoId);
		        if(optionaluservideo.isPresent())
		        {
		        	UserVedio uservideo=optionaluservideo.get();
		        	User user=optionalUser.get();
		        	uservideo.setPaymentStatus(PaymentStatus.COMPLETED);
		        	UserVedio newuservideo=this.userVedioRepos.save(uservideo);
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
		                    .uservedio(newuservideo)
		                    .paymentStatus(PaymentStatus.COMPLETED)
		                    .paymentFor(Payment.PaymentType.EPISODE)
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
		        	UserVedioDto uservideodto= UserVedioMapper.Instance.toDto(newuservideo);
		        	uservideodto.getVedio().setVideourl(newuservideo.getVedio().getVideourl());
		        	return uservideodto;
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
