package org.emberjs.codeInsight;

import com.intellij.lang.javascript.JavaScriptSpecificHandlersFactory;
import com.intellij.lang.javascript.psi.impl.JSReferenceExpressionImpl;
import com.intellij.lang.javascript.psi.resolve.BaseJSSymbolProcessor;
import com.intellij.lang.javascript.psi.resolve.JSResolveUtil;
import com.intellij.lang.javascript.psi.resolve.JSTypeEvaluator;
import com.intellij.psi.PsiFile;
import org.emberjs.codeInsight.EmberJSTypeEvaluator;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dennis.Ushakov
 */
public class EmberJSSpecificHandlersFactory extends JavaScriptSpecificHandlersFactory {
    @NotNull
    @Override
    public JSResolveUtil.Resolver<JSReferenceExpressionImpl> createReferenceExpressionResolver(JSReferenceExpressionImpl referenceExpression,
                                                                                               PsiFile containingFile) {
        return new EmberJSReferenceExpressionResolver(referenceExpression, containingFile);
    }

    @NotNull
    @Override
    public JSTypeEvaluator newTypeEvaluator(BaseJSSymbolProcessor.EvaluateContext context,
                                            BaseJSSymbolProcessor.TypeProcessor processor,
                                            boolean ecma) {
        return new EmberJSTypeEvaluator(context, processor, ecma);
    }
}
