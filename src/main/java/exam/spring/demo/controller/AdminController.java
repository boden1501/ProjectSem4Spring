package exam.spring.demo.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import exam.spring.demo.model.User;
import exam.spring.demo.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	UserRepository usrRepository;
	@RequestMapping(value = "",method= RequestMethod.GET)
	public String indexAdmin(Model model) {
		
		return "ad_layout/indexAdmin";
	}
	@RequestMapping(value = "/login",method= RequestMethod.GET)
	public String loginfrm(Model model,HttpServletRequest request) {
		Object acc = request.getSession().getAttribute("myacc");
		if(acc!=null) {
			return "redirect:/admin";
		}
		return "ad_layout/loginAdmin";
	}
	@RequestMapping(value = "/chklogin",method= RequestMethod.POST)
	public String loginCHK(@RequestParam("usr")String username, @RequestParam("pwd")String password,HttpServletRequest request) {
		Logger log = Logger.getGlobal();
		log.info(username+" "+password);
		List<User> dataList = usrRepository.findAll();
		for(User usr: dataList) {
			if(username.equals(usr.getUsername())||username.equals(usr.getEmail())&&password.equals(usr.getPassword())){
				request.getSession().setAttribute("myacc", username);
				return "redirect:/admin";
			}
			
		}
		return "redirect:/admin";
		
	}
	@RequestMapping(value = "/chklogout",method= RequestMethod.GET)
	public String logoutCHK(Model model,HttpServletRequest request) {
		request.getSession().removeAttribute("myacc");
		return "redirect:/admin/login";
	}
	

}

