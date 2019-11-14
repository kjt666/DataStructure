import java.util.*

fun main(args: Array<String>) {
    val array = intArrayOf(3, 9, -1, 22, 10, -2, 22, 4, -5)
//    bubbleSort(array)
//    selectSort(array)
//    insertSort(array)
//    shellSort(array)
//    quickSort(array, 0, array.size - 1)
//    printArray(array)
    val array2 = intArrayOf(4, 6, 8, 5, 9)
    heapSort(array)
}

/*
* 冒泡排序法（从最大值开始找）
* 循环n-1轮，相邻两元素作比较，如果为逆序则置换
* 优化：做一个flag，若发生置换，设置为true，如果在一轮比较过后，flag还为false则意味没有发生置换，元素已经排序完成。
* */
fun bubbleSort(intArray: IntArray) {

    var size: Int = intArray.size
    var temp: Int
    //循环比较n-1次
    for (i in 0 until intArray.size - 1) {
        var flag = false
        //防止移动到最后一位时数组越界
        for (j in 0 until size - 1) {
            if (intArray[j] > intArray[j + 1]) {
                temp = intArray[j + 1]
                intArray[j + 1] = intArray[j]
                intArray[j] = temp
                flag = true
            }
        }
        if (!flag)
            break
        //因为每次循环过后，第i次循环的倒数第i位是确定好了的，所以下次循环对比时可排除
        //也可直接在内循环-i，这里为方便统计循环次数
        size -= 1
        //打印过程
        printArray(intArray)
    }
    println("排序循环了${intArray.size - size}次")
}

/*
* 选择排序法(从最小值开始找)
* 循环n-1轮，第一轮寻找从第1位到第n位之间的最小值，然后与第一位交换。第二轮从第2位到第n位的最小值，然后与第二位交换。第三轮从。。。
* 注意，选择排序并不像冒泡排序那样，每找到一次就交换，而是找到每次循环之间的最小值，再交换.
* 这就需要一个变量存储每次循环找到的最小值的下标，最后再和第i次循环的第i个值交换。
* */
fun selectSort(intArray: IntArray) {

    var index: Int
    var temp: Int
    //循环n-1次
    for (i in 0 until intArray.size - 1) {
        //用一个index记录最小值的下标，因为要查找每次循环的最小值，这个最小值的位置肯定会不断变化，我们需要拿array[index]不断和它后边的数作比较,直到循环完成。
        //假定每次循环的第一个数是最小数，所以index = i
        index = i
        for (j in i + 1 until intArray.size) {
            if (intArray[index] > intArray[j]) {
                index = j
            }
        }
        //找到最小值后与第i个元素交换
        //如果index == i，说明i本身就是本次循环最小的值，所以无需交换
        if (index != i) {
            temp = intArray[i]
            intArray[i] = intArray[index]
            intArray[index] = temp
        }
        //打印过程
        printArray(intArray)
    }
}

/*
* 插入排序(从小到大排列)
* 将数组分成一个有序表一个无序表，把数组第一个元素放入有序表，再把数组后面的元素与有序表中的元素作比较，找到合适的位置插入，依次类推。
* 如【30，19，25，1】=>[{30},19,25,1]=>[{19,30},25,1]=>[{19,25,30},1]=>[{1,19,25,30}]
* */
fun insertSort(intArray: IntArray) {
    //从第二个元素开始和前面的比较
    for (i in 1 until intArray.size) {
        //用一个常量保存第i个位置的值，因为在后面的比较中可能被它前一个值覆盖掉
        val value = intArray[i]
        //index为有序表的最后一个元素的下标
        //因为要从有序表的最后一个元素向前比较
        var index = i - 1
        //如果从大到小排列的话，把这里的小于改成大于
        while (index >= 0 && value < intArray[index]) {
            intArray[index + 1] = intArray[index]
            index -= 1
        }
        //当退出while循环表示找到第i个元素在有序表的位置，index加1是因为在while最后一次执行时减掉了1
        //小优化：判断插入的位置是不是i当前的位置
        if (index + 1 != i)
            intArray[index + 1] = value
        //打印过程
        printArray(intArray)
    }
}

/*
* 希尔排序，插入排序的升级版
* 因为普通的插入排序，如果较小的值在数组的最后面的话，他需要向前交换很多次才能到合适的位置。
* 所以希尔排序会尽量让较小的值先往前排，避免过多的交换次数。
* 比如有一个长度为10的数组，它会先把数组分为5份，进行比较排序，再分为两份，比较排序，最后分为一份进行排序。
* 每次分组的数量就是不断的用数组长度除以2直到等于1。
* 在最后一次排序的时候，较小的数已经被放在了靠前的位置。
* */
fun shellSort(intArray: IntArray) {
    var gap = intArray.size / 2
    do {
        //从第gap个元素开始和前面的比较
        for (i in gap until intArray.size) {
            //用一个常量保存第i个位置的值，因为在后面的比较中可能被它前一个值覆盖掉
            val value = intArray[i]
            var index = i
            //如果从大到小排列的话，把这里的小于改成大于
            while (index - gap >= 0 && value < intArray[index - gap]) {
                intArray[index] = intArray[index - gap]
                index -= gap
            }
            intArray[index] = value
        }
        //打印过程
        printArray(intArray)
        gap /= 2
    } while (gap > 0)
}

