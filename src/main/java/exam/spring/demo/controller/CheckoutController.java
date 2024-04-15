package exam.spring.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {
	@RequestMapping("")
	public String index(HttpServletRequest request) {
		Object acc = request.getSession().getAttribute("usrList");
		if(acc==null) {
			return "redirect:/auth/login";
		}
		return "client_layout/checkoutClient";
		
	}
}
