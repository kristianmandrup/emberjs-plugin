package org.emberjs.lang.lexer;

import com.intellij.lang.javascript.JSTokenTypes;
import org.emberjs.lang.lexer.EmberJSTokenType;

/**
 * @author Dennis.Ushakov
 */
public interface EmberJSTokenTypes extends JSTokenTypes {
    EmberJSTokenType ESCAPE_SEQUENCE = new EmberJSTokenType("ESCAPE_SEQUENCE");
    EmberJSTokenType INVALID_ESCAPE_SEQUENCE = new EmberJSTokenType("INVALID_ESCAPE_SEQUENCE");
    EmberJSTokenType TRACK_BY_KEYWORD = new EmberJSTokenType("TRACK_BY_KEYWORD");
    EmberJSTokenType ONE_TIME_BINDING = new EmberJSTokenType("ONE_TIME_BINDING");
}
