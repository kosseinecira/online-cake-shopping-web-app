package com.cakeshoppingapp.cake;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cakeshoppingapp.category.Category;
import com.cakeshoppingapp.category.CategoryService;
import com.cakeshoppingapp.converters.cake.CakeDtoToCakeConverter;
import com.cakeshoppingapp.converters.cake.CakeToCakeDtoConverter;
import com.cakeshoppingapp.dtoes.CakeDTO;
import com.cakeshoppingapp.dtoes.CategoryDTO;
import com.cakeshoppingapp.dtoes.FlavorDTO;
import com.cakeshoppingapp.flavor.Flavor;
import com.cakeshoppingapp.flavor.FlavorService;
import com.cakeshoppingapp.image.CakeImage;
import com.cakeshoppingapp.system.exceptions.SomethingAlreadyExistException;
import com.cakeshoppingapp.system.exceptions.SomethingNotFoundException;
import com.cakeshoppingapp.utils.Constant;
import com.cakeshoppingapp.utils.FileManagerUtil;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CakeService {

	private final CakeRepository cakeRepository;
	private final CategoryService categoryService;
	private final FlavorService flavorService;

	private final CakeToCakeDtoConverter cakeToCakeDtoConverter;

	private final CakeDtoToCakeConverter cakeDtoToCakeConverter;

	public CakeService(CakeRepository cakeRepository, CategoryService categoryService, FlavorService flavorService,
			CakeToCakeDtoConverter cakeToCakeDtoConverter, CakeDtoToCakeConverter cakeDtoToCakeConverter) {
		super();
		this.cakeRepository = cakeRepository;
		this.categoryService = categoryService;
		this.flavorService = flavorService;
		this.cakeToCakeDtoConverter = cakeToCakeDtoConverter;
		this.cakeDtoToCakeConverter = cakeDtoToCakeConverter;
	}

	public CakeDTO findById(Long id) {
		return cakeRepository.findById(id).map(cake -> cakeToCakeDtoConverter.convert(cake))
				.orElseThrow(() -> new SomethingNotFoundException("Cake With Id: " + id));
	}

	public CakeDTO save(Long categoryId, Long flavorId, CakeDTO cakeDto, List<MultipartFile> cakeImages) {
		FlavorDTO flavorDTO = flavorService.findById(flavorId);
		CategoryDTO categoryDTO = categoryService.findById(categoryId);
		// CHECK THE NAME OF THE CAKE IF IT IS ALREADY EXIST
		handleIfCakeExist(cakeDto.name());
		// save images on the server side (resource folder). and return a list of
		// CakeImagesObject.
		List<CakeImage> list = null;
		if (cakeImages != null)
			if (!cakeImages.isEmpty()) {
				list = saveImage(categoryId, cakeDto.name().replace(" ", ""), cakeImages);
			}
		// Making a DTO
		Cake cakeEntity = cakeDtoToCakeConverter.convert(cakeDto);
		cakeEntity.setCakeImages(list);
		// SET THE FLAVOR OF THE CAKE
		cakeEntity.setFlavor(new Flavor(flavorDTO.id(), flavorDTO.name(), flavorDTO.description()));
		cakeEntity.setCategory(new Category(categoryDTO.id(), categoryDTO.name()));

		Cake cakeResult = cakeRepository.save(cakeEntity);
		return cakeToCakeDtoConverter.convert(cakeResult);
	}

	public CakeDTO findByName(String name) {
		Optional<Cake> cakeOptional = cakeRepository.findByName(name);
		boolean cakeExist = cakeOptional.isPresent();
		if (!cakeExist) {
			throw new SomethingNotFoundException("Cake With Name: " + name);
		}
		return cakeToCakeDtoConverter.convert(cakeOptional.get());
	}

	public List<CakeDTO> findAll() {
		return cakeRepository.findAll().stream().map(cake -> cakeToCakeDtoConverter.convert(cake)).toList();
	}

	public CakeDTO update(Long categoryId, Long flavorId, Long cakeId, CakeDTO cakeUpdate,
			List<MultipartFile> cakeImages) {
		//
		FlavorDTO flavorDTO = flavorService.findById(flavorId);
		CategoryDTO categoryDTO = categoryService.findById(categoryId);

		Cake updatedCake = cakeRepository.findById(cakeId).map(cake -> {

			if (cakeImages != null)
				if (!cakeImages.isEmpty()) {
					try {
						deleteDirectory(cake.getName().replace(" ", ""));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					List<CakeImage> list = saveImage(categoryId, cakeUpdate.name().replace(" ", ""), cakeImages);
				}

			cake.setName(cakeUpdate.name());
			cake.setPrice(cakeUpdate.price());
			cake.setDiscount(cakeUpdate.discount());
			cake.setDiameter(cakeUpdate.diameter());
			cake.setHeight(cakeUpdate.height());
			cake.setWeight(cakeUpdate.weight());
			cake.setNetQuantity(cakeUpdate.netQuantity());
			cake.setItAllergen(cakeUpdate.isItAllergen());
			cake.setIngredients(cakeUpdate.ingredients());
			cake.setDeliveryInformation(cakeUpdate.deliveryInformation());
			cake.setNoteDescription(cakeUpdate.noteDescription());
			cake.setMessageOnCake(cakeUpdate.messageOnCake());
			cake.setCakeImages(cakeUpdate.cakeImages());
			cake.setFlavor(new Flavor(flavorDTO.id(), flavorDTO.name(), flavorDTO.description()));
			cake.setCategory(new Category(categoryDTO.id(), categoryDTO.name()));
			return cakeRepository.save(cake);
		}).orElseThrow(() -> new SomethingNotFoundException("Cake With Id: " + cakeId));
		return cakeToCakeDtoConverter.convert(updatedCake);
	}

	public void deleteById(long id) {
		cakeRepository.findById(id).ifPresentOrElse((cake) -> cakeRepository.deleteById(cake.getId()), () -> {
			throw new SomethingNotFoundException("Cake With Id: " + id);
		});
	}

	// Things we need to consider:
	// in case of cake delete we need to delete all related pictures
	// in case of cake update we need to delete or replace all older pictures

	private List<CakeImage> saveImage(Long categoryId, String cakeName, List<MultipartFile> cakeImages) {
		String s = System.getProperty("file.separator");
		return cakeImages.stream().map(image -> {
			String imageInfo[] = FileManagerUtil.saveImageToPath(image,
					Constant.IMAGE_PATH + s + "cakes_images" + s + cakeName);
			System.out.println("IMAGE NAME :: " + imageInfo[0]);
			System.out.println("IMAGE PATH :: " + imageInfo[1]);
			// Note: The Slashes in the path is URL slashes not system slashes!!
			String result = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/images/cakes_images/" + cakeName + "/" + imageInfo[0]).build().toUriString();

			return new CakeImage(imageInfo[0], result);
		}).toList();

	}

	private void deleteDirectory(String cakeName) throws IOException {
		String s = System.getProperty("file.separator");
		FileManagerUtil.deleteFile(Constant.IMAGE_PATH + s + "cakes_images" + s + cakeName);
	}

	private void handleIfCakeExist(String name) {
		boolean cakeExist = cakeRepository.findByName(name).isPresent();
		if (cakeExist) {
			throw new SomethingAlreadyExistException("Cake With Name: " + name);
		}
	}

	public List<CakeDTO> findByCategoryId(Long categoryId) {
		CategoryDTO categoryDTO = categoryService.findById(categoryId);
		return cakeRepository.findByCategoryId(categoryId).stream().map(cake -> cakeToCakeDtoConverter.convert(cake))
				.toList();
	}

	public List<CakeDTO> findByFlavorId(Long flavorId) {
		FlavorDTO flavorDTO = flavorService.findById(flavorId);
		return cakeRepository.findByFlavorId(flavorId).stream().map(cake -> cakeToCakeDtoConverter.convert(cake))
				.toList();
	}

	public List<CakeDTO> findByCategoryIdAndByFlavorId(Long categoryId, Long flavorId) {
		CategoryDTO categoryDTO = categoryService.findById(categoryId);
		FlavorDTO flavorDTO = flavorService.findById(flavorId);
		return cakeRepository.findByCategoryIdAndFlavorId(categoryId, flavorId).stream()
				.map(cake -> cakeToCakeDtoConverter.convert(cake)).toList();

	}

}
