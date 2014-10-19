package org.shoper.spider.core.esm;
/**
 * ESMœﬂ≥Ã¿‡
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

	public AbstractESM getEsmThread() {
		return esmThread;
	}

	public void setEsmThread(AbstractESM esmThread) {
		this.esmThread = esmThread;
	}
	
}
