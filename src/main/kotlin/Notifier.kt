import java.time.LocalDate
import java.util.function.Supplier

class Notifier(private val repository: FriendRepository,
               private val sender: Sender,
               private val dateSupplier: Supplier<LocalDate>) {

  fun sendGreetings() {
    repository
        .read()
        .filter { it.dateOfBirth.dayOfMonth == dateSupplier.get().dayOfMonth && it.dateOfBirth.month == dateSupplier.get().month }
        .map { sender.sendTo(it.contact) }
  }
}