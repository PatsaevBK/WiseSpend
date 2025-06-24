package info.javaway.wiseSpend.features.accounts.models

data class Amount(
    val integerPart: Int,
    val separator: String = ".",
    val floatPart: String,
    val currency: String,
) {
    val simpleAmount
        get() = "$integerPart$separator$floatPart"
}
