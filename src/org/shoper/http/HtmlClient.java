package org.shoper.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import javax.management.RuntimeErrorException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.RedirectException;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.DefaultHttpParams;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.shoper.http.exception.FunctionException;
import org.shoper.http.exception.WebpageException;

/**
 * 获取网页内容
 * 
 * @author LiuShangYang
 * 
 */
public class HtmlClient {
	public static long htmlSizeB = 0;

	/**
	 * 初始化设置
	 * 
	 * @author LiuShangYang 2014年6月12日
	 * @throws Exception 
	 */
	public HtmlClient(String url) throws Exception {
		DefaultHttpParams.getDefaultParams().setParameter(HttpClientParams.MAX_REDIRECTS, 10);// 重定向10次
		DefaultHttpParams.getDefaultParams().setParameter("http.protocol.cookie-policy", CookiePolicy.BROWSER_COMPATIBILITY);
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
		System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "OFF");

		this.url = url;
		client = new HttpClient();
		methodLogin = new GetMethod(EncodeURI.encode(url));
		html = executeHtml();

	}

	public void setHtml(String html) {
		this.html = html;
	}

	private static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0";

	public int statusCode = 0;
	public String url = null;
	public HttpClient client = null;
	public GetMethod methodLogin = null;
	private String html = null;

	public String getHtml() {
		return html;
	}

	/**
	 * 获取网页内容
	 * 
	 * @param url
	 * @return
	 * @author LiuShangYang 2014年6月12日
	 * @throws Exception 
	 * @throws WebpageException
	 *             页面错误，例如页面不存在404
	 * @throws FunctionException
	 *             程序异常
	 */
	private String executeHtml() throws Exception {

		// 超时5分钟
		client.getHttpConnectionManager().getParams().setConnectionTimeout(300000);
		client.getHttpConnectionManager().getParams().setSoTimeout(300000);
		// 忽略cookies警告
		client.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
		// 浏览器标识
		client.getParams().setParameter(HttpMethodParams.USER_AGENT, USER_AGENT);

		InputStream is = null;
		try {
			String resString = "";

			// 这里走流量

			statusCode = client.executeMethod(methodLogin);

			// 文件类型
			Header contentType = methodLogin.getResponseHeader("Content-Type");
			if (statusCode == 200 && contentType != null && contentType.getValue() != null && contentType.getValue().indexOf("text/") == -1 && contentType.getValue().indexOf("/json") == -1)
				throw new WebpageException(WebpageException.HTTP_FILE);

			// 文件大小
			if (statusCode == 200 && methodLogin.getResponseContentLength() > Constants.MAX_HTML_LENGTH)
				throw new WebpageException(WebpageException.HTTP_SIZE);

			if (statusCode == 200) {
				String contentEncoding = null;
				Header headerContentEncoding = methodLogin.getResponseHeader("Content-Encoding");
				if (headerContentEncoding != null)
					contentEncoding = headerContentEncoding.getValue();

				// 网页内容 // 走流量
				is = methodLogin.getResponseBodyAsStream();

				// TODO 暂时没有log4j配置
				// Logger loger = Logger.getLogger(HtmlContent2.class);

				if ("gzip".equalsIgnoreCase(contentEncoding))
					is = new GZIPInputStream(is);

				// 读取文件流
				List<Byte> listByte = new ArrayList<Byte>();
				int len = 4096;
				byte[] byteRead = new byte[len];
				int lenRead;
				while ((lenRead = is.read(byteRead)) != -1) {
					for (int i = 0; i < lenRead; i++)
						listByte.add(byteRead[i]);
				}

				htmlSizeB += listByte.size();

				// 网页的byte
				byte[] byteHtml = new byte[listByte.size()];
				for (int i = 0; i < listByte.size(); i++) {
					byteHtml[i] = (byte) listByte.get(i);
				}

				// 网页的String
				resString = new String(byteHtml, methodLogin.getResponseCharSet());

				// 编码
				if ("iso-8859-1".equalsIgnoreCase(methodLogin.getResponseCharSet())) {
					int charsetIndex = resString.indexOf("charset=") + 8;
					if (charsetIndex != 7) {
						int charsetEnd = resString.indexOf("\"", charsetIndex);
						String charsetName = resString.substring(charsetIndex, charsetEnd).replace("\"", "").trim();
						if (!"iso-8859-1".equalsIgnoreCase(charsetName)) {
							resString = new String(byteHtml, charsetName);
						}
					}
				}

				// &#00000;
				Map<String, Character> map = new HashMap<String, Character>();
				String strPattern = "&#\\d*;";
				Pattern p = Pattern.compile(strPattern);
				Matcher m = p.matcher(resString);
				while (m.find()) {
					String s = m.group();
					map.put(s, (char) Integer.parseInt(s.substring(2, s.length() - 1)));
				}
				for (String key : map.keySet())
					resString = resString.replace(key, map.get(key).toString());
				return resString.trim();
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			try {
				if (is != null)
					is.close();
				if (methodLogin != null)
					methodLogin.releaseConnection();
			} catch (IOException e) {
				// e.printStackTrace(); 176:会出现Read timed out 或者 Connection
				// reset
			}
		}

		return "";
	}

}
