fun main() {

    println(requestTransfer(100, accountType = "Maestro"))
    println(requestTransfer(75_001, accountType = "MasterCard"))
    println(requestTransfer(5000, accountType = "Maestro", previousTransfers = 70_001))
    println(requestTransfer(1000))
    println(requestTransfer(100, accountType = "Рога и копыта"))
    println(requestTransfer(15_001))
    println(requestTransfer(10_000, previousTransfers = 30_001))

}

fun commissionCalculation(amount: Int, accountType: String, previousTransfers: Int): Int {

    var commission = 0

    when (accountType) {
        "MasterCard", "Maestro" -> if (amount > 75_000 - previousTransfers) {
            commission = (((amount.toDouble() / 100) * 0.6).toInt()) + 20
        }
        "Visa", "Мир" -> {
            val value = ((amount.toDouble() / 100) * 0.75).toInt()
            commission = if (value > 35) value else 35
        }
    }
    return commission
}

fun requestTransfer(amount: Int, accountType: String = "Vk Pay", previousTransfers: Int = 0): String {

    val message = when (accountType) {
        "MasterCard", "Maestro", "Visa", "Мир" -> if (amount > 150_000) {
            "Сумма перевода не может привышать 150_000"
        } else if (previousTransfers + amount > 600_000) {
            "Привышен месячный лимит"
        } else {
            val commission = commissionCalculation(amount, accountType, previousTransfers)
            "Комиссия составит $commission рублей"
        }
        "Vk Pay" -> if (amount > 15_000) {
            "Сумма перевода не может привышать 15_000"
        } else if (previousTransfers + amount > 40_000) {
            "Привышен месячный лимит"
        } else {
            val commission = commissionCalculation(amount, accountType, previousTransfers)
            "Комиссия составит $commission рублей"
        }
        else -> "Тип карты/счёта не опознан"
    }
    return message
}