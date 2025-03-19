package com.iss.controllers;


import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import com.iss.models.User;
import com.iss.Services.RazorPayService;



@RestController

public class PaymentController {

    @Autowired
    private RazorPayService paymentService;
    
    @PostMapping("/api/create-order")
    public ResponseEntity<?> createOrder(@RequestParam int courseId,@AuthenticationPrincipal Jwt jwt ) {
        try {
        	String email=jwt.getClaimAsString("email");
            return ResponseEntity.ok(paymentService.createOrder(courseId,email));
         
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
    }
    @PostMapping("/payment/webhook")
    public ResponseEntity<String> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("X-Razorpay-Signature") String razorpaySignature) {

    	System.out.println("*********");
    	System.out.println("**WEbhook****");
    	System.out.println("*********");
        String secret = "mysecret"; 
        

        JSONObject jsonPayload = new JSONObject(payload);
        String eventType = jsonPayload.getString("event");

        if ("payment.captured".equals(eventType)) {
            JSONObject paymentData = jsonPayload.getJSONObject("payload").getJSONObject("payment").getJSONObject("entity");
            JSONObject metaData = paymentData.getJSONObject("notes");
            String email = metaData.getString("user_email");
            int courseId = Integer.parseInt(metaData.getString("course_id"));
            
            // Process payment logic here
            System.out.println("Email: " + email);
            System.out.println("Course ID: " + courseId);

            return ResponseEntity.ok("Payment captured successfully!");
        } else {
            return ResponseEntity.ok("Unhandled event: " + eventType);
        }
    }

    @PostMapping("/payment/verify")
    public Map<String, String> verifyPayment(@RequestBody Map<String, String> paymentDetails) {
        Map<String, String> response = new HashMap<>();
        
        try {
            boolean isValidSignature =paymentService.verifyPayment(paymentDetails); 
            if (isValidSignature) {
                response.put("status", "success");
            } else {
                response.put("status", "failure");
            }
        } catch (Exception e) {
            response.put("status", "failure");
            e.printStackTrace();
        }

        return response;
    }
}
