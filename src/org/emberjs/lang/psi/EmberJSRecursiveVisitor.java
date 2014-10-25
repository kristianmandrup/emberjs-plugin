package org.emberjs.lang.psi;

import com.intellij.psi.PsiElement;

/**
 * @author Dennis.Ushakov
 */
public class EmberJSRecursiveVisitor extends EmberJSElementVisitor {
    @Override
    public void visitElement(PsiElement element) {
        element.acceptChildren(this);
    }
}