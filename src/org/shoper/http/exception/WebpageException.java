package org.shoper.http.exception;


/**
 * 页面错误，例如页面不存在404，
 * 已知无法抓取的页面，例如循环重定向
 * 处理方法：尝试重新获取
 * @author LiuShangYang
 *
 */
public class WebpageException extends Exception {
	
	public WebpageException(int statusCode){
		super();
		this.StatusCode = statusCode;
	}
	
	public WebpageException(int statusCode,Throwable cause){
		super(cause);
		this.StatusCode = statusCode;
	}
	
	public WebpageException(int statusCode,String message) {
		super(message);
		this.StatusCode = statusCode;
	}
	
	/**
	 * 记录链接错误
	 * @param urlId
	 * @author LiuShangYang 2014年6月26日
	 */
	public void recordUrl(String urlId){
	}
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 状态码(大于0的正整数)
	 */
	public int StatusCode = 0;
	
	/**
	 *  自定义状态码1：文件类型不是文字
	 */
	public static final int HTTP_FILE = 1;
	
	/**
	 *  自定义状态码2：文件长度超出规定
	 */
	public static final int HTTP_SIZE = 2;
	
	/**
	 *  自定义状态码3：重定向死循环
	 */
	public static final int HTTP_REDIRECT = 3;

}
