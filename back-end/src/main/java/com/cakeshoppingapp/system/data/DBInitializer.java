package com.cakeshoppingapp.system.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.cakeshoppingapp.flavor.Flavor;
import com.cakeshoppingapp.flavor.FlavorService;

@Component
public class DBInitializer implements CommandLineRunner {

	@Autowired
	private FlavorService flavorService;

	public void setUpData() {

	}

	@Override
	public void run(String... args) throws Exception {
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

		flavorService.save(chocolateFlavor);
		flavorService.save(vanillaFlavor);
		flavorService.save(caramelFlavor);

	}
}
