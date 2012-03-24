import org.specs2._
class AppSpec extends Specification { def is =

    "文字列'Hello world'に関するテスト"                                         ^
                                                                                p^
    "文字列'Hello world'は"                                                     ^
      "長さが11であること"                                                      ! e1^
      "'Hello'で始まること"                                                     ! e2^
      "'world'で終わること"                                                     ! e3^
                                                                                end

    def e1 = "Hello world" must have size(11)
    def e2 = "Hello world" must startWith("Hello")
    def e3 = "Hello world" must endWith("world")
}
