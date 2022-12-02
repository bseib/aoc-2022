fun main() {
    Problem2.run()
}

object Problem2 {

    class Weapon(moniker: String) {
        val name: String
        val value: Int
        val flipValue  // translate a values of { 1, 2, 3 } => { 3, 2, 1 }
            get() = 1 + ((value * 2) % 3)

        init {
            // Oh Kotlin, why you no assign directly to member variables with destructuring? 😢
            val (name, value) = when (moniker) {
                "A", "X" -> Pair<String, Int>("Rock", 1)
                "B", "Y" -> Pair<String, Int>("Paper", 2)
                "C", "Z" -> Pair<String, Int>("Scissors", 3)
                else -> throw Exception("invalid moniker")
            }
            this.name = name
            this.value = value
        }
        //       X=1  Y=2  Z=3  <== me
        //  A=3   3    6    0
        //  B=2   0    3    6     ((me + them) % 3) * 3
        //  C=1   6    0    3
        //  them (flipped to descending 3, 2, 1 values)
        fun battle(opponentWeapon: Weapon) = value + (((value + opponentWeapon.flipValue) % 3) * 3)
    }

    fun run() {
        val battle0 = toWeaponPairList(data0()).map { it.second.battle(it.first) }.sum()
        println("battle0 = ${battle0}")
    }

    private fun toWeaponPairList(data: List<String>): List<Pair<Weapon, Weapon>> {
        return data.map { it.trim() }
            .map { it.split(" ") }
            .map { Pair(Weapon(it[0]), Weapon(it[1])) }
    }

    fun data0() = """
        A Y
        B X
        C Z
    """.trimIndent().split("\n").map { it.trim() }
}
