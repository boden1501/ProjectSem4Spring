package exam.spring.demo.model;


public class Category {
	private int id;
	private String name;
	private Integer active;
	public static final String id_cate="idCategory";
	public static final String name_cate="nameCategory";
	public static final String active_cate="active";
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getActive() {
		return active;
	}
	public void setActive(Integer active) {
		this.active = active;
	}
	

	
}
