package com.iss.models;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskReprotData {
	private String name;
    private String email;
    private String invoiceno;
    private String video;
    private Integer taskno;
    private Double subscriptionfee;
    private Double subtotal;
    private Double gst;
    private Timestamp paymentdate;
    private String paymentid;
    private String paymentmethod;
    private String upi;
    private String transactionid;
    private String course;
    private String task;
}
