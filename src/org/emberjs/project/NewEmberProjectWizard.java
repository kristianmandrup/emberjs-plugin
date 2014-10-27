package org.emberjs.project;

import com.intellij.ide.IdeBundle;
import com.intellij.ide.projectWizard.ProjectSettingsStep;
import com.intellij.ide.util.newProjectWizard.AbstractProjectWizard;
import com.intellij.ide.util.newProjectWizard.StepSequence;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
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
        ProjectSettingsStep settingsStep = new ProjectSettingsStep(myWizardContext);
        mySequence.addCommonFinishingStep(settingsStep, null);
        for (ModuleWizardStep step : mySequence.getAllSteps()) {
            addStep(step);
        }
        boolean done = false;

        if (myWizardContext.isCreatingNewProject()) {

            // alternative!?
            // http://www.mkyong.com/java/how-to-execute-shell-command-from-java/
            Process p;
            try {
                p = Runtime.getRuntime().exec("ember new demo-proj");
                p.waitFor();

                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(p.getInputStream()));

                String line = "";
                while ((line = reader.readLine())!= null) {
                    // We need a "hook" in ember cli console output so we can determine if installation went well
                    // Then we could do a regexp match on each line received from console to determine
                    // if/when project has has been successfully created!!
                    if (line == "done");
                        done = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (done) {
                System.out.println("Ember project created successfully :)");
            }
        }
        // here or?
        super.init();
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
