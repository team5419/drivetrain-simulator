package org.team5419.drivetrain.simulator.geometery

import kotlin.math.pow


class Spline(startPoint: Vector2, endPoint: Vector2, vararg controlPoints: Vector2){
    
    private val points: List<Vector2> = listOf(startPoint, *controlPoints, endPoint)
    val order: Int
        get() = points.size

    private fun fact(n: Int): Int = if(n == 0) 1 else n * fact(n-1)

    fun getPoint(u: Double): Vector2 {
        return points.mapIndexed { i: Int, point: Vector2 ->
            point * (fact(order) / fact(i) / fact(order-i) * u.pow(i.toDouble()) * (1 - u).pow((order - i).toDouble()))
        }.reduce { sum: Vector2, nextPoint: Vector2 ->
            sum + nextPoint
        }
    }
}
