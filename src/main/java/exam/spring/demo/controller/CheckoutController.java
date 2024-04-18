package exam.spring.demo.controller;

import java.text.DecimalFormat;
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
	    for(Cart cart:cartList) {
	    	if(cart.getIdDiscount()==0) {
	    		String discount= "0 VNĐ";
	    	    System.out.println("discount:" +discount);
	    	    model.addAttribute("discount",discount);
	    	    String total=cartRepository.getTotal(usr.getId());
	    	    model.addAttribute("totalCart",total);
	    	}else {
	    		String discount= discountRepository.getDiscount(usr.getId());
	    		double discountTotal=discountRepository.getDiscountTemp(usr.getId());
	    		double totalTemp=cartRepository.getTotalTemp(usr.getId());
	    		double temp=totalTemp-discountTotal;
	    		DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
	            String formattedTotal = decimalFormat.format(temp);
	    	    System.out.println("discount:" +discount);
	    	    model.addAttribute("discount",discount);
	    	    model.addAttribute("totalCart",formattedTotal);
	    	}
	    }
	    

	
	    model.addAttribute("cartList",cartList);
	    model.addAttribute("subTotalCart", subTotal);
	    
	    return "client_layout/checkoutClient";  
	}

}
