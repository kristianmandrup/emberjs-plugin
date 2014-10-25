package org.emberjs.codeInsight;

import com.intellij.lang.javascript.index.JSNamedElementProxy;
import com.intellij.lang.javascript.psi.JSLiteralExpression;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.util.Processor;
import com.intellij.util.indexing.ID;
import org.emberjs.index.EmberComponentDocIndex;
import org.emberjs.index.EmberComponentIndex;
import org.emberjs.index.EmberIndexUtil;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * @author Kristian Mandrup
 */
public class ComponentUtil {
    public static String getAttributeName(final String text) {
        final String[] split = StringUtil.unquoteString(text).split("(?=[A-Z])");
        for (int i = 0; i < split.length; i++) {
            split[i] = StringUtil.decapitalize(split[i]);
        }
        return StringUtil.join(split, "-");
    }

    public static String attributeToComponent(String name) {
        final String[] words = name.split("-");
        for (int i = 1; i < words.length; i++) {
            words[i] = StringUtil.capitalize(words[i]);
        }
        return StringUtil.join(words);
    }

    public static boolean processComponents(final Project project,
                                               Processor<JSNamedElementProxy> processor) {
        final Collection<String> docComponents = EmberIndexUtil.getAllKeys(EmberComponentDocIndex.INDEX_ID, project);
        for (String componentName : docComponents) {
            final JSNamedElementProxy component = getComponentProxy(project, componentName, EmberComponentDocIndex.INDEX_ID);
            if (component != null) {
                if (!processor.process(component)) {
                    return false;
                }
            }
        }
        final Collection<String> components = EmberIndexUtil.getAllKeys(EmberComponentIndex.INDEX_ID, project);
        for (String componentName : components) {
            if (!docComponents.contains(componentName)) {
                final JSNamedElementProxy component = getComponentProxy(project, componentName, EmberComponentIndex.INDEX_ID);
                if (component != null) {
                    if (!processor.process(component)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static JSNamedElementProxy getComponentProxy(String componentName, Project project) {
        final JSNamedElementProxy component = getComponentProxy(project, componentName, EmberComponentDocIndex.INDEX_ID);
        return component == null ? getComponentProxy(project, componentName, EmberComponentIndex.INDEX_ID) : component;
    }

    private static JSNamedElementProxy getComponentProxy(Project project, String componentName, final ID<String, Void> index) {
        final JSNamedElementProxy component = EmberIndexUtil.resolve(project, index, componentName);
        // TODO: do some stuff??
        return null;
    }

    @Nullable
    public static JSNamedElementProxy getComponent(@Nullable PsiElement element) {
        if (element instanceof JSNamedElementProxy) {
            return getComponent(element, ((JSNamedElementProxy)element).getName());
        }
        if (element instanceof JSLiteralExpression && ((JSLiteralExpression)element).isQuotedLiteral()) {
            return getComponent(element, StringUtil.unquoteString(element.getText()));
        }
        return null;
    }

    private static JSNamedElementProxy getComponent(PsiElement element, final String name) {
        final String componentName = getAttributeName(name);
        final JSNamedElementProxy component = EmberIndexUtil.resolve(element.getProject(), EmberComponentIndex.INDEX_ID, componentName);
        if (component != null && element.getTextRange().contains(component.getTextOffset())) {
            return component;
        }
        return null;
    }
}
