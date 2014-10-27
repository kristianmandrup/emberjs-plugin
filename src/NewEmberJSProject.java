import com.intellij.ide.actions.NewProjectAction;
import com.intellij.ide.impl.NewProjectUtil;
import com.intellij.ide.util.newProjectWizard.AddModuleWizard;
import com.intellij.ide.util.newProjectWizard.AddModuleWizardPro;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import com.intellij.openapi.util.registry.Registry;

/**
 * Created by kristianmandrup on 26/10/14.
 */

// http://grepcode.com/file/repository.grepcode.com/java/ext/com.jetbrains/intellij-idea/13.0.0/com/intellij/ide/impl/NewProjectUtil.java#NewProjectUtil.createNewProject%28com.intellij.openapi.project.Project%2Ccom.intellij.ide.util.newProjectWizard.AbstractProjectWizard%29
public class NewEmberJSProject extends NewProjectAction {
    public void actionPerformed(AnActionEvent e) {
        NewProjectUtil.createNewProject(CommonDataKeys.PROJECT.getData(e.getDataContext()), getAddModuleWizard());
    }

    private AddModuleWizard getAddModuleWizard() {
        return Registry.is("new.project.wizard")
                ? getAddModuleWizardPro()
                : getAddModuleWizardBasic();
    }

    private AddModuleWizard getAddModuleWizardBasic() {
        return new AddModuleWizard(null, ModulesProvider.EMPTY_MODULES_PROVIDER, null);
    }

    private AddModuleWizardPro getAddModuleWizardPro() {
        return new AddModuleWizardPro(null, ModulesProvider.EMPTY_MODULES_PROVIDER, null);
    }
}
