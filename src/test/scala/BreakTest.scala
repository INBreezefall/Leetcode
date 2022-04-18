package scala

import scala.util.control.Breaks._

/**
 * @Project Leetcode
 * @Author INBreeze
 * @Date 2020/7/31 9:49
 */
// noinspection ScalaDocParserErrorInspection,ScalaDocUnknownTag
object BreakTest {
  def main(args: Array[String]): Unit = {
    // 控制守卫 语法糖
    for (i <- 1 to 10 if i != 5) {
      print(i + " ")
    }
    println()
    // 控制守卫 实质
    for (i <- 1 to 10) {
      breakable(
        if (i == 5) {
          print(i / 2.toDouble + " ")
          break() // ≈ continue
        }
        else {
          print(i + " ")
        }
      )
    }
  }
}
