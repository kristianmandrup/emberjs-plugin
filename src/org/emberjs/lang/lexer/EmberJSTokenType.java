package org.emberjs.lang.lexer;

import com.intellij.psi.tree.IElementType;
import org.emberjs.lang.EmberJSLanguage;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dennis.Ushakov
 */
public class EmberJSTokenType extends IElementType {
    public EmberJSTokenType(@NotNull @NonNls String debugName) {
        super(debugName, EmberJSLanguage.INSTANCE);
    }
}
