package org.shoper.util.ocr;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Locale;
import java.util.UUID;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import com.sun.media.imageio.plugins.tiff.TIFFImageWriteParam;

public class ImageIOHelper1 {

	public static File createImage(URL url, String imageFormat) throws IOException {
		System.out.println(url + " >> " + imageFormat);
		return createImage(url.openStream(), imageFormat);
	}

	public static File createImage(File file, String imageFormat) throws IOException {
		System.out.println(file.getAbsolutePath() + " >> " + imageFormat);
		return createImage(new FileInputStream(file), imageFormat);
	}

	public static File createImage(InputStream stream, String imageFormat) throws IOException {
		File tempFile = null;
		Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(imageFormat);

		ImageReader reader = readers.next();

		ImageInputStream iis = ImageIO.createImageInputStream(stream);
		reader.setInput(iis);
		// Read the stream metadata
		IIOMetadata streamMetadata = reader.getStreamMetadata();

		// Set up the writeParam
		TIFFImageWriteParam tiffWriteParam = new TIFFImageWriteParam(Locale.CHINESE);
		tiffWriteParam.setCompressionMode(ImageWriteParam.MODE_DISABLED);

		// Get tif writer and set output to file
		Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("tiff");
		ImageWriter writer = writers.next();

		BufferedImage bi = reader.read(0);
		IIOImage image = new IIOImage(bi, null, reader.getImageMetadata(0));
		tempFile = new File(Config.tmp_path + UUID.randomUUID().toString() + ".tif");
		ImageOutputStream ios = ImageIO.createImageOutputStream(tempFile);
		writer.setOutput(ios);
		writer.write(streamMetadata, image, tiffWriteParam);
		ios.close();

		writer.dispose();
		reader.dispose();

		return tempFile;

	}

}