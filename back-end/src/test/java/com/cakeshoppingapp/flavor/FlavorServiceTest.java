package com.cakeshoppingapp.flavor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

import com.cakeshoppingapp.converters.flavor.FlavorDtoToFlavorConverter;
import com.cakeshoppingapp.converters.flavor.FlavorToFlavorDtoConverter;
import com.cakeshoppingapp.dtoes.FlavorDTO;
import com.cakeshoppingapp.system.exceptions.SomethingAlreadyExistException;
import com.cakeshoppingapp.system.exceptions.SomethingNotFoundException;

@ExtendWith(MockitoExtension.class)
public class FlavorServiceTest {

	@Mock
	private FlavorRepository flavorRepository;

	@Mock
	FlavorToFlavorDtoConverter flavorToFlavorDtoConverter;

	@Mock
	FlavorDtoToFlavorConverter flavorDtoToFlavorConverter;

	@InjectMocks
	private FlavorService flavorService;

	private Flavor testFlavor;
	private FlavorDTO testFlavorDTO;

	@BeforeEach
	public void setUp() {
		testFlavor = new Flavor(1L, "Chocolate",
				"Chocolate cake boasts a deep," + " indulgent flavor with rich cocoa undertones and a moist,"
						+ " tender crumb. Its intense and satisfying taste is often"
						+ " enhanced by layers of smooth chocolate ganache or creamy frosting,"
						+ " making it a decadent favorite for chocolate lovers.");
		testFlavorDTO = new FlavorDTO(testFlavor.getId(), testFlavor.getName(), testFlavor.getDescription());
	}

	@Test
	void testSaveFlavorSuccess() {
		Flavor expectedNewFlavor = new Flavor(4L, "Lemon Cake",
				"Lemon cake offers a bright, zesty flavor with a perfect balance of tartness and sweetness. "
						+ "Infused with fresh lemon juice and zest, it provides a refreshing and invigorating taste, "
						+ "complemented by a moist and tender crumb. This vibrant cake is often paired with tangy lemon "
						+ "glaze or creamy frosting, creating a delightful and uplifting treat.");

		FlavorDTO expectedNewFlavorDTO = new FlavorDTO(expectedNewFlavor.getId(), expectedNewFlavor.getName(),
				expectedNewFlavor.getDescription());

		given(flavorRepository.findByName(expectedNewFlavor.getName())).willReturn(Optional.empty());
		given(flavorDtoToFlavorConverter.convert(any(FlavorDTO.class))).willReturn(expectedNewFlavor);
		given(flavorRepository.save(any(Flavor.class))).willReturn(expectedNewFlavor);
		given(flavorToFlavorDtoConverter.convert(any(Flavor.class))).willReturn(expectedNewFlavorDTO);

		FlavorDTO returnedFlavorDTO = flavorService.save(expectedNewFlavorDTO);

		assertThat(expectedNewFlavorDTO.equals(returnedFlavorDTO));
		verify(flavorDtoToFlavorConverter, times(1)).convert(any(FlavorDTO.class));
		verify(flavorRepository, times(1)).save(any(Flavor.class));
		verify(flavorToFlavorDtoConverter, times(1)).convert(any(Flavor.class));
		verify(flavorRepository, times(1)).findByName(expectedNewFlavor.getName());
	}

	@Test
	void testSaveFlavorAlreadyExist() {
		given(flavorRepository.findByName(testFlavor.getName())).willReturn(Optional.of(testFlavor));

		Throwable thrown = catchThrowable(() -> flavorService.save(testFlavorDTO));

		assertThat(thrown).isInstanceOf(SomethingAlreadyExistException.class)
				.hasMessage("Already Exist In The Database::: " + "Flavor With Name: " + testFlavorDTO.name());

		// 2nd way a little different
		/*
		 * assertEquals( assertThrows(SomethingAlreadyExistException.class, () ->
		 * flavorService.save(testFlavorDTO)) .getMessage(),
		 * ("Already Exist In The Database::: " + "Flavor With Name: " +
		 * testFlavorDTO.name()));
		 */
		verify(flavorRepository, times(1)).findByName(testFlavor.getName());
	}

