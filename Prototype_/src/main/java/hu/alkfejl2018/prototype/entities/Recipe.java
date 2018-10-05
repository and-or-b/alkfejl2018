package hu.alkfejl2018.prototype.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recipes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Recipe extends BaseEntity implements Serializable {

	@Column
	@NotNull
	private String title;
	
	@Column
	@NotNull
	private String description;

	@JsonIgnore
	@ManyToMany(mappedBy = "recipes")
	private List<CookBook> cookBooks = new ArrayList<>();
}
