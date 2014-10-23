package org.emberjs;

import com.intellij.psi.PsiElement;
import com.intellij.usages.Usage;

/**
* Created with IntelliJ IDEA.
* User: kmandrup
* To change this template use File | Settings | File Templates.
*/
public class EmberItem {
    private String itemName;
    private String key;

    public Usage getUsage() {
        return usage;
    }

    private Usage usage;
    private PsiElement element;
    private final String itemType;

    public PsiElement getElement() {
        return element;
    }

    AngularItem(String key, String itemName, Usage usage, PsiElement element, String itemType) {
        this.key = key;
        this.itemName = itemName;
        this.usage = usage;
        this.element = element;
        this.itemType = itemType;
    }


    public String getItemName() {
        return itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public String getKey() {
        return key;
    }
}
