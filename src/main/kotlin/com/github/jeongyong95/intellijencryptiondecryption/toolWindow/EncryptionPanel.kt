package com.github.jeongyong95.intellijencryptiondecryption.toolWindow

import com.github.jeongyong95.intellijencryptiondecryption.MyBundle
import com.github.jeongyong95.intellijencryptiondecryption.crypto.CryptoAlgorithm
import com.github.jeongyong95.intellijencryptiondecryption.crypto.CryptoService
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPasswordField
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
import javax.swing.JComboBox
import javax.swing.JPanel

class EncryptionPanel : JPanel(BorderLayout()) {

    private val algorithmCombo = JComboBox(CryptoAlgorithm.entries.toTypedArray()).apply {
        selectedItem = CryptoAlgorithm.AES_256_CBC
    }
    private val passwordField = JBPasswordField()
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
        }

        // Algorithm row
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.0
        contentPanel.add(JBLabel(MyBundle.message("label.algorithm")), gbc)
        gbc.gridx = 1; gbc.weightx = 1.0
        contentPanel.add(algorithmCombo, gbc)

        // Password row
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.0
        contentPanel.add(JBLabel(MyBundle.message("label.password")), gbc)
        gbc.gridx = 1; gbc.weightx = 1.0
        contentPanel.add(passwordField, gbc)

        // Input label
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.weightx = 1.0
        contentPanel.add(JBLabel(MyBundle.message("label.input")), gbc)

        // Input area
        gbc.gridy = 3; gbc.weighty = 1.0; gbc.fill = GridBagConstraints.BOTH
        val inputScroll = JBScrollPane(inputArea).apply { preferredSize = Dimension(0, 120) }
        contentPanel.add(inputScroll, gbc)

        // Action buttons
        gbc.gridy = 4; gbc.weighty = 0.0; gbc.fill = GridBagConstraints.HORIZONTAL
        val actionPanel = JPanel().apply {
            val encryptBtn = JButton(MyBundle.message("button.encrypt")).apply {
                addActionListener { doEncrypt() }
            }
            val decryptBtn = JButton(MyBundle.message("button.decrypt")).apply {
                addActionListener { doDecrypt() }
            }
            add(encryptBtn)
            add(decryptBtn)
        }
        contentPanel.add(actionPanel, gbc)

        // Output label
        gbc.gridy = 5
        contentPanel.add(JBLabel(MyBundle.message("label.output")), gbc)

        // Output area
        gbc.gridy = 6; gbc.weighty = 1.0; gbc.fill = GridBagConstraints.BOTH
        val outputScroll = JBScrollPane(outputArea).apply { preferredSize = Dimension(0, 120) }
        contentPanel.add(outputScroll, gbc)

        // Copy/Clear buttons
        gbc.gridy = 7; gbc.weighty = 0.0; gbc.fill = GridBagConstraints.HORIZONTAL
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

    private fun doEncrypt() {
        try {
            val algorithm = algorithmCombo.selectedItem as CryptoAlgorithm
            val password = String(passwordField.password)
            if (password.isEmpty()) {
                outputArea.text = MyBundle.message("error.emptyPassword")
                return
            }
            val input = inputArea.text
            if (input.isEmpty()) {
                outputArea.text = MyBundle.message("error.emptyInput")
                return
            }
            outputArea.text = CryptoService.encrypt(input, password, algorithm)
        } catch (e: Exception) {
            outputArea.text = MyBundle.message("error.encryptFailed", e.message ?: "Unknown error")
        }
    }

    private fun doDecrypt() {
        try {
            val algorithm = algorithmCombo.selectedItem as CryptoAlgorithm
            val password = String(passwordField.password)
            if (password.isEmpty()) {
                outputArea.text = MyBundle.message("error.emptyPassword")
                return
            }
            val input = inputArea.text
            if (input.isEmpty()) {
                outputArea.text = MyBundle.message("error.emptyInput")
                return
            }
            outputArea.text = CryptoService.decrypt(input, password, algorithm)
        } catch (e: Exception) {
            outputArea.text = MyBundle.message("error.decryptFailed", e.message ?: "Unknown error")
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
