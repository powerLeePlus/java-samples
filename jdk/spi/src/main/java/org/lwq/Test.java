package org.lwq;

import java.util.Iterator;
import java.util.ServiceLoader;

import sun.misc.Service;

/**
 * @author lwq
 * @date 2020/7/15 0015
 */
public class Test {

	public static void main(String[] args) {

		// 1、ServiceLoader.load
		ServiceLoader<SPIService> load = ServiceLoader.load(SPIService.class);

		Iterator<SPIService> iterator = load.iterator();
		while (iterator.hasNext()) {
			SPIService next = iterator.next();
			next.invoke();
		}

		System.out.println("-------------------------------");

		// 2、Service.providers
		Iterator<SPIService> providers = Service.providers(SPIService.class);
		while (providers.hasNext()) {
			SPIService next = providers.next();
			next.invoke();
		}

	}
}
