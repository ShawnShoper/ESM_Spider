package org.shoper.spider;

import java.io.IOException;

import org.shoper.spider.core.esm.ESM_SN;

public class Main {
	public static void main(String[] args) throws IOException, InterruptedException {
		ESM_SN esm_SN = new ESM_SN("http://list.suning.com/0-20006-0-0-0-9017.html#sourceUrl4Sa=http://www.suning.com/","digital","phone");
		esm_SN.process();
	}
}
