import java.util.ArrayList;
import java.util.Stack;

public class InversePolishExpressionDemo {

    public static void main(String args[]) {

       /* String inversePolishExpression = "3 4 + 5 * 6 -";
        System.out.println(cal(inversePolishExpression));*/

        String mid = "1+((2+3)*4)-5";
       /*ArrayList<String> list = infixMidExpression(mid);
        for (String s : list) {
            System.out.println(s);
        }*/
        String suffix = mid2Suffix(infixMidExpression(mid));
        System.out.println(mid + " = " + cal(suffix));
    }

    /**
     * 因为对str遍历不方便，先将中缀表达式拆分成list
     *
     * @param midExpression
     * @return
     */
    public static ArrayList<String> infixMidExpression(String midExpression) {
        char[] chars = midExpression.toCharArray();
        ArrayList<String> list = new ArrayList<>();
        String s = "";
        for (int i = 0; i < chars.length; i++) {
            //当为符号时，直接加入列表
            //+ - * / ( ）在ascii码中都在48前面,48为0
            if (chars[i] < 48)
                list.add(chars[i] + "");
            else {
                //用s记录数值
                s += chars[i];
                //到最后一个元素或下一个元素为符号时，将数值加入列表
                if (i == chars.length - 1 || chars[i + 1] < 48) {
                    list.add(s);
                    //重置s
                    s = "";
                }
            }
        }
        return list;
    }

    /**
     * 中缀转后缀表达式
     * 1、初始化两个栈，符号栈s1和中间结果栈s2
     * 2、从左到右遍历中缀表达式
     * 3、如果是数直接存入s2
     * 4、如果是符号：
     * 4.1、如果s1为空或者s1栈顶为(，直接将符号入栈
     * 4.2、如果符号大于栈顶符号优先级，则直接入栈，否则弹出栈顶符号放入s2，再将符号放入s1
     * 5、当符号是括号时：
     * 5.1、当为左括号时，直接放入s1
     * 5.2、当为右括号时，弹出s1栈顶元素放入s2，直到栈顶元素为左括号，此时这对括号丢弃
     * 6、遍历完成时，依次将s1中剩余符号弹出放入s2中
     * 7、依次弹出s2中元素，倒序即为转换后的后缀表达式
     */
    public static String mid2Suffix(ArrayList<String> midExpressionList) {
        if (midExpressionList == null || midExpressionList.isEmpty())
            return null;
        //符号栈
        Stack<String> s1 = new Stack<>();
        //中间结果栈
        Stack<String> s2 = new Stack<>();
        for (String s : midExpressionList) {
            //数字直接放入s2
            if (s.matches("\\d+"))
                s2.push(s);
            else {
                //果s1为空或栈顶为左括号或者符号为左括号，直接仿入s1
                if (s1.empty() || s1.peek().equals("(") || s.equals("(")) {
                    s1.push(s);
                    continue;
                }
                //如果是右括号，弹出s1栈顶元素放入s2，直到栈顶元素为左括号，然后将这对括号丢弃
                if (s.equals(")")) {
                    while (!s1.peek().equals("(")) {
                        s2.push(s1.pop());
                    }
                    //弹出左括号
                    s1.pop();
                    continue;
                }
                //如果符号大于s1栈顶符号优先级，直接放入s1
                if (priority(s) > priority(s1.peek()))
                    s1.push(s);
                else {//反之，弹出s1栈顶符号放入s2，再将符号放入s1
                    s2.push(s1.pop());
                    s1.push(s);
                }
            }
        }
        //遍历完成后依次将s1中剩余符号放入s2
        while (!s1.empty())
            s2.push(s1.pop());
        //此时将s2元素逆序输出即为后缀表达式
        StringBuilder res = new StringBuilder();
        while (!s2.empty())
            res.insert(0, s2.pop() + " ");
        System.out.println(midExpressionList + " => " + res);
        return res.toString().trim();
    }

    /**
     * 计算符号优先级,int和char可以相互比较
     *
     * @param operator
     * @return
     */
    public static int priority(String operator) {
        if (operator.equals("+") || operator.equals("-"))
            return 1;
        else if (operator.equals("*") || operator.equals("/"))
            return 2;
        return 0;
    }


    /**
     * 对逆波兰表达式作计算，从左至右扫描表达式，是数直接放入栈，如遇到符号，则从栈中弹出栈顶元素与次栈顶元素与符号进行计算并将结果再次入栈，知道表达式扫描完成。
     *
     * @param inversePolishExpression
     * @return
     */
    public static int cal(String inversePolishExpression) {

        if (inversePolishExpression == null || inversePolishExpression.isEmpty())
            return 0;
        String[] split = inversePolishExpression.split(" ");
        Stack<Integer> numStack = new Stack<>();
        //对表达式进行扫描
        for (String s : split) {
            //如果是数字，直接入栈
            if (s.matches("\\d+")) {
                numStack.push(Integer.parseInt(s));
            } else { //如果是符号，弹出栈顶和次栈顶元素进行计算
                int res = cal(numStack.pop(), numStack.pop(), s);
                //将结果再次入栈
                numStack.push(res);

            }
        }
        return numStack.pop();
    }


    public static int cal(int num1, int num2, String operator) {
        int res = 0;
        switch (operator) {
            case "+":
                res = num1 + num2;
                break;
            case "-":
                res = num2 - num1;
                break;
            case "*":
                res = num1 * num2;
                break;
            case "/":
                res = num2 / num1;
                break;
        }
        return res;
    }
}
