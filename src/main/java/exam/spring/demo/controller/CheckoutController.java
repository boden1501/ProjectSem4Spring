package exam.spring.demo.controller;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	@RequestMapping(value="/vnpay_return",method = RequestMethod.GET)
	public String returnPayment(Model model, @RequestParam("vnp_TxnRef") String vnp_TxnRef,
	        @RequestParam("vnp_Amount") String vnp_Amount, @RequestParam("vnp_OrderInfo") String vnp_OrderInfo,
	        @RequestParam("vnp_ResponseCode") String vnp_ResponseCode, @RequestParam("vnp_TransactionNo") String vnp_TransactionNo,
	        @RequestParam("vnp_BankCode") String vnp_BankCode, @RequestParam("vnp_PayDate") String vnp_PayDate) {

	    model.addAttribute("vnp_Amount", vnp_Amount);
	    model.addAttribute("vnp_BankCode", vnp_BankCode);
	    model.addAttribute("vnp_OrderInfo", vnp_OrderInfo);
	    model.addAttribute("vnp_ResponseCode", vnp_ResponseCode);
	    model.addAttribute("vnp_TransactionNo", vnp_TransactionNo);
	    model.addAttribute("vnp_TxnRef", vnp_TxnRef);

	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	    LocalDateTime localDateTime = LocalDateTime.parse(vnp_PayDate, formatter);


	    model.addAttribute("vnp_PayDate", localDateTime);

	    return "ad_layout/vnpay_return";
	}
}
