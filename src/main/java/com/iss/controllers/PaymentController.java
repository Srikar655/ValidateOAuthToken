package com.iss.controllers;


import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.iss.Dto.UserCourseDto;
import com.iss.Dto.UserVedioDto;
import com.iss.Services.RazorPayForCourseService;
import com.iss.Services.RazorPayForVideoPaymentService;





@RestController

public class PaymentController {

    @Autowired
    private RazorPayForCourseService coursePaymentService;
    
    @Autowired
    private RazorPayForVideoPaymentService videoPaymentService;


    
    @PostMapping("/api/create-coursesubscriptionorder")
    public ResponseEntity<?> createCourseSubscriptionOrder(@RequestParam int courseId,@AuthenticationPrincipal Jwt jwt ) {
        try {
        	String email=jwt.getClaimAsString("email");
            return ResponseEntity.ok(coursePaymentService.createCourseSubscriptionOrder(courseId,email));
         
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
    }
    @PostMapping("/api/generate-payment-id")
    public ResponseEntity<?> creatVideoPaymenteOrder(@RequestParam int userVideoId,@AuthenticationPrincipal Jwt jwt) {
        try {
            return ResponseEntity.ok(videoPaymentService.createVideoPaymentOrderId(userVideoId,jwt.getClaimAsString("email")));
         
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
    }
    @PostMapping("/payment/webhook")
    public ResponseEntity<String> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("X-Razorpay-Signature") String razorpaySignature) {
        JSONObject jsonPayload = new JSONObject(payload);
        String eventType = jsonPayload.getString("event");
        if ("payment.captured".equals(eventType)) {

            return ResponseEntity.ok("Webhook received successfully!");
        } else {
            return ResponseEntity.ok("Unhandled event: " + eventType);
        }
    }

    
    @PostMapping("api/coursepayment/verify")
    public Map<String, Object> courseVerifyPayment(@RequestBody Map<String, String> paymentDetails) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            UserCourseDto userCourse =coursePaymentService.verifyPayment(paymentDetails); 
            if (userCourse != null) {
                response.put("status", "success");
                response.put("UserCourse", userCourse);
            } else {
                response.put("status", "failure");
            }
        } catch (Exception e) {
            response.put("status", "failure");
            e.printStackTrace();
        }

        return response;
    }
    @PostMapping("api/verify-payment")
    public Map<String, Object> videoPaymentVerify(@RequestBody Map<String, String> paymentDetails) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            UserVedioDto userVedio =videoPaymentService.verifyPayment(paymentDetails); 
            if (userVedio != null) {
                response.put("status", "success");
                response.put("UserVideo", userVedio);
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
