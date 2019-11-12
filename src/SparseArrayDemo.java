public class SparseArrayDemo {
    public static void main(String[] args) {

        //测试，创建一个棋盘
        int[][] checkerBoard = new int[9][9];
        checkerBoard[2][3] = 1;
        checkerBoard[3][4] = 1;
        checkerBoard[4][5] = 2;
        checkerBoard[3][3] = 2;
        System.out.println("原棋盘结构");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.print(checkerBoard[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("将棋盘保存后的结构");
        SparseArray sparseArray = new SparseArray();
        int[][] save = sparseArray.save(checkerBoard);
        for (int i = 0; i < save.length; i++) {
            for (int j = 0; j < save[0].length; j++) {
                System.out.print(save[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("恢复后的棋盘结构");
        int[][] restore = sparseArray.restore();
        for (int i = 0; i < restore.length; i++) {
            for (int j = 0; j < restore[0].length; j++) {
                System.out.print(checkerBoard[i][j] + " ");
            }
            System.out.println();
        }
    }
}

/*
 * 稀疏数组
 * 假设有一个nXn的棋盘（用一个n行n列的二维数组表示它）需要实现对棋子摆放的保存和读取功能，当棋盘上的棋子不多时，直接保存二维数组太浪费内存空间，对此，我们可以使用稀疏数组替代它。
 * */
class SparseArray {


    private int[][] spArr = null;

    public SparseArray() {
    }

    /**
     * 保存棋盘
     * 思路：对于棋盘上的格子，我用0表示无子，1表示白子，2表示黑子。
     * 1.遍历二维数组，统计棋盘上的棋子数量，比如棋子数量为m
     * 2.创建一个m+1行3列的二维数组，第一行用来存储棋盘的长、宽和棋子数量，其余行记录棋子的坐标和数值
     * 3.再次遍历棋盘，将棋子存入创建的数组
     *
     * @param arr 棋盘数组
     * @return 稀疏数组
     */
    public int[][] save(int[][] arr) {
        if (arr == null || arr.length == 0)
            return spArr;
        int count = 0;
        //统计棋子数量
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] != 0)
                    count++;
            }
        }
        //创建稀疏数组
        spArr = new int[count + 1][3];
        //保存棋盘大小和棋子数量
        spArr[0][0] = arr.length;
        spArr[0][1] = arr[0].length;
        spArr[0][2] = count;
        //稀疏数组保存棋子的行数下标
        int index = 1;
        //遍历棋盘，保存棋子坐标和数值
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] != 0) {
                    spArr[index][0] = i;
                    spArr[index][1] = j;
                    spArr[index][2] = arr[i][j];
                    index++;
                }
            }
        }
        return spArr;
    }

    /**
     * 恢复棋盘
     * 思路：读取稀疏数组第一行的棋盘大小，创建棋盘二维数组
     * 从1遍历稀疏数组，将棋子填入棋盘二维数组
     *
     * @return 棋盘二维数组
     */
    public int[][] restore() {
        int[][] arr = null;
        if (spArr == null)
            return arr;
        //创建棋盘二维数组
        arr = new int[spArr[0][0]][spArr[0][1]];
        //遍历稀疏数组，将棋子填入棋盘
        //[i][0]是行，[i][1]是列,[i][2]是棋子的值
        for (int i = 1; i < spArr.length; i++) {
            arr[spArr[i][0]][spArr[i][1]] = spArr[i][2];
        }
        return arr;
    }

}

















