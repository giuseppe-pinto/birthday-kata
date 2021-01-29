import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.Month

class NotifierTest{

  private val sender = FakeSender()

  @Test
  fun sendGreetingForOnlyOneFriend() {
    
    val repository = FakeFriendRepository(listOf(
        Friend("Doe", "John", LocalDate.of(1982, Month.AUGUST, 2), "john.doe@foobar.com"), 
        Friend("Ann", "Mary", LocalDate.of(1966, Month.JANUARY, 16), "mary.ann@foobar.com")))
    val sut = Notifier(repository, sender) { LocalDate.of(2020, Month.JANUARY, 16) }

    sut.sendGreetings()
    
    assertEquals(sender.sent, true)
    assertEquals(sender.email, listOf("mary.ann@foobar.com"))
  }

  @Test
  fun sendGreetingForMultiFriend() {
    
    val repository = FakeFriendRepository(listOf(
        Friend("Doe", "John", LocalDate.of(1982, Month.AUGUST, 2), "john.doe@foobar.com"),
        Friend("Ann", "Mary", LocalDate.of(1966, Month.JANUARY, 16), "mary.ann@foobar.com"),
        Friend("Mario", "Ciccio", LocalDate.of(1977, Month.JANUARY, 16), "mario.ciccio@boj.com")))
    val sut = Notifier(repository, sender) { LocalDate.of(2020, Month.JANUARY, 16) }
    
    sut.sendGreetings()

    assertEquals(sender.sent, true)
    assertIterableEquals(sender.email, listOf("mary.ann@foobar.com", "mario.ciccio@boj.com")) 
  }

  @Test
  fun doNotSendGreetings() {

    val repository = FakeFriendRepository(listOf(
        Friend("Doe", "John", LocalDate.of(1982, Month.AUGUST, 2), "john.doe@foobar.com"),
        Friend("Ann", "Mary", LocalDate.of(1966, Month.JANUARY, 16), "mary.ann@foobar.com"),
        Friend("Mario", "Ciccio", LocalDate.of(1977, Month.JANUARY, 16), "mario.ciccio@boj.com")))
    val sut = Notifier(repository, sender) { LocalDate.of(2020, Month.JANUARY, 18) }


    sut.sendGreetings()

    assertEquals(sender.sent, false)
    assertEquals(sender.email, emptyList<String>())

  }
}

class FakeFriendRepository(var friends: List<Friend>) : FriendRepository {

  override fun read(): List<Friend>{
    return friends
  }
  
}

class FakeSender(var sent: Boolean = false, var email: MutableList<String> = mutableListOf()) : Sender {
  
  override fun sendTo(receiver:String) {
    sent = true
    email.add(receiver)
    println("Message Sent to $email")
  }
}
