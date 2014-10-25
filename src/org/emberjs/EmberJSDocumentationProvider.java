package org.emberjs;

import com.intellij.lang.documentation.DocumentationProviderEx;
import com.intellij.lang.javascript.index.JSNamedElementProxy;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTokenType;
import org.emberjs.codeInsight.ComponentUtil;
import org.emberjs.index.EmberComponentDocIndex;
import org.emberjs.index.EmberIndexUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * @author Dennis.Ushakov
 */
public class EmberJSDocumentationProvider extends DocumentationProviderEx {
    @Nullable
    @Override
    public PsiElement getCustomDocumentationElement(@NotNull Editor editor,
                                                    @NotNull PsiFile file,
                                                    @Nullable PsiElement element) {
        final IElementType elementType = element != null ? element.getNode().getElementType() : null;
        if (elementType == XmlTokenType.XML_NAME || elementType == XmlTokenType.XML_TAG_NAME) {
            return EmberIndexUtil.resolve(element.getProject(), EmberComponentDocIndex.INDEX_ID,
                    ComponentUtil.getAttributeName(element.getText()));
        }
        return null;
    }

    @Override
    public PsiElement getDocumentationElementForLookupItem(PsiManager psiManager, Object object, PsiElement element) {
        if (element instanceof XmlAttribute) {
            return EmberIndexUtil.resolve(element.getProject(), EmberComponentDocIndex.INDEX_ID, object.toString());
        }
        return null;
    }

    // TODO: Divide into one for Ember classes and one for: Components, Helpers etc.?
    @Override
    public List<String> getUrlFor(PsiElement element, PsiElement originalElement) {
        if (element instanceof JSNamedElementProxy) {
            final String name = ((JSNamedElementProxy)element).getName();
            if (EmberIndexUtil.resolve(element.getProject(), EmberComponentDocIndex.INDEX_ID, name) != null) {
                final String className = ComponentUtil.attributeToComponent(name);
                return Collections.singletonList("http://emberjs.org/api/classes/Ember." + className);
            }
        }
        return null;
    }
}
