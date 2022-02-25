import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.Month

class FriendRepositoryFromFileTest {

    @Test
    fun shouldReadFromAFile() {

        val repositoryFromFile = FriendRepositoryFromFile("src/test/kotlin/testFile.txt")

        val friendList = repositoryFromFile.read()

        Assertions.assertAll("Asserting all the friends from the file",
            {
                assertEquals(
                    Friend(
                        "Doe",
                        "John",
                        LocalDate.of(1982, Month.OCTOBER, 8),
                        "john.doe@foobar.com"
                    ),
                    friendList[0]
                )
            },
            {
                assertEquals(
                    Friend(
                        "Ann",
                        "Mary",
                        LocalDate.of(1975, Month.SEPTEMBER, 11),
                        "mary.ann@foobar.com"
                    ),
                    friendList[1]
                )
            })
    }
}