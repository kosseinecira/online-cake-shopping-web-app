package com.cakeshoppingapp.system.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.cakeshoppingapp.cake.Cake;
import com.cakeshoppingapp.cake.CakeRepository;
import com.cakeshoppingapp.flavor.Flavor;
import com.cakeshoppingapp.flavor.FlavorRepository;

@Component
public class DBInitializer implements CommandLineRunner {

	private final FlavorRepository flavorRepository;

	private final CakeRepository cakeRepository;

	public DBInitializer(FlavorRepository flavorRepository, CakeRepository cakeRepository) {
		this.flavorRepository = flavorRepository;
		this.cakeRepository = cakeRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		setUpDB();

	}

	void setUpDB() {

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

//		flavorService.save(chocolateFlavor);
//		flavorService.save(vanillaFlavor);
//		flavorService.save(caramelFlavor);
//		
//		cakeService.save(chocolateFlavor.id(), cake1);
//		cakeService.save(vanillaFlavor.id(), cake2);
//		cakeService.save(caramelFlavor.id(), cake3);
//		cakeService.save(caramelFlavor.id(), cake4);
//		cakeService.save(chocolateFlavor.id(), cake5);
	}
}
