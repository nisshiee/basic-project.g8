object App {
  def main(args: Array[String]): Unit = {
    print("Hello $organization$.$name$!")
  }

  /**
   * {{{
   * // hello returns "Hello World"
   * scala> App.hello
   * res1: String = Hello World
   * }}}
   */
  def hello: String = "Hello World"

  /**
   * {{{
   * // double returns doubled value
   * scala> App.double(20)
   * res1: Int = 40
   *
   * // double returns 0 when argument is 0
   * scala> App.double(0)
   * res2: Int = 0
   *
   * prop> (i: Int) =>
   *     |   App.double(i) == i * 2
   * }}}
   */
  def double(x: Int) = x + x
}
