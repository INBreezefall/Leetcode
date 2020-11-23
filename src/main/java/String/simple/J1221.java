package String.simple;

import java.util.Arrays;

/**
 * @Project Leetcode
 * @Author INBreeze
 * @Date 2020/8/1 10:04
 * @Description 平衡字符串 题意是 数量相等就可以，不在意是否连续，而我的 Scala 思路是 考虑连续字符情况了
 */
public class J1221 {
    public static void main(String[] args) {

    }

    /**
     * String 作为 Char 封装类 执行效率肯定慢一层
     *
     * @param s
     * @return
     */
    public int balancedStringSplit(String s) {
        int ret = 0;
        int tmp = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'L') tmp += 1;
            if (s.charAt(i) == 'R') tmp -= 1;
            if (tmp == 0) ret += 1;
        }
        return ret;
    }

    /**
     * toChar 相较 String 实现 速度更快
     *
     * @param s
     * @return
     */
    public int balancedStringSplit2(String s) {
        int ret = 0;
        int tmp = 0;
        for (char c : s.toCharArray()) {
            if (c == 'L') tmp += 1;
            if (c == 'R') tmp -= 1;
            if (tmp == 0) ret += 1;
        }
        return ret;
    }
}
