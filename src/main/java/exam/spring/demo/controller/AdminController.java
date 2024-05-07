package exam.spring.demo.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import exam.spring.demo.model.Category;
import exam.spring.demo.model.Discount;
import exam.spring.demo.model.Product;
import exam.spring.demo.model.User;
import exam.spring.demo.repositories.CategoryRepository;
import exam.spring.demo.repositories.DiscountRepository;
import exam.spring.demo.repositories.ProductRepository;
import exam.spring.demo.repositories.UserRepository;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	UserRepository usrRepository;
	@Autowired
	CategoryRepository cateRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	DiscountRepository discountRepository;
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String indexAdmin(Model model,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
		int offset=page*size;
		List<Product> dataList = productRepository.findAll(offset,size);
		int totalRows = productRepository.getTotalRows();
		int totalPages = (int) Math.ceil((double) totalRows / size);
		int row = 0;
		if (page > 1) {
			row = offset - size;
		}
		List<Discount> discountList = discountRepository.findDiscountAll();
		model.addAttribute("discountList",discountList);
		model.addAttribute("dataList", dataList);
		model.addAttribute("row", row);
		model.addAttribute("totalRows", totalRows);
		model.addAttribute("totalPages", totalPages); // Thêm totalPages vào model
		model.addAttribute("currentPage", page);
		return "ad_layout/indexAdmin";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginfrm(Model model, HttpServletRequest request) {
		Object acc = request.getSession().getAttribute("myaccAD");
		if (acc != null) {
			return "redirect:/admin";
		}
		return "ad_layout/loginAdmin";
	}

	@RequestMapping(value = "/chklogin", method = RequestMethod.POST)
	public String loginCHK(@RequestParam("usrad") String username, @RequestParam("pwdad") String password,
			HttpServletRequest request) {
		Logger log = Logger.getGlobal();
		log.info(username + " " + password);
		List<User> dataList = usrRepository.findAll();
		for (User usr : dataList) {
			if(usr.getRole()==1) {
				if (username.equals(usr.getUsername())
						|| username.equals(usr.getEmail()) && password.equals(usr.getPassword())) {
					request.getSession().setAttribute("myaccAD", username);
					return "redirect:/admin";
				}

				
			}

		}
		return "redirect:/admin/login";

	}

	@RequestMapping(value = "/chklogout", method = RequestMethod.GET)
	public String logoutCHK(Model model, HttpServletRequest request) {
		request.getSession().removeAttribute("myaccAD");
		return "redirect:/admin/login";
	}

	

	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public String categoryAdmin(Model model, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		// Tính toán offset dựa trên trang và kích thước trang
		int offset = page * size;
		// Lấy danh sách các danh mục từ repository
		List<Category> dataList = cateRepository.findAll(offset, size);
		// Lấy tổng số lượng danh mục trong cơ sở dữ liệu để tính toán phân trang
		int totalRows = cateRepository.getTotalRows();
		// Tính tổng số trang dựa trên số lượng danh mục và kích thước trang
		int totalPages = (int) Math.ceil((double) totalRows / size);
		// Truyền danh sách, tổng số lượng danh mục, số trang hiện tại và tổng số trang
		// vào model
		int row = 0;
		if (page > 1) {
			row = offset - size;
		}
		model.addAttribute("dataList", dataList);
		model.addAttribute("row", row);
		model.addAttribute("totalRows", totalRows);
		model.addAttribute("totalPages", totalPages); // Thêm totalPages vào model
		model.addAttribute("currentPage", page);
		return "ad_layout/categoryAdmin";
	}

	@CrossOrigin
	@RequestMapping(value = "/categoriesUpdate", method = RequestMethod.GET)
	public String getCategory(@RequestParam("id") int id, Model model) {
		Category findCate = cateRepository.findById(id);
		model.addAttribute("findCate", findCate);

		List<Category> dataList = cateRepository.findAll(0, 5);
		model.addAttribute("dataList", dataList);

		int totalRows = cateRepository.getTotalRows();
		int totalPages = (int) Math.ceil((double) totalRows / 5);
		model.addAttribute("totalRows", totalRows);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("row", 0);
		model.addAttribute("currentPage", 0);

		return "ad_layout/categoryAdmin";
	}


	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public String updateCategory(RedirectAttributes redirectAttributes,@Validated Category item) {
		List<Category> itemList=cateRepository.findCategoryAll();
		for(Category cate:itemList) {
			String name = item.getName().trim();
			
			if(cate.getName().equalsIgnoreCase(name)) {
				System.out.println("Hello");
				redirectAttributes.addFlashAttribute("message","Name already exixts!");
				return "redirect:/admin/category";
			}
		}
		System.out.println("VInh");
		cateRepository.update(item);

		return "redirect:/admin/category";
	}

	@CrossOrigin
	@RequestMapping(value = "/submitCategory", method = RequestMethod.POST)
	public String saveCategory(@Validated Category item, Model model,RedirectAttributes redirectAttributes) {
		try {
			cateRepository.insert(item);
			String errorMessage = "Add Successfully.";
			redirectAttributes.addFlashAttribute("success", errorMessage);
		} catch (DataIntegrityViolationException e) {
			// Xử lý ngoại lệ khi cột active có giá trị NULL
			String errorMessage = "Name already exixts!";
			redirectAttributes.addFlashAttribute("message", errorMessage);
		}

		return "redirect:/admin/category";
	}

}

