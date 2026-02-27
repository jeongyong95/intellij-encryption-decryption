package com.github.jeongyong95.intellijencryptiondecryption.toolWindow

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import com.github.jeongyong95.intellijencryptiondecryption.MyBundle

class CryptoToolWindowFactory : ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val contentFactory = ContentFactory.getInstance()

        val encryptionContent = contentFactory.createContent(
            EncryptionPanel(),
            MyBundle.message("tab.encryption"),
            false,
        )
        val hashContent = contentFactory.createContent(
            HashPanel(),
            MyBundle.message("tab.hash"),
            false,
        )
        val base64Content = contentFactory.createContent(
            Base64Panel(),
            MyBundle.message("tab.base64"),
            false,
        )

        toolWindow.contentManager.addContent(encryptionContent)
        toolWindow.contentManager.addContent(hashContent)
        toolWindow.contentManager.addContent(base64Content)
    }
}
