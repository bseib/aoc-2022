import java.lang.Math.abs

class Problem12 : DailyProblem<Int> {

    class HillGraph(
        val startNode: HillNode,
        val endNode: HillNode,
        val grid: List<List<HillNode>>,
    ) {
        fun findShortestPath(): Int {
            return startNode.shortestPathToEndNode() ?: throw Error("no route found")
        }

        override fun toString() = grid.joinToString("\n", "", "\n") { row ->
            row.map { it.letter }.joinToString("")
        }
    }

    class HillNode(val letter: Char, val neighbors: MutableSet<HillNode> = mutableSetOf()) {
        val elevation: Int
        val isStartNode: Boolean
        val isEndNode: Boolean

        init {
            when (letter) {
                'S' -> {
                    elevation = 0
                    isStartNode = true
                    isEndNode = false
                }

                'E' -> {
                    elevation = 25
                    isStartNode = false
                    isEndNode = true
                }

                else -> {
                    elevation = letter.minus('a')
                    isStartNode = false
                    isEndNode = false
                }
            }
        }

        fun maybeAddNeighbor(potentialNeighbor: HillNode) {
            if (abs(elevation - potentialNeighbor.elevation) <= 1) {
                neighbors.add(potentialNeighbor)
                potentialNeighbor.neighbors.add(this)
            }
        }

        fun shortestPathToEndNode(visited: Set<HillNode> = setOf(), step: Int = 0): Int? {
            return if (isEndNode) step
            else (neighbors subtract visited).mapNotNull {
                it.shortestPathToEndNode(visited + this, step + 1)
            }.minOrNull()
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
        return 0
    }

    override fun solvePart2(): Int {
        return 0
    }

    companion object {
        val data0 = """
            Sabqponm
            abcryxxl
            accszExk
            acctuvwj
            abdefghi
        """.toLines()

    }

}