package com.cakeshoppingapp.customer;

import java.util.Objects;

import org.hibernate.annotations.ColumnTransformer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "customers")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ColumnTransformer(write = "trim(?)")
	@Column(unique = true)
	private String username;
	private String password;
	@ColumnTransformer(write = "trim(?)")
	private String firstName;
	@ColumnTransformer(write = "trim(?)")
	private String lastName;
	@ColumnTransformer(write = "trim(?)")
	@Column(unique = true)
	private String email;
	@ColumnTransformer(write = "trim(?)")
	@Column(unique = true)
	private String phone;
	@ColumnTransformer(write = "trim(?)")
	private String role;
	private String imagePath;
	private boolean emailVerified;
	private boolean phoneVerified;
	private boolean blocked;
	private boolean allInformationProvided;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn
	private Address address;

	public Customer(Long id, String username, String email, String password, String role) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	// Without The Role.
	public Customer(Long id, String username, String password, String firstName, String lastName, String email,
			String phone, boolean emailVerified, boolean phoneVerified, String imagePath, Address address) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.imagePath = imagePath;
		this.address = address;
		this.emailVerified = emailVerified;
		this.phoneVerified = phoneVerified;
	}
	
	

	@Override
	public int hashCode() {
		return Objects.hash(email, id, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(email, other.email) && Objects.equals(id, other.id)
				&& Objects.equals(username, other.username);
	}

	public Customer(Long id, String username, String password, String firstName, String lastName, String email,
			String phone, String role, String imagePath, boolean emailVerified, boolean phoneVerified, boolean blocked,
			boolean allInformationProvided, Address address) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.role = role;
		this.imagePath = imagePath;
		this.emailVerified = emailVerified;
		this.phoneVerified = phoneVerified;
		this.blocked = blocked;
		this.allInformationProvided = allInformationProvided;
		this.address = address;
	}

}
