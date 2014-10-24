/*
 * Copyright 2000-2012 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.emberjs;

import com.intellij.codeInsight.navigation.NavigationUtil;
import com.intellij.find.FindManager;
import com.intellij.find.FindModel;
import com.intellij.find.impl.FindInProjectUtil;
import com.intellij.ide.IdeBundle;
import com.intellij.ide.actions.GotoActionBase;
import com.intellij.ide.util.gotoByName.ChooseByNameFilter;
import com.intellij.ide.util.gotoByName.ChooseByNamePopup;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Segment;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.util.PsiUtilBase;
import com.intellij.usageView.UsageInfo;
import com.intellij.usages.FindUsagesProcessPresentation;
import com.intellij.usages.Usage;
import com.intellij.usages.UsageInfo2UsageAdapter;
import com.intellij.util.AdapterProcessor;
import com.intellij.util.CommonProcessors;
import com.intellij.util.Processor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GotoEmberAction extends GotoActionBase {
    private static final int COMPONENT = 0;
    private static final int CONTROLLER = 1;
    private static final int ROUTE = 2;
    private static final int MODEL = 3;
    private static final int VIEW = 4;
    private static final int MIXIN = 5;

    public GotoEmberAction() {
        getTemplatePresentation().setText(IdeBundle.message("goto.inspection.action.text"));
    }

    private String capitalize(String line)
    {
      return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    // Find anything which matches a pattern like:
    // CommentsRoute = Ember.Route.create({...})
    // The name such as CommentsRoute will be in the first matching group $1
    private String findEmberClass(String thing) {
      return "(\\S+)" + capitalize(thing) + "\\s*=\\s*Ember\\." + thing + "\\.(create|extend)";
    }

    private String findModuleFor(String thing) {
      return "moduleFor\\('" + thing + ":(\\S+)";
    }

    private String findModuleForComponent() {
      return "moduleForComponent\\('(\\S+)";
    }

    private String findModuleForModel() {
      return "moduleForModel\\('(\\S+)";
    }

    private FindModel findReplaceEmberClass(FindModel findModel, String thing) {
      findModel.setStringToFind(findEmberClass(thing));
      findModel.setStringToReplace("$1");
      return findModel;
    }

    private FindModel findReplaceModuleFor(FindModel findModel, String thing) {
      findModel.setStringToFind(findModuleFor(thing));
      findModel.setStringToReplace("$1");
      return findModel;
    }

    private FindModel findReplaceModuleForComponent(FindModel findModel) {
      findModel.setStringToFind(findModuleForComponent());
      findModel.setStringToReplace("$1");
      return findModel;
    }

    private FindModel findReplaceModuleForModel(FindModel findModel) {
      findModel.setStringToFind(findModuleForModel());
      findModel.setStringToReplace("$1");
      return findModel;
    }

    private void findResultsForEmberClass(Project project, DataContext dataContext, List<EmberItem> validResults, FindModel findModel, String typeName, int typeId) {
      addResultsFor(project, dataContext, validResults, findReplaceEmberClass(findModel, typeName), typeId);
    }

    private void findResultsModuleFor(Project project, DataContext dataContext, List<EmberItem> validResults, FindModel findModel, String typeName, int typeId) {
      addResultsFor(project, dataContext, validResults, findReplaceModuleFor(findModel, typeName), typeId);
    }

    private void findResultsModuleForComponent(Project project, DataContext dataContext, List<EmberItem> validResults, FindModel findModel, int typeId) {
      addResultsFor(project, dataContext, validResults, findReplaceModuleForComponent(findModel), typeId);
    }

    private void findResultsModuleForModel(Project project, DataContext dataContext, List<EmberItem> validResults, FindModel findModel, int typeId) {
      addResultsFor(project, dataContext, validResults, findReplaceModuleForModel(findModel), typeId);
    }

    private void addResultsFor(Project project, DataContext dataContext, List<EmberItem> validResults, FindModel findModel, int typeId) {
      final Collection<Usage> usages = getEmberUsages(project, dataContext, findModel);
      List<EmberItem> usageResults = getValidResults(project, findModel, usages, typeId);
      validResults.addAll(usageResults);
    }


    @Override
    protected void gotoActionPerformed(final AnActionEvent e) {
        final Project project = e.getData(PlatformDataKeys.PROJECT);
        if (project == null) return;

        PsiDocumentManager.getInstance(project).commitAllDocuments();

        final DataContext dataContext = e.getDataContext();

        final FindManager findManager = FindManager.getInstance(project);
        final FindModel findModel = (FindModel) findManager.getFindInFileModel().clone();


        final List<EmberItem> validResults = new ArrayList<EmberItem>();

        findModel.setRegularExpressions(true);
        findModel.setFileFilter("*.js");

        // TODO: Refactor this! Use some kind of Map instead and do iteration
        findResultsForEmberClass(project, dataContext, validResults, findModel, "Component", COMPONENT);
        findResultsForEmberClass(project, dataContext, validResults, findModel, "Controller", CONTROLLER);
        findResultsForEmberClass(project, dataContext, validResults, findModel, "Route", ROUTE);
        findResultsForEmberClass(project, dataContext, validResults, findModel, "Model", MODEL);
        findResultsForEmberClass(project, dataContext,validResults, findModel, "View", VIEW);
        findResultsForEmberClass(project, dataContext,validResults, findModel, "Mixin", MIXIN);

        // Unit Testing
        // http://emberjs.com/guides/testing/unit-test-helpers/
        // http://emberjs.com/guides/testing/testing-components/

        // moduleFor('controller:posts',
        // TODO: Refactor this! Use some kind of Map instead and do iteration
        // TODO: Perhaps refactor typeId to be TEST_COMPONENT etc.
        findResultsModuleFor(project, dataContext, validResults, findModel, "component", COMPONENT);
        findResultsModuleFor(project, dataContext, validResults, findModel, "controller", CONTROLLER);
        findResultsModuleFor(project, dataContext, validResults, findModel, "route", ROUTE);
        findResultsModuleFor(project, dataContext, validResults, findModel, "model", MODEL);
        findResultsModuleFor(project, dataContext, validResults, findModel, "view", VIEW);
        findResultsModuleFor(project, dataContext, validResults, findModel, "mixin", MIXIN);

        // moduleForComponent(name,
        findResultsModuleForComponent(project, dataContext, validResults, findModel, COMPONENT);
        // moduleForModel(name
        findResultsModuleForModel(project, dataContext, validResults, findModel, MODEL);

        final GotoEmberModel model = new GotoEmberModel(project, validResults);
        showNavigationPopup(e, model, new GotoEmberBase.GotoActionCallback<Object>() {
            @Override
            protected ChooseByNameFilter<Object> createFilter(@NotNull ChooseByNamePopup popup) {
                popup.setSearchInAnyPlace(true);
                popup.setShowListForEmptyPattern(true);
                popup.setMaximumListSizeLimit(255);
                return super.createFilter(popup);
            }

            @Override
            public void elementChosen(ChooseByNamePopup popup, final Object element) {
                ApplicationManager.getApplication().invokeLater(new Runnable() {
                    public void run() {
                        PsiElement psi = ((EmberItem) element).getElement();
                        NavigationUtil.activateFileWithPsiElement(psi.getNavigationElement());
                    }
                });
            }
        });
    }

    private List<EmberItem> getValidResults(final Project project, final FindModel findModel, final Collection<Usage> usages, final int type) {
        final List<EmberItem> validResults = new ArrayList<EmberItem>();

        //todo: needs code review. There must be a better way to do this
        Runnable runnable = new Runnable() {
            public void run() {
                for (final Usage result : usages) {

                    final UsageInfo2UsageAdapter usage = (UsageInfo2UsageAdapter) result;
                    //avoid ember source files. Is there a better way to do this?
                    if (usage.getFile().getName().startsWith("ember")) continue;

                    usage.processRangeMarkers(new Processor<Segment>() {
                        @Override
                        public boolean process(Segment segment) {
                            try {
                                final int textOffset = segment.getStartOffset();

                                final int textEndOffset = segment.getEndOffset();
                                Document document = usage.getDocument();
                                CharSequence charsSequence = document.getCharsSequence();
                                final CharSequence foundString = charsSequence.subSequence(textOffset, textEndOffset);
                                String s = foundString.toString();
                                String regExMatch = FindManager.getInstance(project).getStringToReplace(s, findModel, textOffset, document.getText());
                                System.out.println(regExMatch);
                                PsiElement element = PsiUtilBase.getElementAtOffset(((UsageInfo2UsageAdapter) result).getUsageInfo().getFile(), textOffset + 1);
                                String elementText = element.getText();
                                System.out.println(elementText + ": " + regExMatch + " - " + s);

                                // TODO: Refactor this! Use some kind of Map instead!
                                switch (type) {
                                    case COMPONENT:
                                        validResults.add(new EmberItem(s, elementText, result, element, "component"));
                                        break;

                                    case CONTROLLER:
                                        validResults.add(new EmberItem(s, elementText, result, element, "controller"));
                                        break;

                                    case ROUTE:
                                        validResults.add(new EmberItem(s, elementText, result, element, "route"));
                                        break;

                                    case MODEL:
                                        validResults.add(new EmberItem(s, elementText, result, element, "model"));
                                        break;

                                    case VIEW:
                                        validResults.add(new EmberItem(s, elementText, result, element, "view"));
                                        break;

                                    case MIXIN:
                                        validResults.add(new EmberItem(s, elementText, result, element, "mixin"));
                                        break;
                                }

                                return true;
                            } catch (FindManager.MalformedReplacementStringException e1) {
                                e1.printStackTrace();
                            }

                            return false;
                        }
                    });
                }
            }
        };

        ApplicationManager.getApplication().runReadAction(runnable);
        return validResults;
    }

    private Collection<Usage> getEmberUsages(Project project, DataContext dataContext, FindModel findModel) {

        FindInProjectUtil.setDirectoryName(findModel, dataContext);

        CommonProcessors.CollectProcessor<Usage> collectProcessor = new CommonProcessors.CollectProcessor<Usage>();

        PsiDirectory directory = PsiManager.getInstance(project).findDirectory(project.getBaseDir());
        FindInProjectUtil.findUsages(findModel, directory, project,
                true, new AdapterProcessor<UsageInfo, Usage>(collectProcessor, UsageInfo2UsageAdapter.CONVERTER), new FindUsagesProcessPresentation());


        return collectProcessor.getResults();
    }
}
