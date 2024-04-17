package exam.spring.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import exam.spring.demo.model.Cart;
import exam.spring.demo.model.User;
import exam.spring.demo.repositories.CartRepository;
import exam.spring.demo.repositories.DiscountRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {
	@Autowired
	CartRepository cartRepository;
	@Autowired
	DiscountRepository discountRepository;
	@RequestMapping("")
	public String index(HttpSession session,Model model) {
	    User usr = (User) session.getAttribute("usrList");
	    if (usr == null) {
	        return "redirect:/auth/login";
	    }
	    List<Cart> cartList = cartRepository.loadAllByID(usr.getId());
	    String subTotal=cartRepository.getTotal(usr.getId());
	    System.out.println("total:"+subTotal);
	    String discount= discountRepository.getDiscount(usr.getId());
	    System.out.println("discount:" +discount);
	    model.addAttribute("discount",discount);
	    String total=cartRepository.getTotal(usr.getId());
	    model.addAttribute("cartList",cartList);
	    model.addAttribute("subTotalCart", subTotal);
	    model.addAttribute("totalCart",total);
	    return "client_layout/checkoutClient";  
	}

}
