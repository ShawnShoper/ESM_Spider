package org.shoper.spider.core.esm;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.hc.tesseract.OCR;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ESM_SN_test {
	private ESM_SN esm_SN = new ESM_SN();

	@Before
	public void test_init() {
		esm_SN.init();
	}

	@Test
	public void test_process() throws Exception {
		/*
		 * List<String> list = new ArrayList<>(); list.add("1"); list.add("2");
		 * list.add("3"); list.add("4"); String[] ls = new String[]{};
		 * System.out.println(ls.length); ls = list.toArray(ls); for (String
		 * string : ls) { System.out.println(string); }
		 * System.out.println(ls.length);
		 * 
		 * esm_SN.process();
		 */
		String price = new OCR()
				.recognizeText(
						new URL(
								"http://price2.suning.cn/webapp/wcs/stores/prdprice/22953444_9017_10000_9-1.png"),
						"png");
		if (price != null && price.length() > 0) {
			BigDecimal bprice = new BigDecimal(price.replaceAll("[^0-9.]", "")
					.trim());
			System.out.println(bprice);
		}
	}

	@After
	public void test_destroy() {
		esm_SN.destroy();
	}
}
