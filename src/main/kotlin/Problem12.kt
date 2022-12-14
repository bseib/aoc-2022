class Problem12 : DailyProblem<Int> {

    class HillGraph(
        val startNode: HillNode,
        val endNode: HillNode,
        val grid: List<List<HillNode>>,
    ) {
        val defaultTerminator = { node: HillNode -> node.isStartNode }
        fun findShortestPath(terminator: (node: HillNode) -> Boolean = defaultTerminator): Int {
            return endNode.shortestPathToStartNode(terminator) ?: throw Error("no route found")
        }

        override fun toString() = grid.joinToString("\n", "", "\n") { row ->
            row.map { it.letter }.joinToString("")
        }
    }

    class HillNode(val letter: Char) {
        val isStartNode = 'S' == letter
        val isEndNode = 'E' == letter
        val elevation = when (letter) {
            'S' -> 0
            'E' -> 25
            else -> letter.minus('a')
        }
        val above: MutableSet<HillNode> = mutableSetOf() // neighbors above us, any distance
        val below: MutableSet<HillNode> = mutableSetOf() // neighbors exactly -1 below us
        val equal: MutableSet<HillNode> = mutableSetOf() // neighbors with same elevation
        val every: MutableSet<HillNode> = mutableSetOf() // all neighbors, even those more than -1 below us
        var distanceFromEnd: Int = Int.MAX_VALUE

        fun maybeAddNeighbor(potentialNeighbor: HillNode) {
            if (!every.contains(potentialNeighbor)) {
                every.add(potentialNeighbor)
                if (elevation < potentialNeighbor.elevation) {
                    above.add(potentialNeighbor)
                }
                else if (1 == elevation - potentialNeighbor.elevation) {
                    below.add(potentialNeighbor)
                }
                else if (elevation == potentialNeighbor.elevation) {
                    equal.add(potentialNeighbor)
                }
                potentialNeighbor.maybeAddNeighbor(this)
            }
        }

        fun trailblaze(terminator: (node: HillNode) -> Boolean, nodes: Set<HillNode>, step: Int): Int? {
            return nodes.mapNotNull {
                if (step < it.distanceFromEnd) {
                    it.distanceFromEnd = step
                    it.shortestPathToStartNode(terminator, step + 1)
                }
                else null
            }.minOrNull()
        }

        fun shortestPathToStartNode(terminator: (node: HillNode) -> Boolean, step: Int = 0): Int? {
            return if (terminator(this)) step
            else trailblaze(terminator, below, step)
                ?: trailblaze(terminator, equal, step)
                ?: trailblaze(terminator, above, step)
        }

    }

    fun buildHillGraph(data: List<String>): HillGraph {
        val grid: MutableList<MutableList<HillNode>> = mutableListOf()
        var startNode: HillNode? = null
        var endNode: HillNode? = null
        data.forEachIndexed { y, line ->
            val row = mutableListOf<HillNode>()
            grid.add(row)
            line.toList().forEachIndexed { x, c ->
                val node = HillNode(c).also {
                    if (it.isStartNode) startNode = it
                    if (it.isEndNode) endNode = it
                }
                row.add(node)
                if (x > 0) node.maybeAddNeighbor(grid[y][x - 1])
                if (y > 0) node.maybeAddNeighbor(grid[y - 1][x])
            }
        }
        return HillGraph(startNode ?: throw Error("no start"), endNode ?: throw Error("no end"), grid)
    }

    fun solvePart0(): Int {
        val graph = buildHillGraph(data0)
        return graph.findShortestPath()
    }

    override fun solvePart1(): Int {
        val graph = buildHillGraph(data1)
        return graph.findShortestPath()
    }

    override fun solvePart2(): Int {
        val terminateAtElevationZero = { node: HillNode -> node.elevation == 0 }
        val graph = buildHillGraph(data1)
        return graph.findShortestPath(terminateAtElevationZero)
    }

    companion object {
        val data0 = """
            Sabqponm
            abcryxxl
            accszExk
            acctuvwj
            abdefghi
        """.toLines()

        val data1 = """
abcccaaaaaaccccccccaaaaaccccccaaaaaaccccccaaaaaaaacccaaaaaaaccaaaacccccccccccccccccccccccccaaaaaacccccccccccccccccccccccccccccaaaaaa
abcccaaaaaacccccccaaaaaaccccaaaaaaaacccccccaaaaaaaaaaaaaaaaccaaaaacccccccccccccccccccccccccaaaaaacccccccccccccccccccccccccccccaaaaaa
abccccaaaaacaaaccaaaaaaaacccaaaaaaaaacccccccaaaaaaaaaaaaaaaacaaaaaacccccccccaaacccccccccccaaaaaaaaccccccccccaaccccccccccccccccaaaaaa
abccccaaaaccaaaaaaaaaaaaacccaaaaaaaaaacccccaaaaaaaaaaaaaaaaaaacaaaacccccccccaaaacccccccccaaaaaaaaaacccccccccaaaccccccccccccccccccaaa
abcccccccccaaaaaacccaacccccccccaaacaaaccccccaacccccccaaaaaaaaacaacccccccccccaaaacccccccccaaaaaaaaaacccccccccaaaccacaaccccccccccccaaa
abcccccccccaaaaaacccaacccccccccaaacccccccccccccccccccaaaacaaaacccccccaacaaccaaaccccccccccaccaaaaacacccccccccaaaacaaaaccccccccccccaac
abccccccccccaaaaacccccccccccccccacccaaaacccccccccccccaaaacccccccccccccaaaacccccccccccaacccccaaaaccccccccjjjjaaaaaaaaaccccccccccccccc
abccccccccccaaaacccccccccccccccccccaaaaacccccccccccccaaaccccccccccccccaaaaacccccccccaaaaaacccaaccccccccjjjjjjkkaaaacccccccccaacccccc
abcccccaaccccccccccccccccccccccccccaaaaaacccccccccccccaacccccccccccccaaaaaaccccccccccaaaaaccccccccccccjjjjjjjkkkkaacccccaacaaacccccc
abccaaaacccccccccccccccccccccccccccaaaaaaccccccccccccccccccccccccccccaaaacaccccccccaaaaaaaccccaacccccjjjjoooookkkkkkkklllaaaaaaacccc
abccaaaaaacccccccccccccccccccccccccaaaaacccccccccccccccccccccccccccccccaaccccccccccaaaaaaaaccaaaaccccjjjoooooookkkkkkkllllaaaaaacccc
abcccaaaaacccccccccccccccccccccccccccaaaccccccccaaaacccccccccccccccccccccccccccccccaaaaaaaaccaaaaccccjjooooooooppkkppplllllaccaacccc
abccaaaaaccccccccccccaccccccccccccccccccccccccccaaaacccccccccccccccccccccccccccccccccaaacacccaaaacccijjooouuuuoppppppppplllccccccccc
abcccccaacccccccccccaaaaaaaaccccccccccccccccccccaaaaccccaaccccccccaaacccccccccccccaacaaccccccccccccciijoouuuuuuppppppppplllcccaccccc
abcccccccccccccccccccaaaaaaccccccccccccccccccccccaaccccaaaacccccccaaaaccccccccccaaaaaaccccccccccccciiiiootuuuuuupuuuvvpppllccccccccc
abcccccccccccccccccccaaaaaaccaaaaacccccccccccccccccccccaaaacccccccaaaaccccccccccaaaaaaccccccccccccciiinnotuuxxxuuuuvvvpppllccccccccc
abccccccccccccccacccaaaaaaaacaaaaaaacccccccccccccccccccaaaacccccccaaacccccaaaaccaaaaaccccaaccccccciiiinnnttxxxxuuyyyvvqqqllccccccccc
abcccccccccccaaaaccaaaaaaaaaaaaaaaaaaccaacccccccccccccccccccccccccccccccccaaaacccaaaaaccaaacccccciiinnnnnttxxxxxyyyyvvqqqllccccccccc
abaaaacccccccaaaaaaaaaaaaaaaaaaaaaaaaaaaacccccccccccccccccccccccccccccccccaaaacccaaaaaacaaaccccciiinnnnttttxxxxxyyyyvvqqmmmccccccccc
abaaaaccccccccaaaaacccaaaaacaaaaaacaaaaaaccccccccccccccccaaccccccccccccccccaacccccccaaaaaaaaaaciiinnnnttttxxxxxyyyyvvqqqmmmccccccccc
SbaaaacccccccaaaaaccccaaaaaccaaaaaaaaaaaccccccccccccccccaaacaacccccccccccccccccccccccaaaaaaaaachhhnnntttxxxEzzzzyyvvvqqqmmmccccccccc
abaaaacccccccaacaacccccaaaaaaaacaaaaaaaaaccccccccccccccccaaaaaccccccccccccccccccccccccaaaaaaacchhhnnntttxxxxxyyyyyyvvvqqmmmdddcccccc
abaaaacccccccccccccccccccaaaaaacaaaaaaaaaacccccccccccccaaaaaaccccccccaaaccccccccccccccaaaaaaccchhhnnntttxxxxywyyyyyyvvvqqmmmdddccccc
abaacccccccccccccccccccaaaaaaacccccaaaaaaacccccccccccccaaaaaaaacccccaaaacccccccccccccaaaaaaacaahhhmmmttttxxwwyyyyyyyvvvqqmmmdddccccc
abcccccccccccccccccccccaaaaaaacaaccaaacccccccccccccccccaacaaaaacccccaaaacccccccccccccaaacaaaaaahhhmmmmtsssswwyywwwwvvvvqqqmmdddccccc
abcccccccccccccccaaaccccaaaaaaaaaacaaccaaccccccccccccccccaaacaccccccaaaacccccccccccccccccaaaaacahhhmmmmmsssswwywwwwwvvrrqqmmdddccccc
abcccccccccccccaaaaaaccccaaaaaaaaaccaaaacccccccccccccccccaacccccccccccccccccccccccaaaccccaaaaaaahhhhhmmmmssswwwwwrrrrrrrrmmmmddccccc
abcccccccccccccaaaaaaccccaaaaaaaaaaaaaaaaaccccccccccccccccccccccccccccccccccccccaaaaaacccccaaaaachhhhhmmmmsswwwwrrrrrrrrrkkmdddccccc
abccccccccccccccaaaaaccccccaaaaaaaaaaaaaaaccccccccccccccccccccccccccccccccccccccaaaaaaccccaaaaacccchhggmmmssswwrrrrrkkkkkkkkdddacccc
abccaaaacccccccaaaaacccccccccaaaaaacaaaaacccccccccccccccccccccccccccccccccccccccaaaaaaccccaacaaaccccggggmmsssssrrlkkkkkkkkkdddaccccc
abccaaaacccccccaaaaacccccccccaaaaaaccccaacccccccccccccccccccccccccccccccccccccccaaaaaccccccccaaccccccgggmllssssrllkkkkkkkeeeddaccccc
abccaaaacccccccaaacccccccccccaaaaaacccccccccccccccccccaacccccccccccccccccccccccaaaaaacccccccccccccccccggllllssslllkkeeeeeeeeeaaacccc
abcccaaccccccccaaacaaaccccccaaaaaaaaaaacccccccccccccaaaaaacccccccccccccccccccccaaacaaacccccaacccccccccggglllllllllfeeeeeeeeaaaaacccc
abccccccccccaaaaaaaaaaccccccccccccaccaaaccacccccccccaaaaaaccccaaccaacccaaccccccaaaaaaacccccaaccccccccccggglllllllfffeeecccaaaaaacccc
abccccccccccaaaaaaaaacccccccccccccccaaaaaaaccccccccccaaaaaccccaaaaaacccaaaaaaccaaaaaacccaaaaaaaacccccccggggllllfffffccccccaacccccccc
abcccccccccccaaaaaaacccccccccccccccccaaaaaaccaacccccaaaaaccccccaaaaacccaaaaaacaaaaaaacccaaaaaaaaccccccccgggffffffffccccccccccccccccc
abccccccccccccaaaaaaacccccccccccccaaaaaaaaacaaaaccccaaaaacaaaaaaaaaacaaaaaaacaaaaaaaaaccccaaaacccccccccccggffffffacccccccccccccccaaa
abccccccccccccaaaaaaacaaccccccccccaaaaaaaaacaaaacccccaaaaaaaaaaaaaaaaaaaaaaacaaaaaaaaaacccaaaaacccccccccccaffffaaaaccccccccccccccaaa
abccccccccccccaaacaaaaaacccccccccccaaaaaaaacaaaaaaaaaaaaaaaaaaaaaaaaacaaaaaaacccaaacaaaccaaaaaacccccccccccccccccaaaccccccccccccccaaa
abccccccccccccaaccaaaaaccccccccccccccaaaaaaaccccaaaaaaaaaaaaccccaacccccaaaaaacccaaaccccccaaccaacccccccccccccccccaaacccccccccccaaaaaa
abcccccccccccccccaaaaaaaaccccccccccccaacccacccccccaaaaaaaaaaccccaacccccaaccccccccaccccccccccccccccccccccccccccccccccccccccccccaaaaaa
        """.toLines()
    }

}