package org.shoper.spider.core.esm.manager;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.shoper.spider.core.esm.AbstractESM;

public class ESMManager {
	private ConcurrentMap<String,ThreadLocal<AbstractESM>> esm_thread = new ConcurrentHashMap<>();
}
