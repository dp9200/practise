package com.example.lunchver2.controller;

import android.content.SharedPreferences;

import com.example.lunchver2.general.Tools;
import com.example.lunchver2.model.ClientMgr;
import com.example.lunchver2.model.DataMgr;
import com.example.lunchver2.model.PickMachine;
import com.example.lunchver2.myInterface.IPickSystemProcessor;
import com.example.lunchver2.myInterface.IResultDisplayer;
import com.example.lunchver2.myInterface.IMainProcessor;
import com.example.lunchver2.structObject.SaveFormat;
import com.example.lunchver2.structObject.TypeRecordFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Processor implements IPickSystemProcessor {

    private IResultDisplayer resultDisplayer = null;
    private DataMgr dataMgr = null;
    private PickMachine pickMachine = null;
    private ClientMgr clientMgr = null;

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
        clientMgr = new ClientMgr();
    }

    public void initSavedData(SharedPreferences sharedPreferences)  {
        try {
            String json = sharedPreferences.getString("item_data", "沒有資料");
            if (!json.equals("沒有資料")) {
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
        editor.putString("item_data", dataJson);
        editor.apply();
    }

    @Override
    public void deleteData(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    @Override
    public IPickSystemProcessor registerDisplayer(IResultDisplayer resultDisplayer) {
        this.resultDisplayer = resultDisplayer;
        return this;
    }

    @Override
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

    @Override
    public void excludeItem(String item) {
        if (!dataMgr.checkHaveItem(item)) {
            return;
        }

        dataMgr.putItemToUnused(item);
        updateView();
    }

    @Override
    public void recycleItem(String item) {
        if (!dataMgr.checkItemIsExclude(item)) {
            return;
        }

        dataMgr.putItemToUsing(item);
        updateView();
    }

    @Override
    public void removeItem(String item) {
        if (!dataMgr.checkItemIsExclude(item)) {
            return;
        }

        dataMgr.removeExcludeItem(item);
        updateView();
    }

    @Override
    public void clearItem() {
        dataMgr.clearUnusedItem();
        updateExcludeView();
    }

    @Override
    public void pickItem() {
        if (dataMgr.checkUsingItemIsEmpty())
        {
            resultDisplayer.showMsg("沒有可抽的項目");
            return;
        }
        String[] usingItem = dataMgr.getUsingItems();
        String[] result = pickMachine.pickItem(usingItem);
        uploadResult(result,"自選");
        resultDisplayer.displayPickResult(result);
    }

    @Override
    public void pickNum(int num1, int num2) {
        String[] result = pickMachine.pickNum(num1, num2);
        uploadResult(result,"抽數字");
        resultDisplayer.displayPickResult(result);
    }

    private void uploadResult(String[] result,String type)
    {
        int lastIndex = result.length-1;
        clientMgr.sendResult(result[lastIndex],type);
    }

    @Override
    public void addType(String typeName, int index) {
        if (dataMgr.checkHaveType(typeName)) {
            resultDisplayer.showMsg("已經有該類別了");
        } else {
            dataMgr.addNewType(typeName, index);
            resultDisplayer.addTypeToSpinner(typeName);
        }
    }

    @Override
    public void selectType(int index) {
        if (dataMgr.checkHaveType(index)) {
            dataMgr.changeType(index);
            updateView();
        } else {
            resultDisplayer.showMsg("錯誤，沒有該類");
        }
    }

    @Override
    public void removeCurrentType() {
        if (dataMgr.checkIsEmptyType()) {
            resultDisplayer.showMsg("此種類尚未儲存");
        } else {
            dataMgr.removeCurrentType();
        }
    }

    @Override
    public TypeRecordFormat getRecordFormat() {
        TypeRecordFormat format = new TypeRecordFormat();
        format.currentTypeIndex = dataMgr.getCurrentTypeIndex();
        format.typeNames = dataMgr.getAllTypeName();
        return format;
    }

    @Override
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
