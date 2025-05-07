package com.iss.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoReportData {
    private String name;
    private String email;
    private String invoiceno;
    private String paidvideo;
    private int videono;
    private Double subscriptionfee;
    private Double subtotal;
    private Double gst;
    private java.sql.Timestamp paymentdate;
    private String paymentid;
    private String paymentmethod;
    private String upi;
    private String transactionid;
    private String course;
}

