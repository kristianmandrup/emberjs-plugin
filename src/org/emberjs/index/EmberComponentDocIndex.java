package org.emberjs.index;

import com.intellij.util.indexing.ID;
import org.jetbrains.annotations.NotNull;

/**
 * @author Dennis.Ushakov
 */
public class EmberComponentDocIndex extends EmberIndexBase {
    public static final ID<String, Void> INDEX_ID = ID.create("angularjs.components.doc.index");

    @NotNull
    @Override
    public ID<String, Void> getName() {
        return INDEX_ID;
    }
}
