public class ArrayStackDemo {

    public static void main(String args[]) {
        int size = 5;
        //创建大小为5的栈
        ArrayStack stack = new ArrayStack(size);
        //数据入栈
        for (int i = 0; i < size; i++) {
            stack.push(i);
        }
        //打印栈所有元素
        stack.showStack();
        //取出一个元素
        System.out.println("取出栈顶元素：" + stack.pop());
        //打印当前栈顶元素
        System.out.println("当前栈顶元素为：" + stack.peek());
    }
}

/**
 * 栈是一个先进后出的有序列表
 * 栈是限制线性表中插入和删除只能在线性表同一端进行的操作的线性表结构，变化的一端称为栈顶，固定的一端称为栈底。
 * 因此，最先放入的元素在栈底，最后放入的元素在栈顶，反之，最后放入的最先删除，最先放入的最后删除。
 * <p>
 * 栈的应用场景
 * 子程序的调用：在进入子程序前，会将下个指令的地址放入栈中，直到子程序完成调用后再将地址取出，回到原来的程序中。
 * 处理递归调用：和子程序的调用类似，除了储存下一个指令的地址外，也将参数、区域变量等数据存入栈中。
 * 表达式的转换（中缀表达式转后缀表达式）与求值（实际解决）。
 * 二叉树的遍历（本质也是递归）。
 * 图形的深度优先搜索算法。
 * <p>
 * 思路：用数组来实现栈的功能，包括出栈、入栈、遍历输出
 */
class ArrayStack {

    private int maxSize;
    private int[] stack;
    private int top = -1;

    public ArrayStack(int maxSize) {
        this.maxSize = maxSize;
        stack = new int[maxSize];
    }

    /**
     * 入栈操作
     *
     * @param value
     */
    public void push(int value) {
        if (isFull()) {
            System.out.println("栈已满~");
            return;
        }
        //现将栈底指针上移
        top++;
        //在赋值
        stack[top] = value;
    }

    /**
     * 出栈操作
     *
     * @return
     */
    public int pop() {
        if (isEmpty()) {
            throw new RuntimeException("栈为空！");
        }
        //返回栈顶元素
        int value = stack[top];
        top--;
        return value;
    }

    /**
     * 查看栈顶元素
     *
     * @return
     */
    public int peek() {
        if (isEmpty()) {
            throw new RuntimeException("栈为空！");
        }
        return stack[top];
    }

    /**
     * 打印栈所有元素
     */
    public void showStack() {
        if (isEmpty())
            return;
        for (int i = top; i >= 0; i--) {
            System.out.printf("stack[%d] = %d\n", i, stack[i]);
        }
    }

    public int size() {
        return top + 1;
    }

    /**
     * 判断栈空
     *
     * @return
     */
    public boolean isEmpty() {
        return top == -1;
    }

    /**
     * 判断栈满
     *
     * @return
     */
    public boolean isFull() {
        return top == maxSize - 1;
    }
}