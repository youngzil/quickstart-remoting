package org.quickstart.idea;

/**
 * @author 匠心零度
 */
public class Test8 {
    public static void main(String[] args) {

        //转化为高效运算的二进制

        int num = 128;

        int a = num * 32;
        int b = num / 32;

        int aa = num * 32;
        int bb = num / 32;

        System.out.println(a == aa);
        System.out.println(b == bb);
    }
}
