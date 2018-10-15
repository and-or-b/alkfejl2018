package hu.alkfejl2018.prototype.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User extends BaseEntity implements Serializable {

	@Column
	@NotNull
	private String name;
	
	@Column(unique = true)
	@NotNull
	private String email;
	  
	@NotNull
	@Column
	private String password;

	@Column
	@Enumerated(EnumType.STRING)
	private Role role;
	 
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<CookBook> cookBooks = new ArrayList<>();
	
	public enum Role {
		ROLE_ADMIN,
		ROLE_USER
	}
}



 
