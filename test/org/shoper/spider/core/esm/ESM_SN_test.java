package org.shoper.spider.core.esm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ESM_SN_test {
	private ESM_SN esm_SN = new ESM_SN();
	@Before
	public void test_init(){
		esm_SN.init();
	}
	@Test
	public void test_process() throws Exception{
		/*List<String> list = new ArrayList<>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		String[] ls = new String[]{};
		System.out.println(ls.length);
		ls = list.toArray(ls);
		for (String string : ls) {
			System.out.println(string);
		}
		System.out.println(ls.length);
		*/
		esm_SN.process();
	}
	@After
	public void test_destroy(){
		esm_SN.destroy();
	}
}
