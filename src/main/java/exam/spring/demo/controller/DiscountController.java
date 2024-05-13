package exam.spring.demo.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
	@GetMapping("/discountView")
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
	@PutMapping("/discountAdd")
	public String addDiscoutn(@RequestParam("idProduct") int idProduct,@RequestParam("idDiscount") int idDiscount) {
		System.out.println("id Dis:"+idDiscount);
		productRepository.update(idDiscount, idProduct);
		return "redirect:/admin/discountView?id="+idProduct;
	}
	@GetMapping("/discount")
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
	@GetMapping("/discountUpdate")
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
	@PutMapping("/updateDiscount")
	public String updateBrand(@Validated Discount item) {
		discountRepository.update(item);
		return "redirect:/admin/discount";
	}
	@CrossOrigin
	@PostMapping("/submitDiscount")
	public String saveBrand(@Validated Discount item, Model model) {
		discountRepository.insert(item);
		return "redirect:/admin/discount";
	}
}
