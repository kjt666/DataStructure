fun main(args: Array<String>) {
 val map = Array(8) { IntArray(7) }
     //设置地图边界
     for (i in 0 until 7) {
         map[0][i] = 1
         map[map.lastIndex][i] = 1
     }
     for (i in 0 until 8) {
         map[i][0] = 1
         map[i][map[i].lastIndex] = 1
     }
     map[3][1] = 1
     map[3][2] = 1
     //查看地图
     findPath(map, 1, 1)
     printMap(map)
}

/**
 * 求n的阶乘
 */
fun factorial(n: Int): Int {
    return if (n == 1)
        1
    else
        n * factorial(n - 1)
}

/**
 * 递归解决迷宫回溯问题
 * 思路：用一个二维数组表示地图，数组默认数据为0，周围用1表示，有效路径用2表示，无效路径用3表示
 *      对于往下寻找，当移动到一个坐标时，判断它的下、右、左、上是否为有效路径（往上寻找时，先从上判断即可,）
 * */
fun findPath(map: Array<IntArray>, i: Int, j: Int): Boolean {

    //要找到的点
    if (map[6][5] == 2) {
        return true
    } else {
        if (map[i][j] == 0) {
            map[i][j] = 2
            if (findPath(map, i + 1, j)) {//下
                return true
            } else if (findPath(map, i, j + 1)) {//右
                return true
            } else if (findPath(map, i, j - 1)) {//左
                return true
            } else if (findPath(map, i - 1, j)) {//上
                return true
            } else {
                map[i][j] = 3
                return false
            }
        } else
            return false
    }
}

fun printMap(map: Array<IntArray>) {
    for (i in map.indices) {
        for (j in map[i].indices)
            print("${map[i][j]} ")
        println()
    }
}