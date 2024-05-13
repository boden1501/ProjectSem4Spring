package exam.spring.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import exam.spring.demo.model.Checkout;
import exam.spring.demo.model.Order;
import exam.spring.demo.model.Warranty;
import exam.spring.demo.repositories.OrderRepository;
import exam.spring.demo.repositories.WarrantyRepository;

@Controller
@RequestMapping("/admin")
public class OrderController {
	@Autowired
	OrderRepository OrderRepository;
	@GetMapping("/historyOrder")
    public String lichsudonhang(Model model) {
		List<Checkout> a=OrderRepository.findCheckoutAll();
        model.addAttribute("a", a);
        System.out.println(a.size());
        return "ad_layout/historyOrder";
    }

	@RequestMapping(value = "/updateOrder", method = RequestMethod.PUT)
    public String updateOrderStatus(@RequestParam("idOrder") int orderId, @RequestParam("newStatus") int newStatus) {
        // Gọi phương thức updateOrderStatus từ repository để cập nhật trạng thái
        
		OrderRepository.updateOrderStatus(orderId, newStatus);
       
        // Redirect hoặc trả về một trang nào đó sau khi cập nhật thành công
        return "redirect:/admin/historyOrder";
    }
}


//@Controller
//@RequestMapping("/admin")
//
// class WarrantyController {
//	@Autowired
//	WarrantyRepository warrantyRepository;
//	
//	@RequestMapping(value ="/warranty", method = RequestMethod.GET)
//	public String index(Model model) {
//		List<Warranty> na =warrantyRepository.findAll();
//		model.addAttribute("n",na);
//		return "ad_layout/warrantyAdmin";
//	}
//		
//
//}


