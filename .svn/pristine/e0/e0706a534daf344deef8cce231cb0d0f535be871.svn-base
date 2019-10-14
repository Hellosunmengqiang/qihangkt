package cn.ybzy.qihangkt.model;

import java.util.Date;
import java.util.List;

public class User { // User的名称必须和我们的数据表里的表名称t_user
	private Integer id; // 除了名字对应以外，坑：int，id将获取值，整数类型不能用int，Integer
	private String username;
	private String password;
	private String email;
	private String phone;
	private Integer enable; // 0：未激活，1：激活
	private Date addDate;
	private List<Role> roles;

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + ", phone="
				+ phone + ", enable=" + enable + ", addDate=" + addDate + ", roles=" + roles + "]";
	}

}