	@Test
	void testFindByNameSuccess() {
		given(flavorRepository.findByName(testFlavor.getName())).willReturn(Optional.of(testFlavor));
		given(flavorToFlavorDtoConverter.convert(testFlavor)).willReturn(testFlavorDTO);
		FlavorDTO returnedFlavorDTO = flavorService.findByName(testFlavorDTO.name());
		assertEquals(testFlavor.getName(), returnedFlavorDTO.name());
		verify(flavorRepository, times(1)).findByName(testFlavor.getName());
		verify(flavorToFlavorDtoConverter, times(1)).convert(testFlavor);
	}

	@Test
	void testFindByNameNotFound() {
		String randomName = "Strawberry";
		// Specifying the input and the output of the mock object in isolation of the
		// tested service.

		given(flavorRepository.findByName(randomName)).willReturn(Optional.empty());

		Throwable thrown = catchThrowable(() -> flavorService.findByName(randomName));
		assertThat(thrown).isInstanceOf(SomethingNotFoundException.class)
				.hasMessage("Not Found! ::: " + "Flavor With Name: " + randomName);
		verify(flavorRepository).findByName(randomName);
	}

	@Test
	void testFindAllFlavors() {
		List<Flavor> expectedFlavors = new ArrayList<>();
		List<FlavorDTO> expectedFlavorsDTO = new ArrayList<>();
		Flavor chocolateFlavor = new Flavor(1L, "Chocolate",
				"Chocolate cake boasts a deep," + " indulgent flavor with rich cocoa undertones and a moist,"
						+ " tender crumb. Its intense and satisfying taste is often"
						+ " enhanced by layers of smooth chocolate ganache or creamy frosting,"
						+ " making it a decadent favorite for chocolate lovers.");
		FlavorDTO chocolateFlavorDTO = new FlavorDTO(chocolateFlavor.getId(), chocolateFlavor.getName(),
				chocolateFlavor.getDescription());
		Flavor vanillaFlavor = new Flavor(2L, "Vanilla",
				"Vanilla cake offers a classic, timeless flavor characterized by its delicate sweetness and rich,"
						+ " creamy notes of pure vanilla. Its light and fluffy texture makes"
						+ " it a versatile dessert that pairs well with various frostings and"
						+ " fillings, providing a comforting and nostalgic treat.");
		FlavorDTO vanillaFlavorDTO = new FlavorDTO(vanillaFlavor.getId(), vanillaFlavor.getName(),
				vanillaFlavor.getDescription());

		Flavor caramelFlavor = new Flavor(3L, "Caramel", "\r\n"
				+ "Caramel cake features a luxurious, buttery sweetness " + "with warm, toasty notes of caramelized "
				+ "sugar. Its flavor is reminiscent of rich toffee, with a" + " smooth and slightly smoky depth that "
				+ "creates a sumptuous and comforting dessert experience.");
		FlavorDTO caramelFlavorDTO = new FlavorDTO(caramelFlavor.getId(), caramelFlavor.getName(),
				caramelFlavor.getDescription());
		expectedFlavors.add(chocolateFlavor);
		expectedFlavors.add(vanillaFlavor);
		expectedFlavors.add(caramelFlavor);

		expectedFlavorsDTO.add(chocolateFlavorDTO);
		expectedFlavorsDTO.add(vanillaFlavorDTO);
		expectedFlavorsDTO.add(caramelFlavorDTO);

		given(flavorRepository.findAll()).willReturn(expectedFlavors);
		given(flavorToFlavorDtoConverter.convert(chocolateFlavor)).willReturn(chocolateFlavorDTO);
		given(flavorToFlavorDtoConverter.convert(vanillaFlavor)).willReturn(vanillaFlavorDTO);
		given(flavorToFlavorDtoConverter.convert(caramelFlavor)).willReturn(caramelFlavorDTO);
		List<FlavorDTO> returnedFlavorsDtos = flavorService.findAll();
		assertEquals(expectedFlavorsDTO.size(), returnedFlavorsDtos.size());
		verify(flavorRepository, times(1)).findAll();
		verify(flavorToFlavorDtoConverter, times(1)).convert(chocolateFlavor);
		verify(flavorToFlavorDtoConverter, times(1)).convert(vanillaFlavor);
		verify(flavorToFlavorDtoConverter, times(1)).convert(caramelFlavor);
	}

