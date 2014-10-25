package org.emberjs.settings;

import com.intellij.openapi.components.*;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

/**
 * Created by denofevil on 26/11/13.
 */
@State(
    name="EmberJSConfig",
    storages = {
            @Storage(
                    file = StoragePathMacros.APP_CONFIG + "/other.xml"
            )}
)
public class EmberJSConfig implements PersistentStateComponent<EmberJSConfig> {
    public boolean INSERT_WHITESPACE = false;

    public static EmberJSConfig getInstance() {
        return ServiceManager.getService(EmberJSConfig.class);
    }

    @Nullable
    @Override
    public EmberJSConfig getState() {
        return this;
    }

    @Override
    public void loadState(EmberJSConfig state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}
