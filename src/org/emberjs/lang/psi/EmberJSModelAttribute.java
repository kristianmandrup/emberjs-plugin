package org.emberjs.lang.psi;

import com.intellij.lang.ASTNode;
import com.intellij.lang.javascript.psi.impl.JSBinaryExpressionImpl;

/**
 * Created by kristianmandrup on 25/10/14.
 */
public class EmberJSModelAttribute extends JSBinaryExpressionImpl {
    public EmberJSModelAttribute(ASTNode node) {
        super(node);
    }
}
