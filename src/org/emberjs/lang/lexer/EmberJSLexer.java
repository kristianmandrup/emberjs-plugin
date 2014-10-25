package org.emberjs.lang.lexer;

import com.intellij.lang.javascript.JSTokenTypes;
import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.MergingLexerAdapter;
import com.intellij.psi.tree.TokenSet;

import java.io.Reader;

/**
 * @author Kristian Mandrup
 */
public class EmberJSLexer extends MergingLexerAdapter {
    public EmberJSLexer() {
        // _EmberJSLexer is generated via ember.flex
        super(new FlexAdapter(new _EmberJSLexer((Reader)null)), TokenSet.create(JSTokenTypes.STRING_LITERAL));
    }
}
