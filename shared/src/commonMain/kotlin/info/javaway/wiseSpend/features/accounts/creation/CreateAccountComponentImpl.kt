package info.javaway.wiseSpend.features.accounts.creation

import com.arkivanov.decompose.ComponentContext
import info.javaway.wiseSpend.features.accounts.creation.CreateAccountContract.State.Companion.NONE
import info.javaway.wiseSpend.features.accounts.models.Account
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CreateAccountComponentImpl(
    account: Account?,
    componentContext: ComponentContext,
    private val onSave: (Account) -> Unit,
) : CreateAccountComponent, ComponentContext by componentContext {

    private val _model = MutableStateFlow(CreateAccountContract.State(account ?: NONE.account))
    override val model: StateFlow<CreateAccountContract.State> = _model.asStateFlow()

    override fun changeName(name: String) {
        _model.update { oldState ->
            oldState.copy(account = oldState.account.copy(name = name))
        }
    }

    override fun changeAmount(amount: String) {
        amount.toDoubleOrNull()?.let {
            _model.update { oldState ->
                oldState.copy(account = oldState.account.copy(amount = it))
            }
        }
    }

    override fun finish() = onSave.invoke(model.value.account)
}