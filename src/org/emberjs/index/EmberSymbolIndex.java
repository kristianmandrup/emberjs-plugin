package org.emberjs.index;

import com.intellij.util.indexing.ID;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dennis.Ushakov
 */
public class EmberSymbolIndex extends EmberIndexBase {
    public static final ID<String, Void> INDEX_ID = ID.create("emberjs.symbol.index");

    @NotNull
    @Override
    public ID<String, Void> getName() {
        return INDEX_ID;
    }
}
