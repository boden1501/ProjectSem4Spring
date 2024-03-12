package exam.spring.demo.model;


public class Category {
	private int id;
	private String name;
	private int active;
	public static final String id_cate="id_cate";
	public static final String name_cate="name_cate";
	public static final String active_cate="active";
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
}
