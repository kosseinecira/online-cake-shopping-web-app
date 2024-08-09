package com.cakeshoppingapp.system.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.cakeshoppingapp.cake.Cake;
import com.cakeshoppingapp.cake.CakeRepository;
import com.cakeshoppingapp.category.Category;
import com.cakeshoppingapp.category.CategoryRepository;
import com.cakeshoppingapp.customer.Address;
import com.cakeshoppingapp.customer.Customer;
import com.cakeshoppingapp.customer.CustomerRepository;
import com.cakeshoppingapp.flavor.Flavor;
import com.cakeshoppingapp.flavor.FlavorRepository;

@Component
public class DBInitializer implements CommandLineRunner {

	private final FlavorRepository flavorRepository;

	private final CakeRepository cakeRepository;
	private final CustomerRepository customerRepository;
	private final CategoryRepository categoryRepository;
	private final PasswordEncoder passwordEncoder;

	public DBInitializer(FlavorRepository flavorRepository, CakeRepository cakeRepository,
			CustomerRepository customerRepository, CategoryRepository categoryRepository,
			PasswordEncoder passwordEncoder) {
		this.flavorRepository = flavorRepository;
		this.cakeRepository = cakeRepository;
		this.customerRepository = customerRepository;
		this.categoryRepository = categoryRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void run(String... args) throws Exception {
		setUpCakes();
		setUpCustomers();
		setUpCategories();
	}

	void setUpCakes() {

		Flavor chocolateFlavor = new Flavor(1L, "Chocolate",
				"Chocolate cake boasts a deep," + " indulgent flavor with rich cocoa undertones and a moist,"
						+ " tender crumb. Its intense and satisfying taste is often"
						+ " enhanced by layers of smooth chocolate ganache or creamy frosting,"
						+ " making it a decadent favorite for chocolate lovers.");

		Flavor vanillaFlavor = new Flavor(2L, "Vanilla",
				"Vanilla cake offers a classic, timeless flavor characterized by its delicate sweetness and rich,"
						+ " creamy notes of pure vanilla. Its light and fluffy texture makes"
						+ " it a versatile dessert that pairs well with various frostings and"
						+ " fillings, providing a comforting and nostalgic treat.");

		Flavor caramelFlavor = new Flavor(3L, "Caramel", "Caramel cake features a luxurious, buttery sweetness "
				+ "with warm, toasty notes of caramelized " + "sugar. Its flavor is reminiscent of rich toffee, with a"
				+ " smooth and slightly smoky depth that " + "creates a sumptuous and comforting dessert experience.");

		Cake cake1 = new Cake(1L, "Chocolate Delight", 25.5, 5.0, 8.0, 4.0, 1.5, 10, false,
				"Flour, Sugar, Cocoa, Butter, Eggs", "Delivery within 3-5 days", "A rich and moist chocolate cake",
				"Keep refrigerated", "Happy Birthday!", null, chocolateFlavor);

		Cake cake2 = new Cake(2L, "Vanilla Cake", 20.99, 3.00, 9.0, 4.5, 1.1, 1, true,
				"Flour, Sugar, Vanilla, Eggs, Butter", "Deliver within 2-4 business days",
				"A classic vanilla cake perfect for any occasion.", "Store in a cool place", "Congratulations", null,
				vanillaFlavor);

		Cake cake3 = new Cake(3L, "Red Velvet Cake", 30.99, 4.00, 11.0, 5.5, 1.3, 1, false,
				"Flour, Sugar, Cocoa, Red food coloring, Eggs, Butter", "Deliver within 1-3 business days",
				"A rich and moist red velvet cake with cream cheese frosting.", "Keep refrigerated", "Best Wishes",
				null, caramelFlavor);

		Cake cake4 = new Cake(4L, "Caramel Drip Cake", 60.0, 10.0, 11.0, 6.0, 4.0, 10, false,
				"Flour, Sugar, Butter, Eggs, Caramel Sauce, Cream, Vanilla Extract", "Delivery within 3-4 days",
				"A rich caramel cake with layers of caramel buttercream and caramel drip", "Store in a cool place",
				"Best wishes on your celebration!", null, caramelFlavor);
		Cake cake5 = new Cake(5L, "Chocolate Truffle Cake", 50.0, 10.0, 9.0, 5.0, 4.2, 8, false,
				"Flour, Sugar, Cocoa Powder, Butter, Eggs, Dark Chocolate, Heavy Cream", "Delivery within 3-4 days",
				"A rich chocolate cake filled with creamy chocolate truffle", "Store in a cool place",
				"Happy Birthday!", null, chocolateFlavor);

		flavorRepository.save(chocolateFlavor);
		flavorRepository.save(vanillaFlavor);
		flavorRepository.save(caramelFlavor);

		cakeRepository.save(cake1);
		cakeRepository.save(cake2);
		cakeRepository.save(cake3);
		cakeRepository.save(cake4);
		cakeRepository.save(cake5);

	}

	public void setUpCustomers() {

		Address address1 = new Address(1L, "Algeria", "Algiers", "Algiers", 16000);
		Address address2 = new Address(2L, "Algeria", "Ouargla", "Ouargla", 30000);
		Address address3 = new Address(3L, "Algeria", "Constantine", "Constantine", 25000);

		Customer customer1 = new Customer(1L, "username1", "password1", "First Name1", "Last Name1", "email1@gmail.com",
				"00123456789", "ADMIN USER", "profile image path here", true, true, false, true, address1);

		Customer customer2 = new Customer(2L, "username2", "password2", "First Name2", "Last Name2", "email2@gmail.com",
				"00987654321", "USER", "profile image path here", false, true, false, true, address2);

		Customer customer3 = new Customer(3L, "username3", "password3", "First Name3", "Last Name3", "email3@gmail.com",
				"00159874632", "USER", "profile image path here", true, true, false, true, address3);
		Customer customer4 = new Customer(4L, "username4", "password4", "First Name4", "Last Name4", "email4@gmail.com",
				"00123654789", "USER", "profile image path here", true, false, false, true, address3);
		Customer customer5 = new Customer(5L, "username5", "password5", "First Name5", "Last Name5", "email5@gmail.com",
				"00987456321", "USER", "profile image path here", false, false, false, true, address2);

		customer1.setPassword(passwordEncoder.encode("password1"));
		customer2.setPassword(passwordEncoder.encode("password2"));
		customer3.setPassword(passwordEncoder.encode("password3"));
		customer4.setPassword(passwordEncoder.encode("password4"));
		customer5.setPassword(passwordEncoder.encode("password5"));
		customerRepository.save(customer1);
		customerRepository.save(customer2);
		customerRepository.save(customer3);
		customerRepository.save(customer4);
		customerRepository.save(customer5);

	}

	private void setUpCategories() {

		Category cakeCategory = new Category(1L, "Cake", "Cake.png");
		Category cupCakeCategory = new Category(2L, "CupCake", "CupCake.png");
		Category traditionalCategory = new Category(3L, "Traditional", "traditioanl.png");
		Category juiceCategory = new Category(4L, "Juice", "juice.png");
		Category iceCreamCategory = new Category(5L, "IceCream", "traditioanl.png");

		categoryRepository.save(cakeCategory);
		categoryRepository.save(cupCakeCategory);
		categoryRepository.save(traditionalCategory);
		categoryRepository.save(juiceCategory);
		categoryRepository.save(iceCreamCategory);
	}
}
