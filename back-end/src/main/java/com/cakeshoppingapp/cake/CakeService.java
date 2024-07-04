package com.cakeshoppingapp.cake;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cakeshoppingapp.converters.cake.CakeDtoToCakeConverter;
import com.cakeshoppingapp.converters.cake.CakeToCakeDtoConverter;
import com.cakeshoppingapp.dtoes.CakeDTO;
import com.cakeshoppingapp.dtoes.CakeMultipleFileDTO;
import com.cakeshoppingapp.dtoes.FlavorDTO;
import com.cakeshoppingapp.flavor.Flavor;
import com.cakeshoppingapp.flavor.FlavorService;
import com.cakeshoppingapp.image.CakeImage;
import com.cakeshoppingapp.system.exceptions.SomethingAlreadyExistException;
import com.cakeshoppingapp.system.exceptions.SomethingNotFoundException;
import com.cakeshoppingapp.utils.FileUploadUtil;

import jakarta.servlet.ServletContext;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CakeService {

	private CakeRepository cakeRepository;

	private FlavorService flavorService;

	private CakeToCakeDtoConverter cakeToCakeDtoConverter;

	private CakeDtoToCakeConverter cakeDtoToCakeConverter;

	@Autowired
	private ServletContext servletContext;

	public CakeService(CakeRepository cakeRepository, FlavorService flavorService,
			CakeToCakeDtoConverter cakeToCakeDtoConverter, CakeDtoToCakeConverter cakeDtoToCakeConverter) {
		this.cakeRepository = cakeRepository;
		this.flavorService = flavorService;
		this.cakeToCakeDtoConverter = cakeToCakeDtoConverter;
		this.cakeDtoToCakeConverter = cakeDtoToCakeConverter;
	}

	public CakeDTO findById(Long id) {
		return cakeRepository.findById(id).map(cake -> cakeToCakeDtoConverter.convert(cake))
				.orElseThrow(() -> new SomethingNotFoundException("Cake With Id: " + id));
	}

	public CakeDTO save(Long flavorId, CakeMultipleFileDTO cakeMultipleFileDTO) {

		FlavorDTO flavorDTO = flavorService.findById(flavorId);
		// CHECK THE NAME OF THE CAKE IF IT IS ALREADY EXIST
		handleIfCakeExist(cakeMultipleFileDTO.name());
		// save images on the server side (resource folder). and return a list of
		// CakeImagesObject.
		List<CakeImage> list = null;
		if (!cakeMultipleFileDTO.cakeImages().isEmpty() && cakeMultipleFileDTO.cakeImages() != null) {
			list = handleImageSaving(flavorId, cakeMultipleFileDTO.name(), cakeMultipleFileDTO.cakeImages());
		}
		// Making a DTO
		CakeDTO cakeDTO = new CakeDTO(null, cakeMultipleFileDTO.name(), cakeMultipleFileDTO.price(),
				cakeMultipleFileDTO.discount(), cakeMultipleFileDTO.diameter(), cakeMultipleFileDTO.height(),
				cakeMultipleFileDTO.weight(), cakeMultipleFileDTO.netQuantity(), cakeMultipleFileDTO.isItAllergen(),
				cakeMultipleFileDTO.ingredients(), cakeMultipleFileDTO.deliveryInformation(),
				cakeMultipleFileDTO.description(), cakeMultipleFileDTO.noteDescription(),
				cakeMultipleFileDTO.messageOnCake(), list, flavorDTO.name());

		Cake cakeEntity = cakeDtoToCakeConverter.convert(cakeDTO);
		// SET THE FLAVOR OF THE CAKE
		cakeEntity.setFlavor(new Flavor(flavorDTO.id(), flavorDTO.name(), flavorDTO.description()));

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

	public CakeDTO update(Long flavorId, Long cakeId, CakeDTO cakeUpdate) {
		//
		FlavorDTO flavorDTO = flavorService.findById(flavorId);

		Cake updatedCake = cakeRepository.findById(cakeId).map(cake -> {
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
			return cakeRepository.save(cake);
		}).orElseThrow(() -> new SomethingNotFoundException("Cake With Id: " + cakeId));
		return cakeToCakeDtoConverter.convert(updatedCake);
	}

	public void deleteById(long id) {
		cakeRepository.findById(id).ifPresentOrElse((cake) -> cakeRepository.deleteById(cake.getId()), () -> {
			throw new SomethingNotFoundException("Cake With Id: " + id);
		});
	}

	public List<CakeDTO> findByFlavorId(Long flavorId) {
		FlavorDTO flavorDTO = flavorService.findById(flavorId);
		return cakeRepository.findByFlavorId(flavorId).stream().map(cake -> cakeToCakeDtoConverter.convert(cake))
				.toList();
	}

	private List<CakeImage> handleImageSaving(Long flavorId, String cakeName, List<MultipartFile> imageList) {
		String rootPath = servletContext.getRealPath("\\");
		System.out.println(rootPath);
		String imagesPath = "resources\\static\\images\\flavors_images\\";
		return imageList.stream().map(image -> {
			String fileName = StringUtils.cleanPath(image.getOriginalFilename());
			String imagePath = rootPath + imagesPath + flavorId + "\\cakes_images\\" + cakeName + "_images" + fileName;
			String imageExtension = "";
			try {
				FileUploadUtil.saveFile(imagePath, fileName, image);
				imageExtension = StringUtils.getFilenameExtension(imagePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return new CakeImage(fileName, imagePath, imageExtension);
		}).toList();
	}

	private void handleIfCakeExist(String name) {
		boolean cakeExist = cakeRepository.findByName(name).isPresent();
		if (cakeExist) {
			throw new SomethingAlreadyExistException("Cake With Name: " + name);
		}
	}

}
