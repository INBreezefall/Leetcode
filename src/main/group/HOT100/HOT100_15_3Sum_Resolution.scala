package group.HOT100

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

import topic.template.{InputSet, ResolveTemplate, ResultSet}



/**
 * 注意点:<br>
 * 1.结果集三元组不可重复<br>
 * 2.输入数组元素可重复<br>
 * 3.数组长度 0 ~ 3000<br>
 * 4.数值 10^-5^ ~ 10^5^<br>
 * 5.
 */
// noinspection NotImplementedCode
class HOT100_15_3Sum_Resolution extends ResolveTemplate {

  override def assertFunc(res: ResultSet): Unit = {
    println(res.res.headOption.getOrElse(Array.empty[Any]).mkString("Result(", ", ", ")"))
  }

  override protected def selfSolution(inputSet: InputSet): ResultSet = {
    def keyCountReduce(map: mutable.Map[Int, Int], key: Int, reduceValue: Int = 1): Unit = {
      map.put(key, map(key) - reduceValue)
      // println(s"Reduce $key $reduceValue $map")
    }

    val nums = inputSet.input.head.asInstanceOf[Array[Object]].map(_.toString.toInt)
    // println(nums.mkString("Array(", ", ", ")"))
    val res: ArrayBuffer[ArrayBuffer[Any]] = ArrayBuffer(ArrayBuffer.empty)

    // 0.边界判断
    if (nums.length >= 3) {
      val numAndCountMap: mutable.Map[Int, Int] = mutable.Map.empty ++= nums.map(n => (n, 1)).groupMapReduce(_._1)(_._2)(_ + _)
      // println(s"Initial Map $numAndCountMap")
      for (index1 <- nums.indices) {
        for (index2 <- 1 until nums.length) {
          // println(s"$index1 $index2")
          val one = nums(index1)
          val two = nums(index2)
          val three = -(one + two)
          if (numAndCountMap.contains(three) && numAndCountMap(three) > 0) {
            // 规避 0 0 0 情况
            if (one == 0 && numAndCountMap(0) >= 3) {
              res.head.addOne((0, 0, 0))
              keyCountReduce(numAndCountMap, 0, 3)
            } else {
              res.head.addOne((one, two, three))
              List(one, two, three).foreach(keyCountReduce(numAndCountMap, _))
              // println(s"Reduced Map $one $two $three $numAndCountMap")
              // 改进点①: 删除 nums 队列指定索引元素 one two three 减少循环数量
            }
          }
        }
      }
    }

    ResultSet(res.map(_.toArray).toArray, "selfSolution")
  }

  override def optimumSolution(inputSet: InputSet): ResultSet = ???

  override def peculiarSolution_1(inputSet: InputSet): ResultSet = ???

  override def peculiarSolution_2(inputSet: InputSet): ResultSet = ???

  override def peculiarSolution_3(inputSet: InputSet): ResultSet = ???
}

object HOT100_15_3Sum_Resolution {
  def main(args: Array[String]): Unit = {
    val inputSet = InputSet(Array(Array[Any](-1, 0, 1, 2, -1, -4)))
    val resolution = new HOT100_15_3Sum_Resolution
    resolution.testAllSolution(inputSet)
  }
}