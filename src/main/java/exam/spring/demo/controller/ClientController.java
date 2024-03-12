package exam.spring.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class ClientController {
	@RequestMapping(value = "",method = RequestMethod.GET)
	public String index() {
		return "ad_layout/loginAdmin";
	}
}
