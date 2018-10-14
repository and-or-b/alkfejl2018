package hu.alkfejl2018.prototype.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name="cook_books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CookBook extends BaseEntity implements Serializable {
	
	@Column
	@NotNull
	private String title;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@NotNull
	private User user;
	
	@ManyToMany
	@JoinTable
	private List<Recipe> recipes = new ArrayList<>();
}

