package org.emberjs.lang.psi;

import com.intellij.lang.ASTNode;
import com.intellij.lang.injection.InjectedLanguageManager;
import com.intellij.lang.javascript.psi.JSDefinitionExpression;
import com.intellij.lang.javascript.psi.impl.JSBinaryExpressionImpl;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.PsiReference;
import com.intellij.psi.impl.source.xml.XmlAttributeValueImpl;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlAttribute;
import org.emberjs.codeInsight.ComponentUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Dennis.Ushakov
 */
public class EmberJSAsExpression extends JSBinaryExpressionImpl {
    public EmberJSAsExpression(ASTNode node) {
        super(node);
    }

    @Nullable
    public JSDefinitionExpression getDefinition() {
        return PsiTreeUtil.getChildOfType(this, JSDefinitionExpression.class);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof EmberJSElementVisitor) {
            ((EmberJSElementVisitor)visitor).visitEmberJSAsExpression(this);
        } else {
            super.accept(visitor);
        }
    }

    public static boolean isAsControllerRef(PsiReference ref, PsiElement parent) {
        if (parent instanceof EmberJSAsExpression && ref == parent.getFirstChild()) {
            return true;
        }
        final InjectedLanguageManager injector = InjectedLanguageManager.getInstance(parent.getProject());
        final PsiLanguageInjectionHost host = injector.getInjectionHost(parent);
        final PsiElement hostParent = host instanceof XmlAttributeValueImpl ? host.getParent() : null;
        final String normalized = hostParent instanceof XmlAttribute ?
                ComponentUtil.getAttributeName(((XmlAttribute) hostParent).getName()) : null;

        // TODO: Do some kind of test
        return true;
        // return "ng-controller".equals(normalized);
    }
}
