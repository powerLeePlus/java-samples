package demo.lwq.algorithm.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * 排序相关算法
 * @author lwq
 * @date 2021/12/15 0015
 * @since
 */
public class SortDemo {

	private static Random random = new Random();

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

	/**
	 * 快速排序算法
	 * 思想：选取一个基准值，比它小的都放左边，比它大的都放右边；左边和右边的再分别定一个基准，再重复上述操作，直到基准左右各只有一个元素为止。
	 * 关键是怎么移动，使基准左边的都比基准小，基准右边的都比基准大。
	 * 思路：
	 * 1.用一个临时变量存放基准，存放在临时变量中，指定左右两个指针i,j，最开始i位置和基准重合（这样就可以j替换i，然后i替换j,依次类推......）
	 * 2.先从右往左找到比基准小的数，j和i位置的值互换
	 * 3.再从右往左找到比基准大的数，i和j位置的值互换
	 * 4.重复2,3步骤，直到i和j重合，将基准写入该位置，此时左边的都小于基准值，右边的都大于基准值
	 * 5.将左右边分别单独选一个基准，重复1,2,3,4步骤。
	 * 6.直到基准两边各只有一个元素为止，此时就排好序了
	 */
	public static void fastSort(int[] arr, int low, int high) {
		int i = partition(arr, low, high);
		// 一分为二递归执行上述操作
		if (low < i - 1) {
			fastSort(arr, low, i - 1);
		}
		if (i < high - 1) {
			fastSort(arr, i + 1, high);
		}
	}

	private static int partition(int[] arr, int low, int high) {
		int benchmarkIndex = low;
		int benchmarkValue = arr[benchmarkIndex]; // 基准存到临时变量
		int i = low; // 左边的指针
		int j = high; // 右边的指针
		while (i < j) {
			// 右边的
			while (i < j && arr[j] >= benchmarkValue) {
				j--;
			}
			// 右边的大于基准值，继续找，直到找到小于基准值的数
			if (arr[j] <= benchmarkValue) {
				// 找到了
				// int tmp = arr[i];
				arr[i] = arr[j];
				// arr[j] = tmp;
			}
			// 左边的
			while (i < j && arr[i] <= benchmarkValue) {
				i++;
			}
			// 左边的小于基准值，继续找，直到找到大于基准值的数
			if (arr[i] >= benchmarkValue) {
				// int tmp = arr[j];
				arr[j] = arr[i];
				// arr[i] = tmp;
			}
		}
		// i是新的基准位置
		arr[i] = benchmarkValue;
		return i;
	}

	/**
	 * 随机化快速排序算法：除了随机选取某一个元素（而不是总是选第一个元素），然后默认将选取的元素和第一个元素交换位置。其他和快速排序一样
	 */
	public static void fastSort2(int[] arr, int low, int high) {
		int benchmarkIndex = random.nextInt(high - low) + low; // 随机选择基准
		// 选定的基准值和第一个元素值交换位置
		int tmp = arr[low];
		arr[low] = arr[benchmarkIndex];
		arr[benchmarkIndex] = tmp;
		int i = partition(arr, low, high);
		// 一分为二递归执行上述操作
		if (low < i - 1) {
			fastSort2(arr, low, i - 1);
		}
		if (i < high - 1) {
			fastSort2(arr, i + 1, high);
		}
	}
	
	/**
	 * 希尔排序：是插入排序的优化，比插入排序高效
	 * 通过按下标的一定增量分组，每组单独使用插入排序；（让较小的值排到前面）
	 * 接着缩小增量再分组，再对每组单独插入排序...（让较多较小的值排到前面）
	 * 至增量为1完成插入排序（此时是完整序列进行插入排序，但是只需做微量调整即可）为止。
	 * 增量可以是length/2，每个分组增量该组length/2
	 */
	public static void shellSort(int[] arr) {
		for (int gap = arr.length/2; gap > 0; gap/=2) {
			for (int i = gap; i < arr.length; i++) {
				int j = i;
				while (j >= gap && arr[j] < arr[j-gap]) {
					int tmp = arr[j-gap];
					arr[j-gap] = arr[j];
					arr[j] = tmp;
					j-=gap;
				}
			}
		}
	}

	public static void main(String[] args) {
		int[] arr = {2,1,5,3,4,9,7};
		// bubbleSort(arr);
		// insertSort(arr);
		// fastSort(arr, 0, arr.length - 1);
		// fastSort2(arr, 0, arr.length - 1);
		shellSort(arr);
		System.out.println(Arrays.toString(arr));
		shellSort(arr);
		System.out.println(Arrays.toString(arr));
	}
}
