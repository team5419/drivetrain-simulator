package org.team5419.drivetrain.gui

import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.ImageIcon


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

class Renderer(): JPanel(), MouseListener, MouseMotionListener {

    var radius = 5

    val pastPoints = mutableListOf<Pair<Int, Int>>()
    val framesPerPoint = 10
    var framesSincePoint = 0 
    val maxPastPoints = 300


    var initialSize: Dimension = this.getSize()
    var hasPainted = false;
    var startDrag: Point? = null
    var offset: Point = Point(0,0)

    private var zoomLevel = 1.0
        set(value) {
            field = value;
            println(value)
        }
    public val zoomIncriment = 0.85f;

    public var xOffset = 0.0
    public var yOffset = 0.0


    init{
        addMouseMotionListener(this)
        addMouseListener(this)
    }

    public fun update(){
        Robot.update()
        repaint()
    }

    public fun zoomIn(){ zoomLevel *= zoomIncriment }
    public fun zoomOut() { zoomLevel /= zoomIncriment }

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
        if(startDrag != null){
            startDrag?.move(-e.getPoint().x, -e.getPoint().y)
            offset.move(e.getPoint().x, e.getPoint().y)
        }
    }

    override fun mouseReleased(e: MouseEvent){
        startDrag = null
        println(offset)
    }

    override fun paintComponent(g: Graphics) {

        if(!hasPainted){
            hasPainted = true;
            initialSize = this.getSize()
        }

        super.paintComponent(g);
        if (g !is Graphics2D) return

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

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
            ((Robot.x - radius + offset.x) * zoomLevel).toInt(),
            ((Robot.y - radius + offset.y) * zoomLevel).toInt(),
            (radius*2*zoomLevel).toInt(), (radius*2*zoomLevel).toInt(),
            0, 360
        )
        g.drawLine(
            (Robot.x * zoomLevel + offset.x).toInt(),
            (Robot.y * zoomLevel + offset.y).toInt(),
            ((Robot.x + offset.x + Math.sin(Robot.theta) * 20)*zoomLevel).toInt(),
            ((Robot.y + offset.y + Math.cos(Robot.theta) * 20)*zoomLevel).toInt()
        )
    }
}