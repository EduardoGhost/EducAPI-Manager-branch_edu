package br.ufpb.dcx.apps4society.educapimanager.model.dto;

import java.io.Serializable;

import br.ufpb.dcx.apps4society.educapimanager.model.bean.User;

public class UserDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String email;
	private String password;
	
	public UserDTO() {}

	public UserDTO(String email,String password){
		this.email = email;
		this.password = password;
	}
	
	public UserDTO(User obj) {
		this.id = obj.getId();
		this.name = obj.getName();
		this.email = obj.getEmail();
		this.password = obj.getPassword();
	}
	public UserDTO (String name,String email,String password ){
		this.name = name;
		this.email = email;
		this.password = password;
	}



    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserDTO userDTO = (UserDTO) o;
		return email.equals(userDTO.email) &&
				password.equals(userDTO.password);
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public String toString() {
		return "UserDTO{" +
				"id=" + id +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				'}';
	}
}
