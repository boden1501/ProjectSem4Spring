package exam.spring.demo.model;

public class Product {
	private int idProduct;
	private String nameProduct;
	private String nameBrand;
	private double priceProduct;
	private int quantityProduct;
	private String Detail;
	private int idBrand;
	private int idDiscount;
	private float avgVote;
	private int idCategory;
	private int activeProduct;
	private String img;
	private int mainImg;
	public static final String id_product="idProduct";
	public static final String name_product="Name";
	public static final String price_product="Price";
	public static final String quantity_product="Quantity";
	public static final String detail_product="Detail";
	public static final String id_brand="idBrand";
	public static final String id_discount="idDiscount";
	public static final String avgvote_product="AvgVote";
	public static final String id_category="idCategory";
	public static final String active_product="Active";
	public int getIdProduct() {
		return idProduct;
	}
	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
	}
	public String getNameProduct() {
		return nameProduct;
	}
	public void setNameProduct(String nameProduct) {
		this.nameProduct = nameProduct;
	}
	public double getPriceProduct() {
		return priceProduct;
	}
	public void setPriceProduct(double priceProduct) {
		this.priceProduct = priceProduct;
	}
	public int getQuantityProduct() {
		return quantityProduct;
	}
	public void setQuantityProduct(int quantityProduct) {
		this.quantityProduct = quantityProduct;
	}
	public String getDetail() {
		return Detail;
	}
	public void setDetail(String detail) {
		Detail = detail;
	}
	public int getIdBrand() {
		return idBrand;
	}
	public void setIdBrand(int idBrand) {
		this.idBrand = idBrand;
	}
	public int getIdDiscount() {
		return idDiscount;
	}
	public void setIdDiscount(int idDiscount) {
		this.idDiscount = idDiscount;
	}
	public float getAvgVote() {
		return avgVote;
	}
	public void setAvgVote(float avgVote) {
		this.avgVote = avgVote;
	}
	public int getIdCategory() {
		return idCategory;
	}
	public void setIdCategory(int idCategory) {
		this.idCategory = idCategory;
	}
	public int getActiveProduct() {
		return activeProduct;
	}
	public void setActiveProduct(int activeProduct) {
		this.activeProduct = activeProduct;
	}
	public String getNameBrand() {
		return nameBrand;
	}
	public void setNameBrand(String nameBrand) {
		this.nameBrand = nameBrand;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public int getMainImg() {
		return mainImg;
	}
	public void setMainImg(int mainImg) {
		this.mainImg = mainImg;
	}
	
}
