fun main() {
    fun part1(input: List<String>): Int {
        var result = 0

        input.forEach {current ->
            var nums = current.filter { it.isDigit() }

            result += (nums[0].digitToInt() * 10) + nums.last().digitToInt()
        }

        return result
    }

    fun part2(input: List<String>): Int {
        val mapped = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        var result = 0

        input.forEach {current ->
            val indexes: ArrayList<Pair<Int, Int>> = arrayListOf()

            mapped.forEachIndexed { index, s ->
                Regex(s).findAll(current).map { it.range.first }.forEach {
                    indexes.add(Pair(it, index + 1))
                }
            }

            current.forEachIndexed { index, c ->
                if(c.isDigit())
                    indexes.add(Pair(index, c.digitToInt()))
            }

            indexes.sortWith(compareBy { it.first })

            //println(indexes)
            result += indexes.first().second * 10 + indexes.last().second
        }

        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01-test")
    check(part1(testInput) == 220)
    check(part2(testInput) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
