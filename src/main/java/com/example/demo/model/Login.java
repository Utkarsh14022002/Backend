package com.example.demo.model;


import javax.persistence.*;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonManagedReference;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "login")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
//@ToString
public class Login {
    @Id
    @Column(name = "user_id")
    private String userid;
    @Column(name = "password")
    private String password;
    @Column(name = "isadmin")
    private int admin;
    @Column(name="email")
    private String emailid;
    
    
	@Override
	public String toString() {
		return "Login [userid=" + userid + ", password=" + password + ", admin=" + admin + ", emailid=" + emailid + "]";
	}

    
    @JsonManagedReference
    @OneToMany(mappedBy = "login", cascade = CascadeType.ALL)
    private List<Account> account;

}
