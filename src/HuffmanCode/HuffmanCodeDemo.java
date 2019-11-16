package HuffmanCode;

import java.io.*;
import java.util.*;

public class HuffmanCodeDemo {

    public static void main(String args[]) {

        HuffmanCode huffmanCode = new HuffmanCode();
       /* String content = "i like like like java do you like a java";
       System.out.println("content压缩前的长度：" + content.getBytes().length);
        byte[] huffmanBytes = huffmanCode.encode(content.getBytes());
        System.out.println("content压缩后的长度：" + huffmanBytes.length);
        System.out.println(Arrays.toString(huffmanBytes));
        byte[] original = huffmanCode.decode(huffmanBytes);
        System.out.println(new String(original));*/
        //对图片压缩
       /* String srcFilePath = "/Users/kjt/Desktop/src.jpeg";
        String destFilePath = "/Users/kjt/Desktop/src.zip";
        huffmanCode.zip(srcFilePath, destFilePath);*/
        //对图片解压
        String srcFilePath = "/Users/kjt/Desktop/src.zip";
        String destFilePath = "/Users/kjt/Desktop/src2.jpeg";
        huffmanCode.unZip(srcFilePath, destFilePath);
    }

}

/**
 * 霍夫曼编码（数据压缩算法，无损压缩）
 * "i like like like java do you like a java"有四十个字符，用二进制表示则有320个长度，如不采取压缩，就需要传输320个长度，是最低效的方法。
 * 若要采取压缩，首先需要统计每个字符出现的次数，i:5,l:4,k:4,e:4,j:2,a:5,v:2,d:1,o:2,y:1,u:1，根据出现次数的多少采用从0开始的二进制对其进行表示，从次数最多的开始。
 * 如，i->00,a->01,l->10,k->11,e->100,j->101,v->110,0->111,d->1000,y->1001,u->1010。
 * 但是，这样会有一根问题，即某个字符的编码会成为其他字符的前缀编码，如l就是e的前缀编码，这样就会产生二义性，为了解决这个问题，可以用霍夫曼编码解决。
 * 采取霍夫曼编码后长度可压缩至原来的20%-80%
 */
class HuffmanCode {

