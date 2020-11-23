import java.util.HashMap;

public class LC0001_lszh {
    //给定一个整数数组 nums 和一个目标值 target
    //请你（需求：）在该 数组int[] 中找出 和(a+b) 为= 目标值(target)的那 两个 整数 DT=int ，并(return)返回他们的数组下标(int)

    //启动程序：LC0001_lszh.main()
    public static void main(String[] args) {
        //1.非static方法
        //LC0001_lszh lszh = new LC0001_lszh();
        //int[] result = lszh.twoSum(new int[]{1, 2, 3, 4}, 10);
        //2.static方法
        int[] result = LC0001_lszh.twoSum(new int[]{1, 2}, 3);
        int[] result1 = LC0001_lszh.twoSum(new int[]{1, 2}, 4);
        System.out.println("[" + result[0] + "," + result[1] + "]");
        System.out.println("[" + result1[0] + "," + result1[1] + "]");
        System.out.println("twoSum Function performed");
    }

    /**
     * 函数定义：
     * PPPP(函数访问权限) : public protected package-private/default private
     * RDT(函数返回值类型) :
     * 函数名 :
     * ()形参列表 :
     * { return RDT; }函数体 :
     * <p>
     * 输入：nums = [2, 7, 11, 15], target = 9
     * 输出：[0, 1]
     *
     * @param nums   数组
     * @param target 目标值
     * @return int[]
     */
    private static int[] twoSum(int[] nums, int target) {
        //1.数组：存储一堆 相同类型 数据的DT [口,口,口,口,口]
        //2.变量：存储一个 DT 的值

        //方法一：从数组中 找到 两个 数字(从数组中取数：) 进行加法运算 == 得到一个临时值（和），
        //       然后和 target进行比较，如果相等就 返回2个数字的数组下标

        //从数组中取数：nums[0]; 通过 数组[索引] 直接访问 数组中存储的元素

        /* 核心代码
        int he = nums[0]+nums[1];
        if( he == target){
            return new int[]{0, 1};
        }
         */

        //将数组的每一个，元素都取一遍，然后进行 临时值和Target 做 == 等值运算【暴力破解==爆破】
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                int he = nums[i] + nums[j];
                if (i != j) {
                    if (he == target) {
                        return new int[]{0, 1};
                    }
                }
            }
        }
        return new int[]{};
    }
}

class Solution {
    public static void main(String[] args) {
        int[] nums = new int[]{2, 7, 3, 3, 11, 15};
        int target = 6;
        int[] result = twoSum(nums, target);
        int[] result1 = twoSum1(nums, target);
        int[] result2 = twoSum2(nums, target);
        System.out.println(result[0] + "," + result[1]);
    }

    /**
     * 执行用时 :3 ms, 在所有 java 提交中击败了98.42%的用户
     * 内存消耗 :36.9 MB, 在所有 java 提交中击败了93.40%的用户
     *
     * @param nums   数组
     * @param target 目标值
     * @return 两数索引数组
     */
    private static int[] twoSum(int[] nums, int target) {
        //1.两两求和 改良
        //目标：加速
        //     ① 双层 for 循环 ？？必要吗
        //     ② 数据排序 ? 不也是一层for循环？
        //     ③ 加速命中 ?
        //产物：哈希Map<a的target补数,a的index>
        //     单层 for 循环
        //     插入数值时 ==> 建立索引 ==> HashMap
        //     所查找目标：两数值索引index1，index2 ——> 两数值无所谓，只要证明其关系 ——> 存入数值a-替换->存入a对target补数
        //3.数值b 与 数值a的HashMap<a.补数 , a.index> 进行包含计算 b == a.补数 == target - a
        HashMap<Integer, Integer> hash = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            if (hash.containsKey(nums[i]))
                return new int[]{hash.get(nums[i]), i};
            hash.put(target - nums[i], i);
        }
        return new int[]{};
    }

    /**
     * 执行用时 :50 ms, 在所有 java 提交中击败了21.74%的用户
     * 内存消耗 :37.3 MB, 在所有 java 提交中击败了90.40%的用户
     *
     * @param nums   数组
     * @param target 目标值
     * @return 两数索引数组
     */
    private static int[] twoSum2(int[] nums, int target) {
        //2.两两求和，if 和==target
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                int res = nums[i] + nums[j];
                if (i == j) continue;
                else if (res == target)
                    return new int[]{i, j};
            }
        }
        return new int[]{};
    }

    /**
     * 执行用时 :123 ms, 在所有 java 提交中击败了5.02%的用户
     * 内存消耗 :37.6 MB, 在所有 java 提交中击败了86.00%的用户
     *
     * @param nums   数组
     * @param target 目标值
     * @return 两数索引数组
     */
    private static int[] twoSum1(int[] nums, int target) {
        //1.target-nums[i]，if 差值==nums[j]
        for (int i = 0; i < nums.length; i++) {
            int cha = target - nums[i];
            for (int j = 0; j < nums.length; j++) {
                if (cha == nums[j]) {
                    if (i == j) continue;
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{};
    }
}
