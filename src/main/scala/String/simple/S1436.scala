package String.simple

import scala.collection.mutable

/**
 * @Project Leetcode
 * @Author INBreeze
 * @Date 2020/8/2 20:38
 * @Description 旅行终点站
 */
object S1436 {
  def main(args: Array[String]): Unit = {
    println(destCity(List(List("London", "New York"), List("New York", "Lima"), List("Lima", "Sao Paulo"))))
  }

  // 解法1: flatMap [sum +1 -1]
  def destCity1_1(paths: List[List[String]]): String = {
    val list: List[List[(String, Int)]] = paths.map(arr => {
      List((arr.head, 1), (arr(1), -1))
    })
    val resultMap: Map[Int, String] = list.flatten.groupBy(_._1).map(kv => (kv._2.map(_._2).sum, kv._1))
    resultMap(-1)
  }

  def destCity1_2(paths: List[List[String]]): String = {
    paths.flatMap(arr => List((arr.head, 1), (arr(1), -1))).groupBy(_._1).map(kv => (kv._2.map(_._2).sum, kv._1)).getOrElse(-1, "")
  }

  // 解法2: Set [集合差]
  def destCity2(paths: List[List[String]]): String = {
    val set = mutable.Set[String]()
    paths.foreach(list => set.add(list(1)))
    paths.foreach(list => set.remove(list.head))
    set.head
  }

  // 解法3: Map [模仿]
  def destCity(paths: List[List[String]]): String = {
    val map = paths.flatMap(arr => List((arr.head, arr.last))).toMap
    var from = map.last._1
    while (map.contains(from)){
      from = map(from)
    }
    from
  }
}
