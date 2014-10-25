package org.emberjs.settings;

import com.intellij.openapi.options.BeanConfigurable;
import com.intellij.openapi.options.UnnamedConfigurable;
import com.intellij.ui.IdeBorderFactory;

import javax.swing.*;

/**
 * @author Kristian Mandrup
 */
public class EmberJSConfigurationPage extends BeanConfigurable<EmberJSConfig> implements UnnamedConfigurable {
    protected EmberJSConfigurationPage() {
        super(EmberJSConfig.getInstance());

        checkBox("INSERT_WHITESPACE", "Auto-insert whitespace in the interpolations");
    }

    @Override
    public JComponent createComponent() {
        JComponent result = super.createComponent();
        assert result != null;
        result.setBorder(IdeBorderFactory.createTitledBorder("EmberJS"));
        return result;
    }
}