	@Test
	void testFindFlavorByIdSuccess() {
		given(flavorRepository.findById(1L)).willReturn(Optional.of(testFlavor));
		given(flavorToFlavorDtoConverter.convert(testFlavor)).willReturn(testFlavorDTO);
		FlavorDTO returnedFlavorDTO = flavorService.findById(1L);
		assertEquals(1L, returnedFlavorDTO.id());
	}

	@Test
	void testFindFlavorByIdFailed() {
		given(flavorRepository.findById(1L)).willReturn(Optional.empty());
		Throwable thrown = assertThrows(SomethingNotFoundException.class, () -> flavorService.findById(1L));
		assertEquals(thrown.getMessage(), "Not Found! ::: " + "Flavor With Id: " + 1L);
	}

	@Test
	void testUpdateFlavorSuccess() {
		Flavor oldFlavor = testFlavor;
		Flavor expectedFlavorAfterUpdate = new Flavor();
		expectedFlavorAfterUpdate.setId(oldFlavor.getId());
		expectedFlavorAfterUpdate.setName("Black Chocolate");
		expectedFlavorAfterUpdate.setDescription("Black chocolate cake, also known as dark chocolate cake,"
				+ " delivers an intense and sophisticated flavor profile with deep,"
				+ " bold cocoa notes and a hint of bitterness."
				+ " Its rich and dense texture is complemented by a slightly less"
				+ " sweet taste, appealing to those who appreciate the robust and"
				+ " complex nuances of high-quality dark chocolate."
				+ " This decadent cake often pairs well with a variety of fillings"
				+ " and frostings, such as ganache or espresso-infused cream,"
				+ " enhancing its luxurious experience.");

		FlavorDTO expectedFlavorAfterUpdateDTO = new FlavorDTO(expectedFlavorAfterUpdate.getId(),
				expectedFlavorAfterUpdate.getName(), expectedFlavorAfterUpdate.getDescription());

		given(flavorToFlavorDtoConverter.convert(expectedFlavorAfterUpdate)).willReturn(expectedFlavorAfterUpdateDTO);
		given(flavorRepository.findById(oldFlavor.getId())).willReturn(Optional.of(oldFlavor));
		given(flavorRepository.save(any(Flavor.class))).willReturn(expectedFlavorAfterUpdate);
		FlavorDTO actualReturnedFlavorDTO = flavorService.update(oldFlavor.getId(), expectedFlavorAfterUpdateDTO);
		assertEquals(expectedFlavorAfterUpdateDTO, actualReturnedFlavorDTO);
		verify(flavorRepository, times(1)).findById(oldFlavor.getId());
		verify(flavorRepository, times(1)).save(any(Flavor.class));
		verify(flavorToFlavorDtoConverter, times(1)).convert(expectedFlavorAfterUpdate);
	}

	@Test
	void testUpdateFlavorFailed() {
		Flavor oldFlavor = testFlavor;
		Flavor expectedFlavorAfterUpdate = new Flavor();
		expectedFlavorAfterUpdate.setId(98654153454654L);
		FlavorDTO expectedFlavorAfterUpdateDTO = new FlavorDTO(expectedFlavorAfterUpdate.getId(), "", "");

		given(flavorRepository.findById(testFlavor.getId())).willReturn(Optional.empty());
		assertThrows(SomethingNotFoundException.class,
				() -> flavorService.update(oldFlavor.getId(), expectedFlavorAfterUpdateDTO));
		verify(flavorRepository).findById(testFlavor.getId());
	}

	@Test
	void testDeleteFlavorById() {
		given(flavorRepository.findById(testFlavor.getId())).willReturn(Optional.of(testFlavor));
		doNothing().when(flavorRepository).deleteById(testFlavor.getId());
		flavorService.deleteById(testFlavor.getId());
		verify(flavorRepository, times(1)).findById(testFlavor.getId());
		verify(flavorRepository, times(1)).deleteById(testFlavor.getId());

	}

	@Test
	void testDeleteFlavorByIdNotFound() {
		given(flavorRepository.findById(anyLong())).willReturn(Optional.empty());
		assertThrows(SomethingNotFoundException.class, () -> flavorService.deleteById(testFlavor.getId()));
		verify(flavorRepository, times(1)).findById(anyLong());
	}

}
