package org.emberjs.codeInsight;

import com.intellij.codeInspection.InspectionProfileEntry;
import com.intellij.lang.javascript.highlighting.IntentionAndInspectionFilter;
import com.sixrr.inspectjs.validity.BadExpressionStatementJSInspection;

/**
 * @author Kristian Mandrup
 */
public class EmberJSInspectionFilter extends IntentionAndInspectionFilter {
    public boolean isSupportedInspection(String inspectionToolId) {
        return !inspectionToolId.equals(InspectionProfileEntry.getShortName(BadExpressionStatementJSInspection.class.getSimpleName()));
    }
}
