package scala.String.simple

/**
 * @Project Leetcode
 * @Author INBreeze
 * @Date 2020/7/29 22:31
 * @Description IP 地址无效化
 */
object S1108 {
  def main(args: Array[String]): Unit = {
    println(defangIPaddr("255.100.50.0"))
  }

  def defangIPaddr(address: String): String = {
    address.replaceAll("\\.", "[.]")
  }

  /**
   * 深入学习: String.replaceAll 函数
   * public String replaceAll(String regex, String replacement) {
   *     return Pattern.compile(regex).matcher(this).replaceAll(replacement);
   * }
   *
   * 底层是 Pattern 类函数调用 这个类得找时间深入看，比 String 类还长
   */
}
