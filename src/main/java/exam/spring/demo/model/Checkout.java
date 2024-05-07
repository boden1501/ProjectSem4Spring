package exam.spring.demo.model;

public class Checkout {
	private String idOrder;
	private int idUser;
	private String DateCreate;
	private String nameUser;
	private String phoneUser;
	private String emailUser;
	private String addressUser;
	private double discountPrice;
	private double subTotalPrice;
	private double totalPrice;
	private int active;
	public String getNameUser() {
		return nameUser;
	}
	public void setNameUser(String nameUser) {
		this.nameUser = nameUser;
	}
	public String getPhoneUser() {
		return phoneUser;
	}
	public void setPhoneUser(String phoneUser) {
		this.phoneUser = phoneUser;
	}
	public String getEmailUser() {
		return emailUser;
	}
	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}
	public String getAddressUser() {
		return addressUser;
	}
	public void setAddressUser(String addressUser) {
		this.addressUser = addressUser;
	}

	public String getIdOrder() {
		return idOrder;
	}
	public void setIdOrder(String idOrder) {
		this.idOrder = idOrder;
	}
	public String getDateCreate() {
		return DateCreate;
	}
	public void setDateCreate(String dateCreate) {
		DateCreate = dateCreate;
	}
	public double getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(double discountPrice) {
		this.discountPrice = discountPrice;
	}
	public double getSubTotalPrice() {
		return subTotalPrice;
	}
	public void setSubTotalPrice(double subTotalPrice) {
		this.subTotalPrice = subTotalPrice;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
}
