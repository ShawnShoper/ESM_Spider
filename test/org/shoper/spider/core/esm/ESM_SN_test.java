package org.shoper.spider.core.esm;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.shoper.http.HtmlClient;

public class ESM_SN_test {
	private ESM_SN esm_SN = new ESM_SN(
			"http://list.suning.com/0-20006-0-0-0-9017.html#sourceUrl4Sa=http://www.suning.com/",
			"digital", "phone");
	private static Logger logger = Logger.getLogger(ESM_SN_test.class);

	@Before
	public void test_init() {
		esm_SN.init();
	}

	@Test
	public void test_process() throws Exception {
		esm_SN.process();
	}

	@Test
	public void test_HasNext() throws Exception {
		String suning = "http://list.suning.com";
		String url = "http://list.suning.com/0-20006-0-0-0-9017.html#sourceUrl4Sa=http://shouji.suning.com/";
		StringBuffer sb = new StringBuffer(new HtmlClient(url).getHtml());
		int pageIndex = 0;
		for (;;) {
			url = esm_SN.hasNextPage(sb.toString());
			logger.debug(url + "---" + (null == url ? "没有" : "有") + "下一页"
					+ (null == url ? "没有" : ",页码为" + (pageIndex + 1)));
			if (url == null)
				break;
			url = suning + url;
			sb.setLength(0);
			sb.append(new HtmlClient(url).getHtml());
			pageIndex++;
		}
	}

	@Test
	public void testCatchHTML() {
		String Url = "http://list.suning.com/0-20006-0-0-0-9017.html#sourceUrl4Sa=http://www.suning.com/";

	}

	@After
	public void test_destroy() {
		esm_SN.destroy();
	}
}
