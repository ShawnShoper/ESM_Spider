package org.shoper.spider.core.esm;
/**
 * ESM�߳���
 * @author Shoper
 */
public class ESMThread implements Runnable {
	private AbstractESM esmThread;
	
	public void run() {
		if (null != esmThread){
			try{
				esmThread.process();
			}catch(Exception e){
				esmThread.destroy();
			}
		}
	}

}
