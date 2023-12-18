package com.example.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.example.ecommerce.entity.OrderDetail;
import com.example.ecommerce.entity.Account.Customer;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private TemplateEngine templateEngine;

	public void sendVerificationEmail(Customer customer, String subject, List<OrderDetail> orderDetails)
			throws MessagingException {
		// Tạo MimeMessage
		MimeMessage message = javaMailSender.createMimeMessage();
		double orderTotal = orderDetails.stream()
				.mapToDouble(item -> item.getProduct().getProductPrice() * item.getQuantity()).sum();

		// Tạo đối tượng Context và thêm dữ liệu cần thiết (nếu có)
		Context context = new Context();
		context.setVariable("orderDetails", orderDetails);
		context.setVariable("orderTotal", orderTotal);
		context.setVariable("customer", customer);

		// Sử dụng template Thymeleaf để tạo nội dung email
		String content = templateEngine.process("cart/verification-email", context);
		System.out.println(content);

		// Thiết lập thông tin MimeMessage
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setTo(customer.getEmail());
		helper.setSubject(subject);
		helper.setText(content, true); // true để sử dụng HTML

		// Gửi email
		javaMailSender.send(message);
	}
}
