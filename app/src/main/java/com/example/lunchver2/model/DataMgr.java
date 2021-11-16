package com.example.lunchver2.model;

import com.example.lunchver2.general.Rule;
import com.example.lunchver2.structObject.SaveFormat;

import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class DataMgr {
    private HashMap<Integer, TypeData> typeDataMap = null;
    private TypeData currentType = null;

    public DataMgr() {
        typeDataMap = new HashMap<Integer, TypeData>();
        currentType = new TypeData();
    }

    public boolean checkDataIsEmpty(String data) {
        return (data == null || data.isEmpty());
    }

    public boolean checkUsingItemIsEmpty(){return currentType.checkUsingItemIsEmpty();}

    public void saveData(String data) {
        String[] itemValues = data.split(Rule.dataSplitSing);
        for (String s : itemValues) {
            currentType.addNewItem(s);
        }
    }

    public String[] getUsingItems() {
        return currentType.getUsingItems();
    }

    public String[] getUnusedItems() {
        return currentType.getUnusedItems();
    }

    public boolean checkHaveItem(String item) {
        return currentType.checkHaveUsingItem(item);
    }

    public boolean checkItemIsExclude(String item) {
        return currentType.checkItemIsExclude(item);
    }

    public void putItemToUnused(String item) {
        currentType.removeUsingItem(item);
        currentType.addUnusedItem(item);
    }

    public void putItemToUsing(String item) {
        currentType.removeUnusedItem(item);
        currentType.addNewItem(item);
    }

    public void removeExcludeItem(String item) {
        currentType.removeUnusedItem(item);
    }

    public void clearUnusedItem() {
        currentType.clearUnusedItem();
    }

    public void addNewType(String typeName, int index) {
        if (currentType.getIndex() != 0) {
            currentType = new TypeData();
        }

        currentType.setIndex(index);
        currentType.setTypeName(typeName);
        typeDataMap.put(index, currentType);
    }

    public boolean checkHaveType(String typeName) {
        boolean answer = false;

        for (Map.Entry<Integer, TypeData> entry : typeDataMap.entrySet()) {
            if (entry.getValue().getTypeName().equals(typeName)) {
                answer = true;
                break;
            }
        }

        return answer;
    }

    public boolean checkHaveType(int index) {
        return typeDataMap.containsKey(index) || (index == 0);
    }

    public void changeType(int index) {
        if (index == 0) {
            if (currentType.getIndex() != 0)
                currentType = new TypeData();
        } else
            currentType = typeDataMap.get(index);
    }

    public boolean checkIsEmptyType() {
        return currentType.getIndex() == 0;
    }

    public void removeCurrentType() {
        typeDataMap.remove(currentType.getIndex());
    }

    public SaveFormat getSaveFormat()
    {
        SaveFormat format = new SaveFormat();
        format.typeDataMap = typeDataMap;
        format.currentTypeIndex = currentType.getIndex();
        return format;
    }

    public void setSavedFormat(SaveFormat format)
    {
        typeDataMap = format.typeDataMap;
        currentType = typeDataMap.get(format.currentTypeIndex);
    }

    public String[] getAllTypeName()
    {
        ArrayList<Integer> keys = getSortTypeDataKeys();
        int dataCount = keys.size();
        String[] result = new String[dataCount];
        int tempKey = 0;

        for (int i = 0;i < dataCount;++i)
        {
            tempKey = keys.get(i);
            result[i] = typeDataMap.get(tempKey).getTypeName();
        }

        return result;
    }

    private ArrayList<Integer> getSortTypeDataKeys()
    {
        ArrayList<Integer> mapKeys = new ArrayList<Integer>();
        for (Map.Entry<Integer,TypeData> entry : typeDataMap.entrySet())
        {
            mapKeys.add(entry.getKey());
        }
        return mapKeys;
    }

    public int getCurrentTypeIndex()
    {
        return currentType.getIndex();
    }

    public boolean checkHaveSavedData()
    {
        return typeDataMap.size() > 0;
    }
}
