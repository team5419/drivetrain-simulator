package org.team5419.drivetrain.gui

import org.team5419.drivetrain.simulator.Robot
import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.*

import javax.swing.border.EmptyBorder


class ControlPanel(renderer: Renderer): JPanel() {

    private val gbc = GridBagConstraints()
    private val labels: Map<JLabel, () -> Double>
    private val zoomInButton: JButton = JButton("+").apply{
        addActionListener { renderer.zoomIn() }
        setOpaque(true)
    }
    private val zoomOutButton: JButton = JButton("-").apply {
        addActionListener { renderer.zoomOut() }
        setOpaque(true)
    }

    init{
        this.layout = GridLayout(20, 1, 5, 5);
        this.preferredSize = Dimension(180, 0)
        gbc.anchor = GridBagConstraints.NORTH;

        labels = mapOf<JLabel, () -> Double>(
            addOutput("X: ") to { Robot.x },
            addOutput("Y: ") to { Robot.y },
            addOutput("Velocity : ") to { Robot.velocity },
            addOutput("θ: ") to { Robot.getHeadingInDegrees() },
            addOutput("Left: ") to { Robot.leftWheel },
            addOutput("Right: ") to { Robot.rightWheel }
        )

        this.add(JPanel().apply {
            layout = GridLayout(1,2, 10, 10)
            add(zoomInButton)
            add(zoomOutButton)
        })

        update()
    }

    private fun addOutput(titleStr: String): JLabel{
        val label: JLabel = JLabel("")
        this.add( JPanel().apply{
            add(JLabel(titleStr), BorderLayout.LINE_START)
            add(label, BorderLayout.LINE_END)
            layout = GridLayout(1,2, 10, 10)
            border = EmptyBorder(0,15,0,10)
            // setLayOut(WrapLayout)
        })
        return label
    }

    fun update() {
        labels.forEach { (label, callback) -> label.text = "%.${3}f".format(callback())}
    }
}