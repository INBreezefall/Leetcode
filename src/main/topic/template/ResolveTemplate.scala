package topic.template

/**
 * 不同题目-变化:
 * 1.函数入参、返回值 不同 => 统一封装入参对象 统一封装出参对象
 * 2.依赖的数据结构 不同 => 封装进入独立函数
 * 3.测试输入变量类型 不同 => 覆写
 * 4.测试结果变量校验 不同 => 抽入一个函数 覆写
 *
 * 一个解题模板类的构成
 * 1.最优解方法 * 1
 * 2.奇特解方法 * 3
 * 3.测试方法 * 1
 * 4.主方法 * 1
 */

case class InputSet(var input: Array[Array[Any]])
case class ResultSet(var res: Array[Array[Any]], var funcName: String)

trait ResolveTemplate {
  // TODO 使用 Scala 3 反射 进行函数遍历
  val funcList: List[InputSet => ResultSet] = List(fooSolution, optimumSolution, peculiarSolution_1,
    peculiarSolution_2, peculiarSolution_3)

  /**
   * 首个函数运行 会进行 首次变量初始化 较为耗时 30~50ms
   * fooSolution 仅是减少 初始化干扰
   */
  def fooSolution(inputSet: InputSet): ResultSet = ResultSet(inputSet.input, "fooSolution")
  def optimumSolution(inputSet: InputSet): ResultSet
  def peculiarSolution_1(inputSet: InputSet): ResultSet
  def peculiarSolution_2(inputSet: InputSet): ResultSet
  def peculiarSolution_3(inputSet: InputSet): ResultSet

  def assertFunc(res: ResultSet): Unit

  def testOneSolution(input: InputSet, solution: InputSet => ResultSet): Unit = {
    // 0.Initialization
    val start = System.currentTimeMillis()
    var isPass = true
    var res: ResultSet = null

    // 1.Execution
    try {
      res = solution(input)
    } catch {
      case e: Throwable =>
        println(s"[Test] ${res.funcName} Exception: ${e.getMessage}, ${e.printStackTrace()}")
        isPass = false
    }

    // 2.Assert
    assertFunc(res)

    // 3.Test Report
    println(s"[Test] [$isPass] ${res.funcName} elapse: ${System.currentTimeMillis() - start}")
  }

  def testAllSolution(input: InputSet): Unit = {
    funcList.foreach(testOneSolution(input, _))
  }
}

object ResolveTemplate {
  /**
   * 基本类型参数: 传值，函数内部形参变化不影响传入实参
   * 数组: 传地址，函数内部形参 与 传入实参 指向的地址相同
   * 不要对 函数参数 进行赋值，这是无意义的
   */
  def main(args: Array[String]): Unit = {

    class AnonymousExample() extends ResolveTemplate {
      override def optimumSolution(inputSet: InputSet): ResultSet = ResultSet(Array.empty, "optimumSolution")

      override def peculiarSolution_1(inputSet: InputSet): ResultSet = ResultSet(Array.empty, "peculiarSolution_1")

      override def peculiarSolution_2(inputSet: InputSet): ResultSet = ResultSet(Array.empty, "peculiarSolution_2")

      override def peculiarSolution_3(inputSet: InputSet): ResultSet = ResultSet(Array.empty, "peculiarSolution_3")

      override def assertFunc(res: ResultSet): Unit = {}
    }

    val example = new AnonymousExample()
    example.testAllSolution(InputSet(Array.empty))
  }
}
