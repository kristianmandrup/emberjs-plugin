package org.emberjs.lang.psi;

import com.intellij.lang.ASTNode;
import com.intellij.lang.javascript.psi.impl.JSExpressionImpl;

/**
 * Created by kristianmandrup on 25/10/14.
 */
public class EmberJSObservedProperty extends JSExpressionImpl {
    public EmberJSObservedProperty(ASTNode node) {
        super(node);
    }
}
