package com.cakeshoppingapp.flavor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.cakeshoppingapp.system.exceptions.SomethingAlreadyExistException;
import com.cakeshoppingapp.system.exceptions.SomethingNotFoundException;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class FlavorServiceTest {

	@Mock
	private FlavorRepository flavorRepository;

	@InjectMocks
	private FlavorService flavorService;

	private Flavor flavor;

	/*
	 * TESTS SCENARIOS: SAVING FLAVOR WITH SUCCESS FIALING: - TITLE ALREADY EXSIT IN
	 * DB - SOME REQUIRED DATA WASN'T FILLED UP. - SERVER PROBLEM
	 */

	@BeforeEach
	void setUp() {
		flavor = new Flavor(1L, "Chocolate",
				"Chocolate cake boasts a deep," + " indulgent flavor with rich cocoa undertones and a moist,"
						+ " tender crumb. Its intense and satisfying taste is often"
						+ " enhanced by layers of smooth chocolate ganache or creamy frosting,"
						+ " making it a decadent favorite for chocolate lovers.");
	}

	@Test
	void testAddFlavorSuccess() {
		Flavor newFlavor = new Flavor("Lemon Cake",
				"Lemon cake offers a bright, zesty flavor with a perfect balance of tartness and sweetness. "
						+ "Infused with fresh lemon juice and zest, it provides a refreshing and invigorating taste, "
						+ "complemented by a moist and tender crumb. This vibrant cake is often paired with tangy lemon "
						+ "glaze or creamy frosting, creating a delightful and uplifting treat.");
		Flavor expectedFlavor = newFlavor;
		given(flavorRepository.save(newFlavor)).willReturn(expectedFlavor);
		Flavor returnedFlavor = flavorService.save(newFlavor);
		assertThat(expectedFlavor.equals(returnedFlavor));
	}

	@Test
	void testAddFlavorAlreadyExist() {
		given(flavorRepository.findByTitle(flavor.getTitle())).willReturn(Optional.of(flavor));

		Throwable thrown = catchThrowable(() -> flavorService.save(flavor));

		assertThat(thrown).isInstanceOf(SomethingAlreadyExistException.class)
				.hasMessage("Already Exist In The Database::: " + flavor.getTitle());

		// 2nd way a little different
		assertEquals(assertThrows(SomethingAlreadyExistException.class, () -> flavorService.save(flavor)).getMessage(),
				("Already Exist In The Database::: " + flavor.getTitle()));
	}

	@Test
	void testFindByTitle() {
		given(flavorRepository.findByTitle(flavor.getTitle())).willReturn(Optional.of(flavor));
		Flavor returnedFlavor = flavorService.findByTitle(flavor.getTitle());
		assertEquals(flavor.getTitle(), returnedFlavor.getTitle());
	}

	@Test
	void testTitleNotFound() {
		String randomTitle = "Strawberry";
//		given(flavorRepository.findByTitle(randomTitle)).willThrow(new SomethingNotFoundException(randomTitle));
		// Specifying the input and the output of the mock object in isolation of the
		// tested service.

		given(flavorRepository.findByTitle(randomTitle)).willReturn(Optional.empty());

		Throwable thrown = catchThrowable(() -> flavorService.findByTitle(randomTitle));
		assertThat(thrown).isInstanceOf(SomethingNotFoundException.class).hasMessage("Not Found! ::: " + randomTitle);

	}

	@Test
	void testFindAllFlavors() {
		List<Flavor> expectedFlavors = new ArrayList<>();
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

		Flavor caramelFlavor = new Flavor(3L, "Caramel", "\r\n"
				+ "Caramel cake features a luxurious, buttery sweetness " + "with warm, toasty notes of caramelized "
				+ "sugar. Its flavor is reminiscent of rich toffee, with a" + " smooth and slightly smoky depth that "
				+ "creates a sumptuous and comforting dessert experience.");
		expectedFlavors.add(chocolateFlavor);
		expectedFlavors.add(vanillaFlavor);
		expectedFlavors.add(caramelFlavor);
		given(flavorRepository.findAll()).willReturn(expectedFlavors);
		List<Flavor> returnedFlavors = flavorService.findAll();
		assertThat(expectedFlavors.size() == returnedFlavors.size());
		verify(flavorRepository, times(1)).findAll();
	}

	@Test
	void testFindFlavorByIdSuccess() {
		given(flavorRepository.findById(1L)).willReturn(Optional.of(flavor));
		Flavor returnedFlavor = flavorService.findById(1L);
		assertEquals(1L, returnedFlavor.getId());
	}

	@Test
	void testFindFlavorByIdFailed() {
		given(flavorRepository.findById(1L)).willReturn(Optional.empty());

		// Flavor returnedFlavor = flavorService.findById(1L);
		Throwable thrown = assertThrows(SomethingNotFoundException.class, () -> flavorService.findById(1L));
		assertEquals(thrown.getMessage(), "Not Found! ::: " + 1L);
	}

	@Test
	void testUpdateFlavorSuccess() {
		Flavor oldFlavor = flavor;
		Flavor expectedFlavorAfterUpdate = new Flavor();
		expectedFlavorAfterUpdate.setId(oldFlavor.getId());
		expectedFlavorAfterUpdate.setTitle("Black Chocolate");
		expectedFlavorAfterUpdate.setDescription("Black chocolate cake, also known as dark chocolate cake,"
				+ " delivers an intense and sophisticated flavor profile with deep,"
				+ " bold cocoa notes and a hint of bitterness."
				+ " Its rich and dense texture is complemented by a slightly less"
				+ " sweet taste, appealing to those who appreciate the robust and"
				+ " complex nuances of high-quality dark chocolate."
				+ " This decadent cake often pairs well with a variety of fillings"
				+ " and frostings, such as ganache or espresso-infused cream,"
				+ " enhancing its luxurious experience.");

		given(flavorRepository.findById(oldFlavor.getId())).willReturn(Optional.of(oldFlavor));
		// given(flavorService.findById(oldFlavor.getId())).willReturn(oldFlavor);
		given(flavorRepository.save(expectedFlavorAfterUpdate)).willReturn(expectedFlavorAfterUpdate);
		Flavor actualReturnedFlavor = flavorService.update(oldFlavor.getId(), expectedFlavorAfterUpdate);
		assertEquals(expectedFlavorAfterUpdate, actualReturnedFlavor);
		verify(flavorRepository, times(1)).findById(oldFlavor.getId());
		verify(flavorRepository, times(1)).save(expectedFlavorAfterUpdate);
	}

	@Test
	void testUpdateFailed() {
		Flavor oldFlavor = flavor;
		Flavor expectedFlavorAfterUpdate = new Flavor();
		expectedFlavorAfterUpdate.setId(98654153454654L);
		given(flavorRepository.findById(anyLong())).willReturn(Optional.empty());
		assertThrows(SomethingNotFoundException.class,
				() -> flavorService.update(oldFlavor.getId(), expectedFlavorAfterUpdate));
	}

	@Test
	void testDeleteFlavorById() {
		given(flavorRepository.findById(flavor.getId())).willReturn(Optional.of(flavor));
		doNothing().when(flavorRepository).deleteById(flavor.getId());

		flavorService.deleteById(flavor.getId());
		verify(flavorRepository, times(1)).findById(flavor.getId());
		verify(flavorRepository, times(1)).deleteById(flavor.getId());

	}

	@Test
	void testDeleteFlavorByIdNotFound() {
		given(flavorRepository.findById(anyLong())).willReturn(Optional.empty());

		assertThrows(SomethingNotFoundException.class, () -> flavorService.deleteById(flavor.getId()));
		verify(flavorRepository, times(1)).findById(anyLong());
	}

}
