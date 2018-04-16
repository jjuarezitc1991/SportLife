package com.test.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.test.domain.base.BaseEntity;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "App_User")
public class User extends BaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    @NotEmpty
    @Column(name = "First_Name", nullable = false)
    private String firstName;

    @NotEmpty
    @Column(name = "Last_Name", nullable = false)
    private String lastName;
    
    @NotEmpty
    @Column(name = "Second_Last_Name", nullable = false)
    private String secondLastName;
    
    @NotEmpty
    @Column(name = "Address", nullable = false)
    private String address;

    @NotEmpty
    @Column(name = "User_Name", nullable = false, unique = true)
    private String username;
    
    @Column(name = "Password")
    private String password;

    @NotEmpty
    @Email
    @Column(name = "Email", nullable = false)
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

	public String getSecondLastName() {
		return secondLastName;
	}

	public void setSecondLastName(String secondLastName) {
		this.secondLastName = secondLastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
