package demo.lwq.algorithm.sort;

import java.util.Arrays;

/**
 * 排序相关算法
 * @author lwq
 * @date 2021/12/15 0015
 * @since
 */
public class SortDemo {

	public static void main(String[] args) {
		int[] arr = {2,1,5,3,4,9,7};
		bubbleSort(arr);
		System.out.println(Arrays.toString(arr));
	}

	/**
	 * 冒泡排序算法
	 * 假设一个序列有N个元素，
	 * 1、从左到右依次比较第1和2位置的值，如果位置1的值大于位置2的值，则这两数交换位置。继续比较位置2和3的......依此类推。直到完成位置N-1和N的比较后交换后，此时位置N的值是序列中最大（这个值是从左到右一点点升上来的，所以叫冒泡）；
	 * 2、按上述方式再将位置N-1的次大值“冒”上来，依此类推直到位置2的值”冒“上来，此时序列刚好从小到大排好序了
	 */
	public static void bubbleSort(int[] arr) {
		int n = arr.length;
		for (int i = 0; i < n; i++) { // i控制已经“冒完泡”的值的个数
			for (int j = 0; j < n - i - 1; j++) { // j控制剩余待“冒泡”的个数
				if (arr[j] > arr[j+1]) { // 前面数大于后面数，需要交换位置
					int tmp = arr[j];
					arr[j] = arr[j+1];
					arr[j+1] = tmp;
				}
			}
		}
	}
}
