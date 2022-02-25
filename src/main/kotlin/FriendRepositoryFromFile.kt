import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.stream.Collectors

class FriendRepositoryFromFile(private val path: String) : FriendRepository {

    override fun read(): List<Friend> {

        return File(path)
            .useLines { it.toList() }
            .stream()
            .skip(1)
            .map(this::convertToFriend)
            .collect(Collectors.toList())

    }

    private fun convertToFriend(it: String): Friend {
        val split = it.split(",")
        return Friend(split[0].trim(), split[1].trim(), toLocalDate(split[2].trim()), split[3].trim())
    }

    private fun toLocalDate(stringDate: String): LocalDate {
        return LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("yyyy/MM/dd"))
    }


}