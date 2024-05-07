package exam.spring.demo.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SendEmailApplication {
	
	private EmailService emailService;
	
	public SendEmailApplication(EmailService emailService) {
	this.emailService = emailService;
	}
	
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SendEmailApplication.class, args);
		SendEmailApplication app = context.getBean(SendEmailApplication.class);
		
		app.Run();
	}
	
	private void Run(){
		emailService.sendEmail( to: "sina2110@gmail.com", subject:"Email From JavaSpringBoot", body: "Hi there, this is an email from JavaSpringBoot");
	}

}
