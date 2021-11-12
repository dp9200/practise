package com.example.lunchver2.model;

import java.util.ArrayList;

public class TypeData {

    private int index = 0;
    private String typeName = null;
    private ArrayList<String> usingItems = null;
    private ArrayList<String> unusedItems = null;

    public TypeData() {
        usingItems = new ArrayList<String>();
        unusedItems = new ArrayList<String>();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void addNewItem(String item) {
        usingItems.add(item);
    }

    public void removeUsingItem(String item) {
        usingItems.remove(item);
    }

    public void addUnusedItem(String item) {
        unusedItems.add(item);
    }

    public void removeUnusedItem(String item) {
        unusedItems.remove(item);
    }

    public String[] getUsingItems() {
        return replaceStringListToArray(usingItems);
    }

    public String[] getUnusedItems() {
        return replaceStringListToArray(unusedItems);
    }

    private String[] replaceStringListToArray(ArrayList<String> target) {
        int count = target.size();
        String[] result = new String[count];

        for (int i = 0; i < count; ++i) {
            result[i] = target.get(i);
        }

        return result;
    }

    public boolean checkHaveUsingItem(String item) {
        return usingItems.contains(item);
    }

    public boolean checkItemIsExclude(String item) {
        return unusedItems.contains(item);
    }

    public void clearUnusedItem() {
        unusedItems.clear();
    }

    public boolean checkUsingItemIsEmpty(){
        return usingItems.size() == 0;
    }
}
