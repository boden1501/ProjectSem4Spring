package exam.spring.demo.model;

public class Cart {
	private int idCart;
	private int idProduct;
	private int Quantity;
	private int idUser;
	private String Image;
	private String nameProduct;
	private String priceProduct;
	private int idDiscount;
	public final static String id_cart="idCart";
	public final static String id_product="idProduct";
	public final static String quantity="Quantity";
	public final static String id_User="idUser";
	public final static String name_Product="Name";
	public final static String price_Product="Price";
	public final static String image_Product="Image";
	public final static String id_discount="idDiscount";
	public int getIdCart() {
		return idCart;
	}
	public void setIdCart(int idCart) {
		this.idCart = idCart;
	}
	public int getIdProduct() {
		return idProduct;
	}
	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
	}
	public int getQuantity() {
		return Quantity;
	}
	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public String getNameProduct() {
		return nameProduct;
	}
	public void setNameProduct(String nameProduct) {
		this.nameProduct = nameProduct;
	}
	public String getPriceProduct() {
		return priceProduct;
	}
	public void setPriceProduct(String priceProduct) {
		this.priceProduct = priceProduct;
	}
	public String getImage() {
		return Image;
	}
	public void setImage(String image) {
		Image = image;
	}
	public int getIdDiscount() {
		return idDiscount;
	}
	public void setIdDiscount(int idDiscount) {
		this.idDiscount = idDiscount;
	}
	
}
