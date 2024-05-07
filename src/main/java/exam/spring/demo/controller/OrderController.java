package exam.spring.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import exam.spring.demo.model.Checkout;
import exam.spring.demo.model.Order;
import exam.spring.demo.model.Warranty;
import exam.spring.demo.repositories.OrderRepository;
import exam.spring.demo.repositories.WarrantyRepository;

@Controller
@RequestMapping("")
public class OrderController {
	@Autowired
	OrderRepository OrderRepository;
	@RequestMapping(value="/historyOrder", method=RequestMethod.GET)
    public String lichsudonhang(Model model) {
		List<Checkout> a=OrderRepository.findCheckoutAll();
        model.addAttribute("a", a);
        
        
        return "client_layout/historyOrder";
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


