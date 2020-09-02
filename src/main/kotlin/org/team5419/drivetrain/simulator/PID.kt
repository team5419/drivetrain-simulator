package org.team5419.drivetrain.simulator

class PID (
    private val kP: Double,
    private val kI: Double,
    private val kD: Double
){
    var lastError = 0.0
    var errorSum = 0.0

    fun calculate(error: Double, dt: Double): Double {
        errorSum += error
        val output = kP * error + kD * (error - lastError) / dt + kI * errorSum
        lastError = error
        return output
    }
}