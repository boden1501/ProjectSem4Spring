package exam.spring.demo.model;

public class User {
	private int id;
	private String name;
	private String password;
	private String address;
	private String email;
	private String username;
	private String avatar;
	private int role;
	private String phone; 
	private Integer active;
	public static final String TBL_usr="member";
	public static final String id_usr="idUser";
	public static final String name_usr="Name";
	public static final String pwd_usr="Password";
	public static final String email_usr="Email";
	public static final String username_usr="Username";
	public static final String role_usr="Role";
	public static final String phone_usr="Phone";
	public static final String avatar_usr="Avatar";
	public static final String address_usr="Address";
	public static final String active_usr="active";
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public Integer getActive() {
		return active;
	}
	public void setActive(Integer active) {
		this.active = active;
	}
	
}
