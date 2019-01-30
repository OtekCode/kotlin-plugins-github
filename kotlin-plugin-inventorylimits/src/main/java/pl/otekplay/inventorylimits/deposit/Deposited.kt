package pl.otekplay.inventorylimits.deposit


data class Deposited(val id:Int, val data: Short, var amount:Int){
    val pull get() = amount--
    val canPull get() = amount > 0


    fun addAmount( am: Int){
        amount += am
    }
}