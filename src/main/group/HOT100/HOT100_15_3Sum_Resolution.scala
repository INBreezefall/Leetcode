package group.HOT100

import scala.annotation.unused
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks._

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
    // println map !!!
    println(res.res.map(_.mkString("Result(", ", ", ")")).mkString("Array(", ", ", ")"))
  }

  override protected def selfSolution(inputSet: InputSet): ResultSet = {
    def keyCountReduce(map: mutable.Map[Int, Int], key: Int, reduceValue: Int = 1): Unit = {
      map.put(key, map(key) - reduceValue)
    }

    def keyCountIncrement(map: mutable.Map[Int, Int], key: Int, incrValue: Int = 1): Unit = {
      map.put(key, map(key) + incrValue)
    }

    val nums = inputSet.input.head.asInstanceOf[Array[Object]].map(_.toString.toInt)
    // println(nums.mkString("Array(", ", ", ")"))

    // 兼容 Leetcode 输入输出函数
    def threeSum(nums: Array[Int]): List[List[Int]] = {
      val res: ArrayBuffer[List[Int]] = ArrayBuffer()

      // 0.边界判断
      if (nums.length >= 3) {
        // 0.特殊情况判断 大量单一重复数字 [问题①没想到的边界情况]
        if (nums.toSet.size == 1) {
          if (nums(0) != 0) {
            return res.toList
          }
          return List(List(0, 0, 0))
        }
        // 1.真正要被干掉的是 one 我不希望 one 再被使用 [one 的所有可能性都消耗殆尽了 其他仍有希望]
        // [问题②这个Queue真的需要全部吗？只需要 distinct 的候选数字。numAndCountMap 与 queue 应该是分离开的 直接来源 nums]
        // [问题③如果对 nums 去重了 就不能要求 nums.init 除最后一个数字以外都是 候选数字]
        val oneCandidatesQueue = mutable.Queue(nums.toList.distinct: _ *)
        val numAndCountMap = mutable.Map.empty ++= nums.map(n => (n, 1)).groupMapReduce(_._1)(_._2)(_ + _)
        // [问题④] 0 0 0 特殊情况 未考虑，two 取不到
        if (numAndCountMap.contains(0) && numAndCountMap(0) >= 3) res.addOne(List(0, 0, 0))
        while (oneCandidatesQueue.nonEmpty) {
          val one = oneCandidatesQueue.dequeue()
          val curLoopNumAndCountMap = numAndCountMap.clone()
          keyCountReduce(curLoopNumAndCountMap, one)

          // 2.循环遍历所有 two [这个循环 是 要可变的，可减少的]
          // for (two <- oneCandidatesQueue) {
          val twoAndThreeCandidatesQueue = oneCandidatesQueue.clone()
          while (twoAndThreeCandidatesQueue.nonEmpty) {
            val two = twoAndThreeCandidatesQueue.dequeue()
            keyCountReduce(curLoopNumAndCountMap, two)
            val three = -(one + two)
            if (curLoopNumAndCountMap.contains(three) && curLoopNumAndCountMap(three) > 0) {
              res.addOne(List(one, two, three))
              // 3.three 无需再次成为 two 进行匹配
              twoAndThreeCandidatesQueue.dequeueFirst(_ == three)
              // 4.three 次数也要 -1 避免再被匹配到
              keyCountReduce(curLoopNumAndCountMap, three)
            } else {
              keyCountIncrement(curLoopNumAndCountMap, two)
            }
          }
          keyCountReduce(numAndCountMap, one)
        }
      }

      // 结果去重
      res.map(_.sorted).toSet.toList
    }

    /**
     * 这个版本是 推进到了 倒数第二个测试。死掉的原因也很简单 -- 大量输入去重后 就 3 个数字 -1 0 1
     * 这个测试 也不能说是 卡 bug 和 316 一样 全是 0，这个测试给我一个新的认知
     * oneCandidatesQueue 只需要是去重的即可
     *
     * @param nums 输入元素数组
     * @return List(三个数(和为零))
     */
    @unused
    def threeSum_317_Edition(nums: Array[Int]): List[List[Int]] = {
      val res: ArrayBuffer[List[Int]] = ArrayBuffer()

      // 0.边界判断
      if (nums.length >= 3) {
        // 0.特殊判断
        if (nums.toSet.size == 1) {
          if (nums(0) != 0) {
            return res.toList
          }
          return List(List(0, 0, 0))
        }
        // 1.真正要被干掉的是 one 我不希望 one 再被使用 [one 的所有可能性都消耗殆尽了 其他仍有希望]
        val oneCandidatesQueue = mutable.Queue(nums.toList.init: _ *)
        while (oneCandidatesQueue.nonEmpty) {
          val one = oneCandidatesQueue.dequeue()
          val numAndCountMap = mutable.Map.empty ++= oneCandidatesQueue.map(n => (n, 1)).groupMapReduce(_._1)(_._2)(_ + _)
          numAndCountMap.put(nums.last, numAndCountMap.getOrElse(nums.last, 0) + 1)

          // 2.循环遍历所有 two [这个循环 是 要可变的，可减少的]
          // for (two <- oneCandidatesQueue) {
          val twoAndThreeCandidatesQueue = oneCandidatesQueue.clone()
          while (twoAndThreeCandidatesQueue.nonEmpty) {
            val two = twoAndThreeCandidatesQueue.dequeue()
            keyCountReduce(numAndCountMap, two)
            val three = -(one + two)
            if (numAndCountMap.contains(three) && numAndCountMap(three) > 0) {
              res.addOne(List(one, two, three))
              // 3.three 无需再次成为 two 进行匹配
              twoAndThreeCandidatesQueue.dequeueFirst(_ == three)
              // 4.three 次数也要 -1 避免再被匹配到
              keyCountReduce(numAndCountMap, three)
            } else {
              keyCountIncrement(numAndCountMap, two)
            }
          }
        }
      }

      // 结果去重
      res.map(_.sorted).toSet.toList
    }

    /**
     * 这个版本是 各个元素 只能使用一次
     *
     * @param nums 输入元素数组
     * @return List(三个数(和为零))
     */
    @unused
    def threeSum_SecondEdition(nums: Array[Int]): List[List[Int]] = {
      val res: ArrayBuffer[List[Int]] = ArrayBuffer()

      // 0.边界判断
      if (nums.length >= 3) {
        val candidatesQueue = mutable.Queue(nums: _ *)
        // 错误 3: 读题错误 -- 并不是 one two 结束 one 丢掉
        // 而是 two = 剩余全体，one 遍历全部 two 才能被丢掉
        var one = candidatesQueue.dequeue()
        var two = candidatesQueue.front
        // 错误 2: candidatesSet 和 one two 相同位置 并且不能包含 two
        var candidatesSet = candidatesQueue.toList.tail.toSet
        breakable(
          while (candidatesQueue.length >= 2) {
            val three = -(one + two)
            if (candidatesSet.contains(three)) {
              res.addOne(List(one, two, three))
              candidatesQueue.dequeueFirst(_ == three)
            }
            // 错误 1: front 空指针
            if (candidatesQueue.size <= 1) {
              break
            }
            one = candidatesQueue.dequeue()
            two = candidatesQueue.front
            candidatesSet = candidatesQueue.toList.tail.toSet
          }
        )
      }

      res.toList
    }

    /**
     * 这个版本是 初始版本 仍存在 BUG
     *
     * @param nums 输入元素数组
     * @return List(三个数(和为零))
     */
    @unused
    def threeSum_FirstEdition(nums: Array[Int]): List[List[Int]] = {
      val res: ArrayBuffer[List[Int]] = ArrayBuffer()
      // 0.边界判断
      if (nums.length >= 3) {
        val numAndCountMap: mutable.Map[Int, Int] = mutable.Map.empty ++= nums.map(n => (n, 1)).groupMapReduce(_._1)(_._2)(_ + _)
        // println(s"Initial Map $numAndCountMap")
        for (index1 <- nums.indices) {
          // 错误 1: 应从 index1 + 1 而不是 1 开始
          for (index2 <- index1 + 1 until nums.length) {
            // println(s"$index1 $index2")
            val one = nums(index1)
            val two = nums(index2)
            val three = -(one + two)
            // 错误 4: 理应先删除自身 one two in Map 再判断 three，
            // 理应用 Queue 队列来处理这个问题 但一开始思路被另一个类似问题 影响了 贪图 Map 性能
            // 改进思路: 先 pop 出 one two，然后遍历队列 寻找 three
            if (numAndCountMap.contains(three) && numAndCountMap(three) > 0) {
              // 错误 2: 递进条件 不能写在 上面的 if
              // 0 0 0 情况
              if (one == two && two == three) {
                if (numAndCountMap(0) >= 3) {
                  res.addOne(List(0, 0, 0))
                  keyCountReduce(numAndCountMap, 0, 3)
                }
              }
              // 错误 3: 1 -2 1 情况
              else if (one == three || two == three) {
                if (numAndCountMap(three) > 1) {
                  res.addOne(List(one, two, three))
                  List(one, two, three).foreach(keyCountReduce(numAndCountMap, _))
                }
                // println(s"Reduced Map $one $two $three $numAndCountMap")
                // 改进点①: 删除 nums 队列指定索引元素 one two three 减少循环数量
              } else {
                res.addOne(List(one, two, three))
                List(one, two, three).foreach(keyCountReduce(numAndCountMap, _))
              }
            }
          }
        }
      }
      res.toList
    }

    ResultSet(threeSum(nums).map(_.map(_.asInstanceOf[Any])).map(_.toArray).toArray, "selfSolution")
  }

  override def optimumSolution(inputSet: InputSet): ResultSet = ???

  override def peculiarSolution_1(inputSet: InputSet): ResultSet = ???

  override def peculiarSolution_2(inputSet: InputSet): ResultSet = ???

  override def peculiarSolution_3(inputSet: InputSet): ResultSet = ???
}

object HOT100_15_3Sum_Resolution {
  def main(args: Array[String]): Unit = {
    val inputSet = InputSet(Array(Array[Any](-4, -2, 1, -5, -4, -4, 4, -2, 0, 4, 0, -2, 3, 1, -5, 0)))
    val resolution = new HOT100_15_3Sum_Resolution
    resolution.testAllSolution(inputSet)
  }
}