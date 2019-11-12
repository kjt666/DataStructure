public class Calculator1Demo {

    public static void main(String args[]) {
        //表达式
        String expression = "3+2*6-2+10";
        Calculator1 cal = new Calculator1();
        System.out.println(expression + " = " + cal.calculator(expression));
    }

}

/**
 * 简易版计算器，不包含括号，小数运算
 * 使用栈进行一个表达式的结果计算,需要一个数栈和一个符号栈
 * 1、通过对表达式的字符进行遍历
 * 2、如果是数字直接放入数栈
 * 3、如果是符号，当符号栈为空时，直接入栈，否则，如果当前符号优先级小于等于符号栈栈顶符号优先级，从数栈弹出两个数字，符号栈弹出栈顶符号进行计算，计算结果放入数栈
 * 反之当前符号优先级大于符号栈栈顶符号，则直接放入符号栈
 * 4、当遍历完成后，就顺序从数栈和符号栈弹出数字和符号进行运算，每次运算结果需要再次入数栈
 * 5、当数栈只有一个元素时，就是结果
 */
class Calculator1 {


    //创建数栈和符号栈
    //因为是对字符串遍历，数栈保存char具体对应数值
    ArrayStack numStack = new ArrayStack(10);
    //符号栈则直接保存char对应ascii码表值
    ArrayStack operStack = new ArrayStack(10);
    String s = "";

    public int calculator(String expression) {
        char[] chars = expression.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char aChar = chars[i];
            //判断char为数字还是符号
            if (isOperator(aChar)) {
                //如符号栈为空，直接入栈
                if (operStack.isEmpty())
                    operStack.push(aChar);
                else {
                    //大于符号栈栈顶符号优先级时，直接入符号栈
                    if (priority(aChar) > priority(operStack.peek()))
                        operStack.push(aChar);
                    else {//否则从数栈弹出两个元素，符号栈弹出栈顶符号进行计算
                        int value = calculator(numStack.pop(), numStack.pop(), operStack.pop());
                        numStack.push(value);
                        //再将当前符号入栈
                        operStack.push(aChar);
                    }
                }
            } else {
//                numStack.push(aChar - 48);
                //改进支持双位数
                s += aChar;
                if (i == chars.length - 1 || isOperator(chars[i + 1])) {
                    numStack.push(Integer.parseInt(s));
                    s = "";
                }
            }
        }
//        numStack.showStack();
//        operStack.showStack();
        //表达式遍历完成后，顺序从数栈和符号栈取出元素计算
        while (numStack.size() != 1) {
            int value = calculator(numStack.pop(), numStack.pop(), operStack.pop());
            numStack.push(value);
        }
        //此时数栈只剩下一个元素，既为结果
        return numStack.pop();
    }


    /**
     * 计算符号优先级,int和char可以相互比较
     *
     * @param operator
     * @return
     */
    public int priority(int operator) {
        if (operator == '-' || operator == '+')
            return 1;
        else if (operator == '*' || operator == '/')
            return 2;
        return 0;
    }

    /**
     * 判断是不是符号
     *
     * @param c
     * @return
     */
    public boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    /**
     * 根据符号计算两个数的结果
     *
     * @param num1
     * @param num2
     * @param operator
     * @return
     */
    public int calculator(int num1, int num2, int operator) {
        int res = 0;
        switch (operator) {
            case '+':
                res = num1 + num2;
                break;
            case '-'://注意顺序
                res = num2 - num1;
                break;
            case '*':
                res = num1 * num2;
                break;
            case '/'://注意顺序
                res = num2 / num1;
                break;
        }
        return res;
    }

}