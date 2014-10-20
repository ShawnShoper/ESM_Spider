package org.shoper.http.exception;


/**
 * 程序异常
 * 处理方法：完善程序,检查环境（数据库链接，网络链接等）
 * @author LiuShangYang
 *
 */
public class FunctionException extends Exception {
	private static final long serialVersionUID = 1L;

	public FunctionException(){}
	
	public FunctionException(Throwable cause){
		super(cause);
	}
	
	public FunctionException(String message) {
		super(message);
	}
	
	
	/**
	 * 记录链接错误
	 * @param urlId
	 * @author LiuShangYang 2014年6月26日
	 */
	public void recordUrl(String urlId){
	}
	
	
}