/*
* 快速排序，找到位于数组中间的值，将小于它的值放在它的左边，大于它的值放在右边。然后对左右两部分再次进行以上递归操作。
* (3, 9, -1, 10, -2,4)
* */
fun quickSort(intArray: IntArray, left: Int, right: Int) {

    //找到位于数组的中间值
    var piovt = intArray[(left + right) / 2]
    var l = left
    var r = right
    var temp: Int
    while (l < r) {
        //当不满足这个条件时，表示在左边找到大于中间值的值
        while (intArray[l] < piovt) {
            l++
        }
        //当不满足这个条件时，表示在右边找到小于中间值的值
        while (intArray[r] > piovt) {
            r--
        }
        //表示左右两边的值都已按大小分好，退出循环
        if (l >= r) {
            break
        }
        //进行交换
        temp = intArray[l]
        intArray[l] = intArray[r]
        intArray[r] = temp

        /*if (intArray[l] == piovt) {
            r -= 1
        }
        if (intArray[r] == piovt) {
            l += 1
        }*/

    }
    //防止栈溢出,因为当整个数组排好序以后，l必定<right,r必定>¬left
    if (l == r) {
        l += 1
        r -= 1
    }
    printArray(intArray)
    if (r > left) {
        quickSort(intArray, left, r)
    }
    if (l < right) {
        quickSort(intArray, l, right)
    }
}

/*
 * 堆排序
 * 1、将无序数组构成一个堆，根据升序或降序选择大顶堆或小顶堆（大顶堆：父结点大于或等于左右子节点，小顶堆反之）[重点步骤]
 * 2、将堆顶元素与末尾元素交换。此时的堆顶元素即为根结点为整个堆中最大值，对应数组中的第0个元素。将最大元素沉到数组末端，即将第0个元素点与数组中的最后元素交换位置。
 * 3、重新调整结构，使其满足堆的定义，然后继续交换堆顶元素与末尾元素，反复执行调整+交换的步骤，直至整个数组有序。
 */
fun heapSort(intArray: IntArray) {

    //先从树的最后一个非叶子结点从下往上遍历调整每颗子树构成大顶堆
    //最后一个非叶子结点在数组中的坐标用size/2-1可得出，i为每个非叶子结点的下标
    for (i in intArray.size / 2 - 1 downTo 0) {
        adjustHeap(intArray, i, intArray.size)
    }
    println(Arrays.toString(intArray))
    //将头结点放到数组最后,并重复1、2步骤
    //传入方法的数组长度一直在变，（相当于将第2步置换到数组最后的元素排除出去，对剩余元素再次进行调整），重复上述步骤直到length=1
    for (j in intArray.size - 1 downTo 1) {
        val temp = intArray[j]
        intArray[j] = intArray[0]
        intArray[0] = temp
        //此时从根节点调整一次即可
        adjustHeap(intArray, 0, j)
        println(Arrays.toString(intArray))
    }
}

/**
 * 调整以当前非叶子结点为父结点的子树，将此子树中的最大值调到父结点。
 *
 * @param intArray 数组
 * @param k 非叶子结点对应数组中的下标
 * @param length 要调整的数组长度
 */
fun adjustHeap(intArray: IntArray, k: Int, length: Int) {

    var k = k
    //首先记录此子树父结点的值
    var temp = intArray[k]
    //创建指针指向k的左子节点
    var nodeIndex = k * 2 + 1
    while (nodeIndex < length) {
        //比较此子树左右结点的值
        if (nodeIndex + 1 < length && intArray[nodeIndex] < intArray[nodeIndex + 1]) {
            nodeIndex++//将指针指向k的右子结点
        }
        //如果子节点的值大于父结点
        if (intArray[nodeIndex] > temp) {
            //将子节点的值赋给父结点
            intArray[k] = intArray[nodeIndex]
            //进行下一个子树的判断
            k = nodeIndex
        } else
            break
        nodeIndex = k * 2 + 1
    }
    intArray[k] = temp
}

fun printArray(intArray: IntArray) {
    intArray.forEachIndexed { index, i ->
        print(if (index == intArray.lastIndex) "$i" else "$i,")
    }
    println()
}