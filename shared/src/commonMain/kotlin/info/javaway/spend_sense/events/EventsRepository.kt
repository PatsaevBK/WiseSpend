package info.javaway.spend_sense.events

import info.javaway.spend_sense.events.models.SpendEvent
import kotlinx.coroutines.flow.flow

class EventsRepository {

    fun getAllFlow() = flow { emit(SpendEvent.getStubs()) }

    fun create(spendEvent: SpendEvent) {

    }
}