    /**
     * 压缩文件
     *
     * @param srcFilePath
     * @param destFilePath
     */
    public void zip(String srcFilePath, String destFilePath) {
        if (srcFilePath.isEmpty() || destFilePath.isEmpty())
            return;
        InputStream is = null;
        OutputStream os = null;
        ObjectOutputStream oos = null;
        try {
            //读取源文件数据
            is = new FileInputStream(srcFilePath);
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            System.out.println("压缩前的大小：" + bytes.length);
            //对读取的数据进行霍夫曼编码
            byte[] huffmanBytes = encode(bytes);
            System.out.println("压缩后的大小：" + huffmanBytes.length);
            //将编码后的数据保存起来
            os = new FileOutputStream(destFilePath);
            oos = new ObjectOutputStream(os);
            oos.writeObject(huffmanBytes);
            //将霍夫曼编码也保存起来
            oos.writeObject(huffmanCodes);
            System.out.println("压缩成功~");
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            try {
                is.close();
                oos.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 解压文件
     *
     * @param srcFilePath
     * @param destFilePath
     */
    public void unZip(String srcFilePath, String destFilePath) {
        if (srcFilePath.isEmpty() || destFilePath.isEmpty())
            return;

        InputStream is = null;
        ObjectInputStream ois = null;
        OutputStream os = null;
        try {
            //读取压缩文件
            is = new FileInputStream(srcFilePath);
            ois = new ObjectInputStream(is);
            byte[] huffmanBytes = (byte[]) ois.readObject();
            Map<Byte, String> map = (Map<Byte, String>) ois.readObject();
            //对压缩数据解码
            byte[] originalBytes = decode(map, huffmanBytes);
            //将解码后的数据写入要保持的文件中
            os = new FileOutputStream(destFilePath);
            os.write(originalBytes);
            System.out.println("解压成功~");
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            try {
                ois.close();
                is.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 重载decode，方便使用
     *
     * @param bytes 霍夫曼编码后的数据
     * @return
     */
    public byte[] decode(byte[] bytes) {
        if (huffmanCodes.isEmpty())
            return null;
        return decode(huffmanCodes, bytes);
    }

    /**
     * 解码
     * 将采取霍夫曼编码后的数据转换成原始数据
     *
     * @param huffmanCode 霍夫曼编码
     * @param bytes       霍夫曼编码后的数据
     * @return 原始数据
     */
    private byte[] decode(Map<Byte, String> huffmanCode, byte[] bytes) {

        //将霍夫曼编码后的字节数组转换为二进制字符串
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            //最后一个byte不需要补高位，因为他可能不足8位
            boolean flag = (i == bytes.length - 1);
            sb.append(byte2String(bytes[i], !flag));
        }
        //打印转换后的byte数组二进制字符串
//        System.out.println(sb.toString());
        //转换霍夫曼编码集合 97=>100 转换为 100=>97
        Map<String, Byte> map = new HashMap<>();
        for (Map.Entry<Byte, String> entry : huffmanCode.entrySet()) {
            map.put(entry.getValue(), entry.getKey());
        }
        //遍历字符串，将霍夫曼编码转换为原数据
        //先用一个list保存转换后的数据，因为不知道转换后的数据长度，所以不能直接使用数组
        List<Byte> list = new ArrayList<>();
        //使用StringBuilder保存字符串，再用字符串去map中进行匹配
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < sb.length(); i++) {
            temp.append(sb.charAt(i));
            Byte aByte = map.get(temp.toString());
            //如果匹配到了，将字节放入集合，清空匹配的字符串
            if (aByte != null) {
                list.add(aByte);
                temp.delete(0, temp.length());
            }
        }
        //将集合转换成字节数组
        byte[] original = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            original[i] = list.get(i);
        }
        return original;
    }

    /**
     * 字节转二进制字符串
     *
     * @param b    要转换的byte
     * @param flag 是否要进行补高位
     * @return b对应的二进制字符串
     */
    private String byte2String(byte b, boolean flag) {
        //将b转成成int
        int temp = b;
        if (flag)
            temp |= 256; //按位或256  1 0000 0000 | 1 = 1 0000 0001
        //str为temp的补码
        String str = Integer.toBinaryString(temp);
        if (flag)
            return str.substring(str.length() - 8);
        else
            return str;
    }

    /**
     * 对数据进行霍夫曼编码压缩
     *
     * @param bytes 原始数据对应的字节数组
     * @return 使用霍夫曼编码后的字节数组(压缩后的字节数组)
     */
    public byte[] encode(byte[] bytes) {
        ArrayList<Node> nodes = getNode(bytes);
//        System.out.println(nodes);
        Node huffmanTreeRoot = createHuffmanTree(nodes);
//        huffmanCode.preOrder(huffmanTreeRoot);
        createHuffmanCode(new StringBuilder(), "", huffmanTreeRoot);
//        System.out.println(huffmanCodes);
        byte[] conversion = conversionByte(bytes);
        return conversion;
    }

    /**
     * 将数据转换为结点，供我们生成霍夫曼树使用
     * 格式为Node{value=32, count=9}, Node{value=97, count=5}
     *
     * @param bytes
     * @return
     */
    private ArrayList<Node> getNode(byte[] bytes) {
        if (bytes.length == 0)
            return null;
        Map<Byte, Integer> map = new HashMap<>();
        //统计每个字符出现的次数
        for (byte aByte : bytes) {
            Integer integer = map.get(aByte);
            if (integer == null) {
                map.put(aByte, 1);
            } else {
                map.put(aByte, integer + 1);
            }
        }
        ArrayList<Node> nodes = new ArrayList<Node>();
        //遍历map，生成叶子结点
        for (Map.Entry<Byte, Integer> entry : map.entrySet()) {
            Node node = new Node(entry.getKey(), entry.getValue());
            nodes.add(node);
        }
        return nodes;
    }

    /**
     * 生成霍夫曼树，将所有数据分布在叶子结点
     *
     * @param nodes
     * @return
     */
    private Node createHuffmanTree(ArrayList<Node> nodes) {
        if (nodes.isEmpty())
            return null;
        while (nodes.size() > 1) {
            //对结点排序
            Collections.sort(nodes);
            //取出权值最小的两棵树
            Node leftNode = nodes.get(0);
            Node rightNode = nodes.get(1);
            //生成一颗新的二叉树
            Node newNode = new Node(null, leftNode.count + rightNode.count);
            newNode.left = leftNode;
            newNode.right = rightNode;
            //删除权值最小的两棵树
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            //将新生成的树放入集合
            nodes.add(newNode);
        }
        //此时集合中唯一的元素就是霍夫曼树的头结点
        return nodes.get(0);
    }

    /**
     * 前序遍历二叉树
     *
     * @param node
     */
    private void preOrder(Node node) {
        if (node == null)
            return;
        System.out.println(node.toString());
        if (node.left != null)
            preOrder(node.left);
        if (node.right != null)
            preOrder(node.right);
    }

    Map<Byte, String> huffmanCodes = new HashMap<>();

    /**
     * 通过霍夫曼树生成霍夫曼编码的map集合
     * 设结点到它左子结点的路径值为0，到右结点的路径值为1，那么从根节点到某个叶子结点所通过的路径值即为叶子结点数据的霍夫曼编码
     *
     * @param sb   用于拼接结点路径
     * @param code 路径值，结点的左路径为0，右路径为1
     * @param node 结点
     */
    private void createHuffmanCode(StringBuilder sb, String code, Node node) {
        StringBuilder newSb = new StringBuilder(sb);
        newSb.append(code);
        if (node != null) {
            //判断当前结点是否为叶子结点
            if (node.value == null) {
                //向左递归
                createHuffmanCode(newSb, "0", node.left);
                //向右递归
                createHuffmanCode(newSb, "1", node.right);
            } else {
                //找到叶子结点时，将编码放入map集合
                huffmanCodes.put(node.value, newSb.toString());
            }
        }
    }

    /**
     * 将原数据转换为使用霍夫曼编码后的数据
     *
     * @param bytes
     */
    private byte[] conversionByte(byte[] bytes) {

        //将原数据中的字符使用霍夫曼编码替换生成一个新的数据
        String str = "";
        for (byte aByte : bytes) {
            str += huffmanCodes.get(aByte);
        }
        //将字符串按8位分割转成字节数组
        int len = 0;
        //确定新字节数组长度（高级写法：len = str.length() + 7 / 8;）
        if (str.length() % 8 == 0)
            len = str.length() / 8;
        else
            len = str.length() / 8 + 1;
        byte[] newBytes = new byte[len];
        int index = 0;
        for (int i = 0; i < str.length(); i += 8) {
            String value;
            //截取字符串长度不满足8位时
            if (i + 8 > str.length())
                value = str.substring(i);
            else
                //含头不含尾
                value = str.substring(i, i + 8);
            //将二进制字符串对应的数字通过byte存储起来
            newBytes[index] = (byte) Integer.parseInt(value, 2);
            index++;
        }

        return newBytes;
    }
}

class Node implements Comparable<Node> {

    //数据对应的ASCII码,如a->97
    Byte value;
    //权值，表示数据出现的次数
    int count;
    Node left;
    Node right;

    public Node(Byte value, int count) {
        this.value = value;
        this.count = count;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                ", count=" + count +
                '}';
    }

    //从小到大排序
    @Override
    public int compareTo(Node o) {
        return this.count - o.count;
    }
}