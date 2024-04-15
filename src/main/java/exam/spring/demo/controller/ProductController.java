package exam.spring.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import exam.spring.demo.model.Brand;
import exam.spring.demo.model.Category;
import exam.spring.demo.model.Image;
import exam.spring.demo.model.ImageInfo;
import exam.spring.demo.model.Product;
import exam.spring.demo.model.TempImages;
import exam.spring.demo.repositories.BrandRepository;
import exam.spring.demo.repositories.CategoryRepository;

import exam.spring.demo.repositories.ImageRepository;
import exam.spring.demo.repositories.ProductRepository;
import exam.spring.demo.service.FilesStorageService;
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
	@Autowired
	FilesStorageService storageService;

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createProduct(Model model) {
		List<Category> categoryList = cateRepository.findCategoryAll();
		List<Brand> brandList = brandRepository.findBrandAll();
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("brandList", brandList);
		return "ad_layout/createAdmin";
	}

	@RequestMapping(value = "/findProduct", method = RequestMethod.POST)
	public String findProduct(@RequestParam("txtSearch") String txtSearch, Model model) {
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
	public String imageProduct(RedirectAttributes redirectAttributes, @RequestParam("id") int id, HttpSession session,
			Model model) {
		Product findProduct = productRepository.findById(id);
		List<Image> listImg=imgRepository.findListImgByID(id);
		int mainCheck=1;
		for(Image img:listImg) {
			if(img.getMain()==1) {
				model.addAttribute("mainCheck",mainCheck);
			}
		}
		session.setAttribute("Product", findProduct);
		model.addAttribute("findProduct", findProduct);
		model.addAttribute("listImg", listImg);
		List<ImageInfo> imageInfos = new ArrayList<>();

		// Kiểm tra xem có dữ liệu hình ảnh không trước khi loadAll()
		if (storageService.loadAll().count() > 0) {
			imageInfos = storageService.loadAll().map(imageData -> {
				if (imageData != null) {
					String filename = imageData.getFilename();
					String url = MvcUriComponentsBuilder
							.fromMethodName(ProductController.class, "getImage", imageData.getFilename()).build()
							.toString();
					byte[] data = imageData.getData();
					return new ImageInfo(filename, url, data);
				} else {
					return null; // Hoặc một giá trị mặc định khác nếu cần
				}
			}).filter(Objects::nonNull) // Loại bỏ các phần tử null
					.collect(Collectors.toList());
		}

		model.addAttribute("images", imageInfos);

//	    storageService.deleteAll();
		redirectAttributes.addFlashAttribute("id", id);
		return "ad_layout/imageProduct";
	}

	@RequestMapping(value = "/addImages", method = RequestMethod.POST)
	public String addProduct(@RequestParam("img") MultipartFile images, RedirectAttributes redirectAttributes,
			@RequestParam("id") int id) {
		try {
			storageService.save(images);
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("id" + id);
//		redirectAttributes.addFlashAttribute("id",id);
		return "redirect:/admin/images?id=" + id;
	}

	@RequestMapping(value = "/images/{filename:.+}", method = RequestMethod.GET)
	public ResponseEntity<Resource> getImage(@PathVariable String filename) throws IOException {
		Resource file = storageService.load(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@RequestMapping(value = "/images/delete/{filename:.+}", method = RequestMethod.GET)
	public String deleteImage(@PathVariable String filename, Model model, RedirectAttributes redirectAttributes,
			@RequestParam("id") int id) {
		try {
			boolean existed = storageService.delete(filename);

			if (existed) {
				redirectAttributes.addFlashAttribute("message", "Delete the image successfully: " + filename);
			} else {
				redirectAttributes.addFlashAttribute("message", "The image does not exist!");
			}
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("message",
					"Could not delete the image: " + filename + ". Error: " + e.getMessage());
		}

		return "redirect:/admin/images?id=" + id;
	}

	@RequestMapping(value = "/saveImage", method = RequestMethod.POST)
	public String saveImage(@RequestParam(value = "mainImg", required = false) String nameImg,RedirectAttributes redirectAttributes, @RequestParam("id") int id) throws IOException {
		String uploadDir = "src/main/resources/static/images/";
		List<ImageInfo> imageInfos = storageService.loadAll().map(imageData -> {
			if (imageData != null && imageData.getData() != null) {
				String filename = imageData.getFilename();
				String url = MvcUriComponentsBuilder
						.fromMethodName(ProductController.class, "getImage", imageData.getFilename()).build()
						.toString();
				byte[] data = imageData.getData();
				return new ImageInfo(filename, url, data);
			}
			return null;
		}).collect(Collectors.toList());
		System.out.println("mainImg:" +nameImg);
		for (ImageInfo img : imageInfos) {
			if (img != null && img.getData() != null) {
				Integer mainImg;
				if (nameImg != null) {
					if (img.getName().equals(nameImg)) {
						mainImg = 1;
					} else {
						mainImg = 0;
					}
				} else {
					mainImg = 0;
				}
				imgRepository.insert(id, mainImg, img.getName());
				Files.write(Paths.get(uploadDir + img.getName()), img.getData());
			}
		}

		// Sau khi đã lưu xong tất cả các hình ảnh, bạn có thể gọi deleteAll()
		storageService.deleteAllFilesInRoot();

		return "redirect:/admin/images?id=" + id;
	}

}
