package com.iss.controllers;


import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.iss.Dto.CoursePayementVerificationDto;
import com.iss.Dto.TaskPayementVerificationDto;
import com.iss.Dto.UserTaskDto;
import com.iss.Dto.VideoPayementVerificationDto;
import com.iss.Services.RazorPayForCourseService;
import com.iss.Services.RazorPayForVideoPaymentService;
import com.iss.Services.RazorPayServiceForTask;





@RestController

public class PaymentController {

    @Autowired
    private RazorPayForCourseService coursePaymentService;
    
    @Autowired
    private RazorPayForVideoPaymentService videoPaymentService;
    
    @Autowired
    private RazorPayServiceForTask taskPaymentService;


    
    @PostMapping("/api/create-coursesubscriptionorder")
    public ResponseEntity<?> createCourseSubscriptionOrder(@RequestParam int courseId,@AuthenticationPrincipal Jwt jwt ) {
        try {
        	String email=jwt.getClaimAsString("email");
            return ResponseEntity.ok(coursePaymentService.createCourseSubscriptionOrder(courseId,email));
         
        }catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }  catch (Exception e) {
        	e.printStackTrace();
        	return ResponseEntity.status(500).body("Error adding the Payments: " + e.getMessage());
        }
    }
    @PostMapping("/api/generate-videopayment-id")
    public ResponseEntity<?> creatVideoPaymenteOrder(@RequestParam int userVideoId,@AuthenticationPrincipal Jwt jwt) {
        try {
            return ResponseEntity.ok(videoPaymentService.createVideoPaymentOrderId(userVideoId,jwt.getClaimAsString("email")));
         
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
    }
    @PostMapping("/api/generate-taskpayment-id")
    public ResponseEntity<?> createTaskPaymetOrder(@RequestParam int userTaskId,@AuthenticationPrincipal Jwt jwt) {
        try {
            return ResponseEntity.ok(taskPaymentService.createTaskPaymentOrderId(userTaskId,jwt.getClaimAsString("email")));
         
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
        	System.out.println(jsonPayload);
            return ResponseEntity.ok("Webhook received successfully!");
        } else {
            return ResponseEntity.ok("Unhandled event: " + eventType);
        }
    }

    
    @PostMapping("api/coursepayment/verify")
    public ResponseEntity<?> courseVerifyPayment(@RequestBody Map<String, String> paymentDetails) {
        
        try {
        	CoursePayementVerificationDto paymentVerification =coursePaymentService.verifyPayment(paymentDetails); 
            if (paymentVerification != null) {
                return ResponseEntity.ok(paymentVerification);
            } else {
            	return ResponseEntity.ok(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
    @PostMapping("api/video_payment_verification")
    public ResponseEntity<?> videoPaymentVerify(@RequestBody Map<String, String> paymentDetails) {        
        try {
            VideoPayementVerificationDto videoPaymentVerification =videoPaymentService.verifyPayment(paymentDetails); 
            if (videoPaymentVerification != null) {
            	return ResponseEntity.ok(videoPaymentVerification);
            } else {
            	return ResponseEntity.ok(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
    @PostMapping("api/taskverify-payment")
    public ResponseEntity<?> taskPaymentVerify(@RequestBody Map<String, String> paymentDetails) {
        
        try {
        	TaskPayementVerificationDto taskPayementVerificationDto =taskPaymentService.verifyPayment(paymentDetails); 
            if (taskPayementVerificationDto != null) {
            	return ResponseEntity.ok(taskPayementVerificationDto);
            } else {
            	return ResponseEntity.ok(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
