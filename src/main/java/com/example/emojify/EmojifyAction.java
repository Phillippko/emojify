package com.example.emojify;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.JBColor;
import com.intellij.ui.awt.RelativePoint;
import org.jetbrains.annotations.NotNull;

public class EmojifyAction extends AnAction {
    Emojifyer emojifyer = new Emojifyer();

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        final Editor editor = event.getRequiredData(CommonDataKeys.EDITOR);
        final String selectedText = editor.getSelectionModel().getSelectedText();
        final Project project = event.getRequiredData(CommonDataKeys.PROJECT);

        if (selectedText != null) {
            WriteCommandAction.runWriteCommandAction(project, () -> {
                final String result = emojifyer.emojify(selectedText);
                final RelativePoint relPoint = JBPopupFactory.getInstance()
                        .guessBestPopupLocation(editor.getSelectionModel().getEditor());
                JBPopupFactory.getInstance().createHtmlTextBalloonBuilder(
                                result, null, JBColor.LIGHT_GRAY, null
                        )
                        .createBalloon()
                        .show(relPoint, Balloon.Position.atLeft);
            });
        }
    }
}
