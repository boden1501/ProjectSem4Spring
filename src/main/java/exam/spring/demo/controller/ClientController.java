package exam.spring.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import exam.spring.demo.model.Brand;
import exam.spring.demo.model.Cart;
import exam.spring.demo.model.Category;
import exam.spring.demo.model.Comment;
import exam.spring.demo.model.Discount;
import exam.spring.demo.model.Image;
import exam.spring.demo.model.Product;
import exam.spring.demo.model.User;
import exam.spring.demo.repositories.BrandRepository;
import exam.spring.demo.repositories.CartRepository;
import exam.spring.demo.repositories.CategoryRepository;
import exam.spring.demo.repositories.CommentRepository;
import exam.spring.demo.repositories.DiscountRepository;
import exam.spring.demo.repositories.ImageRepository;
import exam.spring.demo.repositories.IndexRepository;
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
	@Autowired
	DiscountRepository discountRepository;
	@Autowired
	CommentRepository commentRepository;
	@Autowired
	IndexRepository indexRepository;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request,HttpSession session) {
		User usr = (User) session.getAttribute("usrList");
		String URL=request.getRequestURI();
		request.getSession().setAttribute("URL",URL);
		List<Category> cateList = cateRepository.findCategoryAll();
		List<Discount> discount = discountRepository.findDiscountAll();
		List<Brand> brandList = brandRepository.findBrandAll();
		List<Product> proList = indexRepository.findBestSeller();
		List<Image> imgList = imgRepository.findImgAll();
		for (Product pro : proList) {

			for (Image img : imgList) {
				if (pro.getIdProduct() == img.getIdProduct() && img.getMain() != 0) {
					pro.setImg(img.getImage());
					model.addAttribute("proList", proList);
				}

			}
			
			for (Discount dis : discount) {
				if (pro.getIdDiscount() == dis.getIdDiscount()) {
					double discountTemp = (double) pro.getPriceTemp() * dis.getPercentDiscount();
					double price = pro.getPriceTemp() - discountTemp;
					DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
					String formattedTotal = decimalFormat.format(price);
					String formattedDiscount=decimalFormat.format(discountTemp);
					pro.setPriceDiscount(formattedTotal);
					pro.setDiscount(formattedDiscount);
					model.addAttribute("proList", proList);
				}
			}
		}
		model.addAttribute("proList",proList);
		model.addAttribute("cateList", cateList);
		model.addAttribute("brandList", brandList);
		
		if (usr == null) {
			
			model.addAttribute("message", "Please login!");
			return "client_layout/indexClient";
		} else {

			List<Cart> cartList = cartRepository.loadAllByID(usr.getId());
			for(Cart cart:cartList) {
				for(Product pro: proList) {
					if(pro.getIdProduct()==cart.getIdProduct()) {
						cart.setPriceDiscount(pro.getPriceDiscount());
						cart.setDiscount(pro.getDiscount());
						model.addAttribute("cartList", cartList);
						session.setAttribute("cart", cartList);
					}
				}
			}
			int countCart = cartRepository.getNum(usr.getId());
			if (countCart == 0) {
				model.addAttribute("message", "Your cart is currently empty!");
			}
			model.addAttribute("cartList", cartList);
			session.setAttribute("cart", cartList);
			model.addAttribute("countCart", countCart);
			return "client_layout/indexClient";
		}
	}

	@RequestMapping(value = "/product", method = RequestMethod.GET)
	public String product(Model model, HttpSession session, HttpServletRequest request) {
		User usr = (User) session.getAttribute("usrList");
		String URL=request.getRequestURI();
		request.getSession().setAttribute("URL",URL);
		List<Discount> discount = discountRepository.findDiscountAll();
		List<Product> proList = proRepository.findProductActive();
		List<Image> imgList = imgRepository.findImgAll();
		List<Category> cateList = cateRepository.findCategoryAll();
		List<Brand> brandList = brandRepository.findBrandAll();
		for (Product pro : proList) {

			for (Image img : imgList) {
				if (pro.getIdProduct() == img.getIdProduct() && img.getMain() != 0) {
					pro.setImg(img.getImage());
					System.out.println("img:" + pro.getImg());
					model.addAttribute("proList", proList);
				}

			}
			
			for (Discount dis : discount) {
				if (pro.getIdDiscount() == dis.getIdDiscount()) {
					double discountTemp = (double) pro.getPriceTemp() * dis.getPercentDiscount();
					double price = pro.getPriceTemp() - discountTemp;
					DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
					String formattedTotal = decimalFormat.format(price);
					String formattedDiscount=decimalFormat.format(discountTemp);
					pro.setPriceDiscount(formattedTotal);
					pro.setDiscount(formattedDiscount);
					model.addAttribute("proList", proList);
				}
			}
		}
		
		model.addAttribute("cateList", cateList);
		model.addAttribute("brandList", brandList);

		model.addAttribute("imgList", imgList);
		if (usr == null) {
			model.addAttribute("message", "Please login!");
			return "client_layout/productClient";
		} else {
			List<Cart> cartList = cartRepository.loadAllByID(usr.getId());
			for(Cart cart:cartList) {
				for(Product pro: proList) {
					if(pro.getIdProduct()==cart.getIdProduct()) {
						cart.setPriceDiscount(pro.getPriceDiscount());
						System.out.println("price:"+cart.getPriceDiscount());
						cart.setDiscount(pro.getDiscount());
						model.addAttribute("cartList", cartList);
						session.setAttribute("cart", cartList);
					}
				}
			}
			int countCart = cartRepository.getNum(usr.getId());
			if (countCart == 0) {
				model.addAttribute("message", "Your cart is currently empty!");
			}
			model.addAttribute("cartList", cartList);
			session.setAttribute("cart", cartList);
			model.addAttribute("countCart", countCart);
			return "client_layout/productClient";
		}
	}
	@RequestMapping(value = "/product/brand", method = RequestMethod.GET)
	public String productBrand(@RequestParam("id") int id,Model model, HttpSession session, HttpServletRequest request) {
		User usr = (User) session.getAttribute("usrList");
		String URL=request.getRequestURI();
		request.getSession().setAttribute("URL",URL);
		List<Discount> discount = discountRepository.findDiscountAll();
		List<Product> proList = proRepository.findByBrand(id);
		List<Image> imgList = imgRepository.findImgAll();
		List<Category> cateList = cateRepository.findCategoryAll();
		List<Brand> brandList = brandRepository.findBrandAll();
		for (Product pro : proList) {

			for (Image img : imgList) {
				if (pro.getIdProduct() == img.getIdProduct() && img.getMain() != 0) {
					pro.setImg(img.getImage());
					System.out.println("img:" + pro.getImg());
					model.addAttribute("proList", proList);
				}

			}
			
			for (Discount dis : discount) {
				if (pro.getIdDiscount() == dis.getIdDiscount()) {
					double discountTemp = (double) pro.getPriceTemp() * dis.getPercentDiscount();
					double price = pro.getPriceTemp() - discountTemp;
					DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
					String formattedTotal = decimalFormat.format(price);
					String formattedDiscount=decimalFormat.format(discountTemp);
					pro.setPriceDiscount(formattedTotal);
					pro.setDiscount(formattedDiscount);
					model.addAttribute("proList", proList);
				}
			}
		}
		
		model.addAttribute("cateList", cateList);
		model.addAttribute("brandList", brandList);

		model.addAttribute("imgList", imgList);
		if (usr == null) {
			model.addAttribute("message", "Please login!");
			return "client_layout/productClient";
		} else {
			List<Cart> cartList = cartRepository.loadAllByID(usr.getId());
			for(Cart cart:cartList) {
				for(Product pro: proList) {
					if(pro.getIdProduct()==cart.getIdProduct()) {
						cart.setPriceDiscount(pro.getPriceDiscount());
						System.out.println("price:"+cart.getPriceDiscount());
						cart.setDiscount(pro.getDiscount());
						model.addAttribute("cartList", cartList);
					}
				}
			}
			int countCart = cartRepository.getNum(usr.getId());
			if (countCart == 0) {
				model.addAttribute("message", "Your cart is currently empty!");
			}
			model.addAttribute("cartList", cartList);
			model.addAttribute("countCart", countCart);
			return "client_layout/productClient";
		}
	}
	@RequestMapping(value = "/product/category", method = RequestMethod.GET)
	public String productCategory(@RequestParam("id") int id,Model model, HttpSession session, HttpServletRequest request) {
		User usr = (User) session.getAttribute("usrList");
		String URL=request.getRequestURI();
		request.getSession().setAttribute("URL",URL);
		List<Discount> discount = discountRepository.findDiscountAll();
		List<Product> proList = proRepository.findByCategory(id);
		List<Image> imgList = imgRepository.findImgAll();
		List<Category> cateList = cateRepository.findCategoryAll();
		List<Brand> brandList = brandRepository.findBrandAll();
		for (Product pro : proList) {

			for (Image img : imgList) {
				if (pro.getIdProduct() == img.getIdProduct() && img.getMain() != 0) {
					pro.setImg(img.getImage());
					System.out.println("img:" + pro.getImg());
					model.addAttribute("proList", proList);
				}

			}
			
			for (Discount dis : discount) {
				if (pro.getIdDiscount() == dis.getIdDiscount()) {
					double discountTemp = (double) pro.getPriceTemp() * dis.getPercentDiscount();
					double price = pro.getPriceTemp() - discountTemp;
					DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
					String formattedTotal = decimalFormat.format(price);
					String formattedDiscount=decimalFormat.format(discountTemp);
					pro.setPriceDiscount(formattedTotal);
					pro.setDiscount(formattedDiscount);
					model.addAttribute("proList", proList);
				}
			}
		}
		
		model.addAttribute("cateList", cateList);
		model.addAttribute("brandList", brandList);

		model.addAttribute("imgList", imgList);
		if (usr == null) {
			model.addAttribute("message", "Please login!");
			return "client_layout/productClient";
		} else {
			List<Cart> cartList = cartRepository.loadAllByID(usr.getId());
			for(Cart cart:cartList) {
				for(Product pro: proList) {
					if(pro.getIdProduct()==cart.getIdProduct()) {
						cart.setPriceDiscount(pro.getPriceDiscount());
						System.out.println("price:"+cart.getPriceDiscount());
						cart.setDiscount(pro.getDiscount());
						model.addAttribute("cartList", cartList);
					}
				}
			}
			int countCart = cartRepository.getNum(usr.getId());
			if (countCart == 0) {
				model.addAttribute("message", "Your cart is currently empty!");
			}
			model.addAttribute("cartList", cartList);
			model.addAttribute("countCart", countCart);
			return "client_layout/productClient";
		}
	}
	@RequestMapping(value = "/product/detail", method = RequestMethod.GET)
	public String productDetail(@RequestParam("id") int id, Model model, HttpSession session,
			HttpServletRequest request,RedirectAttributes redirectAttributes) {
		User usr = (User) session.getAttribute("usrList");
		String URL=request.getRequestURI()+"?id="+id;
		request.getSession().setAttribute("URL",URL);
		Product findProduct = proRepository.findById(id);
		List<Product> proList = proRepository.findProductActive();
		List<Comment> commentList = commentRepository.findById(id);
		model.addAttribute("commentList",commentList);
		model.addAttribute("findProduct", findProduct);
		List<Discount> discount = discountRepository.findDiscountAll();
		for (Product pro : proList) {

			
			for (Discount dis : discount) {
				if (pro.getIdDiscount() == dis.getIdDiscount()) {
					double discountTemp = (double) pro.getPriceTemp() * dis.getPercentDiscount();
					double price = pro.getPriceTemp() - discountTemp;
					DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
					String formattedTotal = decimalFormat.format(price);
					String formattedDiscount=decimalFormat.format(discountTemp);
					pro.setPriceDiscount(formattedTotal);
					pro.setDiscount(formattedDiscount);
					model.addAttribute("proList", proList);
				}
			}
		}
		
		for (Discount dis : discount) {
			if (findProduct.getIdDiscount() == dis.getIdDiscount()) {
				double discountTemp = (double) findProduct.getPriceTemp() * dis.getPercentDiscount();
				double price = findProduct.getPriceTemp() - discountTemp;
				DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
				String formattedTotal = decimalFormat.format(price);
				model.addAttribute("priceDiscount", formattedTotal);
				
			}
		}
		
		
		List<Image> imgList = imgRepository.findImgAll();
		List<Image> imgProduct = imgRepository.findListImgByID(id);

		model.addAttribute("proList", proList);
		model.addAttribute("imgList", imgList);
		model.addAttribute("imgProduct", imgProduct);
		model.addAttribute(imgProduct);
		if (usr == null) {
			model.addAttribute("message", "Please login!");
		} else {
			List<Cart> cartList = cartRepository.loadAllByID(usr.getId());
			for(Cart cart:cartList) {
				for(Product pro: proList) {
					if(pro.getIdProduct()==cart.getIdProduct()) {
						cart.setPriceDiscount(pro.getPriceDiscount());
						System.out.println("price:"+cart.getPriceDiscount());
						cart.setDiscount(pro.getDiscount());
						model.addAttribute("cartList", cartList);
					}
				}
			}
			int countCart = cartRepository.getNum(usr.getId());
			if (countCart == 0) {
				model.addAttribute("message", "Your cart is currently empty!");
			}
			model.addAttribute("cartList", cartList);
			model.addAttribute("countCart", countCart);
			redirectAttributes.addFlashAttribute("id",id);
		}
		return "client_layout/detail";
	}
	@RequestMapping(value="/chkAddCommnent",method=RequestMethod.POST)
	public String addComment(@RequestParam("id") int id,@RequestParam("content") String content,HttpSession session) {
		User usr = (User) session.getAttribute("usrList");
		commentRepository.insert(usr.getId(), id, content);
		return "redirect:/product/detail?id="+id;
	}
	@RequestMapping(value = "/searchProduct",method=RequestMethod.POST)
	public String searchProduct(@RequestParam("search") String search,Model model, HttpSession session,
			HttpServletRequest request,RedirectAttributes redirectAttributes) {
		User usr = (User) session.getAttribute("usrList");
		String URL=request.getRequestURI();
		request.getSession().setAttribute("URL",URL);
		List<Discount> discount = discountRepository.findDiscountAll();
		List<Product> proList = proRepository.findByName(search);
		List<Image> imgList = imgRepository.findImgAll();
		List<Category> cateList = cateRepository.findCategoryAll();
		List<Brand> brandList = brandRepository.findBrandAll();
		for (Product pro : proList) {

			for (Image img : imgList) {
				if (pro.getIdProduct() == img.getIdProduct() && img.getMain() != 0) {
					pro.setImg(img.getImage());
					System.out.println("img:" + pro.getImg());
					model.addAttribute("proList", proList);
				}

			}
			
			for (Discount dis : discount) {
				if (pro.getIdDiscount() == dis.getIdDiscount()) {
					double discountTemp = (double) pro.getPriceTemp() * dis.getPercentDiscount();
					double price = pro.getPriceTemp() - discountTemp;
					DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
					String formattedTotal = decimalFormat.format(price);
					String formattedDiscount=decimalFormat.format(discountTemp);
					pro.setPriceDiscount(formattedTotal);
					pro.setDiscount(formattedDiscount);
					model.addAttribute("proList", proList);
				}
			}
		}
		
		model.addAttribute("cateList", cateList);
		model.addAttribute("brandList", brandList);

		model.addAttribute("imgList", imgList);
		if (usr == null) {
			model.addAttribute("message", "Please login!");
			return "client_layout/productClient";
		} else {
			List<Cart> cartList = cartRepository.loadAllByID(usr.getId());
			for(Cart cart:cartList) {
				for(Product pro: proList) {
					if(pro.getIdProduct()==cart.getIdProduct()) {
						cart.setPriceDiscount(pro.getPriceDiscount());
						System.out.println("price:"+cart.getPriceDiscount());
						cart.setDiscount(pro.getDiscount());
						model.addAttribute("cartList", cartList);
						session.setAttribute("cart", cartList);
					}
				}
			}
			int countCart = cartRepository.getNum(usr.getId());
			if (countCart == 0) {
				model.addAttribute("message", "Your cart is currently empty!");
			}
			model.addAttribute("cartList", cartList);
			session.setAttribute("cart", cartList);
			model.addAttribute("countCart", countCart);
			return "client_layout/productClient";
		}
	}
	@RequestMapping(value="/updateQuantity",method=RequestMethod.POST)
	public String updateQuantity(@RequestParam("cartId") int cartId,
            @RequestParam("newQuantity") int newQuantity,HttpSession session,HttpServletRequest request) {
		String url = (String) request.getSession().getAttribute("URL");
		cartRepository.updateQuantiy(newQuantity, cartId);
		return "redirect:"+url;
	}
	@RequestMapping(value = "/product/addCart", method = RequestMethod.POST)
	public String addCart(@RequestParam("id") int id, @RequestParam("quantity") int quantity, HttpSession session,
			RedirectAttributes redirectAttributes,HttpServletRequest request) {
		String url = (String) request.getSession().getAttribute("URL");
		System.out.println("URL:" + url);
		User usr = (User) session.getAttribute("usrList");
		if (usr == null) {
			return "redirect:/auth/login";
		}
		Product findProduct = proRepository.findById(id);
		List<Cart> cartList = cartRepository.loadAllByID(usr.getId());
		if (quantity > findProduct.getQuantityProduct()) {
			redirectAttributes.addFlashAttribute("messageQuantity", "Not enough quantity in stock");
			return "redirect:" + url;
		}
		if (!cartList.isEmpty()) {
			for (Cart cart : cartList) {
				if (cart.getIdProduct() == id) {
					
					int quantityTemp = quantity + cart.getQuantity();
					System.out.println("quantityTemp:"+quantityTemp);
					cartRepository.updateQuantiyProduct(quantityTemp, id);
					System.out.println("Hello");
					return "redirect:" + url;
				}
			}
			cartRepository.insert(id, quantity, usr.getId());
			return "redirect:" + url;

		} else {
			cartRepository.insert(id, quantity, usr.getId());

		}
		return "redirect:" + url;
	}

	@RequestMapping(value = "/product/delete", method = RequestMethod.DELETE)
	public String deleteCart(@RequestParam("id") int id, @RequestParam("idCart") int idCart,HttpServletRequest request) {
		String url = (String) request.getSession().getAttribute("URL");
		System.out.println("URL:" + url);
		cartRepository.deleteByID(idCart);
		return "redirect:" + url;
	}

	@RequestMapping(value = "/product/deleteProduct", method = RequestMethod.DELETE)
	public String deletePro(@RequestParam("idCart") int idCart,HttpServletRequest request) {
		String url = (String) request.getSession().getAttribute("URL");
		System.out.println("URL:" + url);
		cartRepository.deleteByID(idCart);
		return "redirect:" + url;
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
	
	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public String getSortedAndFilteredProducts(
			@RequestParam(required = false) String sortBy,
			@RequestParam(required = false) String filterBy,Model model,HttpSession session,HttpServletRequest request) {

		List<Product> products = proRepository.findProductActive();

		// Kiểm tra nếu có yêu cầu sắp xếp
		if (sortBy != null) {
			switch (sortBy) {
				case "priceLowToHigh":
					products.sort(Comparator.comparing(Product::getPriceTemp));
					break;
				case "priceHighToLow":
					products.sort(Comparator.comparing(Product::getPriceTemp).reversed());
					break;
				case "nameAZ":
					products.sort(Comparator.comparing(Product::getNameProduct, String.CASE_INSENSITIVE_ORDER));
					break;
				case "nameZA":
					products.sort(Comparator.comparing(Product::getNameProduct, String.CASE_INSENSITIVE_ORDER).reversed());
					break;
				case "avgVote":
					products.sort(Comparator.comparing(Product::getAvgVote).reversed());
					break;
				default:
					// Mặc định nếu không có sắp xếp, không làm gì cả
					break;
			}
		}
		User usr = (User) session.getAttribute("usrList");
		String URL=request.getRequestURI();
		request.getSession().setAttribute("URL",URL);
		List<Discount> discount = discountRepository.findDiscountAll();
		List<Image> imgList = imgRepository.findImgAll();
		// Kiểm tra nếu có yêu cầu lọc
		if (filterBy != null) {
			// Ví dụ: lọc theo tên thương hiệu
			products = products.stream()
					.filter(p -> p.getNameBrand().equalsIgnoreCase(filterBy))
					.collect(Collectors.toList());
		}
		model.addAttribute("proList", products);
		for (Product pro : products) {

			for (Image img : imgList) {
				if (pro.getIdProduct() == img.getIdProduct() && img.getMain() != 0) {
					pro.setImg(img.getImage());
					model.addAttribute("proList", products);
				}

			}
			
			for (Discount dis : discount) {
				if (pro.getIdDiscount() == dis.getIdDiscount()) {
					double discountTemp = (double) pro.getPriceTemp() * dis.getPercentDiscount();
					double price = pro.getPriceTemp() - discountTemp;
					DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
					String formattedTotal = decimalFormat.format(price);
					String formattedDiscount=decimalFormat.format(discountTemp);
					pro.setPriceDiscount(formattedTotal);
					pro.setDiscount(formattedDiscount);
					model.addAttribute("proList", products);
				}
			}
		}
		return "client_layout/productClient";
	}
}
