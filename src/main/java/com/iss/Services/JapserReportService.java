package com.iss.Services;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.iss.models.CourseReportData;
import com.iss.models.EmailDetails;
import com.iss.models.TaskReprotData;
import com.iss.models.VideoReportData;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Service
public class JapserReportService {
	
	private final EmailService emailService;
	
	@Value("${coursePaymentReport-path}")
	private String courseReportPath;
	
	@Value("${videoreportpath}")
	private String videoReportPath;
	
	@Value("${taskreportpath}")
	private String taskReportPath;
	
	public JapserReportService(EmailService emailService) {
		this.emailService = emailService;
	}
	
	@Async
	public void createAndSendCoursePaymentInvoiceReport(CourseReportData courseReportData) {
		try {
			// Create a map of parameters for the report
			Map<String, Object> parameters = new HashMap<>();
	        parameters.put("name", courseReportData.getName());
	        parameters.put("email", courseReportData.getEmail());
	        parameters.put("invoiceno", courseReportData.getInvoiceno());
	        parameters.put("enrolledcourse", courseReportData.getEnrolledcourse());
	        parameters.put("coursecategory", courseReportData.getCoursecategory());
	        parameters.put("subscriptionfee", courseReportData.getSubscriptionfee());
	        parameters.put("subtotal", courseReportData.getSubtotal());
	        parameters.put("gst", courseReportData.getGst());
	        parameters.put("paymentdate", courseReportData.getPaymentdate());
	        parameters.put("paymentid", courseReportData.getPaymentid());
	        parameters.put("paymentmethod", courseReportData.getPaymentmethod());
	        parameters.put("upi", courseReportData.getUpi());
	        parameters.put("transactionid", courseReportData.getTransactionid());

	        // Load the compiled report (.jasper file)
	        FileInputStream reportFileInputStream = new FileInputStream(new File(courseReportPath));
	        JasperReport jasperReport = JasperCompileManager.compileReport(reportFileInputStream);

	        // Fill the report with parameters (this returns a JasperPrint object)
	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

	        // Export the JasperPrint to a PDF
	        byte[] reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);
	        EmailDetails emailDetails=new EmailDetails(courseReportData.getEmail(),"Paisal Baane Kattav ra great!","subject ha ha",reportBytes);
	        emailService.sendMailWithAttachment(emailDetails);
	        

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	@Async
	public void createAndSendVideoPaymentInvoiceReport(VideoReportData videoReportData) {
		try {
			// Create a map of parameters for the report
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("name", videoReportData.getName());
			parameters.put("email", videoReportData.getEmail());
			parameters.put("invoiceno", videoReportData.getInvoiceno());
			parameters.put("paidvideo", videoReportData.getPaidvideo());
			parameters.put("videono", videoReportData.getVideono());
			parameters.put("subscriptionfee", videoReportData.getSubscriptionfee());
			parameters.put("subtotal", videoReportData.getSubtotal());
			parameters.put("gst", videoReportData.getGst());
			parameters.put("paymentdate", videoReportData.getPaymentdate());
			parameters.put("paymentid", videoReportData.getPaymentid());
			parameters.put("paymentmethod", videoReportData.getPaymentmethod());
			parameters.put("upi", videoReportData.getUpi());
			parameters.put("transactionid", videoReportData.getTransactionid());
			parameters.put("course", videoReportData.getCourse());


	        // Load the compiled report (.jasper file)
	        FileInputStream reportFileInputStream = new FileInputStream(new File(videoReportPath));
	        JasperReport jasperReport = JasperCompileManager.compileReport(reportFileInputStream);

	        // Fill the report with parameters (this returns a JasperPrint object)
	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

	        // Export the JasperPrint to a PDF
	        byte[] reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);
	        EmailDetails emailDetails=new EmailDetails(videoReportData.getEmail(),"Paisal Baane Kattav ra great!","subject ha ha",reportBytes);
	        emailService.sendMailWithAttachment(emailDetails);
	        

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	@Async
	public void createAndSendTaskPaymentInvoiceReport(TaskReprotData taskReportData) {
		try {
			// Create a map of parameters for the report
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("name", taskReportData.getName());
			parameters.put("email", taskReportData.getEmail());
			parameters.put("invoiceno",taskReportData.getInvoiceno());
			parameters.put("video", taskReportData.getVideo());
			parameters.put("taskno", taskReportData.getTaskno());
			parameters.put("subscriptionfee", taskReportData.getSubscriptionfee());
			parameters.put("subtotal", taskReportData.getSubtotal());
			parameters.put("gst", taskReportData.getGst());
			parameters.put("paymentdate", taskReportData.getPaymentdate());
			parameters.put("paymentid", taskReportData.getPaymentid());
			parameters.put("paymentmethod", taskReportData.getPaymentmethod());
			parameters.put("upi", taskReportData.getUpi());
			parameters.put("transactionid", taskReportData.getTransactionid());
			parameters.put("course", taskReportData.getCourse());
			parameters.put("task", taskReportData.getTask());



	        // Load the compiled report (.jasper file)
	        FileInputStream reportFileInputStream = new FileInputStream(new File(this.taskReportPath));
	        JasperReport jasperReport = JasperCompileManager.compileReport(reportFileInputStream);

	        // Fill the report with parameters (this returns a JasperPrint object)
	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

	        // Export the JasperPrint to a PDF
	        byte[] reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);
	        EmailDetails emailDetails=new EmailDetails(taskReportData.getEmail(),"Paisal Baane Kattav ra great ika musukoni task chey submit cheyalii...!","subject ha ha",reportBytes);
	        emailService.sendMailWithAttachment(emailDetails);
	        

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
