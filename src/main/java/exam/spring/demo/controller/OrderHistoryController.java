package exam.spring.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import exam.spring.demo.repositories.OrderDetailRepository;
import exam.spring.demo.repositories.OrderRepository;
import jakarta.servlet.http.HttpSession;
import exam.spring.demo.model.Checkout;
import exam.spring.demo.model.CheckoutDT;
import exam.spring.demo.model.User;

@Controller
@RequestMapping("")

public class OrderHistoryController {
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	OrderDetailRepository detailRepository;
	@RequestMapping(value = "/orderHistory", method = RequestMethod.GET)
	public String index(HttpSession session, Model model, @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "5") int size) {
	    User usr = (User) session.getAttribute("usrList");
	    if (usr == null) {
	        return "redirect:/auth/login";
	    }
	    int offset = page * size;
	    List<Checkout> orderList = orderRepository.findByName(usr.getId(), offset, size);
	    if(orderList.isEmpty()) {
	    	model.addAttribute("message", "You have not place any orders");
	    	return "client_layout/orderHistoryClient";
	    }
	    int totalRows = orderRepository.getTotalRows(usr.getId());
	    int totalPages = (int) Math.ceil((double) totalRows / size);
	    int row = 0;
	    if (page > 1) {
	        row = offset - size;
	    }
	    model.addAttribute("row", row);
	    model.addAttribute("orderList", orderList);
	    model.addAttribute("totalRows", totalRows);
	    model.addAttribute("totalPages", totalPages);
	    model.addAttribute("currentPage", page);


	    return "client_layout/orderHistoryClient";
	}

	@RequestMapping(value = "/orderDetail", method = RequestMethod.GET)
	public String orderDetail(Model model, @RequestParam("idOrder") String idOrder) {
	    List<CheckoutDT> detailList = detailRepository.findByID(idOrder);
	    model.addAttribute("detailList", detailList);
	    return "common/client/_layoutModal"; // Trả về fragment chứa thông tin chi tiết đơn hàng
	}





}
