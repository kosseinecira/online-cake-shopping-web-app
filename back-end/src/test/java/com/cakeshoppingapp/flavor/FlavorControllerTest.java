package com.cakeshoppingapp.flavor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.cakeshoppingapp.dtoes.FlavorDTO;
import com.cakeshoppingapp.system.StatusCode;
import com.cakeshoppingapp.system.exceptions.SomethingAlreadyExistException;
import com.cakeshoppingapp.system.exceptions.SomethingNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class FlavorControllerTest {

	@Autowired
	private MockMvc mockMvc;
	// the scope is per test method. spring will create a new instance for each
	// test.

	@MockBean
	private FlavorService flavorService;

	private FlavorDTO flavorDTO;
	private FlavorDTO expectedFlavorDTO;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		flavorDTO = new FlavorDTO(3L, "Caramel", "Caramel cake features a luxurious, buttery sweetness "
				+ "with warm, toasty notes of caramelized " + "sugar. Its flavor is reminiscent of rich toffee, with a"
				+ " smooth and slightly smoky depth that " + "creates a sumptuous and comforting dessert experience.");
		expectedFlavorDTO = flavorDTO;
	}

	@Test
	void test() {
	}

	@Test
	void testSaveFlavor() throws Exception {

		FlavorDTO exptectedNewFlavorDTO = new FlavorDTO(5L, "Lemon",
				"Lemon cake offers a bright, zesty flavor with a perfect balance of tartness and sweetness. "
						+ "Infused with fresh lemon juice and zest, it provides a refreshing and invigorating taste, "
						+ "complemented by a moist and tender crumb. This vibrant cake is often paired with tangy lemon "
						+ "glaze or creamy frosting, creating a delightful and uplifting treat.");

		String flavorAsJson = objectMapper.writeValueAsString(exptectedNewFlavorDTO);

		// Given
		given(flavorService.save(any(FlavorDTO.class))).willReturn(exptectedNewFlavorDTO);

		// When and Then
		this.mockMvc
				.perform(post("/flavors").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
						.content(flavorAsJson))
				.andExpect(jsonPath("$.flag").value(true)).andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
				.andExpect(jsonPath("$.message").value("Flavor Saved Successfully!"))
				.andExpect(jsonPath("$.data.id").isNotEmpty())
				.andExpect(jsonPath("$.data.name").value(exptectedNewFlavorDTO.name()))
				.andExpect(jsonPath("$.data.description").value(exptectedNewFlavorDTO.description()));

	}

	@Test
	void testSaveFlavorFailed() throws Exception {
		// pre-requisites
		FlavorDTO newFlavorForDTO = flavorDTO;
		// Like we said before: the DTO doesn't have an ID if it's coming from request.
		// So we set it as null.
		String flavorAsJson = objectMapper.writeValueAsString(newFlavorForDTO);
		// given
		given(flavorService.save(any(FlavorDTO.class)))
				.willThrow(new SomethingAlreadyExistException("Flavor With Name: " + newFlavorForDTO.name()));
		// When and Then
		this.mockMvc
				.perform(post("/flavors").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
						.content(flavorAsJson))
				.andExpect(jsonPath("$.flag").value(false))
				.andExpect(jsonPath("$.code").value(StatusCode.ALREADY_EXIST))
				.andExpect(jsonPath("$.message")
						.value("Already Exist In The Database::: " + "Flavor With Name: " + newFlavorForDTO.name()))
				.andExpect(jsonPath("$.data").isEmpty());
	}

	@Test
	void testFindAll() throws Exception {
		List<FlavorDTO> expectedFlavorsAsDTO = new ArrayList<>();
		FlavorDTO chocolateFlavorDTO = new FlavorDTO(1L, "Chocolate",
				"Chocolate cake boasts a deep," + " indulgent flavor with rich cocoa undertones and a moist,"
						+ " tender crumb. Its intense and satisfying taste is often"
						+ " enhanced by layers of smooth chocolate ganache or creamy frosting,"
						+ " making it a decadent favorite for chocolate lovers.");
		FlavorDTO vanillaFlavorDTO = new FlavorDTO(2L, "Vanilla",
				"Vanilla cake offers a classic, timeless flavor characterized by its delicate sweetness and rich,"
						+ " creamy notes of pure vanilla. Its light and fluffy texture makes"
						+ " it a versatile dessert that pairs well with various frostings and"
						+ " fillings, providing a comforting and nostalgic treat.");

		FlavorDTO caramelFlavorDTO = new FlavorDTO(3L, "Caramel", "\r\n"
				+ "Caramel cake features a luxurious, buttery sweetness " + "with warm, toasty notes of caramelized "
				+ "sugar. Its flavor is reminiscent of rich toffee, with a" + " smooth and slightly smoky depth that "
				+ "creates a sumptuous and comforting dessert experience.");
		expectedFlavorsAsDTO.add(chocolateFlavorDTO);
		expectedFlavorsAsDTO.add(vanillaFlavorDTO);
		expectedFlavorsAsDTO.add(caramelFlavorDTO);
		// given
		given(flavorService.findAll()).willReturn(expectedFlavorsAsDTO);
		// When and Then
		this.mockMvc.perform(get("/flavors").accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.flag").value(true)).andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
				.andExpect(jsonPath("$.message").value("All Flavors Found!"))
				.andExpect(jsonPath("$.data", Matchers.hasSize(expectedFlavorsAsDTO.size())));
	}

	@Test
	void testFindByName() throws Exception {

		given(flavorService.findByName(expectedFlavorDTO.name())).willReturn(expectedFlavorDTO);
		this.mockMvc.perform(get("/flavors").param("name", expectedFlavorDTO.name()).accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.flag").value(true)).andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
				.andExpect(jsonPath("$.message").value("Flavor With The Requested Name Is Found!"))
				.andExpect(jsonPath("$.data.id").value(expectedFlavorDTO.id()))
				.andExpect(jsonPath("$.data.name").value(expectedFlavorDTO.name()))
				.andExpect(jsonPath("$.data.description").value(expectedFlavorDTO.description()));
	}

	@Test
	void testFindByNameFailed() throws Exception {
		String randomFlavorName = "Carrot";
		given(flavorService.findByName(randomFlavorName))
				.willThrow(new SomethingNotFoundException("Flavor With Name: " + randomFlavorName));

		this.mockMvc.perform(get("/flavors").param("name", randomFlavorName).accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.flag").value(false)).andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
				.andExpect(jsonPath("$.message").value("Not Found! ::: " + "Flavor With Name: " + randomFlavorName))
				.andExpect(jsonPath("$.data").isEmpty());
	}

	@Test
	void testFindByIdSuccess() throws Exception {
		given(flavorService.findById(flavorDTO.id())).willReturn(expectedFlavorDTO);
		this.mockMvc.perform(get("/flavors/" + flavorDTO.id()).accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.flag").value(true)).andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
				.andExpect(jsonPath("$.message").value("Flavor With The Requested Name Is Found!"))
				.andExpect(jsonPath("$.data.id").value(expectedFlavorDTO.id()))
				.andExpect(jsonPath("$.data.name").value(expectedFlavorDTO.name()))
				.andExpect(jsonPath("$.data.description").value(expectedFlavorDTO.description()));
	}

	@Test
	void testFindByIdFailed() throws Exception {
		// just a random id;
		Long randomId = 16875645403545L;
		given(flavorService.findById(16875645403545L))
				.willThrow(new SomethingNotFoundException("Flavor With Id: " + randomId));

		this.mockMvc.perform(get("/flavors/{id}", randomId).accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.flag").value(false)).andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
				.andExpect(jsonPath("$.message").value("Not Found! ::: " + "Flavor With Id: " + randomId))
				.andExpect(jsonPath("$.data").isEmpty());
	}

	@Test
	void testUpdateFlavorSuccess() throws Exception {
		FlavorDTO exptectedUpdatedFlavorDTO = new FlavorDTO(1L, "Black Chocolate",
				"Black chocolate cake, also known as dark chocolate cake,"
						+ " delivers an intense and sophisticated flavor profile with deep,"
						+ " bold cocoa notes and a hint of bitterness."
						+ " Its rich and dense texture is complemented by a slightly less"
						+ " sweet taste, appealing to those who appreciate the robust and"
						+ " complex nuances of high-quality dark chocolate."
						+ " This decadent cake often pairs well with a variety of fillings"
						+ " and frostings, such as ganache or espresso-infused cream,"
						+ " enhancing its luxurious experience.");
		// DTO To JSON
		String flavorAsJson = objectMapper.writeValueAsString(exptectedUpdatedFlavorDTO);

		given(flavorService.update(eq(1L), any(FlavorDTO.class))).willReturn(exptectedUpdatedFlavorDTO);

		// When and Then
		mockMvc.perform(put("/flavors/{id}", 1L).content(flavorAsJson).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.flag").value(true))
				.andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
				.andExpect(jsonPath("$.message").value("Flavor Updated Successfully!"))
				.andExpect(jsonPath("$.data.id").value(exptectedUpdatedFlavorDTO.id()))
				.andExpect(jsonPath("$.data.name").value(exptectedUpdatedFlavorDTO.name()))
				.andExpect(jsonPath("$.data.description").value(exptectedUpdatedFlavorDTO.description()));

	}

	@Test
	void testUpdateFlavorFailed() throws Exception {
		// pre-requisites
		FlavorDTO flavorUpdatesDTO = new FlavorDTO(4651546511L, "Potato", "Potato Flavor Description");

		String flavorAsJson = objectMapper.writeValueAsString(flavorUpdatesDTO);
		// given
		given(flavorService.update(eq(4651546511L), any(FlavorDTO.class)))
				.willThrow(new SomethingNotFoundException("Flavor With Id: " + 4651546511L));

		this.mockMvc
				.perform(put("/flavors/{id}", flavorUpdatesDTO.id()).accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content(flavorAsJson))
				.andExpect(jsonPath("$.flag").value(false)).andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
				.andExpect(jsonPath("$.message").value("Not Found! ::: " + "Flavor With Id: " + flavorUpdatesDTO.id()))
				.andExpect(jsonPath("$.data").isEmpty());
	}

	@Test
	void deleteFlavorById() throws Exception {
		doNothing().when(flavorService).deleteById(flavorDTO.id());
		this.mockMvc.perform(delete("/flavors/{id}", flavorDTO.id()).accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.flag").value(true)).andExpect(jsonPath("$.code").value(StatusCode.SUCCESS))
				.andExpect(jsonPath("$.message").value("Flavor Deleted Successfully!"))
				.andExpect(jsonPath("$.data").isEmpty());
	}

	@Test
	void deleteFlavorByIdFailed() throws Exception {
		Long randomId = 64513147L;
		doThrow(new SomethingNotFoundException("Flavor With Id: " + randomId)).when(flavorService).deleteById(randomId);
		this.mockMvc.perform(delete("/flavors/{id}", randomId).accept(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.flag").value(false)).andExpect(jsonPath("$.code").value(StatusCode.NOT_FOUND))
				.andExpect(jsonPath("$.message").value("Not Found! ::: " + "Flavor With Id: " + randomId))
				.andExpect(jsonPath("$.data").isEmpty());
	}

}
