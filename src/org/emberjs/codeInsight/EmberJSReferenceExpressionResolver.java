package org.emberjs.codeInsight;

import com.intellij.lang.javascript.index.JSNamedElementProxy;
import com.intellij.lang.javascript.psi.JSNamedElement;
import com.intellij.lang.javascript.psi.impl.JSReferenceExpressionImpl;
import com.intellij.lang.javascript.psi.resolve.JSReferenceExpressionResolver;
import com.intellij.lang.javascript.psi.resolve.JSResolveResult;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.ResolveResult;
import com.intellij.util.Consumer;
import com.intellij.util.Function;
import com.intellij.util.containers.ContainerUtil;
import org.emberjs.index.EmberControllerIndex;
import org.emberjs.index.EmberIndexUtil;
import org.emberjs.lang.psi.EmberJSAsExpression;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Dennis.Ushakov
 */
public class EmberJSReferenceExpressionResolver extends JSReferenceExpressionResolver {
    public EmberJSReferenceExpressionResolver(JSReferenceExpressionImpl expression, PsiFile file) {
        super(expression, file);
    }

    public static Collection<JSNamedElement> getItemsByName(final String name, PsiElement element) {
        final Collection<JSNamedElement> result = new ArrayList<JSNamedElement>();
        org.angularjs.codeInsight.EmberJSProcessor.process(element, new Consumer<JSNamedElement>() {
            @Override
            public void consume(JSNamedElement element) {
                if (name.equals(element.getName())) {
                    result.add(element);
                }
            }
        });
        return result;
    }

    @Override
    public ResolveResult[] doResolve() {
        if (myReferencedName == null) return ResolveResult.EMPTY_ARRAY;

        if (EmberJSAsExpression.isAsControllerRef(myRef, myRef.getParent())) {
            final JSNamedElementProxy resolve = EmberIndexUtil.resolve(myParent.getProject(), EmberControllerIndex.INDEX_ID, myReferencedName);
            if (resolve != null) {
                return new JSResolveResult[]{new JSResolveResult(resolve)};
            }
        } else {
            final Collection<JSNamedElement> localVariables = getItemsByName(myReferencedName, myRef);
            if (!localVariables.isEmpty()) {
                return ContainerUtil.map2Array(localVariables, JSResolveResult.class, new Function<JSNamedElement, JSResolveResult>() {
                    @Override
                    public JSResolveResult fun(JSNamedElement item) {
                        return new JSResolveResult(item);
                    }
                });
            }
        }
        return super.doResolve();
    }
}
