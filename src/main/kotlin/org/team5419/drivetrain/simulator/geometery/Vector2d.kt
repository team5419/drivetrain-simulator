package org.team5419.drivetrain.simulator.geometery

data class Vector2(val x : Double, val y : Double){
    constructor() : this(0.0, 0.0)

    operator override fun equals(other: Any?): Boolean =
            other is Vector2 && this.x == other.x && this.y == other.y
    operator fun unaryMinus(): Vector2 = Vector2(-this.x, -this.y)
    operator fun plus(other: Vector2): Vector2 = Vector2(this.x + other.x, this.y + other.y)
    operator fun times(k: Double): Vector2 = Vector2(this.x * k, this.y * k )
    fun dot(other: Vector2): Double = this.x * other.x + this.y * other.y
}
