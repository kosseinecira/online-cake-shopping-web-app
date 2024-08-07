package com.cakeshoppingapp.cake;

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
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.MultipartFile;

import com.cakeshoppingapp.converters.cake.CakeDtoToCakeConverter;
import com.cakeshoppingapp.converters.cake.CakeToCakeDtoConverter;
import com.cakeshoppingapp.dtoes.CakeDTO;
import com.cakeshoppingapp.dtoes.CakeMultipleFileDTO;
import com.cakeshoppingapp.dtoes.FlavorDTO;
import com.cakeshoppingapp.flavor.Flavor;
import com.cakeshoppingapp.flavor.FlavorService;
import com.cakeshoppingapp.system.exceptions.SomethingAlreadyExistException;
import com.cakeshoppingapp.system.exceptions.SomethingNotFoundException;

import jakarta.servlet.ServletContext;

@ExtendWith(MockitoExtension.class)
//@ContextConfiguration(classes = { ApplicationConfig.class })
@SpringBootTest
@WebAppConfiguration
class CakeServiceTest {
	// When the test runs , Mockito will Inject A mock Object that simulates the
	// Real one into CakeService
	// since we don't want the Repository to influence our results.
	// we want to test our service in Isolation of other classes.
	@Mock
	CakeRepository cakeRepository;

	@InjectMocks
	CakeService cakeService;
	// private MockMvc mockMvc;
	@Mock
	private CakeToCakeDtoConverter cakeToCakeDtoConverter;
	@Mock
	private CakeDtoToCakeConverter cakeDtoToCakeConverter;

	@Mock
	private FlavorService flavorService;
	@Mock
	ServletContext context;

//	@Autowired
//	private WebApplicationContext webApplicationContext;

	private Cake chocolateDelightCake, vanillaCake, redVelvetCake, caramelDripCake;
	private Flavor chocolateFlavor, vanillaFlavor, caramelFlavor;
	private CakeDTO chocolateDelightCakeDTO, redVelvetCakeDTO;

	@BeforeEach
	void setUp() throws Exception {
		// this.mockMvc =
		// MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

		chocolateFlavor = new Flavor(1L, "Chocolate",
				"Chocolate cake boasts a deep," + " indulgent flavor with rich cocoa undertones and a moist,"
						+ " tender crumb. Its intense and satisfying taste is often"
						+ " enhanced by layers of smooth chocolate ganache or creamy frosting,"
						+ " making it a decadent favorite for chocolate lovers.");

		vanillaFlavor = new Flavor(2L, "Vanilla",
				"Vanilla cake offers a classic, timeless flavor characterized by its delicate sweetness and rich,"
						+ " creamy notes of pure vanilla. Its light and fluffy texture makes"
						+ " it a versatile dessert that pairs well with various frostings and"
						+ " fillings, providing a comforting and nostalgic treat.");

		caramelFlavor = new Flavor(3L, "Caramel", "Caramel cake features a luxurious, buttery sweetness "
				+ "with warm, toasty notes of caramelized " + "sugar. Its flavor is reminiscent of rich toffee, with a"
				+ " smooth and slightly smoky depth that " + "creates a sumptuous and comforting dessert experience.");

		chocolateDelightCake = new Cake(1L, "Chocolate Delight", 25.5, 5.0, 8.0, 4.0, 1.5, 10, false,
				"Flour, Sugar, Cocoa, Butter, Eggs", "Delivery within 3-5 days", "A rich and moist chocolate cake",
				"Keep refrigerated", "Happy Birthday!", null, chocolateFlavor);

		chocolateDelightCakeDTO = new CakeDTO(1L, "Chocolate Delight", 25.5, 5.0, 8.0, 4.0, 1.5, 10, false,
				"Flour, Sugar, Cocoa, Butter, Eggs", "Delivery within 3-5 days", "A rich and moist chocolate cake",
				"Keep refrigerated", "Happy Birthday!", null, chocolateFlavor.getName(),"category1");

		vanillaCake = new Cake(2L, "Vanilla Cake", 20.99, 3.00, 9.0, 4.5, 1.1, 1, true,
				"Flour, Sugar, Vanilla, Eggs, Butter", "Deliver within 2-4 business days",
				"A classic vanilla cake perfect for any occasion.", "Store in a cool place", "Congratulations", null,
				vanillaFlavor);

		redVelvetCake = new Cake(3L, "Red Velvet Cake", 30.99, 4.00, 11.0, 5.5, 1.3, 1, false,
				"Flour, Sugar, Cocoa, Red food coloring, Eggs, Butter", "Deliver within 1-3 business days",
				"A rich and moist red velvet cake with cream cheese frosting.", "Keep refrigerated", "Best Wishes",
				null, caramelFlavor);
		redVelvetCakeDTO = new CakeDTO(3L, "Red Velvet Cake", 30.99, 4.00, 11.0, 5.5, 1.3, 1, false,
				"Flour, Sugar, Cocoa, Red food coloring, Eggs, Butter", "Deliver within 1-3 business days",
				"A rich and moist red velvet cake with cream cheese frosting.", "Keep refrigerated", "Best Wishes",
				null, caramelFlavor.getName(),"category1");
		caramelDripCake = new Cake(4L, "Caramel Drip Cake", 60.0, 10.0, 11.0, 6.0, 4.0, 10, false,
				"Flour, Sugar, Butter, Eggs, Caramel Sauce, Cream, Vanilla Extract", "Delivery within 3-4 days",
				"A rich caramel cake with layers of caramel buttercream and caramel drip", "Store in a cool place",
				"Best wishes on your celebration!", null, caramelFlavor);
	}

