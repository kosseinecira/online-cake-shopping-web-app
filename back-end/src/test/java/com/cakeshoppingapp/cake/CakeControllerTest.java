package com.cakeshoppingapp.cake;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import com.cakeshoppingapp.category.Category;
import com.cakeshoppingapp.dtoes.CakeDTO;
import com.cakeshoppingapp.dtoes.FlavorDTO;
import com.cakeshoppingapp.system.StatusCode;
import com.cakeshoppingapp.system.exceptions.SomethingAlreadyExistException;
import com.cakeshoppingapp.system.exceptions.SomethingNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

//Once we start this test class , SpringBoot will auto configure the MockMvc and Inject it inside the test class

@SpringBootTest
@AutoConfigureMockMvc
public class CakeControllerTest {

	// will perform a fake HTTP request
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CakeService cakeService;

	@Autowired
	private ObjectMapper objectMapper;
	private CakeDTO chocolateDelightCakeDTO, vanillarCakeDTO, redVelvetCakeDTO;
	private FlavorDTO chocolateFlavorDTO, vanillaFlavorDTO, caramelFlavorDTO;
	private Category cakeCategory, traditionalCategory;
	private String base_url;

	private HttpHeaders headers;

	@BeforeEach
	void setUp() throws Exception {

		cakeCategory = new Category(1L, "Cake",
				"https://images.pexels.com/photos/1414234/pexels-photo-1414234.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1");
		traditionalCategory = new Category(3L, "Traditional",
				"https://images.pexels.com/photos/10865949/pexels-photo-10865949.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1");

		chocolateFlavorDTO = new FlavorDTO(1L, "Chocolate",
				"Chocolate cake boasts a deep," + " indulgent flavor with rich cocoa undertones and a moist,"
						+ " tender crumb. Its intense and satisfying taste is often"
						+ " enhanced by layers of smooth chocolate ganache or creamy frosting,"
						+ " making it a decadent favorite for chocolate lovers.");

		vanillaFlavorDTO = new FlavorDTO(2L, "Vanilla",
				"Vanilla cake offers a classic, timeless flavor characterized by its delicate sweetness and rich,"
						+ " creamy notes of pure vanilla. Its light and fluffy texture makes"
						+ " it a versatile dessert that pairs well with various frostings and"
						+ " fillings, providing a comforting and nostalgic treat.");

		caramelFlavorDTO = new FlavorDTO(3L, "Caramel", "Caramel cake features a luxurious, buttery sweetness "
				+ "with warm, toasty notes of caramelized " + "sugar. Its flavor is reminiscent of rich toffee, with a"
				+ " smooth and slightly smoky depth that " + "creates a sumptuous and comforting dessert experience.");

		chocolateDelightCakeDTO = new CakeDTO(1L, "Chocolate Delight", 25.5, 5.0, 8.0, 4.0, 1.5, 10, false,
				"Flour, Sugar, Cocoa, Butter, Eggs", "Delivery within 3-5 days", "A rich and moist chocolate cake",
				"Keep refrigerated", "Happy Birthday!", null, chocolateFlavorDTO.name(), traditionalCategory.getName());

		vanillarCakeDTO = new CakeDTO(2L, "Vanilla Cake", 20.99, 3.00, 9.0, 4.5, 1.1, 1, true,
				"Flour, Sugar, Vanilla, Eggs, Butter", "Deliver within 2-4 business days",
				"A classic vanilla cake perfect for any occasion.", "Store in a cool place", "Congratulations", null,
				vanillaFlavorDTO.name(), cakeCategory.getName());

		redVelvetCakeDTO = new CakeDTO(3L, "Red Velvet Cake", 30.99, 4.00, 11.0, 5.5, 1.3, 1, false,
				"Flour, Sugar, Cocoa, Red food coloring, Eggs, Butter", "Deliver within 1-3 business days",
				"A rich and moist red velvet cake with cream cheese frosting.", "Keep refrigerated", "Best Wishes",
				null, caramelFlavorDTO.name(), cakeCategory.getName());

		base_url = "";
		headers = new HttpHeaders();
		headers.setBasicAuth("email1@gmail.com", "password1");
	}

	@Test

	void testFindCakeByIdSuccess() throws Exception {
		// Given
		given(cakeService.findById(1L)).willReturn(chocolateDelightCakeDTO);

		// When and then [combined] ,
		// We invoked the handler method (findById in controller) Get method in the
		// controller using fake HTTP request with the help of mockMvc bean

		this.mockMvc
				.perform(get("/cakes/{cakeId}", chocolateFlavorDTO.id()).accept(MediaType.APPLICATION_JSON)
						.headers(headers))
				.andExpect(jsonPath("$.flag").value(true)).andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
				.andExpect(jsonPath("$.message").value("Cake Found!")).andExpect(jsonPath("$.data.id").value(1L))
				.andExpect(jsonPath("$.data.name").value(chocolateDelightCakeDTO.name()));
	}

	@Test

	void testFindCakeByIdNotFound() throws Exception {
		given(cakeService.findById(Mockito.anyLong())).willThrow(new SomethingNotFoundException("Cake With Id: " + 5L));

		this.mockMvc.perform(get("/cakes/{id}", 1L).accept(MediaType.APPLICATION_JSON).headers(headers))
				.andExpect(jsonPath("$.flag").value(false)).andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
				.andExpect(jsonPath("$.message").value("Not Found! ::: " + "Cake With Id: " + 5L));

	}

