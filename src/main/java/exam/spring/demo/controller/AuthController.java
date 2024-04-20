package exam.spring.demo.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import exam.spring.demo.model.User;
import exam.spring.demo.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {
	@Autowired
	UserRepository usrRepository;
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request) {
		Object acc = request.getSession().getAttribute("usrList");
		
		if (acc != null) {
			return "redirect:/";
		}
		return "/auth/login";
	}
	
	@RequestMapping(value = "/chklogin",method= RequestMethod.POST)
	public String loginCHK(@RequestParam("usr")String username, @RequestParam("pwd")String password,HttpServletRequest request,HttpSession session) {
		Logger log = Logger.getGlobal();
		log.info(username+" "+password);
		List<User> dataList = usrRepository.findAll();
		for(User usr: dataList) {
			if(username.equals(usr.getUsername())&&password.equals(usr.getPassword())){
				System.out.println("name:" +usr.getName());
				System.out.println("name:" +usr.getAvatar());
				request.getSession().setAttribute("usrList", usr);
				return "redirect:/";
			}
			
		}
		return "redirect:/auth/login";
		
	}
	@RequestMapping(value = "/chklogout", method = RequestMethod.GET)
	public String logoutCHK(Model model, HttpServletRequest request) {
		request.getSession().removeAttribute("usrList");
		return "redirect:/";
	}
	@GetMapping("/register")
	public String register() {
		return "/auth/register";
	}
	
	@RequestMapping(value = "/register",method= RequestMethod.POST)
	public String saveUser(@Validated User user, Model model)
	{
		usrRepository.registerUser(user);
		return "redirect:/auth/login"; 
	}
}
