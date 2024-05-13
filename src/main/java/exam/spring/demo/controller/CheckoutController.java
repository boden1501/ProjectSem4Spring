package exam.spring.demo.controller;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import exam.spring.demo.model.Cart;
import exam.spring.demo.model.CheckoutTemp;
import exam.spring.demo.model.Product;
import exam.spring.demo.model.User;
import exam.spring.demo.repositories.CartRepository;
import exam.spring.demo.repositories.DiscountRepository;
import exam.spring.demo.repositories.OrderDetailRepository;
import exam.spring.demo.repositories.OrderRepository;
import exam.spring.demo.repositories.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {
	@Autowired
	ProductRepository productRepository;
	@Autowired
	CartRepository cartRepository;
	@Autowired
	DiscountRepository discountRepository;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	OrderDetailRepository orderDetailRepository;

	@RequestMapping("")
	public String index(HttpSession session, Model model) {
		User usr = (User) session.getAttribute("usrList");
		if (usr == null) {
			return "redirect:/auth/login";
		}
		List<Cart> cartList = cartRepository.loadAllByID(usr.getId());
		if (cartList.isEmpty()) {
			return "redirect:/";
		}
		String subTotal = cartRepository.getTotal(usr.getId());
		System.out.println("total:" + subTotal);
		for (Cart cart : cartList) {
			if (cart.getIdDiscount() == 0) {
				String discount = "0 VNĐ";
				System.out.println("discount:" + discount);
				model.addAttribute("discountTotal", 0);
				model.addAttribute("discount", discount);
				String total = cartRepository.getTotal(usr.getId());
				double totalTemp = cartRepository.getTotalTemp(usr.getId());
				model.addAttribute("totalTemp", totalTemp);
				model.addAttribute("total", totalTemp);
				model.addAttribute("totalCart", total);
			} else {
				String discount = discountRepository.getDiscount(usr.getId());
				double discountTotal = discountRepository.getDiscountTemp(usr.getId());
				model.addAttribute("discountTotal", discountTotal);
				double totalTemp = cartRepository.getTotalTemp(usr.getId());
				model.addAttribute("totalTemp", totalTemp);
				double temp = totalTemp - discountTotal;
				model.addAttribute("total", temp);
				DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
				String formattedTotal = decimalFormat.format(temp);
				System.out.println("discount:" + discount);
				model.addAttribute("discount", discount);
				model.addAttribute("totalCart", formattedTotal);
				break;
			}
		}

		model.addAttribute("cartList", cartList);
		model.addAttribute("subTotalCart", subTotal);

		return "client_layout/checkoutClient";
	}

	@Autowired
	PaymentController payment;

	@PostMapping("/chkCheckout")
	public String checkout(HttpServletRequest request, @RequestParam("nameUser") String name,
			@RequestParam("phoneUser") String phone, @RequestParam("emailUser") String email,
			@RequestParam("addressUser") String address, @RequestParam("discount") double discount,
			@RequestParam("subTotalCart") double subTotalCart, @RequestParam("total") double total)
			throws UnsupportedEncodingException {
		CheckoutTemp temp = new CheckoutTemp();
		temp.setNameUser(name);
		temp.setPhoneUser(phone);
		temp.setAddressUser(address);
		temp.setEmailUser(email);
		temp.setDiscountPrice(discount);
		temp.setSubTotalPrice(subTotalCart);
		temp.setTotalPrice(total);
		HttpSession session = request.getSession();
		session.setAttribute("temp", temp);

		ResponseEntity<?> responseEntity = payment.createPayment(request);
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			return "redirect:" + (String) responseEntity.getBody();
		} else {
			return "Error";
		}
	}

	@GetMapping("/vnpay_return")
	public String returnPayment(HttpServletRequest request, Model model, @RequestParam("vnp_TxnRef") String vnp_TxnRef,
			@RequestParam("vnp_Amount") String vnp_Amount, @RequestParam("vnp_OrderInfo") String vnp_OrderInfo,
			@RequestParam("vnp_ResponseCode") String vnp_ResponseCode,
			@RequestParam("vnp_TransactionNo") String vnp_TransactionNo,
			@RequestParam("vnp_BankCode") String vnp_BankCode, @RequestParam("vnp_PayDate") String vnp_PayDate) {
		HttpSession session = request.getSession();
		CheckoutTemp data = (CheckoutTemp) session.getAttribute("temp");
		User usr = (User) session.getAttribute("usrList");
		if (usr == null) {
			return "redirect:/auth/login";
		}
		List<Cart> cartList = cartRepository.loadAllByID(usr.getId());
		double amountValue = Double.parseDouble(vnp_Amount);
		amountValue = amountValue / 100;
		DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
		String formattedVnp_Amount = decimalFormat.format(amountValue);

		model.addAttribute("vnp_Amount", formattedVnp_Amount);
		model.addAttribute("vnp_BankCode", vnp_BankCode);
		model.addAttribute("vnp_OrderInfo", vnp_OrderInfo);
		model.addAttribute("vnp_ResponseCode", vnp_ResponseCode);
		model.addAttribute("vnp_TransactionNo", vnp_TransactionNo);
		model.addAttribute("vnp_TxnRef", vnp_TxnRef);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		LocalDateTime localDateTime = LocalDateTime.parse(vnp_PayDate, formatter);
		String dateCreate = localDateTime.format(formatter);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
		String formattedDateTime = localDateTime.format(format);
		List<Product> proList = productRepository.findProductAll();
		model.addAttribute("vnp_PayDate", formattedDateTime);
		orderRepository.insert(vnp_TxnRef,usr.getId() ,dateCreate, data, 1);
		for (Cart chkDTTemp : cartList) {
			orderDetailRepository.insert(vnp_TxnRef, chkDTTemp);
			for (Product product : proList) {
				System.out.println("id:"+chkDTTemp.getIdProduct());
				if (chkDTTemp.getIdProduct()==product.getIdProduct()) {
					int quantity=product.getQuantityProduct()-chkDTTemp.getQuantity();
					productRepository.updateQuantity(quantity, chkDTTemp.getIdProduct());
				}
			}
		}
		cartRepository.deletaAll(usr.getId());
		session.removeAttribute("temp");
		return "client_layout/vnpay_return";
	}

}
