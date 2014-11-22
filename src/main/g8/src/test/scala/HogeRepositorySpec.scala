import org.specs2._
import scalikejdbc._
import scalikejdbc.specs2.AutoRollback

class HogeRepositorySpec extends Specification {
  def is = s2"""

if you want test repository classes, use this template                          \${todo}
                                                                                """
}
//class HogeRepositorySpec extends Specification with DBSettings {
//  def is = s2"""
// 
//all                                                                             \${Db().e1}
//                                                                                """
// 
//  case class Db() extends AutoRollback {
// 
//    def e1 = this{
//      val actual = HogeRepository.all()
//      actual must equalTo(Hoge(1))
//    }
//  }
//}
