package org.emberjs.index;

import com.intellij.util.indexing.ID;
import org.jetbrains.annotations.NotNull;

/**
 * Created by kristianmandrup on 25/10/14.
 */
public class EmberModelIndex extends EmberIndexBase {
    public static final ID<String, Void> INDEX_ID = ID.create("emberjs.model.index");
    @NotNull
    @Override
    public ID<String, Void> getName() {
        return INDEX_ID;
    }
}