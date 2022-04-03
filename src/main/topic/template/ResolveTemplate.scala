package template

/**
 * 不同题目-变化:
 * 1.函数入参、返回值 不同
 * 2.依赖的数据结构 不同
 * 3.测试输入变量类型 不同
 * 4.测试结果变量校验 不同
 *
 * 一个解题模板类的构成
 * 1.最优解方法 * 1
 * 2.奇特解方法 * 5
 * 3.测试方法 * 1
 * 4.主方法 * 1
 */
object ResolveTemplate {
  /**
   * 首个函数运行 会进行 首次变量初始化 较为耗时 30~50ms
   * 故此 这个函数仅仅是减少 初始化干扰
   */
  def fooSolution(): Unit = {}

  def optimumSolution(): Unit = {}

  def peculiarSolution_1(): Unit = {}

  def peculiarSolution_2(): Unit = {}

  def peculiarSolution_3(): Unit = {}

  def peculiarSolution_4(): Unit = {throw Throwable("I don't know either")}

  def peculiarSolution_5(): Unit = {}

  def testOneSolution()(solutionNameAndFunc: (String, () => Unit)): Boolean = {
    val start = System.currentTimeMillis()
    var isPass = true
    var res: Unit = AnyRef
    try {
      res = solutionNameAndFunc._2()
    } catch {
      case e: Throwable =>
        println(s"[Test] ${solutionNameAndFunc._1} Exception: ${e.getMessage}, ${e.printStackTrace()}")
        isPass = false
    }
    // Assert
    if (!"peculiarSolution_4".equals(solutionNameAndFunc._1)) isPass = true
    println(s"[Test] ${solutionNameAndFunc._1} isPass: $isPass, elapse: ${System.currentTimeMillis() - start}")
    isPass
  }

  def main(args: Array[String]): Unit = {
    val solutionNameAndFuncs = List(("fooSolution", fooSolution _),
      ("optimumSolution", optimumSolution _),
      ("peculiarSolution_1", peculiarSolution_1 _),
      ("peculiarSolution_2", peculiarSolution_2 _),
      ("peculiarSolution_3", peculiarSolution_3 _),
      ("peculiarSolution_4", peculiarSolution_4 _),
      ("peculiarSolution_5", peculiarSolution_5 _))
    solutionNameAndFuncs.foreach(solutionNameAndFunc => testOneSolution()(solutionNameAndFunc))
  }
}
