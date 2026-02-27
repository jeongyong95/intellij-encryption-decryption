package com.github.jeongyong95.intellijencryptiondecryption.toolWindow

import com.github.jeongyong95.intellijencryptiondecryption.MyBundle
import com.github.jeongyong95.intellijencryptiondecryption.crypto.Base64Service
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTextArea
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import javax.swing.JButton
import javax.swing.JPanel

class Base64Panel : JPanel(BorderLayout()) {

    private val inputArea = JBTextArea().apply {
        lineWrap = true
        wrapStyleWord = true
    }
    private val outputArea = JBTextArea().apply {
        lineWrap = true
        wrapStyleWord = true
        isEditable = false
    }

    init {
        val contentPanel = JPanel(GridBagLayout())
        val gbc = GridBagConstraints().apply {
            insets = Insets(4, 8, 4, 8)
            fill = GridBagConstraints.HORIZONTAL
            gridx = 0
            gridwidth = 2
            weightx = 1.0
        }

        // Input label
        gbc.gridy = 0
        contentPanel.add(JBLabel(MyBundle.message("label.input")), gbc)

        // Input area
        gbc.gridy = 1; gbc.weighty = 1.0; gbc.fill = GridBagConstraints.BOTH
        val inputScroll = JBScrollPane(inputArea).apply { preferredSize = Dimension(0, 150) }
        contentPanel.add(inputScroll, gbc)

        // Action buttons
        gbc.gridy = 2; gbc.weighty = 0.0; gbc.fill = GridBagConstraints.HORIZONTAL
        val actionPanel = JPanel().apply {
            val encodeBtn = JButton(MyBundle.message("button.encode")).apply {
                addActionListener { doEncode() }
            }
            val decodeBtn = JButton(MyBundle.message("button.decode")).apply {
                addActionListener { doDecode() }
            }
            add(encodeBtn)
            add(decodeBtn)
        }
        contentPanel.add(actionPanel, gbc)

        // Output label
        gbc.gridy = 3
        contentPanel.add(JBLabel(MyBundle.message("label.output")), gbc)

        // Output area
        gbc.gridy = 4; gbc.weighty = 1.0; gbc.fill = GridBagConstraints.BOTH
        val outputScroll = JBScrollPane(outputArea).apply { preferredSize = Dimension(0, 150) }
        contentPanel.add(outputScroll, gbc)

        // Copy/Clear buttons
        gbc.gridy = 5; gbc.weighty = 0.0; gbc.fill = GridBagConstraints.HORIZONTAL
        val bottomPanel = JPanel().apply {
            val copyBtn = JButton(MyBundle.message("button.copy")).apply {
                addActionListener { copyOutput() }
            }
            val clearBtn = JButton(MyBundle.message("button.clear")).apply {
                addActionListener { clearAll() }
            }
            add(copyBtn)
            add(clearBtn)
        }
        contentPanel.add(bottomPanel, gbc)

        add(contentPanel, BorderLayout.CENTER)
    }

    private fun doEncode() {
        try {
            val input = inputArea.text
            if (input.isEmpty()) {
                outputArea.text = MyBundle.message("error.emptyInput")
                return
            }
            outputArea.text = Base64Service.encode(input)
        } catch (e: Exception) {
            outputArea.text = MyBundle.message("error.encodeFailed", e.message ?: "Unknown error")
        }
    }

    private fun doDecode() {
        try {
            val input = inputArea.text
            if (input.isEmpty()) {
                outputArea.text = MyBundle.message("error.emptyInput")
                return
            }
            outputArea.text = Base64Service.decode(input)
        } catch (e: Exception) {
            outputArea.text = MyBundle.message("error.decodeFailed", e.message ?: "Unknown error")
        }
    }

    private fun copyOutput() {
        val text = outputArea.text
        if (text.isNotEmpty()) {
            val selection = StringSelection(text)
            Toolkit.getDefaultToolkit().systemClipboard.setContents(selection, null)
        }
    }

    private fun clearAll() {
        inputArea.text = ""
        outputArea.text = ""
    }
}
