fun main() {
    fun part1(input: List<String>): Int {
        fun isValidSymbol(symbol: Char): Boolean {
            return !symbol.isDigit() && symbol != '.'
        }

        var result = 0

        input.forEachIndexed { lineNum, line ->
            var num = -1
            var haveAdjacentSymbol = false

            line.forEachIndexed { index, c ->
                if(c.isDigit()){
                    num = when(num){
                        -1 -> c.digitToInt()
                        else -> num * 10 + c.digitToInt()
                    }

                    haveAdjacentSymbol = haveAdjacentSymbol || ( (lineNum > 0 && isValidSymbol(input[lineNum - 1][index])) || //up
                            (lineNum + 1 < input.size && isValidSymbol(input[lineNum + 1][index])) || //down
                            (index > 0 && isValidSymbol(input[lineNum][index - 1])) || // left
                            (index + 1 < line.length && isValidSymbol(input[lineNum][index + 1])) || //right
                            (lineNum > 0 && index > 0 && isValidSymbol(input[lineNum - 1][index - 1])) || //upper left
                            (lineNum > 0 && index + 1 < line.length && isValidSymbol(input[lineNum - 1][index + 1])) || // upper right
                            (lineNum + 1 < input.size && index > 0 && isValidSymbol(input[lineNum + 1][index - 1])) || //down left
                            (lineNum + 1 < input.size && index + 1 < line.length && isValidSymbol(input[lineNum + 1][index + 1])) ) //down right

                }else if(num != -1 && haveAdjacentSymbol){
                    result += num
                    num = -1
                    haveAdjacentSymbol = false
                }else
                    num = -1
            }

            if(num != -1 && haveAdjacentSymbol) result += num
        }

        return result
    }

    fun part2(input: List<String>): Int {
        fun isValidSymbol(symbol: Char): Boolean {
            return symbol == '*'
        }

        var result = 0

        val candidateGears = mutableMapOf<Pair<Int, Int>, MutableList<Int>>()

        input.forEachIndexed { lineNum, line ->
            var adjacentGears = mutableSetOf<Pair<Int, Int>>()
            var num = -1

            line.forEachIndexed { index, c ->
                if(c.isDigit()){
                    num = when(num){
                        -1 -> c.digitToInt()
                        else -> num * 10 + c.digitToInt()
                    }

                    if(lineNum > 0 && isValidSymbol(input[lineNum - 1][index])) adjacentGears.add(Pair(lineNum - 1, index)) //up
                    if(lineNum + 1 < input.size && isValidSymbol(input[lineNum + 1][index])) adjacentGears.add(Pair(lineNum + 1, index)) //down
                    if(index > 0 && isValidSymbol(input[lineNum][index - 1])) adjacentGears.add(Pair(lineNum, index - 1)) //left
                    if(index + 1 < line.length && isValidSymbol(input[lineNum][index + 1])) adjacentGears.add(Pair(lineNum, index + 1)) //right
                    if(lineNum > 0 && index > 0 && isValidSymbol(input[lineNum - 1][index - 1])) adjacentGears.add(Pair(lineNum - 1, index - 1)) //upper left
                    if(lineNum > 0 && index + 1 < line.length && isValidSymbol(input[lineNum - 1][index + 1])) adjacentGears.add(Pair(lineNum - 1, index + 1)) //upper right
                    if(lineNum + 1 < input.size && index > 0 && isValidSymbol(input[lineNum + 1][index - 1])) adjacentGears.add(Pair(lineNum + 1, index - 1)) //down left
                    if(lineNum + 1 < input.size && index + 1 < line.length && isValidSymbol(input[lineNum + 1][index + 1])) adjacentGears.add(Pair(lineNum + 1, index + 1)) //down right

                }else {
                    adjacentGears.forEach { gearPos ->
                        if(candidateGears.containsKey(gearPos)) candidateGears[gearPos]!!.add(num)
                        else candidateGears.put(gearPos, mutableListOf(num))
                    }

                    num = -1
                    adjacentGears.clear()
                }
            }

            if(adjacentGears.isNotEmpty())
                adjacentGears.forEach { gearPos ->
                    if(candidateGears.containsKey(gearPos)) candidateGears[gearPos]!!.add(num)
                    else candidateGears.put(gearPos, mutableListOf(num))
                }
        }

        candidateGears.forEach { key, value ->
            if(value.size == 2) result += value[0] * value[1]
        }

        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03-test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
