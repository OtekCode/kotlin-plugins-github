package pl.otekplay.inventorylimits.deposit

import pl.otekplay.database.DatabaseEntity
import java.util.*

class Deposit(val uniqueId: UUID) : DatabaseEntity() {
    override val id: String get() = uniqueId.toString()
    override val key: String get() = "uniqueId"
    override val collection: String get() = "deposits"

    private val deposited = arrayListOf<Deposited>()

    fun getDeposit(id: Int, data: Short) = deposited.singleOrNull { it.id == id && it.data == data }

    fun addDeposit(id: Int, data: Short, amount: Int) = getDeposit(id, data)?.addAmount(amount)
            ?: createDeposit(id, data, amount)

    private fun createDeposit(id: Int, data: Short, amount: Int) = Deposited(id, data, amount).also { deposited.add(it) }


}