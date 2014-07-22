import org.specs2._

class AppSpec extends Specification { def is = s2"""

test about String 'Hello world'

'Hello world'
  has 11 length                                                                 \$e1
  starts with 'Hello'                                                           \$e2
  ends with 'world'                                                             \$e3
                                                                                """

    def e1 = "Hello world" must have size(11)
    def e2 = "Hello world" must startWith("Hello")
    def e3 = "Hello world" must endWith("world")
}
