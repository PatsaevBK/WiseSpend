package info.javaway.spend_sense.events.models

import info.javaway.spend_sense.extensions.now
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.plus

data class SpendEvent(
    val id: String,
    val categoryId: String,
    val title: String,
    val cost: Double,
    val date: LocalDate,
    val createAt: LocalDateTime,
    val updateAt: LocalDateTime,
) {
    companion object {
        val NONE = SpendEvent(
            id = "",
            categoryId = "",
            title = "",
            cost = 0.0,
            date = LocalDate.now(),
            createAt = LocalDateTime.now(),
            updateAt = LocalDateTime.now()
        )
        
        fun getStubs() = List(20) {
            NONE.copy(
                id = "$it",
                title = "event $it",
                cost = it.toDouble(),
                date = LocalDate.now().plus(it, DateTimeUnit.DAY),
            )
        }
    }
}
