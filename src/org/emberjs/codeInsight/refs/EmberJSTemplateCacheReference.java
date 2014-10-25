package org.emberjs.codeInsight.refs;

import com.intellij.lang.javascript.psi.JSLiteralExpression;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Ref;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.ElementManipulators;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.util.ArrayUtil;
import com.intellij.util.Processor;
import com.intellij.util.indexing.FileBasedIndex;
import org.emberjs.index.EmberIndexUtil;
import org.emberjs.index.EmberTemplateCacheIndex;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * @author Kristian Mandrup
 */
public class EmberJSTemplateCacheReference extends EmberJSReferenceBase<JSLiteralExpression> {
    public EmberJSTemplateCacheReference(@NotNull JSLiteralExpression element) {
        super(element, ElementManipulators.getValueTextRange(element));
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        final Collection<String> keys = EmberIndexUtil.getAllKeys(EmberTemplateCacheIndex.TEMPLATE_CACHE_INDEX, getElement().getProject());
        return ArrayUtil.toStringArray(keys);
    }

    @Override
    @Nullable
    public PsiElement resolveInner() {
        final FileBasedIndex instance = FileBasedIndex.getInstance();
        final Project project = getElement().getProject();
        final String id = getCanonicalText();
        final Collection<VirtualFile> files = instance.getContainingFiles(EmberTemplateCacheIndex.TEMPLATE_CACHE_INDEX, id,
                GlobalSearchScope.allScope(project));
        final Ref<PsiElement> result = new Ref<PsiElement>();
        for (VirtualFile file : files) {
            final PsiFile psiFile = PsiManager.getInstance(project).findFile(file);
            EmberTemplateCacheIndex.processTemplates(psiFile, new Processor<XmlAttribute>() {
                @Override
                public boolean process(XmlAttribute attribute) {
                    if (id.equals(attribute.getValue())) {
                        result.set(attribute.getValueElement());
                    }
                    return result.isNull();
                }
            });
        }
        return result.get();
    }
}
