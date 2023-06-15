package tdld4k.world

import java.awt.Point

//class GamePolygon(private val vertices: List<Point>) : GameShape() {
//    fun intersection(point: Point): Boolean {
//        val xPoint = point.x
//        val yPoint = point.y
//        val valuesOfSides = mutableListOf<Int>()
//        for (i in vertices.indices) {
//            val currentVertex = vertices[i]
//            val xCurrentVertex = currentVertex.x
//            val yCurrentVertex = currentVertex.y
//
//            var nextVertex = vertices[0]
//            if (i != vertices.size - 1) {
//                nextVertex = vertices[i + 1]
//            }
//            val xNextVertex = nextVertex.x
//            val yNextVertex = nextVertex.y
//            valuesOfSides.add((xCurrentVertex - xPoint) * (yNextVertex - yCurrentVertex) - (xNextVertex - xCurrentVertex) * (yCurrentVertex - yPoint))
//        }
//        var inShape = true
//        var isSomethingZero = false
//        val value = valuesOfSides[0]
//        valuesOfSides.forEach { e ->
//            if (e != value) {
//                inShape = false
//            }
//            if (e == 0) {
//                isSomethingZero = true
//            }
//        }
//        if (isSomethingZero) {
//            return true
//        }
//        return inShape
//    }
//}