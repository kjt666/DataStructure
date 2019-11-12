import java.util.*
import javax.swing.plaf.basic.BasicTreeUI
import kotlin.collections.ArrayList

fun main(args: Array<String>) {

    val intArray = intArrayOf(1, 3, 5, 7, 7, 7, 9, 11)
    //线性查找
//    println(linearSearch(intArray, 5))
    //二分查找
//    println(halfSearch(intArray, 0, intArray.lastIndex, 3))
    //二分查找加强版
    /*val list = halfSearch2(intArray, 0, intArray.lastIndex, 7)
    list.forEach {
        print("$it ")
    }*/
    //插值查找
    println(insertSearch(intArray, 0, intArray.lastIndex, 11))
}

/*
* 线性查找
* 遍历数组，找到对应的值后返回下标
* */
fun linearSearch(intArray: IntArray, value: Int): Int {

    intArray.forEachIndexed { index, i ->
        if (i == value)
            return index
    }
    return -1
}


/*
* 二分查找（折半查找）
* 使用二分查找的前提是数组必须是有序数组，思路是使用递归，从数组的中间值查起，如果查找的值小于它，那么就继续从他的左边的中间查找，反之从它右边查找，直到找到。
* 退出递归的条件：查找到时，查不到时
* */
fun halfSearch(intArray: IntArray, left: Int, right: Int, value: Int): Int {

    val mid = (left + right) / 2
    //查询不到时的退出条件，因为当查不到时，要查询的值要么小于数组的最小值，要么大于数组的最大值，此时left必定大于right。
    if (left > right)
        return -1
    //小于中间值时递归对左边进行查询
    return if (value < intArray[mid]) {
        halfSearch(intArray, left, mid - 1, value)
    }
    //大于中间值时递归对右边进行查询
    else if (value > intArray[mid]) {
        halfSearch(intArray, mid + 1, right, value)
    }
    //查询到时的退出条件
    else
        mid
}

/*
* 二分查找加强版
* 把数组中某个值得所有位置返回来，如【1，3，5，7，7，7，9，11】，把7所有的位置返回来。
* 思路：当查询到该值下标时，先向左扫描，将满足条件的下标添加到集合，再向右扫描，将满足条件的下标添加到集合
* */
fun halfSearch2(intArray: IntArray, left: Int, right: Int, value: Int): ArrayList<Int> {

    var array = arrayListOf<Int>()
    val mid = (left + right) / 2
    //查询不到时的退出条件，因为当查不到时，此时的left必定大于right
    if (left > right)
        return array
    //小于中间值时递归对左边进行查询
    return if (value < intArray[mid]) {
        halfSearch2(intArray, left, mid - 1, value)
    }
    //大于中间值时递归对右边进行查询
    else if (value > intArray[mid]) {
        halfSearch2(intArray, mid + 1, right, value)
    }
    //查询到时的退出条件
    else {
        //向左扫描
        var temp = mid - 1
        while (true) {
            if (temp < 0 || intArray[temp] != value)
                break
            array.add(temp)
            temp--
        }
        array.add(mid)
        //向右扫描
        temp = mid + 1
        while (true) {
            if (temp > intArray.lastIndex || intArray[temp] != value)
                break
            array.add(temp)
            temp++
        }
        array
    }
}

/*
* 插值查找（二分查找升级版）(对于数据量较大,数值分布比较均匀的查找表来说,采用插值查找，速度较快。数值分布不均匀的情况下，该方法不一定比二分查找好)
* 对于一个从1到100的数组，当要查询1的位置时，二分查找却还要从50的位置进行多次折半查找，为了提高效率，插值查找在二分查找的基础上对mid值的求解做了优化
* mid = low + (high - low ) * (key - arr[low])/(arr[high] - arr[low])
* */
fun insertSearch(intArray: IntArray, left: Int, right: Int, value: Int): Int {


    //查询不到时的退出条件，因为当查不到时，此时的left必定大于right.
    //value < intArray[left] || value > intArray[right] 这两个条件是防止计算出的mid数组越界
    if (left > right || value < intArray[left] || value > intArray[right])
        return -1

    val mid = left + (right - left) * (value - intArray[left]) / (intArray[right] - intArray[left])

    //小于中间值时递归对左边进行查询
    return if (value < intArray[mid]) {
        halfSearch(intArray, left, mid - 1, value)
    }
    //大于中间值时递归对右边进行查询
    else if (value > intArray[mid]) {
        halfSearch(intArray, mid + 1, right, value)
    }
    //查询到时的退出条件
    else
        mid
}

