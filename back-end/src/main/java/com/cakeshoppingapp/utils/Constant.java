package com.cakeshoppingapp.utils;

public class Constant {

	private static final String IMAGE_PATH () {
		String s = System.getProperty("file.separator");
		String ImagesSavingPath = "_images";
		ImagesSavingPath = ImagesSavingPath.replace("_", s);
		return System.getProperty("user.dir") + ImagesSavingPath;
	}
	public static final String IMAGE_PATH = IMAGE_PATH();
}
