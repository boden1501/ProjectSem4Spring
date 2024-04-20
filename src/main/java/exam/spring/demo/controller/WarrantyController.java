package exam.spring.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import exam.spring.demo.model.Warranty;
import exam.spring.demo.repositories.WarrantyRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")

 class WarrantyController {
	@Autowired
	WarrantyRepository warrantyRepository;
	
	@RequestMapping(value ="/warranty", method = RequestMethod.GET)
	public String index(Model model) {
		List<Warranty> na =warrantyRepository.findAll();
		model.addAttribute("n",na);
		return "ad_layout/warrantyAdmin";
	}
		

}
