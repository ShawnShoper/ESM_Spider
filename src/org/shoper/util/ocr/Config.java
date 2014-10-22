package org.shoper.util.ocr;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
	public static String app_path;
	public static String tmp_path;

	static {

		try {
			Properties p = new Properties();
			InputStream input = Config.class.getResourceAsStream("/ocr.properties");
			p.load(input);
			if (p.containsKey("tesseract.app.path"))
				app_path = p.getProperty("tesseract.app.path");
			if (p.containsKey("tesseract.ocr.temp"))
				tmp_path = p.getProperty("tesseract.ocr.temp");
			else
				tmp_path = "/opt";
			File file = new File(tmp_path);
			if (!file.exists() || file.isDirectory())
				file.mkdirs();
			System.out.println("APP_PATH : " + app_path);
			System.out.println("TMP_PATH : " + tmp_path);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
