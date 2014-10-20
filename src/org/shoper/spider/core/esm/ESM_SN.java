package org.shoper.spider.core.esm;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.hc.tesseract.OCR;
import org.shoper.http.HtmlClient;

public class ESM_SN extends AbstractESM {
	private static Logger LOGGER = Logger.getLogger(ESM_SN.class);
	private int count = 40;
	@Override
	public void process() throws Exception {
		LOGGER.info("解析开始....");
		String[] prices = new String[]{};
		String[] titles = new String[]{};
		String[] picUrls =  new String[]{};
		String[] itemUrls = new String[]{};
		String[] itemIDs = new String[]{};
		String url = "http://list.suning.com/0-20006-0-0-0-9017.html";
		StringBuffer sb = new StringBuffer(new HtmlClient(url).getHtml());
		
		 FileOutputStream fileOutputStream = new FileOutputStream(new File("d:\\t.txt"));
		 
		// fileOutputStream.write(new HtmlClient(url).getHtml().getBytes());
		// fileOutputStream.flush();fileOutputStream.close();
		{
			// 获取产品连接和ID
			Pattern pattern = Pattern.compile("(?<=class=\"search-bl\"\\s\\shref=')http://product.suning.com/\\d*.html(?='\\s)");
			Matcher matcher = pattern.matcher(sb);
			List<String> urlList = new ArrayList<>(count);
			List<String> idList = new ArrayList<>(count);
			while (matcher.find()) {
				String item_url = matcher.group();
				LOGGER.debug("Get product url:" + item_url);
				urlList.add(item_url);
				String ID = item_url.substring(item_url.lastIndexOf('/')+1,item_url.lastIndexOf('.'));
				LOGGER.debug("Get product id:" + ID);
				idList.add(ID);
			}
			itemUrls = urlList.toArray(itemUrls);
			itemIDs = idList.toArray(itemIDs);
		}
		{
			// 获取产品标题,以及产品图片
			Pattern pattern = Pattern
					.compile("<img\\sclass=\"err-product\"\\ssrc\\d?=\"([\\w./_x:]*)\"\\salt=\"([\u4e00-\u9fa5\\s\\w-\uff08\uff09/\\\\_()]*)\".*(?!/>)");
			Matcher matcher = pattern.matcher(sb);
			List<String> titleList = new ArrayList<>(count);
			List<String> picUrlList = new ArrayList<>(count);
			while (matcher.find()) {
				String picUrl = matcher.group(1);
				LOGGER.debug("Get product image url:" + picUrl);
				String title = matcher.group(2);
				LOGGER.debug("Get product title:" + title);
				picUrlList.add(picUrl);
				titleList.add(title);
			}
			picUrls =  picUrlList.toArray(picUrls);
			titles = titleList.toArray(titles);
		}
		{
			// -------------苏宁价格是图片--------------所以需要ocr识别
			Pattern pattern = Pattern
					.compile("(?<=<img class=\"liprice\" src2=\")[\\w:/.-]*(?!=\")");
			Matcher matcher = pattern.matcher(sb);
			List<String> priceList = new ArrayList<>(count);
			while (matcher.find()) {
				String priceUrl = matcher.group();
				LOGGER.info("Get product link:" + priceUrl);
				String p = new OCR().recognizeText(new URL(priceUrl),
						priceUrl.substring(priceUrl.lastIndexOf('.') + 1));
				priceList.add(p);
				LOGGER.info("Get product price:" + p);
				// 避免被封IP,增加访问间隔---1秒
				Thread.sleep(1000);
			}
			prices = priceList.toArray(prices);
		}
		for (int i = 0; i < picUrls.length; i++) {
			String result = "item_ID:"+itemIDs[i]+"\n item_link:" + itemUrls[i] + "\n item_price:"+ prices[i] + "\n item_title:" + titles[i]+ "\n item_picUrl:" + picUrls[i]+"\n";
			fileOutputStream.write(result.getBytes());
			fileOutputStream.write(("-------------------------------"+ (i+1) +" item-----------------------").getBytes());
			LOGGER.debug(result);
		}
		fileOutputStream.flush();fileOutputStream.close();
		LOGGER.info("解析完毕....");
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}
}
