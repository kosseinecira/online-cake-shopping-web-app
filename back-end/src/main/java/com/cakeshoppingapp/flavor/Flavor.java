package com.cakeshoppingapp.flavor;

import java.io.Serializable;

import org.hibernate.annotations.ColumnTransformer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "flavors")
public class Flavor implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4745584064715838359L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(unique = true)
	@ColumnTransformer(write = "trim(?)")
	private String name;

	@Column(length = 2000)
	private String description;

	public Flavor(String name, String description) {
		this.name = name;
		this.description = description;
	}

}