	@Test
	void testFindCakeByName() throws Exception {
		// Given
		given(cakeService.findByName("Vanilla Cake")).willReturn(vanillarCakeDTO);
		mockMvc.perform(
				get("/cakes").queryParam("name", "Vanilla Cake").accept(MediaType.APPLICATION_JSON).headers(headers))
				.andExpect(jsonPath("$.flag").value(true)).andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
				.andExpect(jsonPath("$.message").value("Cake Found!"))
				.andExpect(jsonPath("$.data.id").value(vanillarCakeDTO.id()))
				.andExpect(jsonPath("$.data.name").value(vanillarCakeDTO.name()));
	}

	@Test
	void testFindCakeByNameNotFound() throws Exception {
		// just a random name that does not exist in the database
		String name = "Marble cake";
		given(cakeService.findByName(name)).willThrow(new SomethingNotFoundException("Cake With Name: " + name));

		this.mockMvc.perform(get("/cakes").queryParam("name", name).accept(MediaType.APPLICATION_JSON).headers(headers))
				.andExpect(jsonPath("$.flag").value(false)).andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
				.andExpect(jsonPath("$.message").value("Not Found! ::: " + "Cake With Name: " + name));

	}

	@Test // got some errors I will try to fix it later
	void testSaveCake() throws Exception {
		CakeDTO classicVanillaLayerCakeDTO = new CakeDTO(5L, "Classic Vanilla Layer Cake", 50.0, 12.0, 12.0, 6.0, 4.0,
				8, false, "Flour, Sugar, Butter, Eggs, Vanilla Extract, Baking Powder, Milk",
				"Delivery within 3-5 days", "A classic vanilla layer cake with rich buttercream frosting",
				"Store in a cool place", "Congratulations!", null, vanillaFlavorDTO.name(),
				traditionalCategory.getName());

		String dtoAsJson = objectMapper.writeValueAsString(classicVanillaLayerCakeDTO);

		given(cakeService.save(traditionalCategory.getId(), vanillaFlavorDTO.id(), classicVanillaLayerCakeDTO, null))
				.willReturn(classicVanillaLayerCakeDTO);

		MockMultipartFile cakeData = new MockMultipartFile("cakeDto", "", "application/json", dtoAsJson.getBytes());

		mockMvc.perform(multipart(HttpMethod.POST, "/categories/{categoryId}/flavors/{flavorId}/cakes",
				traditionalCategory.getId(), vanillaFlavorDTO.id()).file(cakeData).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).headers(headers)).andExpect(jsonPath("$.flag").value(true))
				.andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
				.andExpect(jsonPath("$.message").value("Cake Saved Successfully!"))
				.andExpect(jsonPath("$.data.id").value(classicVanillaLayerCakeDTO.id()))
				.andExpect(jsonPath("$.data.name").value(classicVanillaLayerCakeDTO.name()))
				.andExpect(jsonPath("$.data.flavorName").value(classicVanillaLayerCakeDTO.flavorName())).andDo(print());
	}

	@Test
	void testSaveCakeFailedAlreadyExist() throws Exception {

		CakeDTO vanillaCakeDto = new CakeDTO(2L, "Vanilla Cake", 50.0, 12.0, 12.0, 6.0, 4.0, 8, false,
				"Flour, Sugar, Butter, Eggs, Vanilla Extract, Baking Powder, Milk", "Delivery within 3-5 days",
				"A classic vanilla layer cake with rich buttercream frosting", "Store in a cool place",
				"Congratulations!", null, vanillaFlavorDTO.name(), traditionalCategory.getName());

		String dtoAsJson = objectMapper.writeValueAsString(vanillaCakeDto);

		given(cakeService.save(traditionalCategory.getId(), vanillaFlavorDTO.id(), vanillaCakeDto, null))
				.willThrow(new SomethingAlreadyExistException("Cake With Name: " + chocolateDelightCakeDTO.name()));

		MockMultipartFile cakeData = new MockMultipartFile("cakeDto", "", "application/json", dtoAsJson.getBytes());

		mockMvc.perform(multipart(HttpMethod.POST, "/categories/{categoryId}/flavors/{flavorId}/cakes",
				traditionalCategory.getId(), vanillaFlavorDTO.id()).file(cakeData).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).headers(headers)).andExpect(jsonPath("$.flag").value(false))
				.andExpect(jsonPath("$.code").value(StatusCode.ALREADY_EXIST)).andExpect(jsonPath("$.message").value(
						"Already Exist In The Database::: " + "Cake With Name: " + chocolateDelightCakeDTO.name()));
	}

	@Test
	void testFindAll() throws Exception {
		List<CakeDTO> expectedCakesDTO = Arrays.asList(vanillarCakeDTO, chocolateDelightCakeDTO, redVelvetCakeDTO);
		when(cakeService.findAll()).thenReturn(expectedCakesDTO);

		this.mockMvc.perform(get("/cakes").accept(MediaType.APPLICATION_JSON).headers(headers))
				.andExpect(jsonPath("$.flag").value(true)).andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
				.andExpect(jsonPath("$.message").value("All Cakes Found!"))
				.andExpect(jsonPath("$.data", Matchers.hasSize(expectedCakesDTO.size())));
		verify(cakeService, times(1)).findAll();
	}

}
