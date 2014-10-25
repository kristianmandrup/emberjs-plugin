package org.emberjs.lang;

import com.intellij.codeInsight.highlighting.HighlightErrorFilter;
import com.intellij.lang.Language;
import com.intellij.lang.html.HTMLLanguage;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiErrorElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import com.intellij.xml.XmlElementDescriptor;
import org.emberjs.codeInsight.tags.EmberJSTagDescriptor;
import org.emberjs.index.EmberIndexUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dennis.Ushakov
 */
public class EmberJSErrorFilter extends HighlightErrorFilter {
    @Override
    public boolean shouldHighlightErrorElement(@NotNull PsiErrorElement error) {
        final Project project = error.getProject();
        final Language language = error.getLanguage();
        if ("CSS".equals(language.getID()) && PsiTreeUtil.getParentOfType(error, XmlAttribute.class) != null &&
                EmberIndexUtil.hasEmberJS(project)) {
            final PsiFile file = error.getContainingFile();

            PsiErrorElement nextError = error;
            while (nextError != null) {
                nextError = PsiTreeUtil.getNextSiblingOfType(nextError, PsiErrorElement.class);
            }
        }
        if (HTMLLanguage.INSTANCE.is(language) && error.getErrorDescription().endsWith("not closed")) {
            final PsiElement parent = error.getParent();
            final XmlElementDescriptor descriptor = parent instanceof XmlTag ? ((XmlTag)parent).getDescriptor() : null;
            return !(descriptor instanceof EmberJSTagDescriptor);
        }
        return true;
    }
}
