package String.simple

/**
 * @Project Leetcode
 * @Author INBreeze
 * @Date 2020/8/4 9:55
 * @Description
 */
object S709 {
  def main(args: Array[String]): Unit = {
    var tmp = 'A'
    println((tmp + 32).toChar)

    var t2 = '\uD800'
    println()
  }

  // 1.String API
  def toLowerCase(str: String): String = {
    str.toLowerCase
  }

  // 1.1 深入底层
  def toLowerString(): Unit = {
    // high_surrogate || leading-surrogate == 'a' Unicode high-surrogate code unit in the UTF-16 encoding
    // public static final char MIN_HIGH_SURROGATE = '\uD800'
    // public static final char MAX_HIGH_SURROGATE = '\uDBFF'
  }

  // 2.遍历 String 每个 Char 遇到 A-Z 在 StringBuffer 追加Map小写 否则 直接追加

  // 3.位运算
  def toLowerString(str: String): String = {
    val res = str.toCharArray
    for (i <- 0 until res.length) {
      res(i) = (res(i) | 32).toChar
    }
    new String(res)
  }
}
