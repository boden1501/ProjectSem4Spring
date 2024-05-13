package exam.spring.demo.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
			return "redirect/:";
		}
		return "/auth/login";
	}

	@PostMapping("/chklogin")
	public String loginCHK(@RequestParam("usr") String username, @RequestParam("pwd") String password,
			HttpServletRequest request, HttpSession session) {
		Logger log = Logger.getGlobal();
		log.info(username + " " + password);
		List<User> dataList = usrRepository.findAll();
		String url = (String) request.getSession().getAttribute("URL");
		System.out.println("URL:" + url);
		for (User usr : dataList) {
			if (username.equals(usr.getUsername()) && password.equals(usr.getPassword())) {
				System.out.println("name:" + usr.getName());
				System.out.println("name:" + usr.getAvatar());
				request.getSession().setAttribute("usrList", usr);
				if (url == null) {
					return "redirect:/";
				}
				else {
					return "redirect:"+url;
				}
			}

		}
		return "redirect:/auth/login";

	}

	@GetMapping("/chklogout")
	public String logoutCHK(Model model, HttpServletRequest request) {
		request.getSession().removeAttribute("usrList");
		return "redirect:/";
	}

	@GetMapping("/register")
	public String register() {
		return "/auth/register";
	}

	@PostMapping("/register")
	public String saveUser(@Validated User user, Model model,RedirectAttributes redirectAttributes) {
		List<User> dataList = usrRepository.findAll();
		for (User usr : dataList) {
			if (usr.getName() == user.getName()) {
				redirectAttributes.addFlashAttribute("errorName","This username has been taken, please choose another one!");
				return "redirect:/register";
			}
			if (usr.getEmail() == user.getEmail()) {
				redirectAttributes.addFlashAttribute("errorEmail","This email has been taken, please choose another one!");
				return "redirect:/register";
			}
		}
		usrRepository.registerUser(user);
		return "redirect:/auth/login";
	}
}
