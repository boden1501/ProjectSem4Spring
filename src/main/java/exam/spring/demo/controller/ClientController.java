package exam.spring.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import exam.spring.demo.model.Brand;
import exam.spring.demo.model.Category;
import exam.spring.demo.model.Image;
import exam.spring.demo.model.Product;
import exam.spring.demo.model.User;
import exam.spring.demo.repositories.BrandRepository;
import exam.spring.demo.repositories.CategoryRepository;
import exam.spring.demo.repositories.ImageRepository;
import exam.spring.demo.repositories.ProductRepository;
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
	@RequestMapping(value = "",method = RequestMethod.GET)
	public String index(Model model,HttpServletRequest request,HttpSession session) {
		Object acc = request.getSession().getAttribute("myacc");
		String nameUsr= (String)session.getAttribute("nameUsr");
//		System.out.println("size:"+usrList.size());
		model.addAttribute("acc",acc);
		model.addAttribute("nameUsr",nameUsr);
		List<Category> cateList=cateRepository.findCategoryAll();
		List<Brand> brandList=brandRepository.findBrandAll();
		model.addAttribute("cateList",cateList);
		model.addAttribute("brandList",brandList);
		return "client_layout/indexClient";
	}
	@RequestMapping(value="/product",method=RequestMethod.GET)
	public String product(Model model) {
		List<Product> proList=proRepository.findProductAll();
		List<Image> imgList=imgRepository.findImgAll();
		model.addAttribute("proList",proList);
		model.addAttribute("imgList",imgList);
		return "client_layout/productClient";
	}
}
