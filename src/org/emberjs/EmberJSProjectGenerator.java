package org.angularjs;

import com.intellij.lang.javascript.boilerplate.AbstractGithubTagDownloadedProjectGenerator;
import com.intellij.platform.templates.github.GithubTagInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Dennis.Ushakov
 */
public class EmberJSProjectGenerator extends AbstractGithubTagDownloadedProjectGenerator {
    @NotNull
    @Override
    protected String getDisplayName() {
        return "EmberJS";
    }

    @NotNull
    @Override
    protected String getGithubUserName() {
        return "emberjs";
    }

    @NotNull
    @Override
    protected String getGithubRepositoryName() {
        return "ember.js";
    }

    @Nullable
    @Override
    public String getDescription() {
        return "<html>This project is an application skeleton for a typical <a href=\"https://ember-cli.org\">Ember CLI</a> web app.<br>" +
                "Don't forget to install dependencies by running<pre>npm install</pre> and <pre>bower install</pre></html>";
    }

    @Nullable
    @Override
    public String getPrimaryZipArchiveUrlForDownload(@NotNull GithubTagInfo tag) {
        return null;
    }
}
