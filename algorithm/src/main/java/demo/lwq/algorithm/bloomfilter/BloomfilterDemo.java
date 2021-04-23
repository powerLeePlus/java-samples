package demo.lwq.algorithm.bloomfilter;

import java.math.BigDecimal;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/** 布隆过滤器-guava实现
 *
 * @author lwq
 * @date 2021/4/23 0023
 */
public class BloomfilterDemo {

	private static int size = 1000000;//预计要插入多少数据

	private static double fpp = 0.01;//期望的误判率

	private static BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size, fpp);

	public static void main(String[] args) {
		// 插入数据
		for (int i = 0; i < 1000000; i++) {
			bloomFilter.put(i);
		}

		// 实际存在的过滤
		int fppCount1 = 0;
		for (int i = 0; i < 1000000; i++) {
			boolean mightContain = bloomFilter.mightContain(i);
			if (!mightContain) {
				fppCount1++;
				System.out.println("误判了：" + i);
			}
		}

		System.out.println("总共误判了" + fppCount1 + "次");

		// 实际不存在的过滤
		int fppCount2 = 0;
		for (int i = 1000000; i < 2000000; i++) {
			boolean mightContain = bloomFilter.mightContain(i);
			if (mightContain) {
				fppCount2++;
			}
		}
		BigDecimal f = new BigDecimal(fppCount2);
		BigDecimal s = new BigDecimal(size);

		System.out.println("总共误判了" + fppCount2 + "次，ffp:" + f.divide(s).toString());

	}
}
