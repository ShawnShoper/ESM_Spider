package org.shoper.spider;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.shoper.util.ocr.OCR;

public class Main {
	public static void main(String[] args) throws MalformedURLException,
			IOException, InterruptedException {
		{
			String[] urls = { "http://price2.suning.cn/webapp/wcs/stores/prdprice/22953444_9017_10000_9-1.png" };
			for (String url : urls) {
				test(url, "png");
				System.out.println("");
			}
		}
	}

	public static void test(String url, String type)
			throws MalformedURLException, IOException, InterruptedException {
		String valCode = new OCR().recognizeText(new URL(url), type);
		System.out.println("VAL:" + valCode.trim());
	}

}
