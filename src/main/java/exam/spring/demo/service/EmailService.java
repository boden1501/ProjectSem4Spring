package exam.spring.demo.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	
	private final JavaMailSender mailSender;
		
		public EmailService(JavaMailSender mailSender) {
			this.mailSender= mailSender;		
	    }
		
	@Async	
    public void sendEmail(String to, String subject, String body) {
    	
    	SimpleMailMessage message = new SimpleMailMessage();
    	
    	message.setTo(to);
    	message.setFrom("anyname@freelance.mailtrap.link");
    	message.setSubject(subject);
    	message.setText(body);
    	
    	mailSender.send(message);
    }
}
