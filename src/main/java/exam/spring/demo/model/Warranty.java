package exam.spring.demo.model;

public class Warranty {
	private int idWarranty;
	private String idOrder;
	private int idUser;
	private String issueDescription;
	public int getIdWarranty() {
		return idWarranty;
	}
	public void setIdWarranty(int idWarranty) {
		this.idWarranty = idWarranty;
	}
	public String getIdOrder() {
		return idOrder;
	}
	public void setIdOrder(String idOrder) {
		this.idOrder = idOrder;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public String getIssueDescription() {
		return issueDescription;
	}
	public void setIssueDescription(String issueDescription) {
		this.issueDescription = issueDescription;
	}
	

}

