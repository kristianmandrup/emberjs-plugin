package org.emberjs.codeInsight.attributes;

import com.intellij.lang.javascript.index.JSNamedElementProxy;
import com.intellij.openapi.project.Project;
import com.intellij.psi.xml.XmlAttribute;
import org.emberjs.codeInsight.ComponentUtil;
import org.emberjs.index.EmberComponentDocIndex;
import org.emberjs.index.EmberComponentIndex;
import org.emberjs.index.EmberIndexUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Dennis.Ushakov
 */
public class EmberAttributesRegistry {
    static EmberAttributeDescriptor createDescriptor(@Nullable final Project project,
                                                       @NotNull String componentName) {
        if ("@component".equals(componentName)) {
            return new EmberAttributeDescriptor(project, componentName, EmberComponentIndex.INDEX_ID);
        }
        return new EmberAttributeDescriptor(project, componentName, null);
    }

    public static boolean isEmberExpressionAttribute(XmlAttribute parent) {
        final String attributeName = ComponentUtil.getAttributeName(parent.getName());
        final JSNamedElementProxy directive = EmberIndexUtil.resolve(parent.getProject(), EmberComponentDocIndex.INDEX_ID, attributeName);
        if (directive != null) {
            final String restrict = directive.getIndexItem().getTypeString();
            final String param = restrict.split(";", -1)[2];
            return param.endsWith("expression") || param.startsWith("string");
        }
        return false;
    }

    public static boolean isJSONAttribute(XmlAttribute parent) {
        final String value = parent.getValue();
        if (value == null || !value.startsWith("{")) return false;

        final String attributeName = ComponentUtil.getAttributeName(parent.getName());
        final JSNamedElementProxy directive = EmberIndexUtil.resolve(parent.getProject(), EmberComponentDocIndex.INDEX_ID, attributeName);
        if (directive != null) {
            final String restrict = directive.getIndexItem().getTypeString();
            final String type = restrict.split(";", -1)[2];
            return type.contains("object literal") || type.equals("mixed");
        }
        return false;
    }
}
