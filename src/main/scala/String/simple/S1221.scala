package String.simple

import scala.util.control.Breaks._

/**
 * @Project Leetcode
 * @Author INBreeze
 * @Date 2020/7/29 23:36
 * @Description 分割平衡字符串
 */
object S1221 {
  def main(args: Array[String]): Unit = {
    // "RLLLLRRRLR"
    //println("len: " + balancedStringSplit("LLRLRR"))
    //println("len: " + balancedStringSplit("LLRRLRRL"))
    //println("len: " + balancedStringSplit("RLRRRLLRLL"))
    println("len: " + balancedStringSplit("RLLLLRRRLR"))
  }


  /**
   * 对每个字符进行 for 循环 执行下列操作
   * 1.找 L 字符最大相同长度
   * 2.如果遇到了 R 字符 则 找 R 字符的最大相同长度
   * 3.如果又遇到了 L 字符 此时比较 L个数 与 R个数
   * 当这两相等时 直接从 当前字符索引 + LR个数*2 继续循环
   * 当这两不等时 则分 左大右小 及 左小右大 两种情况处理
   * 左小右大 则以 i + lr * 2 为下一个 循环起始，左大右小 则继续向后循环 找到 lc+ = rc+ 的最后平衡位 为下一个 循环起始
   * 记录总循环次数
   *
   * @param s 平衡字符串
   * @return 平衡字符子串个数
   */
  def balancedStringSplit(s: String): Int = {
    val array = s.toCharArray
    var len = 0
    var next = 0

    // 跳过 已配对 平衡字符
    for (i <- 0 until array.length - 1; if i >= next) {
      println("i= " + i + " next= " + next)
      println("--- i = " + i)

      // 左不能取最后一位 否则右溢出
      var lc = 1
      breakable(
        for (j <- i + 1 until array.length - 1) {
          if (array(i) != array(j))
            break()
          println("j=" + j)
          println("ai= " + array(i) + " aj= " + array(j))
          lc += 1
        }
      )
      println("lc: " + lc)

      // 右可取最后一位
      var rc = 0
      breakable(
        for (k <- i + lc until array.length) {
          if (array(i) == array(k))
            break()
          println("k= " + k)
          println("ai= " + array(i) + " " + "ak= " + array(k))
          rc += 1
        }
      )
      println("rc: " + rc)

      if (lc == rc && lc != 0) {
        len += 1
        next = i + lc * 2
      }
      else if (rc != 0) {

        // 左小 右大
        if (lc < rc) {
          len += 1
          next = i + lc * 2
        } // 左大 右小
        else {
          for (l <- i + lc + rc until array.length; if lc != rc) {
            if (array(i) == array(l))
              lc += 1
            else
              rc += 1
          }
          len += 1
          next = i + lc * 2
        }

      }
      else
        len += 0
    }
    len
  }
}
