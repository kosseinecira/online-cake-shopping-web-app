package com.cakeshoppingapp.cake;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.cakeshoppingapp.category.Category;
import com.cakeshoppingapp.flavor.Flavor;
import com.cakeshoppingapp.image.CakeImage;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@Entity
@ToString
@Table(name = "cakes")
public class Cake implements Serializable {
	/*
	 * 
	 * */

	private static final long serialVersionUID = 6235547172671695196L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private double price;
	private double discount;
	private double diameter;
	private double height;
	private double weight;
	private int netQuantity;
	private boolean isItAllergen;

	private String ingredients;
	private String deliveryInformation;
	private String description;
	private String noteDescription;
	private String messageOnCake;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "cake_image_id")
	private List<CakeImage> cakeImages = new ArrayList<>();

	@ManyToOne()
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "")
	private Flavor flavor;

	@ManyToOne()
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "")
	private Category category;

	public Cake(Long id, String name, double price, double discount, double diameter, double height, double weight,
			int netQuantity, boolean isItAllergen, String ingredients, String deliveryInformation, String description,
			String noteDescription, String messageOnCake, List<CakeImage> cakeImages, Flavor flavor) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.discount = discount;
		this.diameter = diameter;
		this.height = height;
		this.weight = weight;
		this.netQuantity = netQuantity;
		this.isItAllergen = isItAllergen;
		this.ingredients = ingredients;
		this.deliveryInformation = deliveryInformation;
		this.description = description;
		this.noteDescription = noteDescription;
		this.messageOnCake = messageOnCake;
		this.cakeImages = cakeImages;
		this.flavor = flavor;
	}

	public Cake(String name, double price, double discount, double diameter, double height, double weight,
			int netQuantity, boolean isItAllergen, String ingredients, String deliveryInformation, String description,
			String noteDescription, String messageOnCake, List<CakeImage> cakeImages, Flavor flavor,
			Category category) {
		this.name = name;
		this.price = price;
		this.discount = discount;
		this.diameter = diameter;
		this.height = height;
		this.weight = weight;
		this.netQuantity = netQuantity;
		this.isItAllergen = isItAllergen;
		this.ingredients = ingredients;
		this.deliveryInformation = deliveryInformation;
		this.description = description;
		this.noteDescription = noteDescription;
		this.messageOnCake = messageOnCake;
		this.cakeImages = cakeImages;
		this.flavor = flavor;
		this.category = category;
	}

}
