package exam.spring.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import exam.spring.demo.model.Comment;
import exam.spring.demo.model.Product;
import exam.spring.demo.model.User;
import exam.spring.demo.repositories.CommentRepository;
import exam.spring.demo.repositories.ProductRepository;

@Controller
@RequestMapping("/admin")
public class CommentController {
	@Autowired
	ProductRepository pro;
	@Autowired 
	CommentRepository com;

	@GetMapping("/comments")
	public String admincomments (@RequestParam("id") int id,Model model, @RequestParam(defaultValue ="0") int page,
			@RequestParam(defaultValue="10") int size,RedirectAttributes redirectAttributes) {
		Product productList=pro.findById(id);
		model.addAttribute("productList",productList);
		// tinh toan offseet diua trenn trang va kich thuoc trang 
		    int offset = page * size ;
			// lay danh sach cac muc tu repository
		    List<Comment> dataList = com.findAll(id,offset, size);
		    System.out.println("size: "+dataList.size());
		// lay tong so luong danh muc trong co so du lieu de tinh toan phann trang
		    int totalRows = com.getToalRows();
		// tinh tonng so trang dua tren so luong danh muc va kich thuoc trang 
		    int totalPages = (int) Math.ceil((double) totalRows / size);
		// truyen danh sach, tong so luong danh muc, so trang hien tai va tong so trang vao model
		    int row = 0;
		    if (page >1) {
		    	row = offset -size;
		    }
		    model.addAttribute("datalist",dataList);
		    model.addAttribute("row", row);
		    model.addAttribute("totalRows", totalRows);
		    model.addAttribute("totalPages", totalPages);
		    model.addAttribute("currentPages", page);
		    redirectAttributes.addAttribute("id", id);
		    return "ad_layout/admincomments";
		    
	}
	@DeleteMapping("/commentDelete")
	public String delete (@RequestParam("btn") int idcmt,@RequestParam("btnid") int id,Model model,RedirectAttributes redirectAttributes){
		System.out.println("id t: "+id);
		com.deleteById(idcmt);
		redirectAttributes.addAttribute("id", id);
		return "redirect:/admin/comments";
	}
	
}


