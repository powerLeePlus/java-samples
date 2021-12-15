package demo.lwq.algorithm.search;

import java.util.Arrays;

/**
 * 查找相关算法
 * @author lwq
 * @date 2021/12/15 0015
 * @since
 */
public class SearchDemo {

	public static void main(String[] args) {
		int[] sortedArr = {1,2,3,4,5,6,7};
		int i = binarySearch(sortedArr, 9);
		System.out.println(i);
	}

	/**
	 * 二分查找法
	 * 又叫折半查找，要求待查找的序列有序
	 * 原理：
	 * 1、每次先获取序列的中间值，将系列一分为二。待查值和中间值比较：
	 * 2.1、如果等于中间值，太好了；
	 * 2.2、如果小于中间值，则中间值后面的不用看了，取前半部分，继续上述操作
	 * 2.3、如果大于中间值，则中间值前面的不用看了，取后半部分，继续上述操作
	 * 3、直到某次循环的中间值等于待查值，否则循环结束也没匹配到说明待查值不存在
	 *
	 * @param array 待查的序列
	 * @param a 待查值
	 * @return int 待查值所在序列中的位置，-1表示不存在序列中
	 */
	public static int binarySearch(int[] array, int a) {
		int startIndex = 0;
		int endIndex = array.length - 1;
		int middleIndex;

		while (startIndex <= endIndex) {
			middleIndex = (endIndex + startIndex)/2;
			if (array[middleIndex] == a) { // 中间位置匹配
				return middleIndex;
			} else if (array[middleIndex] > a) { // 向左查找
				endIndex = middleIndex - 1;
			} else if (array[middleIndex] < a) { // 向右查找
				startIndex = middleIndex + 1;
			}
		}
		return -1;
	}
}
