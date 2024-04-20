package exam.spring.demo.model;

public class Feedback {
private int idFeedback;
private int nameUser;
private String email;
private String detail;
private int active;
public int getIdFeedback() {
	return idFeedback;
}
public void setIdFeedback(int idFeedback) {
	this.idFeedback = idFeedback;
}

public String getNameUser() {
	return nameUser;
}
public void setNameUser(int i) {
	this.nameUser = i;
}
public int getActive() {
	return active;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getDetail() {
	return detail;
}
public void setDetail(String detail) {
	this.detail = detail;
}
public int getActive(int i) {
	return active;
}
public void setActive(int active) {
	this.active = active;
}

}
