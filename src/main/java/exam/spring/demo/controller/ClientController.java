package exam.spring.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import exam.spring.demo.model.Brand;
import exam.spring.demo.model.Cart;
import exam.spring.demo.model.Category;
import exam.spring.demo.model.Image;
import exam.spring.demo.model.Product;
import exam.spring.demo.model.User;
import exam.spring.demo.repositories.BrandRepository;
import exam.spring.demo.repositories.CartRepository;
import exam.spring.demo.repositories.CategoryRepository;
import exam.spring.demo.repositories.ImageRepository;
import exam.spring.demo.repositories.ProductRepository;
import exam.spring.demo.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class ClientController {
	@Autowired
	CategoryRepository cateRepository;
	@Autowired
	BrandRepository brandRepository;
	@Autowired
	ProductRepository proRepository;
	@Autowired
	ImageRepository imgRepository;
	@Autowired
	UserRepository usrRepository;
	@Autowired
	CartRepository cartRepository;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request, HttpSession session) {
		List<Category> cateList = cateRepository.findCategoryAll();
		List<Brand> brandList = brandRepository.findBrandAll();
		model.addAttribute("cateList", cateList);
		model.addAttribute("brandList", brandList);
		return "client_layout/indexClient";
	}

	@RequestMapping(value = "/product", method = RequestMethod.GET)
	public String product(Model model) {
		List<Product> proList = proRepository.findProductAll();
		List<Image> imgList = imgRepository.findImgAll();
		model.addAttribute("proList", proList);
		model.addAttribute("imgList", imgList);
		return "client_layout/productClient";
	}

	@RequestMapping(value = "/product/detail", method = RequestMethod.GET)
	public String productDetail(@RequestParam("id") int id, Model model, HttpSession session) {
		User usr = (User) session.getAttribute("usrList");
		if (usr == null) {
			Product findProduct = proRepository.findById(id);
			model.addAttribute("findProduct", findProduct);
			List<Product> proList = proRepository.findProductAll();
			List<Image> imgList = imgRepository.findImgAll();
			List<Image> imgProduct = imgRepository.findListImgByID(id);
			model.addAttribute("proList", proList);
			model.addAttribute("imgList", imgList);
			model.addAttribute("imgProduct", imgProduct);
			model.addAttribute("message", "Your cart is currently empty!");
		} else {
			Product findProduct = proRepository.findById(id);
			model.addAttribute("findProduct", findProduct);
			List<Product> proList = proRepository.findProductAll();
			List<Image> imgList = imgRepository.findImgAll();
			List<Image> imgProduct = imgRepository.findListImgByID(id);
			model.addAttribute("proList", proList);
			model.addAttribute("imgList", imgList);
			model.addAttribute("imgProduct", imgProduct);

			List<Cart> cartList = cartRepository.loadAllByID(usr.getId());
			model.addAttribute("cartList", cartList);

		}
		return "client_layout/detail";
	}

	@RequestMapping(value = "/product/addCart", method = RequestMethod.POST)
	public String addCart(@RequestParam("id") int id, @RequestParam("quantity") int quantity, HttpSession session) {

		User usr = (User) session.getAttribute("usrList");
		if (usr == null) {
			return "redirect:/auth/login";
		}
		List<Cart> cartList = cartRepository.loadAllByID(usr.getId());
		if (!cartList.isEmpty()) {
			for (Cart cart : cartList) {
				if (cart.getIdProduct() == id) {
					System.out.println("Hello");
					int quantityTemp = quantity + cart.getQuantity();
					cartRepository.updateQuantiy(quantityTemp, id);
					return "redirect:/product/detail?id=" + id;
				}
			}
			System.out.println("Vinh1");
			cartRepository.insert(id, quantity, usr.getId());
			return "redirect:/product/detail?id=" + id;
	
		} else {
			System.out.println("Vinh");
			cartRepository.insert(id, quantity, usr.getId());
			
		}
		return "redirect:/product/detail?id=" + id;
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String profile(HttpSession session, Model model) {
		User usr = (User) session.getAttribute("usrList");
		model.addAttribute("usr", usr);
		return "client_layout/profileClient";
	}

	@RequestMapping(value = "/editProfile", method = RequestMethod.GET)
	public String editProfile(HttpSession session, Model model) {
		User usr = (User) session.getAttribute("usrList");
		model.addAttribute("usr", usr);
		return "client_layout/editProfileClient";
	}

	@RequestMapping(value = "/updateProfile", method = RequestMethod.PUT)
	public String updateProfile(@Validated User usr, HttpServletRequest request,
			@RequestParam(value = "imgAvt", required = false) MultipartFile imgAvt)
			throws IllegalStateException, IOException {

		if (!imgAvt.isEmpty()) {
			String uploadDir = "src/main/resources/static/client/images/";
			String fileName = imgAvt.getOriginalFilename();
			System.out.println("Hello");
			Files.write(Paths.get(uploadDir + fileName), imgAvt.getBytes());
			usr.setAvatar(imgAvt.getOriginalFilename());
		}
		usrRepository.updateProfile(usr);
		request.getSession().setAttribute("usrList", usr);
		return "redirect:/profile";
	}
}
