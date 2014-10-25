package org.emberjs.navigation;

import org.emberjs.index.EmberSymbolIndex;
import com.intellij.lang.javascript.index.JSNamedElementProxy;
import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import com.intellij.util.ArrayUtil;
import org.emberjs.index.EmberIndexUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author Kristian Mandrup
 */
public class EmberGotoSymbolContributor implements ChooseByNameContributor {
    @NotNull
    @Override
    public String[] getNames(Project project, boolean includeNonProjectItems) {
        return ArrayUtil.toStringArray(EmberIndexUtil.getAllKeys(EmberSymbolIndex.INDEX_ID, project));
    }

    @NotNull
    @Override
    public NavigationItem[] getItemsByName(String name, String pattern, Project project, boolean includeNonProjectItems) {
        final JSNamedElementProxy item = EmberIndexUtil.resolve(project, EmberSymbolIndex.INDEX_ID, name);
        return item != null ? new NavigationItem[] {item} : NavigationItem.EMPTY_NAVIGATION_ITEM_ARRAY;
    }
}
