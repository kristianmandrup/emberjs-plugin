package org.emberjs.lang.parser;

import com.intellij.psi.tree.IElementType;
import org.emberjs.lang.EmberJSLanguage;

/**
 * @author Kristian Mandrup
 */
public interface EmberJSElementTypes {

    // AS expression is in the Handlebars each template (each post in posts) post is the AS
    IElementType AS_EXPRESSION = new IElementType("AS_EXPRESSION", EmberJSLanguage.INSTANCE);

    // REPEAT is just a handlebars EACH
    IElementType REPEAT_EXPRESSION = new IElementType("REPEAT_EXPRESSION", EmberJSLanguage.INSTANCE);

    // TODO: If not here, we do we define such JS constructs?
    IElementType COMPUTED_PROPERTY = new IElementType("COMPUTED_PROPERTY", EmberJSLanguage.INSTANCE);
    IElementType OBSERVED_PROPERTY = new IElementType("OBSERVED_PROPERTY", EmberJSLanguage.INSTANCE);
    IElementType ACTION_METHOD = new IElementType("ACTION_METHOD", EmberJSLanguage.INSTANCE);
    IElementType QUERY_PARAM = new IElementType("QUERY_PARAM", EmberJSLanguage.INSTANCE);
}
