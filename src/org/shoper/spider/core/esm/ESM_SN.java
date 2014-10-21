package org.shoper.spider.core.esm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
	private static final String prefix = "http://list.suning.com";
	private int count = 40;
	private String url ;
	private String lv1 ; 
	private String lv2 ; 
	private ESM_SN(){
		
	}
	public ESM_SN(String url, String lv1, String lv2) {
		this.url = url;
		this.lv1 = lv1;
		this.lv2 = lv2;
	}

	@Override
	public void process() throws IOException, InterruptedException {
		LOGGER.info("解析开始....");
		String[] prices = new String[] {};
		String[] titles = new String[] {};
		String[] picUrls = new String[] {};
		String[] itemUrls = new String[] {};
		String[] itemIDs = new String[] {};
		//String url = "http://list.suning.com/0-20006-0-0-0-9017.html#sourceUrl4Sa=http://shouji.suning.com/";
		StringBuffer sb = new StringBuffer();
		{
			int errCount = 0;
			do {
				try {
					sb = new StringBuffer(new HtmlClient(url).getHtml());
					break;
				} catch (Exception e) {
					LOGGER.error(e.getStackTrace());
					e.printStackTrace();
					errCount++;
					Thread.sleep(5000);
				}
			} while (errCount <= 3);
		}
		if(sb.length()==0){
			LOGGER.debug("Program over");
			return;
		}
		/*
		  FileOutputStream fileOS = new FileOutputStream(new
		  File("d:\\html.txt")); fileOS.write(sb.toString().getBytes());
		  fileOS.flush(); fileOS.close();
		 */
		// 判断是否有下一页
		int pageIndex = 0;
		for (;;) {
			FileOutputStream fileOutputStream = new FileOutputStream(new File(
					"./"+lv1+"."+lv2+".txt"), true);
			LOGGER.debug("------------Page :" + (pageIndex + 1)
					+ "------------");
			{
				// 获取产品连接和ID
				Pattern pattern = Pattern
						.compile("(?<=class=\"search-bl\"\\s\\shref=')http://product.suning.com/\\d*.html(?='\\s)");
				Matcher matcher = pattern.matcher(sb);
				List<String> urlList = new ArrayList<>(count);
				List<String> idList = new ArrayList<>(count);
				while (matcher.find()) {
					String item_url = matcher.group();
					LOGGER.debug("Get product url:" + item_url);
					urlList.add(item_url);
					String ID = item_url.substring(
							item_url.lastIndexOf('/') + 1,
							item_url.lastIndexOf('.'));
					LOGGER.debug("Get product id:" + ID);
					idList.add(ID);
				}
				itemUrls = urlList.toArray(itemUrls);
				itemIDs = idList.toArray(itemIDs);
			}
			{
				// 获取产品标题,以及产品图片
				Pattern pattern = Pattern
						.compile("<img\\sclass=\\\"err-product\\\"\\ssrc\\d?=\\\"([\\w./_x:]*)\\\"\\salt=\\\"([\u4e00-\u9fa5\\s\\w-\uff08-\uff09/\u3010-\u3011\\+=\\-\\_()<>{}\\[\\]:'\"\\?]*?)\\\".*(?!/>)");
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
				picUrls = picUrlList.toArray(picUrls);
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
					String p = null;
					int errCount = 0;
					do {
						try {
							p = new OCR().recognizeText(new URL(priceUrl),priceUrl.substring(priceUrl.lastIndexOf('.') + 1));
							// 避免被封IP,增加访问间隔---1秒
							Thread.sleep(1000);
							break;
						} catch (Exception e) {
							LOGGER.info("wait 5s to continue get next info");
							LOGGER.info("Get next page error " + (errCount+1)
									+ " times by " + e.getStackTrace());
							Thread.sleep(5000);
							errCount++;
							e.printStackTrace();
						}
					} while (errCount <= 3);
					if(p!=null)
						p = p.replaceAll("[\r\n\\s]", "");
					priceList.add(p);
					LOGGER.info("Get product price:" + p);
				}
				prices = priceList.toArray(prices);
			}
			for (int i = 0; i < picUrls.length; i++) {
				//如果图片没抓出来,那么为null,那么从页面链接里去拿数据
				if(null == prices[i]){
					LOGGER.debug("价格为null,说明获取到图片没解析出..那么通过访问商品页来匹配价格");
					//获取当前图片对应的产品连接
					try {
						String content = new HtmlClient(itemUrls[i]).getHtml();
						Pattern pattern = Pattern.compile("");
						Matcher matcher = pattern.matcher(content);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				String result = "item_ID:" + itemIDs[i] + "\n item_link:"
						+ itemUrls[i] + "\n item_price:" + prices[i]
						+ "\n item_title:" + titles[i] + "\n item_picUrl:"
						+ picUrls[i] + "\n";
				fileOutputStream.write(result.getBytes());
				fileOutputStream.write(("-------------------------------"
						+ (i + 1) + " item-----------------------\n").getBytes());
				LOGGER.debug(result);
			}
			fileOutputStream.flush();
			fileOutputStream.close();
			LOGGER.info("The" + (pageIndex + 1) + " page Analyzered....");

			// check has next page
			LOGGER.info("check has next page");
			url = hasNextPage(sb.toString());
			// if has not next page,break
			if (url == null) {
				LOGGER.info("No next page...");
				pageIndex = 0;
				break;
			}
			LOGGER.info("get the next page");
			LOGGER.info("wait 20s,to reduce the sealing IP risk");
			Thread.sleep(20000);
			// Has next page to continue get product info
			url = prefix + url;
			sb.setLength(0);
			int errCount = 0;
			do {
				try {
					sb.append(new HtmlClient(url).getHtml());
					break;
				} catch (Exception e) {
					LOGGER.info("Get next page error " + errCount
							+ " times by " + e.getStackTrace());
					LOGGER.info("wait 5s to continue get next info");
					errCount++;
					e.printStackTrace();
					Thread.sleep(5000);
				}
			} while (errCount <= 3);
			if(errCount>=3){
				continue;
			}
			pageIndex++;
		}
	}

	@Override
	public String hasNextPage(String html) {
		Pattern pattern = Pattern
				.compile("<a\\sid=\"nextPage\"[\\s\\w=_\\-\\\\/+'\"\\(\\),.<>\u4e00-\u9fa5\uff08-\uff09\u3010-\u3011]* href=\"([\\d/\\-.\\w]+)\">(?=<b)");
		Matcher matcher = pattern.matcher(html);
		if (matcher.find())
			return matcher.group(1);
		return null;
	}

	@Override
	public void destroy() {

	}

	@Override
	public void init() {
	}
}
