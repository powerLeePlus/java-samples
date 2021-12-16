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
		// bubbleSort(arr);
		insertSort(arr);
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
	
	/**
	 * 插入排序算法
	 * 核心思想：通过构造有序序列，对未排序数据，在已排序序列中从后向前扫描，找到相应位置并插入，如此重复，知道最后一个未排序数据插入，完成序列排序
	 * 已插入的是排好序的，待插入的要从右向左依次和已插入的比较，找到自己的位置插进去。直至最后一个插进去
	 * 实现思路：
	 *  1.从序列第一个元素开始，可以认为该元素已经被排序（此时已排序序列中只有这一个元素）
	 *  2.取下一个元素，作为待插入元素，
	 *  3.在已排序序列中，从后往前查找，取下一个元素，如果该元素（已排序序列元素）大于待排序元素，则该元素向后移一位。
	 *  4.重复第3步，直到已排序元素小于（或等于）待插入元素，待插入元素插入
	 *  5.重复2,3,4步，直到所有的元素都插入，完成排序
	 */
	public static void insertSort(int[] arr) {
		// 第一个默认排好了，所有i从1开始
		for (int i = 1; i < arr.length; i++) {
			int ready = arr[i]; // 待排序元素
			int index = i; // 准备插入的位置
			while (index > 0 && ready < arr[index-1]) { // arr[index-1] 待比较的已插入元素
				// 待插入的小于已插入的，已插入的往后移一位
				arr[i] = arr[i-1];
				index--; // 查找准备插入的位置前移一位
			}
			// 确定位置了，插入
			arr[index] = ready;
		}
	}
}
