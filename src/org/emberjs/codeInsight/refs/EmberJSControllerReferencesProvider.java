package org.emberjs.codeInsight.refs;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.lang.javascript.completion.JSLookupUtilImpl;
import com.intellij.lang.javascript.psi.JSLiteralExpression;
import com.intellij.lang.javascript.psi.resolve.VariantsProcessor;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import org.emberjs.index.EmberControllerIndex;
import org.emberjs.index.EmberIndexUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * @author Kristian Mandrup
 */
public class EmberJSControllerReferencesProvider extends PsiReferenceProvider {
    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
        return new PsiReference[] { new EmberJSControllerReference((JSLiteralExpression)element) };
    }

    public static class EmberJSControllerReference extends EmberJSReferenceBase<JSLiteralExpression> {
        public EmberJSControllerReference(@NotNull JSLiteralExpression element) {
            super(element, ElementManipulators.getValueTextRange(element));
        }

        @Nullable
        @Override
        public PsiElement resolveInner() {
            return EmberIndexUtil.resolve(getElement().getProject(), EmberControllerIndex.INDEX_ID, getCanonicalText());
        }

        @NotNull
        @Override
        public Object[] getVariants() {
            final Collection<String> controllers = EmberIndexUtil.getAllKeys(EmberControllerIndex.INDEX_ID, getElement().getProject());
            final LookupElement[] result = new LookupElement[controllers.size()];
            int i = 0;
            for (String controller : controllers) {
                final LookupElement item = JSLookupUtilImpl.createPrioritizedLookupItem(null, controller,
                        VariantsProcessor.LookupPriority.LOCAL_SCOPE_MAX_PRIORITY,
                        false, false);
                result[i] = item;
                i++;
            }
            return result;
        }
    }
}
