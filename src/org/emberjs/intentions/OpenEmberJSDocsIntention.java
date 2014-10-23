package org.emberjs.intentions;

import com.intellij.codeInsight.intention.AbstractIntentionAction;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInsight.intention.LowPriorityAction;
import com.intellij.ide.BrowserUtil;
import com.intellij.lang.xml.XMLLanguage;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.util.text.StringUtilRt;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.xml.XmlTokenImpl;
import com.intellij.psi.util.PsiUtilBase;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

/**
 * Created by @kmandrup on 01/11/2014
 */
public class OpenEmberJSDocsIntention extends AbstractIntentionAction implements LowPriorityAction {
    @NotNull
    @Override
    public String getText() {
        return "Open Ember API Docs";
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile psiFile) {
        final PsiElement element = PsiUtilBase.getElementAtCaret(editor);

        return element.getText().startsWith("Ember.");
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, PsiFile psiFile) throws IncorrectOperationException {
        final PsiElement element = PsiUtilBase.getElementAtCaret(editor);
        assert element != null;
        String text = element.getText();
        String clazz = text.split(".")[1];
        StringBuilder name = new StringBuilder("http://emberjs.com/api/classes/Ember." + clazz);
        BrowserUtil.open(name.toString());
    }

    @Override
    public boolean startInWriteAction() {
        return false;
    }
}
