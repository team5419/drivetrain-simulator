package org.team5419.drivetrain.gui

import org.team5419.drivetrain.simulator.Robot

import javax.swing.JPanel
import javax.swing.JLabel
import javax.swing.JTextField
import javax.swing.JFormattedTextField
import javax.swing.InputVerifier
import javax.swing.JComponent

import java.awt.GridBagLayout
import java.awt.GridBagConstraints
import java.awt.GridLayout
import java.awt.BorderLayout
import java.awt.event.ActionListener
import java.awt.event.ActionEvent

class ControlPanel(): JPanel() {

    val gbc = GridBagConstraints()
    val xLabel: JLabel
    val yLabel: JLabel
    val thetaLabel: JLabel
    val leftLabel: JLabel
    val rightLabel: JLabel


    init{
        this.layout = GridLayout(20, 1);
        gbc.anchor = GridBagConstraints.NORTH;
        xLabel = addOutput("X: ")
        yLabel = addOutput("Y: ")
        thetaLabel = addOutput("Theta: ")
        leftLabel = addOutput("Left: ")
        rightLabel = addOutput("Right: ")

        update()

    }

    fun addOutput(title: String): JLabel{
        val label: JLabel = JLabel("")
        this.add( JPanel().apply{
            add(JLabel(title), BorderLayout.LINE_START)
            add(label, BorderLayout.LINE_END)
            // setLayOut(WrapLayout)
        }, gbc)
        return label
    }

    fun update() {
        xLabel.setText("%.${3}f".format(Robot.x))
        yLabel.setText("%.${3}f".format(Robot.y))
        thetaLabel.setText("%.${3}f".format(Robot.theta))
        leftLabel.setText("%.${3}f".format(Robot.leftWheel))
        rightLabel.setText("%.${3}f".format(Robot.rightWheel))
    }
}