fun main() {
    fun part1(input: List<String>): Int {
        var result = 0

        val availableBalls = mapOf(Pair("red", 12), Pair("green", 13), Pair("blue", 14))

        input.forEachIndexed { game, gameString ->
            var isPossible = true

            gameString.substringAfter(':').split(";").forEach { play ->
                play.split(",").forEach { balls ->
                    val splitted = balls.substring(1).split(' ')

                    val amount = splitted[0].toInt()
                    val color = splitted[1]

                    if(availableBalls[color]!! < amount){
                        isPossible = false
                        return@forEach
                    }
                }

                if(!isPossible) return@forEach
            }

            if(isPossible) result += game + 1
        }

        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0

        input.forEachIndexed { game, gameString ->

            val minBalls = mutableMapOf(Pair("red", 0), Pair("green", 0), Pair("blue", 0))

            gameString.substringAfter(':').split(";").forEach { play ->
                play.split(",").forEach { balls ->
                    val splitted = balls.substring(1).split(' ')

                    val amount = splitted[0].toInt()
                    val color = splitted[1]

                    minBalls[color] = minBalls[color]!!.coerceAtLeast(amount)
                }

            }

            result += minBalls["red"]!! * minBalls["green"]!! * minBalls["blue"]!!
        }

        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02-test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
