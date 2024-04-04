package exam.spring.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import exam.spring.demo.model.Brand;
import exam.spring.demo.model.Category;
import exam.spring.demo.model.Image;

import exam.spring.demo.model.Product;
import exam.spring.demo.model.TempImages;
import exam.spring.demo.repositories.BrandRepository;
import exam.spring.demo.repositories.CategoryRepository;

import exam.spring.demo.repositories.ImageRepository;
import exam.spring.demo.repositories.ProductRepository;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class ProductController {
	@Autowired
	BrandRepository brandRepository;
	@Autowired
	CategoryRepository cateRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	ImageRepository imgRepository;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createProduct(Model model) {
		List<Category> categoryList = cateRepository.findCategoryAll();
		List<Brand> brandList = brandRepository.findBrandAll();
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("brandList", brandList);
		return "ad_layout/createAdmin";
	}
	@RequestMapping(value="/findProduct",method=RequestMethod.POST)
	public String findProduct(@RequestParam("txtSearch") String txtSearch,Model model) {
		List<Product> productList = productRepository.findByName(txtSearch); 
		model.addAttribute("dataList", productList);
		return "ad_layout/indexAdmin";
	}
	@CrossOrigin
	@RequestMapping(value = "/createProduct", method = RequestMethod.POST)
	public String createProduct(@Validated Product item) {
		productRepository.insert(item);
		return "redirect:/admin/create";
	}

	@RequestMapping(value = "/images", method = RequestMethod.GET)
	public String imageProduct(@RequestParam("id") int id, HttpSession session, Model model) {
		Product findProduct = productRepository.findById(id);
		session.setAttribute("Product", findProduct);
		model.addAttribute("findProduct", findProduct);
		session.removeAttribute("imgList");
		return "ad_layout/imageProduct";
	}

	@ModelAttribute("uploadedFiles")
	public List<TempImages> initUploadedFiles() {
		return new ArrayList<>();
	}

	@RequestMapping(value = "/addImages", method = RequestMethod.POST)
	public String addProduct(@RequestParam("img") List<MultipartFile> images, HttpSession session,
			@ModelAttribute("uploadedFiles") List<TempImages> uploadedFiles) {
		List<TempImages> currentImageList = (List<TempImages>) session.getAttribute("imgList");
		if (currentImageList == null) {
			currentImageList = new ArrayList<>();
		}
		for (MultipartFile cuFile : images) {
			try {
				if (!cuFile.isEmpty()) {
					currentImageList.add(new TempImages(cuFile.getOriginalFilename(), cuFile.getBytes()));
				}
			} catch (IOException e) {
				// Handle exception
			}
		}

		session.setAttribute("imgList", currentImageList);

		return "redirect:/admin/imagesLoad";
	}

	@RequestMapping(value = "/imagesLoad", method = RequestMethod.GET)
	public String imageProduct(Model model, HttpSession session,RedirectAttributes redirectAttributes) {
		Product findProduct = (Product) session.getAttribute("Product");
		model.addAttribute("findProduct", findProduct);
		List<MultipartFile> imageList = (List<MultipartFile>) session.getAttribute("imgList");
		model.addAttribute("imgList", imageList);
		redirectAttributes.addAttribute("id", findProduct.getIdProduct());
		return "ad_layout/imageProduct";
	}

	@RequestMapping(value = "/saveImage", method = RequestMethod.POST)
	public String saveImage(@RequestParam(value = "mainImg", required = false) String nameImg, HttpSession session,
			@ModelAttribute("uploadedFiles") List<TempImages> upLoadFile, Model model,
			RedirectAttributes redirectAttributes) {
		String uploadDir = "src/main/resources/static/images/";
		List<TempImages> currentImageList = (List<TempImages>) session.getAttribute("imgList");
		Product findProduct = (Product) session.getAttribute("Product");
		model.addAttribute("findProduct", findProduct);
		if (currentImageList != null) {
			for (TempImages img : currentImageList) {
				try {
					String fileName = img.getFileName();
					byte[] fileData = img.getData();
					Integer mainImg;
					if (nameImg != null) {
						if (fileName.equals(nameImg)) {
							mainImg = 1;
						} else {
							mainImg = 0;
						}
					} else {
						mainImg = 0;
					}
					System.out.println("name:" + fileName);
					System.out.println("PATH:" + Paths.get(uploadDir + fileName));
					Files.write(Paths.get(uploadDir + fileName), fileData);
					imgRepository.insert(findProduct.getIdProduct(), mainImg, fileName);
					System.out.println("Thanh cong");
					session.removeAttribute("imgList");
				} catch (IOException e) {
					// Xử lý ngoại lệ nếu cần
				}
			}
		}

		redirectAttributes.addAttribute("id", findProduct.getIdProduct());
		return "redirect:/admin/images";
	}

}
