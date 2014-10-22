package org.shoper.util.ocr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jdesktop.swingx.util.OS;

public class OCR {

	private final String EOL = System.getProperty("line.separator");

	public String recognizeText(String file, String imageFormat) throws FileNotFoundException, IOException, InterruptedException {
		return this.recognizeText(new FileInputStream(file), imageFormat);
	}

	public String recognizeText(URL url, String imageFormat) throws IOException, InterruptedException {
		return this.recognizeText(url.openStream(), imageFormat);
	}

	public String recognizeText(InputStream input, String imageFormat) throws IOException, InterruptedException {
		File tempImage = ImageIOHelper.createImage(input, imageFormat);
		//BufferedImage bi = ImageIO.read(input);
		//ImageFilter imageFilter = new ImageFilter(bi);
		//File file = new File("./test.tif");
		//ImageIO.write(new ImageFilter(imageFilter.grayFilter()).sharp(),"tif",file);
		File outputFile = new File(Config.tmp_path + "output");
		StringBuffer strB = new StringBuffer();
		List<String> cmd = new ArrayList<String>();
		if (OS.isWindows()) {
			cmd.add(Config.app_path + "tesseract");
		} else if (OS.isLinux()) {
			cmd.add("tesseract");
		} else {
			cmd.add(Config.app_path + "tesseract");
		}
		//cmd.add(tempImage.getName());
		cmd.add(tempImage.getName());
		
		cmd.add(outputFile.getName());

		ProcessBuilder pb = new ProcessBuilder();
		pb.directory(new File(Config.tmp_path));
		pb.command(cmd);
		pb.redirectErrorStream(true);

		Process process = pb.start();
		int w = process.waitFor();
System.out.println(tempImage.getAbsolutePath());
		//tempImage.delete();

		if (w == 0) {
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(outputFile.getAbsolutePath() + ".txt"), "UTF-8"));

			String str;
			while ((str = in.readLine()) != null) {
				strB.append(str).append(EOL);
			}
			in.close();
		} else {
			String msg;
			switch (w) {
			case 1:
				msg = "Errors accessing files.There may be spaces in your image's filename.";
				break;
			case 29:
				msg = "Cannot recongnize the image or its selected region.";
				break;
			case 31:
				msg = "Unsupported image format.";
				break;
			default:
				msg = "Errors occurred.";
			}
			tempImage.delete();
			throw new RuntimeException(msg);
		}
		new File(outputFile.getAbsolutePath() + ".txt").delete();
		return strB.toString();
	}

}
