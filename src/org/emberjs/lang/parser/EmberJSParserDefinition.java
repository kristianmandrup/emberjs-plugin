package org.emberjs.lang.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiParser;
import com.intellij.lang.javascript.JavascriptParserDefinition;
import com.intellij.lang.javascript.types.JSFileElementType;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import org.emberjs.lang.EmberJSLanguage;
import org.emberjs.lang.lexer.EmberJSLexer;
import org.emberjs.lang.psi.EmberJSComputedProperty;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dennis.Ushakov
 */
public class EmberJSParserDefinition extends JavascriptParserDefinition {
    private static final IFileElementType FILE = new JSFileElementType(EmberJSLanguage.INSTANCE);

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new EmberJSLexer();
    }

    @NotNull
    @Override
    public PsiParser createParser(Project project) {
        return new EmberParser();
    }

    @NotNull
    @Override
    public PsiElement createElement(ASTNode node) {
        final IElementType type = node.getElementType();
        if (type == EmberJSElementTypes.COMPUTED_PROPERTY) {
            return new EmberJSComputedProperty(node);
        }
        return super.createElement(node);
    }

    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }
}
