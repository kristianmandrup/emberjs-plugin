package org.emberjs.project;

import com.intellij.execution.console.ConsoleExecuteAction;
import com.intellij.execution.console.LanguageConsoleImpl;
import com.intellij.execution.console.LanguageConsoleViewImpl;
import com.intellij.lang.Language;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;

/**
 * Created by kristianmandrup on 28/10/14.
 */
public class ConsoleAction {

    public ConsoleAction(Project project) {
        EmberConsoleExecuteActionHandler execHandler = new EmberConsoleExecuteActionHandler(false);

        LanguageConsoleImpl langConsoleImp = new LanguageConsoleImpl(project, "ember project", javascriptLanguage());
        LanguageConsoleViewImpl langView = new LanguageConsoleViewImpl(langConsoleImp);

        // this seems to be a way to execute a console action based on selection in editor. Not really what we want!
        // run ember cli :)
        ConsoleExecuteAction consoleAction = new ConsoleExecuteAction(langView, execHandler);
        consoleAction.execute(TextRange.allOf("ember new demo-proj"), "ember new demo-proj", null);
    }

    private boolean isJavascript(Language lang) {
        return lang.getAssociatedFileType().getDefaultExtension() == "js";
    }

    private Language javascriptLanguage() {
        for (Language language : Language.getRegisteredLanguages()) {
            if (!isJavascript(language))
                return language;
        }
        return null;
    }

}
