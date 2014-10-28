package org.emberjs.project;

import com.intellij.ide.IdeBundle;
import com.intellij.ide.util.newProjectWizard.AbstractProjectWizard;
import com.intellij.ide.util.newProjectWizard.StepSequence;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by kristianmandrup on 26/10/14.
 * More to follow here... Should ask some questions and then run Ember CLI generator I guess!!!
 */
public class NewEmberProjectWizard extends AbstractProjectWizard {

    private final StepSequence mySequence = new StepSequence();

    private final String PROJECT_CREATED_SUCCESS = "ember cli project created successfully.";
    private final String COMMAND_NOT_FOUND = "command not found";

    // based roughly on the Java new project wizard!!

    public NewEmberProjectWizard(@Nullable Project project, @NotNull ModulesProvider modulesProvider, @Nullable String defaultPath) {
        super(project == null ? IdeBundle.message("title.new.project") : IdeBundle.message("title.add.module"), project, defaultPath);
        init(project, modulesProvider);
    }

    public NewEmberProjectWizard(Project project, Component dialogParent, ModulesProvider modulesProvider) {
        super(IdeBundle.message("title.add.module"), project, dialogParent);
        init(project, modulesProvider);
    }

    protected void init(@NotNull Project project, @NotNull ModulesProvider modulesProvider) {
        WizardContext myWizardContext = new WizardContext(project);
        myWizardContext.setNewWizard(true);

        // TODO: Would be nice if we could have a setting step open a Dialog to ask for project options!!
//        ProjectSettingsStep settingsStep = new ProjectSettingsStep(myWizardContext);
//        mySequence.addCommonFinishingStep(settingsStep, null);
//        for (ModuleWizardStep step : mySequence.getAllSteps()) {
//            addStep(step);
//        }

        if (myWizardContext.isCreatingNewProject()) {
            createNewProject(project);
        }
        // here or?
        super.init();
    }

    private void createNewProject(Project project) {
        boolean done = false;
        Process p;
        String name = project.getName();
        String newProjectCliCommand = "ember new " + name;
        try {
            p = Runtime.getRuntime().exec(newProjectCliCommand);
            p.waitFor();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                // We need a "hook" in ember cli console output so we can determine if installation went well
                // Then we could do a regexp match on each line received from console to determine
                // if/when project has has been successfully created!!
                if (line.matches(PROJECT_CREATED_SUCCESS)) {
                    done = true;
                }
                if (line.matches(COMMAND_NOT_FOUND)) {
                    installEmberCLI();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (done) {
            System.out.println("Ember project created successfully :)");
        } else {

        }
    }

    private void installEmberCLI() {
        Process p;
        String installEmberCLICommand = "npm install -g ember-cli";
        try {
            p = Runtime.getRuntime().exec(installEmberCLICommand);
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Nullable
    @Override
    protected String getDimensionServiceKey() {
        return "new ember project wizard";
    }

    @Override
    public StepSequence getSequence() {
        return mySequence;
    }
}
