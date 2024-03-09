package net.maiatoday.forestwalk.lsystem

class Plant {
    var seed: String = ""
    var rules: Map<String, String> = emptyMap<String, String>()
    var bluePrint = seed
    var iterations = 0
    fun grow(repeat: Int) {
        iterations = repeat
        bluePrint = seed
        repeat(iterations) {
            for (rule in rules) {
                bluePrint = bluePrint.replace(rule.key, rule.value)
            }
        }
    }
}

fun plant(block: Plant.() -> Unit): Plant {
    val plant = Plant()
    plant.block()
    return plant
}