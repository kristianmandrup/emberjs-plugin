package org.emberjs.project;

import com.intellij.ide.util.newProjectWizard.AbstractProjectWizard;
import com.intellij.ide.util.newProjectWizard.StepSequence;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.openapi.project.Project;

import javax.swing.*;
import java.awt.*;

/**
 * Created by kristianmandrup on 26/10/14.
 * More to follow here... Should ask some questions and then run Ember CLI generator I guess!!!
 */
public class EmberProjectWizard extends AbstractProjectWizard {
    public EmberProjectWizard(String title, Project project, String defaultPath) {
        super(title, project, defaultPath);
    }

    public EmberProjectWizard(String title, Project project, Component dialogParent) {
        super(title, project, dialogParent);
    }

    public StepSequence getSequence() {
        ModuleWizardStep step = new ModuleWizardStep() {
            @Override
            public JComponent getComponent() {
                return null;
            }

            @Override
            public void updateDataModel() {

            }
        };

        return new StepSequence(step);
    }
}