	@Test
	void testFindByIdSuccess() {

		// Given. Arrange inputs [cakeRespistory.findById(1L) is the input]
		// and targets. [the target is the result cake object].
		// Define the behavior of Mock Object cakeRepository.[The whole thing is the
		// behavior input+target]
		// Translation: Given That : cakeRepository.findById(1L) will return cake

		given(cakeRepository.findById(anyLong())).willReturn(Optional.of(chocolateDelightCake));
		given(cakeToCakeDtoConverter.convert(chocolateDelightCake)).willReturn(chocolateDelightCakeDTO);
		// When Act on the target behavior [means that the output should be as given].
		// When steps should cover the method to be tested. [We are testing findById()
		// method in Service class]

		CakeDTO returnedCakeDTO = cakeService.findById(1L);

		// Then (Assert step). Assert expected outcomes.
		// Asserting outcomes using

		assertEquals(returnedCakeDTO.id(), chocolateDelightCakeDTO.id());
		assertEquals(returnedCakeDTO.name(), chocolateDelightCakeDTO.name());
		assertEquals(returnedCakeDTO.flavorName(), chocolateDelightCakeDTO.flavorName());
		// conclusion: Given That f(X) = Y assert that f(X) will produce Y

		verify(cakeRepository, times(1)).findById(1L);
		// verify(cakeToCakeDtoConverter, times(1)).convert(chocolateDelightCake);
	}

	@Test
	void testFindByIdNotFound() {
		// Given
		given(cakeRepository.findById(Mockito.anyLong())).willReturn(Optional.empty());

		// When
		Throwable thrown = catchThrowable(() -> {
			cakeService.findById(10L);
		});

		// then
		assertThat(thrown).isInstanceOf(SomethingNotFoundException.class)
				.hasMessage("Not Found! ::: " + "Cake With Id: " + 10L);
		verify(cakeRepository, times(1)).findById(10L);

	}

