package org.emberjs.lang.psi;

import com.intellij.lang.ASTNode;
import com.intellij.lang.javascript.psi.impl.JSExpressionImpl;

/**
 * Created by kristianmandrup on 25/10/14.
 */
public class EmberJSActionMethod extends JSExpressionImpl {
    public EmberJSActionMethod(ASTNode node) {
        super(node);
    }
}
