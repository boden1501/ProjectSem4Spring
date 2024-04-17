package exam.spring.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import exam.spring.demo.model.Brand;
import exam.spring.demo.model.Discount;
import exam.spring.demo.model.Product;
import exam.spring.demo.repositories.DiscountRepository;
import exam.spring.demo.repositories.ProductRepository;

@Controller
@RequestMapping("/admin")
public class DiscountController {
	@Autowired
	DiscountRepository discountRepository;
	@Autowired
	ProductRepository productRepository;
	@RequestMapping(value = "/discountView", method = RequestMethod.GET)
	public String index(@RequestParam("id") int id,Model model, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		int offset = page * size;
		Product product=productRepository.findById(id);
		model.addAttribute("product",product);
		List<Discount> discountList = discountRepository.findAll(offset, size);
		int totalRows = discountRepository.getTotalRows();

		int totalPages = (int) Math.ceil((double) totalRows / size);

		int row = 0;
		if (page > 1) {
			row = offset - size;
		}

		model.addAttribute("discountList", discountList);
		model.addAttribute("row", row);
		model.addAttribute("totalRows", totalRows);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", page);
		return "ad_layout/discountView";
	}
	@RequestMapping(value="/discountAdd",method=RequestMethod.PUT)
	public String addDiscoutn(@RequestParam("idProduct") int idProduct,@RequestParam("idDiscount") int idDiscount) {
		System.out.println("id Dis:"+idDiscount);
		productRepository.update(idDiscount, idProduct);
		return "redirect:/admin/discountView?id="+idProduct;
	}
	@RequestMapping(value = "/discount", method = RequestMethod.GET)
	public String indexDiscount(Model model, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		int offset = page * size;
		List<Discount> discountList = discountRepository.findAll(offset, size);
		int totalRows = discountRepository.getTotalRows();

		int totalPages = (int) Math.ceil((double) totalRows / size);

		int row = 0;
		if (page > 1) {
			row = offset - size;
		}
		
		model.addAttribute("discountList", discountList);
		model.addAttribute("row", row);
		model.addAttribute("totalRows", totalRows);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("currentPage", page);
		return "ad_layout/discountAdmin";
	}

	@CrossOrigin
	@RequestMapping(value = "/discountUpdate", method = RequestMethod.GET)
	public String getBrand(@RequestParam("id") int id, Model model) {
		Discount findDiscount = discountRepository.findById(id);
		model.addAttribute("findDiscount", findDiscount);

		List<Discount> DiscountList = discountRepository.findAll(0, 5);
		model.addAttribute("discountList", DiscountList);

		int totalRows = discountRepository.getTotalRows();
		int totalPages = (int) Math.ceil((double) totalRows / 5);
		model.addAttribute("totalRows", totalRows);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("row", 0);
		model.addAttribute("currentPage", 0);

		return "ad_layout/discountAdmin";
	}
	@CrossOrigin
	@RequestMapping(value = "/updateDiscount", method = RequestMethod.PUT)
	public String updateBrand(@Validated Discount item) {
		discountRepository.update(item);
		return "redirect:/admin/discount";
	}
	@CrossOrigin
	@RequestMapping(value = "/submitDiscount", method = RequestMethod.POST)
	public String saveBrand(@Validated Discount item, Model model) {
		discountRepository.insert(item);
		return "redirect:/admin/discount";
	}
}
