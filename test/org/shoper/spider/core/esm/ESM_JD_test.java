package org.shoper.spider.core.esm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ESM_JD_test {
	private ESM_JD esm_JD = new ESM_JD();
	@Before
	public void test_init(){
		esm_JD.init();
	}
	@Test
	public void test_process(){
		esm_JD.process();
	}
	@After
	public void test_destroy(){
		esm_JD.destroy();
	}
}
