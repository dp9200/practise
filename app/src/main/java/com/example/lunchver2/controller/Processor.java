package com.example.lunchver2.controller;

import android.content.SharedPreferences;

import com.example.lunchver2.general.Rule;
import com.example.lunchver2.general.Tools;
import com.example.lunchver2.model.ClientMgr;
import com.example.lunchver2.model.DataMgr;
import com.example.lunchver2.model.PickMachine;
import com.example.lunchver2.myInterface.IRandomItemDisplayer;
import com.example.lunchver2.myInterface.IMainProcessor;
import com.example.lunchver2.structObject.SaveFormat;
import com.example.lunchver2.structObject.TypeRecordFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Processor implements IMainProcessor {

    private IRandomItemDisplayer resultDisplayer = null;
    private DataMgr dataMgr = null;
    private PickMachine pickMachine = null;

    private static IMainProcessor mainProcessor = null;

    public static IMainProcessor getMainProcessor() {
        if (mainProcessor == null) {
            mainProcessor = new Processor();
        }

        return mainProcessor;
    }

    public Processor() {
        dataMgr = new DataMgr();
        pickMachine = new PickMachine();
    }

    public void initSavedData(SharedPreferences sharedPreferences)  {
        try {
            String json = sharedPreferences.getString(Rule.saveKey, Rule.emptyData);
            if (!json.equals(Rule.emptyData)) {
                ObjectMapper objectMapper = new ObjectMapper();
                SaveFormat format = objectMapper.readValue(json, SaveFormat.class);
                dataMgr.setSavedFormat(format);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveData(SharedPreferences sharedPreferences) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        SaveFormat format = dataMgr.getSaveFormat();
        String dataJson = objectMapper.writeValueAsString(format);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Rule.saveKey, dataJson);
        editor.apply();
    }

    public void deleteData(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    @Override
    public Processor registerDisplayer(IRandomItemDisplayer resultDisplayer) {
        this.resultDisplayer = resultDisplayer;
        return this;
    }

    public void addNewItem(String value) {
        if (dataMgr.checkDataIsEmpty(value)) {
            return;
        }
        String[] usingItems = null;
        value = Tools.ToBj(value);
        dataMgr.saveData(value);
        usingItems = dataMgr.getUsingItems();
        resultDisplayer.displayUsingItem(usingItems);
    }

    public void excludeItem(String item) {
        if (!dataMgr.checkHaveItem(item)) {
            return;
        }

        dataMgr.putItemToUnused(item);
        updateView();
    }

    public void recycleItem(String item) {
        if (!dataMgr.checkItemIsExclude(item)) {
            return;
        }

        dataMgr.putItemToUsing(item);
        updateView();
    }

    public void removeItem(String item) {
        if (!dataMgr.checkItemIsExclude(item)) {
            return;
        }

        dataMgr.removeExcludeItem(item);
        updateView();
    }

    public void clearItem() {
        dataMgr.clearUnusedItem();
        updateExcludeView();
    }

    public void pickItem() {
        if (dataMgr.checkUsingItemIsEmpty())
        {
            resultDisplayer.showMsg(Rule.emptyItemMsg);
            return;
        }
        String[] usingItem = dataMgr.getUsingItems();
        String[] result = pickMachine.pickItem(usingItem);
        String pickType = Rule.EPickType.PickItem.toString();
        uploadResult(result,pickType);
        resultDisplayer.displayPickResult(result);
    }

    private void uploadResult(String[] result,String type)
    {
        int lastIndex = result.length-1;
        ClientMgr.getInstance().sendResult(result[lastIndex],type);
    }

    public void addType(String typeName, int index) {
        if (dataMgr.checkHaveType(typeName)) {
            resultDisplayer.showMsg(Rule.repeatType);
        } else {
            dataMgr.addNewType(typeName, index);
            resultDisplayer.addTypeToSpinner(typeName);
        }
    }

    public void selectType(int index) {
        if (dataMgr.checkHaveType(index)) {
            dataMgr.changeType(index);
            updateView();
        } else {
            resultDisplayer.showMsg(Rule.typeError);
        }
    }

    public void removeCurrentType() {
        if (dataMgr.checkIsEmptyType()) {
            resultDisplayer.showMsg(Rule.typeNotSave);
        } else {
            dataMgr.removeCurrentType();
        }
    }

    public TypeRecordFormat getRecordFormat() {
        TypeRecordFormat format = new TypeRecordFormat();
        format.currentTypeIndex = dataMgr.getCurrentTypeIndex();
        format.typeNames = dataMgr.getAllTypeName();
        return format;
    }

    public boolean checkHaveSavedData() {
        return dataMgr.checkHaveSavedData();
    }

    private void updateView() {
        updateUsingItems();
        updateExcludeView();
    }

    private void updateUsingItems() {
        String[] usingItems = null;
        usingItems = dataMgr.getUsingItems();
        resultDisplayer.displayUsingItem(usingItems);
    }

    private void updateExcludeView() {
        String[] unusedItems = null;
        unusedItems = dataMgr.getUnusedItems();
        resultDisplayer.displayExcludeItem(unusedItems);
    }
}
