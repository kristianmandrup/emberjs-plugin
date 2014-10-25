package org.emberjs.lang;

import com.intellij.lang.javascript.DialectOptionHolder;
import com.intellij.lang.javascript.JSLanguageDialect;

/**
 * @author Dennis.Ushakov
 */
public class EmberJSLanguage extends JSLanguageDialect {
    public static final EmberJSLanguage INSTANCE = new EmberJSLanguage();

    protected EmberJSLanguage() {
        super("EmberJS", DialectOptionHolder.OTHER);
    }

    @Override
    public String getFileExtension() {
        return "js";
    }
}
