package exam.spring.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import exam.spring.demo.model.User;

import exam.spring.demo.repositories.UserRepository;

@Controller
@RequestMapping("/admin")
public class AccountController {
	@Autowired
	UserRepository usr;
	@RequestMapping(value = "/viewAccount",method= RequestMethod.GET)
	public String  viewAccount(Model model, @RequestParam(defaultValue ="0") int page,
			@RequestParam(defaultValue="10") int size) {
		
		 
			// tinh toan offset dua tren trang va kich thuoc trang 
				int offset = page * size;
			// lay danh sach cac danh muc tu repository
				List<User> dataList = usr.findAll(offset, size);
			// lay tong so luong danh muc trong co so du lieu de tinh toan phan trang 
				int totalRows = usr.getTotalRows();
		    // tinh tonng so trang dua trenn so luong danh muc va kich thuoc trang
				int totalPages =(int) Math.ceil((double) totalRows / size);
			// truyen danh sach , tong so luong danh muc , so trang hien tai va tong so tyrang vao model
				int row = 0;
				if (page >1) {
					row = offset - size;
				}
				model.addAttribute("userList", dataList);
				model.addAttribute("row", row);
				model.addAttribute("totalRows", totalRows);
				model.addAttribute("totalPages", totalPages); // them totalPages vao model
				model.addAttribute("currentPages", page);
			    return "ad_layout/view_account"; // tra ve te view template //cho nay chua tim thay file, anh dang de trong ad_layout thi phai them 1 duong dan den ad_layout nua thi moi den trang view
}

	@CrossOrigin
	@RequestMapping(value ="/updateAccount", method = RequestMethod.PUT)
	public String updateAccount (@Validated User item) {
		System.out.println("ID:"+item.getId());
		usr.update(item);
		return "redirect:/admin/viewAccount";
	}
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String searchName(@RequestParam("nameSearch") String keyword,Model model) {
    	List<User> userList =usr.findByName(keyword,keyword);
    	model.addAttribute("userList",userList);
    	return "ad_layout/view_account"; 
    }
    


}