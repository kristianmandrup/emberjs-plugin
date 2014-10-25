package org.emberjs.lang.psi;

import com.intellij.lang.javascript.psi.JSElementVisitor;

/**
 * @author Dennis.Ushakov
 */
public class EmberJSElementVisitor extends JSElementVisitor {
    public void visitEmberJSRepeatExpression(EmberJSRepeatExpression repeatExpression) {
        visitJSExpression(repeatExpression);
    }

    public void visitEmberJSAsExpression(EmberJSAsExpression asExpression) {
        visitJSExpression(asExpression);
    }
}
