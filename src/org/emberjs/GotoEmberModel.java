package org.emberjs;

import com.intellij.ide.util.gotoByName.ChooseByNameBase;
import com.intellij.ide.util.gotoByName.SimpleChooseByNameModel;
import com.intellij.openapi.project.Project;
import com.intellij.ui.JBColor;
import com.intellij.ui.SimpleColoredComponent;
import com.intellij.ui.SimpleTextAttributes;
import com.intellij.ui.speedSearch.SpeedSearchUtil;
import com.intellij.util.ArrayUtil;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: @kmandrup
 * To change this template use File | Settings | File Templates.
 */
public class GotoEmberModel extends SimpleChooseByNameModel{

    private final List<EmberItem> emberItems;

    public GotoEmberModel(@NotNull Project project, List<EmberItem> emberItems) {
        super(project, "EmberJS", "Help id");
        this.emberItems = emberItems;
    }

    //these are searched
    @Override
    public String[] getNames() {
        List<String> strings = new ArrayList<String>();
        for (EmberItem EmberItem : emberItems) {
            strings.add(EmberItem.getKey());
        }

        return ArrayUtil.toStringArray(strings);
    }

    //list provided to the item renderer
    @Override
    protected Object[] getElementsByName(String name, String pattern) {
        for (EmberItem EmberItem : emberItems) {
            if (EmberItem.getKey().equals(name)) {
                return new Object[]{EmberItem};
            }
        }

        return ArrayUtil.EMPTY_OBJECT_ARRAY;
    }

    @Override
    public ListCellRenderer getListCellRenderer() {
        return new GotoEmberCellRenderer();
    }

    @Nullable
    @Override
    public String getElementName(Object element) {
        return "Element name!";
    }

    protected class GotoEmberCellRenderer implements ListCellRenderer {
        private final SimpleTextAttributes SELECTED;
        private final SimpleTextAttributes PLAIN;


        public GotoEmberCellRenderer() {
            SELECTED = new SimpleTextAttributes(UIUtil.getListSelectionBackground(),
                    UIUtil.getListSelectionForeground(),
                    JBColor.RED,
                    SimpleTextAttributes.STYLE_PLAIN);
            PLAIN = new SimpleTextAttributes(UIUtil.getListBackground(),
                    UIUtil.getListForeground(),
                    JBColor.RED,
                    SimpleTextAttributes.STYLE_PLAIN);

        }

        @Override
        public Component getListCellRendererComponent(JList jList, Object value, int i, boolean sel, boolean focus) {
            JPanel jPanel = new JPanel(new BorderLayout());
            jPanel.setOpaque(true);

            final Color bg = sel ? UIUtil.getListSelectionBackground() : UIUtil.getListBackground();
            final Color fg = sel ? UIUtil.getListSelectionForeground() : UIUtil.getListForeground();
            jPanel.setBackground(bg);
            jPanel.setForeground(fg);

            SimpleTextAttributes attr = sel ? SELECTED : PLAIN;
            if (value instanceof EmberItem) {
                EmberItem item = (EmberItem) value;
                final SimpleColoredComponent c = new SimpleColoredComponent();
                SpeedSearchUtil.appendColoredFragmentForMatcher("  " + item.getItemName(), c, attr, null, bg, sel);
                jPanel.add(c, BorderLayout.WEST);

                final SimpleColoredComponent group = new SimpleColoredComponent();
                SpeedSearchUtil.appendColoredFragmentForMatcher(item.getItemType() + "  ", group, attr, null, bg, sel);
                final JPanel right = new JPanel(new BorderLayout());
                right.setBackground(bg);
                right.setForeground(fg);
                right.add(group, BorderLayout.CENTER);
                jPanel.add(right, BorderLayout.EAST);
            }
            else {
                // E.g. "..." item
                return ChooseByNameBase.renderNonPrefixSeparatorComponent(UIUtil.getListBackground());
            }

            return jPanel;
        }
    }

}
