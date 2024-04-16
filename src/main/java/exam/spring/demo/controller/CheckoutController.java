package exam.spring.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import exam.spring.demo.model.Cart;
import exam.spring.demo.model.User;
import exam.spring.demo.repositories.CartRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {
	@Autowired
	CartRepository cartRepository;
	@RequestMapping("")
	public String index(HttpSession session,Model model) {
	    User usr = (User) session.getAttribute("usrList");
	    if (usr == null) {
	        return "redirect:/auth/login";
	    }
	    List<Cart> cartList = cartRepository.loadAllByID(usr.getId());
	    model.addAttribute("cartList",cartList);
	    return "client_layout/checkoutClient";  
	}

}
