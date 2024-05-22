package net.maiatoday.forestwalk.lsystem

import kotlin.random.Random

data class Plant(
    val seed: String,
    val rules: Map<String, String>,
    val bluePrint: String,
    val iterations: Int
)

class PlantBuilder {
    var seed: String = ""
    var rules: Map<String, String> = emptyMap<String, String>()
    private var bluePrint = seed
    private var iterations = 0

    fun grow(repeat: Int) {
        iterations = repeat
        bluePrint = seed
        repeat(iterations) {
            for (rule in rules) {
                bluePrint = bluePrint.replace(rule.key, rule.value)
            }
        }
    }


    fun sprout(repeat: Int, probabilities: List<Double>) {
        require(probabilities.size >= rules.size) {
            "One probability for each rule. Fill the list with 1.0"
        }
        iterations = repeat
        bluePrint = seed
        repeat(iterations) {
            var index = 0
            for (rule in rules) {
                val dieRoll = Random.nextDouble()
                val probability = probabilities[index]
                if (dieRoll <= probability)
                    bluePrint = bluePrint.replace(rule.key, rule.value)
                index++
            }
        }
    }

    internal fun buildPlant() = Plant(seed, rules, bluePrint, iterations)
}

fun plant(block: PlantBuilder.() -> Unit): Plant =
    PlantBuilder().apply(block).buildPlant()

