package exam.spring.demo.model;

public class Image {
	private int idImage;
	private int main;
	private int idProduct;
	private String image;
	public static final String id_img="idImage";
	public static final String main_img="main";
	public static final String id_product="idProduct";
	public static final String image_img="image";
	
	public int getIdImage() {
		return idImage;
	}
	public void setIdImage(int idImage) {
		this.idImage = idImage;
	}
	public int getIdProduct() {
		return idProduct;
	}
	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
	}
	public int getMain() {
		return main;
	}
	public void setMain(int main) {
		this.main = main;
	}
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
}
