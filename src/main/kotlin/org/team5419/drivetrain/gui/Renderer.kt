package org.team5419.drivetrain.gui

import javax.swing.JPanel


import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.Color
import java.awt.BasicStroke
import java.awt.RenderingHints
import java.awt.Dimension
import java.awt.event.MouseMotionListener
import java.awt.event.MouseListener
import java.awt.event.MouseEvent
import java.awt.Point

import org.team5419.drivetrain.simulator.Robot
import kotlin.math.cos
import kotlin.math.sin

class Renderer: JPanel(), MouseListener, MouseMotionListener {

    var radius = 5

    val pastPoints = mutableListOf<Pair<Int, Int>>()
    val framesPerPoint = 10
    var framesSincePoint = 0 
    val maxPastPoints = 300


    var initialSize: Dimension = this.getSize()
    var hasPainted = false
    var startDrag: Point? = null

    private var zoomLevel = 1.0
        set(value) {
            field = value
            println(value)
        }
    val zoomIncrement = 0.85f

    var xOffset = 0.0
    var yOffset = 0.0


    init{
        addMouseMotionListener(this)
        addMouseListener(this)
    }

    fun update(){
        Robot.update()
        repaint()
    }

    fun zoomIn(){ zoomLevel /= zoomIncrement }
    fun zoomOut() { zoomLevel *= zoomIncrement }

    override fun mouseMoved(e: MouseEvent){}
    override fun mouseEntered(e: MouseEvent){}
    override fun mouseClicked(e: MouseEvent){}
    override fun mouseExited(e: MouseEvent){}

    override fun mousePressed(e: MouseEvent) {
        startDrag = e.getPoint()
        println("pressed")
    }

    override fun mouseDragged(e: MouseEvent) {
        // println("drag")
        xOffset += e.point.x - startDrag!!.x
        yOffset += e.point.y - startDrag!!.y
        startDrag = e.point

    }

    override fun mouseReleased(e: MouseEvent){
        startDrag = null
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        if (g !is Graphics2D) return

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        g.stroke = BasicStroke(2f)
        g.color = Color.WHITE
        g.fillRect(0, 0, width, height)

        framesSincePoint++

        if(framesSincePoint == framesPerPoint){
            pastPoints.add(Pair(Robot.x.toInt(), Robot.y.toInt()))
            framesSincePoint = 0

            if(pastPoints.size >= maxPastPoints){
                pastPoints.removeAt(0)
            }
        }
        g.color = Color.decode("#008000")
        pastPoints.zipWithNext { a,b -> g.drawLine(a.first, a.second, b.first, b.second) }

        g.color = Color.decode("#990000")

        g.fillArc(
            ((Robot.x - radius + xOffset) * zoomLevel).toInt(),
            ((Robot.y - radius + yOffset) * zoomLevel).toInt(),
            (radius*2*zoomLevel).toInt(), (radius*2*zoomLevel).toInt(),
            0, 360
        )
        g.drawLine(
            ((Robot.x + xOffset) * zoomLevel).toInt(),
            ((Robot.y + yOffset) * zoomLevel).toInt(),
            ((Robot.x + xOffset + sin(Robot.theta) * 20)*zoomLevel).toInt(),
            ((Robot.y + yOffset + cos(Robot.theta) * 20)*zoomLevel).toInt()
        )
    }
}