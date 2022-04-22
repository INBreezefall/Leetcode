package scala

import scala.collection.mutable.ArrayBuffer

object SetListUniqueTest {
  def main(args: Array[String]): Unit = {
    val value = ArrayBuffer(List(1, 0, -1))
    val value1 = value.addOne(List(1, 0, -1))
    val value2 = value1.addOne(List(-1, 0, 1))
    val set = value2.map(_.sorted).toSet
    println(set)
  }
}
