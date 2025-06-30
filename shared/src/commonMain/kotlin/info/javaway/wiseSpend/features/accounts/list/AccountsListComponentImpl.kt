package info.javaway.wiseSpend.features.accounts.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import info.javaway.wiseSpend.extensions.componentScope
import info.javaway.wiseSpend.features.accounts.creation.CreateAccountComponent
import info.javaway.wiseSpend.features.accounts.creation.CreateAccountComponentImpl
import info.javaway.wiseSpend.features.accounts.data.AccountRepository
import info.javaway.wiseSpend.features.accounts.models.Account
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class AccountsListComponentImpl(
    componentContext: ComponentContext,
    private val accountRepository: AccountRepository,
): AccountsListComponent, ComponentContext by componentContext {

    private val scope = componentScope()

    private val _model: MutableStateFlow<AccountsListContract.State> = MutableStateFlow(
        AccountsListContract.State(emptyList())
    )

    init {
        scope.launch {
            _model.update { it.copy(accounts = accountRepository.getAll()) }
        }
    }

    override val model: StateFlow<AccountsListContract.State> = _model.asStateFlow()

    private val nav = SlotNavigation<Config>()
    override val slot: Value<ChildSlot<*, CreateAccountComponent>> = childSlot(
        source = nav,
        serializer = Config.serializer(),
        handleBackButton = true,
        childFactory = { cnf, ctx ->
            CreateAccountComponentImpl(
                account = cnf.account,
                componentContext = ctx,
                onSave = { account ->
                    scope.launch {
                        if (cnf.account == null) {
                            accountRepository.create(account)
                        } else {
                            accountRepository.update(
                                id = account.id,
                                name = account.name,
                                amount = account.amount,
                                updatedAt = account.updatedAt
                            )
                        }
                        nav.dismiss()
                    }
                }
            )
        },
    )

    init {
        subscribeAccounts()
    }

    override fun changeAccount(accountId: String) {
        scope.launch {
            val account = accountRepository.getById(accountId) ?: return@launch
            nav.activate(Config(account))
        }
    }

    override fun newAccount() {
        nav.activate(Config(null))
    }

    override fun onDismiss() {
        nav.dismiss()
    }

    private fun subscribeAccounts() {
        accountRepository.getAllFlow().onEach {
            _model.update { oldState -> oldState.copy(accounts = it) }
        }.launchIn(scope)
    }

    @Serializable
    private data class Config(val account: Account?)

    class Factory(
        private val accountRepository: AccountRepository,
    ) : AccountsListComponent.Factory {
        override fun create(componentContext: ComponentContext): AccountsListComponent {
            return AccountsListComponentImpl(componentContext, accountRepository)
        }
    }
}