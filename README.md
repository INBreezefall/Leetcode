# 序言
### 核心思想
先内化 再创造

### 主要目标
1. 最优解 (Max_1)
2. 奇异思路解 (Max_5)
3. 全面 (国际站 & 国内站)
4. 本地测试 且 兼容网页提交
5. 自身思考发展痕迹
6. 可继承可复制 (Pave reliable and efficient roads)
7. 面向 0-75 分选手 (大道至简 返璞归真 易于理解)

# 架构
### Package
```text
    一级分类: 单题 || 题单(超链) || 周赛
    二级分类: 编程语言
    三级分类: LC 题型标签 || 题单名称 || 周序号
    四级分类: 难易程度
    
    - Topic | Group | Contest
    -- Java | Scala
    --- Tags | Titles | Numbers
    ---- Complexity
```
### 类
```text
    一个解题模板类的构成
        1.最优解方法 * 1
        2.奇特解方法 * 3
        3.测试方法 * 1
        4.主方法 * 1
        5.foo方法 * 1
```
### 测试框架
Java: Junit 5
Scala: ScalaTest 3 - Scala 3