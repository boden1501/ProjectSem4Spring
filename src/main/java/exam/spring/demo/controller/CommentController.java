package exam.spring.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import exam.spring.demo.model.Comment;
import exam.spring.demo.model.Product;
import exam.spring.demo.repositories.CommentRepository;
import exam.spring.demo.repositories.ProductRepository;

@Controller
@RequestMapping("/admin")
public class CommentController {
	@Autowired
	ProductRepository pro;
	@Autowired 
	CommentRepository com;
	@RequestMapping(value = "/comments", method= RequestMethod.GET)
	public String index(@RequestParam("id") int id,Model model,RedirectAttributes redirectAttributes) {
		System.out.println("id:"+id);
		Product productList=pro.findById(id);
		List<Comment> commentlist =com.findById(id);
		model.addAttribute("commentlist", commentlist);
		model.addAttribute("productList",productList);
		System.out.println("size:"+commentlist.size());
		redirectAttributes.addAttribute("id", id);
		return "ad_layout/admincomments";
	}
	@RequestMapping(value = "/commentDelete", method = RequestMethod.DELETE)
	public String delete (@RequestParam("btn") int idcmt,@RequestParam("btnid") int id,Model model,RedirectAttributes redirectAttributes){
		System.out.println("id t: "+id);
		com.deleteById(idcmt);
		redirectAttributes.addAttribute("id", id);
		return "redirect:/admin/comments";
	}
}



