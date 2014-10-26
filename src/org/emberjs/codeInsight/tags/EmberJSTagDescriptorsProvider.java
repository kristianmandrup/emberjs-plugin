package org.emberjs.codeInsight.tags;

import com.intellij.codeInsight.completion.XmlTagInsertHandler;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.lang.javascript.index.JSNamedElementProxy;
import com.intellij.openapi.project.Project;
import com.intellij.psi.html.HtmlTag;
import com.intellij.psi.impl.source.xml.XmlElementDescriptorProvider;
import com.intellij.psi.xml.XmlTag;
import com.intellij.util.Processor;
import com.intellij.xml.XmlElementDescriptor;
import com.intellij.xml.XmlNSDescriptor;
import com.intellij.xml.XmlTagNameProvider;
import com.intellij.xml.impl.schema.AnyXmlElementDescriptor;
import org.emberjs.codeInsight.ComponentUtil;
import org.emberjs.index.EmberIndexUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author Dennis.Ushakov
 */
public class EmberJSTagDescriptorsProvider implements XmlElementDescriptorProvider, XmlTagNameProvider {
    @Override
    public void addTagNameVariants(final List<LookupElement> elements, @NotNull XmlTag xmlTag, String prefix) {
        if (!(xmlTag instanceof HtmlTag && EmberIndexUtil.hasEmberJS(xmlTag.getProject()))) return;

        final Project project = xmlTag.getProject();
        ComponentUtil.processComponents(project, new Processor<JSNamedElementProxy>() {
            @Override
            public boolean process(JSNamedElementProxy component) {
                addLookupItem(elements, component);
                return true;
            }
        });
    }

    private static void addLookupItem(List<LookupElement> elements, JSNamedElementProxy component) {
        elements.add(LookupElementBuilder.create(component).withInsertHandler(XmlTagInsertHandler.INSTANCE));
    }

    @Nullable
    @Override
    public XmlElementDescriptor getDescriptor(XmlTag xmlTag) {
        if (!(xmlTag instanceof HtmlTag && EmberIndexUtil.hasEmberJS(xmlTag.getProject()))) return null;

        final String componentName = ComponentUtil.getAttributeName(xmlTag.getName());
        final XmlNSDescriptor nsDescriptor = xmlTag.getNSDescriptor(xmlTag.getNamespace(), false);
        final XmlElementDescriptor descriptor = nsDescriptor != null ? nsDescriptor.getElementDescriptor(xmlTag) : null;
        if (descriptor != null && !(descriptor instanceof AnyXmlElementDescriptor)) {
            return null;
        }

        final Project project = xmlTag.getProject();
        final JSNamedElementProxy component = ComponentUtil.getComponentProxy(componentName, project);

        return component != null ? new EmberJSTagDescriptor(componentName, component) : null;
    }
}
