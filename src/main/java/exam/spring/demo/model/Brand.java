package exam.spring.demo.model;

public class Brand {
	private int id_brand;
	private String name_brand;
	private Integer active_brand;
	public static final String idBrand="idBrand";
	public static final String nameBrand="nameBrand";
	public static final String activeBrand="active";
	public int getId_brand() {
		return id_brand;
	}
	public void setId_brand(int id_brand) {
		this.id_brand = id_brand;
	}
	public String getName_brand() {
		return name_brand;
	}
	public void setName_brand(String name_brand) {
		this.name_brand = name_brand;
	}
	public Integer getActive_brand() {
		return active_brand;
	}
	public void setActive_brand(Integer active_brand) {
		this.active_brand = active_brand;
	} 
	
}
