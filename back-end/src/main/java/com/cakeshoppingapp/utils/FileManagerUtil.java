package com.cakeshoppingapp.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cakeshoppingapp.system.exceptions.NotSupportedException;

import jakarta.servlet.ServletContext;

@Component
public class FileManagerUtil {

	private static ServletContext servletContext;

	public FileManagerUtil(ServletContext servletContext) {

		this.servletContext = servletContext;

	}

	public static String[] saveImageToPath(MultipartFile image, String imagesPath) {
		String fileName = StringUtils.cleanPath(image.getOriginalFilename());
		System.out.println("file name :::: " + fileName);
		if (!Arrays.asList("jpg", "jpeg", "png").contains(StringUtils.getFilenameExtension(fileName)))
			throw new NotSupportedException(
					"File Type: " + StringUtils.getFilenameExtension(fileName) + " Not Supported");
		try {
			FileManagerUtil.saveFile(imagesPath, fileName.replace(" ", ""), image);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(imagesPath);
		return new String[] { fileName, imagesPath };
	}

	private static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
		Path uploadPath = Path.of(uploadDir);
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioe) {
			throw new IOException("Could not save image file: " + fileName, ioe);
		}
	}

	public static void deleteFile(String dir) throws IOException {
		System.out.println("FILE TO BE DELETED :::: " + dir);
		File file = new File(dir);
		FileSystemUtils.deleteRecursively(file);
	}

}
