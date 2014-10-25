package org.emberjs.codeInsight;

import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlAttribute;
import com.intellij.psi.xml.XmlTag;
import com.intellij.xml.HtmlXmlExtension;
import org.emberjs.index.EmberIndexUtil;

/**
 * @author Dennis.Ushakov
 */
public class EmberJSHtmlExtension extends HtmlXmlExtension {
    @Override
    public boolean isAvailable(PsiFile file) {
        return super.isAvailable(file) && EmberIndexUtil.hasEmberJS(file.getProject());
    }

    @Override
    public boolean isRequiredAttributeImplicitlyPresent(XmlTag tag, String attrName) {
        for (XmlAttribute attribute : tag.getAttributes()) {
            // TODO: match correctly on handlebars expression
            if (("{{" + attrName).equals(ComponentUtil.getAttributeName(attribute.getName()))) {
                return true;
            }
        }

        return super.isRequiredAttributeImplicitlyPresent(tag, attrName);
    }

    //TODO: Perhaps we can use namespacing with Handlebars2 and HTMLbars?
//    @Override
//    public SchemaPrefix getPrefixDeclaration(XmlTag context, String namespacePrefix) {
//        if ("ng".equals(namespacePrefix)) {
//            return new SchemaPrefix(null, null, namespacePrefix);
//        }
//        return super.getPrefixDeclaration(context, namespacePrefix);
//    }
}
