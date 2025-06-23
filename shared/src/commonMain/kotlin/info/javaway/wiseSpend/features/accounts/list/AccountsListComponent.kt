package info.javaway.wiseSpend.features.accounts.list

import com.arkivanov.decompose.ComponentContext

interface AccountsListComponent {

    interface Factory {
        fun create(componentContext: ComponentContext) : AccountsListComponent
    }
}