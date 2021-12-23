package demo.lwq.algorithm.sort;

import java.util.*;

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

	/**
	 * 归并排序算法
	 * 分治(divide-adn-conquer)思想实现的
	 * 将多个有序序列合并成一个新的有序序列，即把待排序序列分成多个子序列，每个子序列是有序。然后再把有序子序列合并成整体有序序列
	 * 归并排序是稳定排序，它也是一种十分高效的排序，能利用完全二叉树特性的排序一般性能都不会太差。java中Arrays.sort()采用了一种名为TimSort的排序算法，就是归并排序的优化版本。
	 * 实现思路：
	 * 1、将序列拆分成子序列，按层级一层层拆分，直到每个序列只有一个元素为止（以下称最终子序列）
	 * 2、最终子序列排好序 （以上两步为“分”的过程）
	 * 3、将每个最终子序列两两一组，合并成一个有序序列（通过某个子序列从前到后，分别与另一个元素从前到后比较，将小的从左到右依次放入一个临时数组，直到所有元素都放到临时数组完毕）
	 * 4、递归上一级子序列重复第三步操作，直到最上一层位置 （以上两步为“治”的过程）
	 */
	public static void mergeSort(int[] arr) {
		int[] tempArr = new int[arr.length]; // 临时数组
		sort(arr, 0, arr.length-1, tempArr);
	}

	private static void sort(int[] arr, int start, int end, int[] tempArr) {
		if(start < end) {
			// 拆分的临界index
			int mid = (start + end) / 2;
			// 左边的子序列排序
			sort(arr, start, mid, tempArr);
			// 右边的子序列排序
			sort(arr, mid + 1, end, tempArr);
			// 左右两边的合并
			merge(arr, start, end, mid, tempArr);
		}
	}
	private static void merge(int[] arr, int start, int end, int mid, int[] tempArr) {
		int left = start; // 左子序列指针
		int right = mid + 1; // 右子序列指针
		int tmpIndex = 0; // 临时序列待填入排好序的元素指针
		// 左子序列从前往后依次和右子序列比较大小，小的按次放在临时序列中
		while (left <= mid && right <= end) {
			if (arr[left] <= arr[right]) {
				tempArr[tmpIndex++] = arr[left++];
			} else {
				tempArr[tmpIndex++] = arr[right++];
			}
		}
		// 最后左子序列或者右子序列存在还没放入临时序列的元素。依次放进去即可(实际上以下两个while体只会执行到一个)
		while (left <= mid) {
			tempArr[tmpIndex++] = arr[left++];
		}
		while (right <= end) {
			tempArr[tmpIndex++] = arr[right++];
		}

		// 原start-end的元素复制回原数组中
		tmpIndex = 0;
		while (start <= end) {
			arr[start++] = tempArr[tmpIndex++];
		}
	}

	/**
	 * 桶排序算法
	 * 采用的分治思想
	 * 划分多个范围相同的区间，每个子区间自排序，最后合并
	 * 计数排序是桶排序的扩展算法
	 * 通过映射函数，将待排序序列的元素映射到各个对应的桶中，对各个桶分别排序(这里的排序算法是可选的)，最后将非空桶中元素依次放回原序列，完成排序
	 * 1、计算范围（最大值，最小值）
	 * 2、计算桶的数量
	 * 3、将元素放到各自桶中
	 * 4、对每个桶排序
	 * 5、依次将桶中元素复制回原序列
	 *
	 * 桶排序需要尽量保证元素分散均匀，否则当所有数据集中在同一个桶中时，桶排序失效
	 */
	public static void bucketSort(int[] arr) {
		// 计算范围
		int max = arr[0];
		int min = max;
		for (int i = 1; i < arr.length; i++) {
			max = Math.max(max, arr[i]);
			min = Math.min(min, arr[i]);
		}

		// 计算桶的数量
		int bucketNum = (max - min)/arr.length + 1;
		ArrayList<ArrayList<Integer>> bucketArr = new ArrayList<>((int)(bucketNum/0.75F + 1F));
		for (int i = 0; i < bucketNum; i++) {
			bucketArr.add(new ArrayList<Integer>());
		}

		// 将元素放在各自桶中
		for (int i = 0; i < arr.length; i++) {
			int num = (arr[i] - min)/arr.length;
			bucketArr.get(num).add(arr[i]);
		}

		// 对每个桶排序
		for (ArrayList<Integer> list : bucketArr) {
			// 这里排序算法可选
			Collections.sort(list);
		}

		// 依次将桶中元素复制回原序列
		int index = 0;
		for (ArrayList<Integer> list : bucketArr) {
			for (Integer integer : list) {
				arr[index++] = integer;
			}
		}
	}
	
	/**
	 * 基数排序算法
	 * 属于分配式排序，又称“桶子法”
	 * 1、将待排序序列中元素（正整数）按位数分割成不同的数字；
	 * 2、从低位开始，划分0~9的桶，比较所有元素该位的值，放入相应匹配的桶中；
	 * 3、全部放完后，依次从桶中取回原序列；
	 * 4、再重复2,3步骤处理高一位，直至最高位处理完，即完成排序
	 */
	public static void radixSort(int[] arr) {
		// 确定排序的次数（最大数有几位）
		int max = arr[0];
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] > max) {
				max = arr[i];
			}
		}
		int times = 0;
		while (max > 0) {
			max /= 10;
			times++;
		}

		// 划分10个桶（0到9），用于存放对应位匹配0到9的元素
		ArrayList<ArrayList<Integer>> buckets = new ArrayList((int)(10/0.75+1));
		for (int i = 0; i < 10; i++) {
			buckets.add(new ArrayList<>());
		}

		// 每一位分配排序（判断改位值，放入对应桶中；再按次取回原序列）
		for (int i = 0, n = 1; i < times; i++, n *= 10) {
			for (int j = 0; j < arr.length; j++) {
				int value = arr[j];
				int index = value / n % 10;
				buckets.get(index).add(value);
			}
			// 取回原序列
			int index = 0;
			for (ArrayList<Integer> bucket : buckets) {
				Iterator<Integer> iterator = bucket.iterator();
				while (iterator.hasNext()) {
					arr[index++] = iterator.next();
					// 清空桶，用于下一位处理
					iterator.remove();
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
		// shellSort(arr);
		// mergeSort(arr);
		// bucketSort(arr);
		radixSort(arr);
		System.out.println(Arrays.toString(arr));
		// shellSort(arr);
		int[] arr2 = {222,221,5344,543,452849,412,92,74};

		// bucketSort(arr2);
		radixSort(arr2);
		System.out.println(Arrays.toString(arr2));
	}
}
