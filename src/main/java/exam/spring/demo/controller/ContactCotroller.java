package exam.spring.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import exam.spring.demo.repositories.FeedbackRepository;

@Controller
@RequestMapping("/")
public class ContactCotroller {
	@Autowired
	FeedbackRepository fd;
 @RequestMapping(value ="/contact", method= RequestMethod.GET)
 public String index() {
	 return  "client_layout/ContactClient";
 }
 @RequestMapping(value="/SubmitContact", method= RequestMethod.POST)
 public String Submitcontact(@RequestParam("name") String name,@RequestParam("email") String email,@RequestParam("messege") String messege) {
	 fd.insert(name, email, messege);
	 return "redirect:/contact";
 }
}

