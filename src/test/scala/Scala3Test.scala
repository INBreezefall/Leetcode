package scala

import scala.compiletime.summonInline

object InlineTest {
  given context: String = "definition"

  /**
   * inline 内联代码块 -- 将 内联传入参数代码 直接嵌入 参数所在位置 [即累积调用多次]
   * summonInline 也会根据 调用环境中的 given 进行覆盖
   *
   * @param p 内联参数 = 可以用函数替代 => 内联传入函数参数
   */
  inline def f(inline p: Int): Unit = {
    val ctx = summonInline[String]
    println(s"Inline1: parameter: $p, given $ctx")
    println(s"Inline2: parameter: $p, given $ctx")
  }

  /**
   * 普通函数 则是 基本的传值调用
   * summonInline 仅依照 其定义区域内的 given
   *
   * @param p 值
   */
  def g(p: Int): Unit = {
    val ctx = summonInline[String]
    println(s"Regular1: parameter: $p, given $ctx")
    println(s"Regular2: parameter: $p, given $ctx")
  }

  def main(args: Array[String]): Unit = {
    given context: String = "usage"

    var a = 1

    def sideEffect(): Int = {
      a += 1
      a
    }

    f(sideEffect())
    g(sideEffect())
  }
}

case class ColDef[T](name: String)

object MatchTest {
  // 提取子类型
  type Elem[X] = X match
    case BigInt => Int
    case String => Char
    case List[t] => t

  def lastOneElem[T](something: T): Elem[T] = something match {
    case b: BigInt => (b % 10).toInt
    case s: String => if (s.isEmpty) throw new NoSuchElementException else s.charAt(s.length - 1)
    case l: List[_] => if (l.isEmpty) throw new NoSuchElementException else l.last
  }

  // 空元组作为停止条件返回
  // '*:' 作为递归匹配的含义 代表了减员的右侧
  // xs = Xs 实例变量 类型约束为了 <: Tuple 子类型
  type CallArgs[Xs <: Tuple] <: Tuple = Xs match
    case EmptyTuple => Xs
    case ColDef[b] *: xs => b *: CallArgs[xs]

  /**
   * 使用场景:
   *   1.更加灵活的 子类型提取 + 严格的编译器级别的类型安全检测
   *   2.递归 匹配
   */
  def main(args: Array[String]): Unit = {
    val lastChar: Elem[String] = lastOneElem("abcde")
    lastChar match {
      case c: Char => println(s"lastChar: $c")
      case _ => println("Error: it should be Char, but not")
    }
  }
}