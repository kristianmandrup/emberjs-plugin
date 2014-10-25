package org.emberjs.codeInsight.attributes;

import com.intellij.lang.javascript.index.JSNamedElementProxy;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.ThreeState;
import com.intellij.util.indexing.ID;
import com.intellij.xml.XmlAttributeDescriptor;
import com.intellij.xml.XmlAttributeDescriptorsProvider;
import org.emberjs.codeInsight.ComponentUtil;
import org.emberjs.index.EmberComponentDocIndex;
import org.emberjs.index.EmberComponentIndex;
import org.emberjs.index.EmberIndexUtil;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.emberjs.codeInsight.attributes.EmberAttributesRegistry.createDescriptor;

/**
 * @author Dennis.Ushakov
 */
public class EmberJSAttributeDescriptorsProvider implements XmlAttributeDescriptorsProvider {
    @Override
    public XmlAttributeDescriptor[] getAttributeDescriptors(XmlTag xmlTag) {
        if (xmlTag != null) {
            final Project project = xmlTag.getProject();
            final Map<String, XmlAttributeDescriptor> result = new LinkedHashMap<String, XmlAttributeDescriptor>();
            final Collection<String> docComponents = EmberIndexUtil.getAllKeys(EmberComponentDocIndex.INDEX_ID, project);
            for (String componentName : docComponents) {
                if (isApplicable(project, componentName, xmlTag.getName(), EmberComponentDocIndex.INDEX_ID) == ThreeState.YES) {
                    addAttributes(project, result, componentName);
                }
            }
            for (String componentName : EmberIndexUtil.getAllKeys(EmberComponentIndex.INDEX_ID, project)) {
                if (!docComponents.contains(componentName) &&
                        isApplicable(project, componentName, xmlTag.getName(), EmberComponentIndex.INDEX_ID) == ThreeState.YES) {
                    addAttributes(project, result, componentName);
                }
            }
            return result.values().toArray(new XmlAttributeDescriptor[result.size()]);
        }
        return XmlAttributeDescriptor.EMPTY;
    }

    // TODO> We need to make sure that any handlebars component reference is marked as @component
    protected void addAttributes(Project project, Map<String, XmlAttributeDescriptor> result, String componentName) {
        result.put(componentName, createDescriptor(project, componentName));
        if ("@component".equals(componentName)) {
            result.put(componentName + "-start", createDescriptor(project, componentName + "-start"));
            result.put(componentName + "-end", createDescriptor(project, componentName + "-end"));
        }
    }

    private static ThreeState isApplicable(Project project, String componentName, String tagName, final ID<String, Void> index) {
        final JSNamedElementProxy component = EmberIndexUtil.resolve(project, index, componentName);
        if (component == null) {
            return ThreeState.UNSURE;
        }

        return ThreeState.YES;
    }

    private static boolean tagMatches(String tagName, String tag) {
        if (StringUtil.isEmpty(tag) || StringUtil.equalsIgnoreCase(tag, "ANY")) {
            return true;
        }
        for (String s : tag.split(",")) {
            if (StringUtil.equalsIgnoreCase(tagName, s.trim())) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    @Override
    public XmlAttributeDescriptor getAttributeDescriptor(final String attrName, XmlTag xmlTag) {
        final String attributeName = ComponentUtil.getAttributeName(attrName);
        if (xmlTag != null) {
            final Project project = xmlTag.getProject();
            final String tagName = xmlTag.getName();
            ThreeState attributeAvailable = isApplicable(project, attributeName, tagName, EmberComponentDocIndex.INDEX_ID);
            if (attributeAvailable == ThreeState.UNSURE) {
                attributeAvailable = isApplicable(project, attributeName, tagName, EmberComponentIndex.INDEX_ID);
            }
            return attributeAvailable == ThreeState.YES ? createDescriptor(project, attributeName) : null;
        }
        return null;
    }
}