	@Test
	void testSaveCakeSuccess() {
		// pre-requisites.
		// this what should be saved in the database.
		// the null flavor is because it is irrelevant during request, since we use the
		// flavor id from the path variable. we will update it later after checking the
		// flavor exist or not
		Cake vanillaCheeseCake = new Cake(5L, "Vanilla Cheesecake", 40.0, 10.0, 10.0, 4.5, 3.0, 6, false,
				"Cream Cheese, Flour, Sugar, Butter, Eggs, Vanilla Extract", "Delivery within 1-2 days",
				"A creamy and rich vanilla cheesecake", "Store in a cool place", "Best Wishes!", null, null);
		// this what should be returned in the response.
		CakeDTO expectedVanillaCheeseCakeDTO = new CakeDTO(4L, "Vanilla Cheesecake", 40.0, 10.0, 10.0, 4.5, 3.0, 6,
				false, "Cream Cheese, Flour, Sugar, Butter, Eggs, Vanilla Extract", "Delivery within 1-2 days",
				"A creamy and rich vanilla cheesecake", "Store in a cool place", "Best Wishes!", null,
				vanillaFlavor.getName(),"category1");
		// this what should be returned when call flavorService.findById().
		FlavorDTO vanillaFlavorDTO = new FlavorDTO(vanillaFlavor.getId(), vanillaFlavor.getName(),
				vanillaFlavor.getDescription());
		// request DTO
		List<MultipartFile> multipartFiles = new ArrayList<>();
		CakeMultipleFileDTO cakeMultipleFileDTO = new CakeMultipleFileDTO("Vanilla Cheesecake", 40.0, 10.0, 10.0, 4.5,
				3.0, 6, false, "Cream Cheese, Flour, Sugar, Butter, Eggs, Vanilla Extract", "Delivery within 1-2 days",
				"A creamy and rich vanilla cheesecake", "Store in a cool place", "Best Wishes!", multipartFiles);
		// ------------------------------------------------------------------------------------------//
		// given the flavor found.
		given(flavorService.findById(anyLong())).willReturn(vanillaFlavorDTO);
		// given cake name doesn't exist.
		given(cakeRepository.findByName(vanillaCheeseCake.getName())).willReturn(Optional.empty());
		//
		given(cakeDtoToCakeConverter.convert(any(CakeDTO.class))).willReturn(vanillaCheeseCake);
		//
		given(cakeToCakeDtoConverter.convert(any(Cake.class))).willReturn(expectedVanillaCheeseCakeDTO);
		vanillaCheeseCake
				.setFlavor(new Flavor(vanillaFlavorDTO.id(), vanillaFlavorDTO.name(), vanillaFlavorDTO.description()));
		given(cakeRepository.save(any(Cake.class))).willReturn(vanillaCheeseCake);

		CakeDTO returnedCakeDTO = cakeService.save(1L,vanillaCheeseCake.getId(), cakeMultipleFileDTO);

		assertEquals(expectedVanillaCheeseCakeDTO.id(), returnedCakeDTO.id());
		assertEquals(expectedVanillaCheeseCakeDTO.name(), returnedCakeDTO.name());
		assertEquals(expectedVanillaCheeseCakeDTO.flavorName(), returnedCakeDTO.flavorName());
		verify(cakeRepository, times(1)).save(vanillaCheeseCake);
	}

	@Test
	void testSaveCakeFailedAlreadyExist() {
		String cakeNameAlreadyInTheDB = "Red Velvet Cake";
		CakeMultipleFileDTO cakeMultipleFileDTO = new CakeMultipleFileDTO("Red Velvet Cake", 30.99, 4.00, 11.0, 5.5,
				1.3, 1, false, "Flour, Sugar, Cocoa, Red food coloring, Eggs, Butter",
				"Deliver within 1-3 business days", "A rich and moist red velvet cake with cream cheese frosting.",
				"Keep refrigerated", "Best Wishes", null);
		given(cakeRepository.findByName(cakeNameAlreadyInTheDB)).willReturn(Optional.of(redVelvetCake));
		assertThrows(SomethingAlreadyExistException.class,
				() -> cakeService.save(1L,redVelvetCake.getId(), cakeMultipleFileDTO));
		verify(cakeRepository, times(1)).findByName(cakeNameAlreadyInTheDB);
	}

	@Test
	void testFindByNameSuccess() {
		String cakeNameAlreadyInTheDB = "Red Velvet Cake";
		when(cakeRepository.findByName(cakeNameAlreadyInTheDB)).thenReturn(Optional.of(redVelvetCake));
		given(cakeToCakeDtoConverter.convert(redVelvetCake)).willReturn(redVelvetCakeDTO);
		CakeDTO returnedValueDTO = cakeService.findByName(cakeNameAlreadyInTheDB);
		assertEquals(redVelvetCakeDTO.id(), returnedValueDTO.id());
		assertEquals(redVelvetCakeDTO.name(), returnedValueDTO.name());
		assertEquals(redVelvetCakeDTO.flavorName(), returnedValueDTO.flavorName());
		verify(cakeRepository, times(1)).findByName(cakeNameAlreadyInTheDB);
		verify(cakeToCakeDtoConverter, times(1)).convert(redVelvetCake);
	}

	@Test
	void testFindByNameNotFound() {
		String cakeNameNotInTheDB = "Marble cake";
		when(cakeRepository.findByName(cakeNameNotInTheDB)).thenReturn(Optional.empty());
		Throwable thrown = catchThrowable(() -> {
			cakeService.findByName(cakeNameNotInTheDB);
		});
		assertThat(thrown).isInstanceOf(SomethingNotFoundException.class)
				.hasMessage("Not Found! ::: " + "Cake With Name: " + cakeNameNotInTheDB);
		verify(cakeRepository, times(1)).findByName(cakeNameNotInTheDB);
	}

