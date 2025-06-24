package info.javaway.wiseSpend.features.accounts.creation

import kotlinx.coroutines.flow.StateFlow

interface CreateAccountComponent {

    val model: StateFlow<CreateAccountContract.State>

    fun changeName(name: String)
    fun changeAmount(amount: String)
    fun finish()
}