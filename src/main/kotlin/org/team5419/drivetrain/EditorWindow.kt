package org.team5419.drivetrain

import org.team5419.drivetrain.gui.ControlPanel
import org.team5419.drivetrain.gui.Renderer
import org.team5419.drivetrain.simulator.Robot

import java.awt.EventQueue
import java.awt.BorderLayout
import java.awt.event.ActionListener
import java.awt.event.ActionEvent
import javax.swing.Timer
import javax.swing.JFrame

/**
 * Renders and manages the state of the main editor.f
 */
class EditorWindow : JFrame() {

    val controlPanel = ControlPanel();
    val fieldPanel = Renderer();

    private fun launchUI() {
        title = "Drivetrain Simulator"
        setSize(1280,720)

        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        this.isVisible = true

        val listener = ActionListener {
            fieldPanel.update()
            controlPanel.update()
        }

        val timer = Timer((1000 * Robot.dt).toInt(), listener)
        timer.start()
    }

    private fun createUI() {
        this.add(controlPanel, BorderLayout.LINE_START)
        this.add(fieldPanel, BorderLayout.CENTER);
    }

    companion object {

        private fun createAndShowGUI() {

            val frame = EditorWindow()
            frame.createUI()
            frame.launchUI()

        }

        @JvmStatic
        fun main(args: Array<String>) {
            EventQueue.invokeLater(::createAndShowGUI)
        }
    }
}