	@Test
	void testFindAll() {
		List<Cake> expectedList = Arrays.asList(chocolateDelightCake, redVelvetCake, vanillaCake);
		given(cakeRepository.findAll()).willReturn(expectedList);
		List<CakeDTO> returnedListDTO = cakeService.findAll();
		assertEquals(expectedList.size(), returnedListDTO.size());
		verify(cakeRepository, times(1)).findAll();
	}

	@Test

	void testUpdateCake() {

		Cake updatedRedVelvetCake = new Cake(3L, "Red Velvet Cake Extra Chocolate", 40.58, 5.00, 10.5, 5.5, 1.3, 1,
				true, "Flour, Sugar, Cocoa, Red food coloring, Eggs, Butter", "Deliver within 1-4 business days",
				"A rich and moist red velvet cake with cream cheese frosting.", "Keep refrigerated", "Best Wishes",
				null, chocolateFlavor);

		CakeDTO updatedRedVelvetCakeDTO = new CakeDTO(3L, "Red Velvet Cake Extra Chocolate", 40.58, 5.00, 10.5, 5.5,
				1.3, 1, true, "Flour, Sugar, Cocoa, Red food coloring, Eggs, Butter",
				"Deliver within 1-4 business days", "A rich and moist red velvet cake with cream cheese frosting.",
				"Keep refrigerated", "Best Wishes", null, chocolateFlavor.getName(),"category1");

		FlavorDTO chocolateFlavorDTO = new FlavorDTO(1L, "Chocolate",
				"Chocolate cake boasts a deep," + " indulgent flavor with rich cocoa undertones and a moist,"
						+ " tender crumb. Its intense and satisfying taste is often"
						+ " enhanced by layers of smooth chocolate ganache or creamy frosting,"
						+ " making it a decadent favorite for chocolate lovers.");
		given(flavorService.findById(chocolateFlavor.getId())).willReturn(chocolateFlavorDTO);
		given(cakeRepository.findById(updatedRedVelvetCake.getId())).willReturn(Optional.of(redVelvetCake));
		given(cakeRepository.save(any(Cake.class))).willReturn(updatedRedVelvetCake);
		given(cakeToCakeDtoConverter.convert(updatedRedVelvetCake)).willReturn(updatedRedVelvetCakeDTO);
		CakeDTO returnedValueCakeDTO = cakeService.update(1L,chocolateFlavorDTO.id(), updatedRedVelvetCake.getId(),
				updatedRedVelvetCakeDTO);

		assertEquals(returnedValueCakeDTO.id(), updatedRedVelvetCake.getId());
		assertEquals(returnedValueCakeDTO.name(), updatedRedVelvetCake.getName());
		assertEquals(returnedValueCakeDTO.flavorName(), updatedRedVelvetCake.getFlavor().getName());
		verify(cakeRepository, times(1)).findById(3L);
		verify(cakeRepository, times(1)).save(any(Cake.class));
		verify(cakeToCakeDtoConverter, times(1)).convert(updatedRedVelvetCake);
	}

	@Test
	void testUpdateFailedIdNotExist() {
		Long flavorId = 1L;
		// this id doesn't exist in the database
		Long cakeId = 5L;
		given(cakeRepository.findById(cakeId)).willThrow(new SomethingNotFoundException("With Id: " + 5L));
		// DTO is irrelevant in that case.
		assertThrows(SomethingNotFoundException.class, () -> cakeService.update(1L,flavorId, cakeId, null));
		verify(cakeRepository, times(1)).findById(5L);
	}

	@Test
	void testDeleteById() {
		// stubbing means looks like the real method.
		when(cakeRepository.findById(1L)).thenReturn(Optional.of(chocolateDelightCake));
		doNothing().when(cakeRepository).deleteById(1L);
		cakeService.deleteById(1L);
		verify(cakeRepository, times(1)).findById(1L);
		verify(cakeRepository, times(1)).deleteById(1L);
	}

	@Test
	void testDeleteByIdFailed() {
		when(cakeRepository.findById(5L)).thenReturn(Optional.empty());
		assertThrows(SomethingNotFoundException.class, () -> cakeService.deleteById(5L));
		verify(cakeRepository, times(1)).findById(5L);
	}

	@Test
	void testFindAllByFlavorId() {
		when(cakeRepository.findByFlavorId(caramelFlavor.getId())).thenReturn(List.of(redVelvetCake, caramelDripCake));
		List<CakeDTO> cakeDTOList = cakeService.findByFlavorId(caramelFlavor.getId());
		assertEquals(2, cakeDTOList.size());
		verify(cakeRepository, times(1)).findByFlavorId(caramelFlavor.getId());
	}

}
