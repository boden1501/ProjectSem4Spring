package exam.spring.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import exam.spring.demo.model.Brand;
import exam.spring.demo.repositories.BrandRepository;


@Controller
@RequestMapping("/admin")
public class BrandController {
	@Autowired
	BrandRepository brandRepository;

	@GetMapping("/brand")
	public String indexBrand(Model model, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		int offset = page * size;
		List<Brand> brandList = brandRepository.findAll(offset, size);

		int totalRows = brandRepository.getTotalRows();

		int totalPages = (int) Math.ceil((double) totalRows / size);

		int row = 0;
		if (page > 1) {
			row = offset - size;
		}
		model.addAttribute("brandList", brandList);
		model.addAttribute("row", row);
		model.addAttribute("totalRows", totalRows);
		model.addAttribute("totalPages", totalPages); 
		model.addAttribute("currentPage", page);

		return "ad_layout/brandAdmin";
	}

	@CrossOrigin
	@GetMapping("/brandsUpdate")
	public String getBrand(@RequestParam("id") int id, Model model) {
		Brand findCate = brandRepository.findById(id);
		model.addAttribute("findBrand", findCate);

		List<Brand> brandList = brandRepository.findAll(0, 5);
		model.addAttribute("brandList", brandList);

		int totalRows = brandRepository.getTotalRows();
		int totalPages = (int) Math.ceil((double) totalRows / 5);
		model.addAttribute("totalRows", totalRows);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("row", 0);
		model.addAttribute("currentPage", 0);

		return "ad_layout/brandAdmin";
	}

	@CrossOrigin
	@PutMapping("/updateBrand")
	public String updateBrand(@Validated Brand item,RedirectAttributes redirectAttributes) {
		List<Brand> branList = brandRepository.findBrandAll();
		String name=item.getName_brand().trim();
		for(Brand brand:branList) {
			if(brand.getName_brand().equalsIgnoreCase(name)) {
				redirectAttributes.addFlashAttribute("message","Name already exixts!");
				return "redirect:/admin/brand";
			}
		}
		brandRepository.update(item);
		return "redirect:/admin/brand";
	}

	@CrossOrigin
	@PostMapping("/submitBrand")
	public String saveBrand(@Validated Brand item, Model model,RedirectAttributes redirectAttributes) {
		try {
			brandRepository.insert(item);
			String errorMessage = "Add Successfully.";
			redirectAttributes.addFlashAttribute("success", errorMessage);
		} catch (DataIntegrityViolationException e) {
			// Xử lý ngoại lệ khi cột active có giá trị NULL
			String errorMessage = "Name already exixts!";
			redirectAttributes.addFlashAttribute("message", errorMessage);
		}

		return "redirect:/admin/brand";
	}
}
