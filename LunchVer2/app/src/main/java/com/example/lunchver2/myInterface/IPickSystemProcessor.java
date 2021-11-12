package com.example.lunchver2.myInterface;

import com.example.lunchver2.structObject.TypeRecordFormat;

public interface IPickSystemProcessor extends IMainProcessor {
    void addNewItem(String value);
    void excludeItem(String item);
    void recycleItem(String item);
    void removeItem(String item);
    void clearItem();
    void pickItem();
    void pickNum(int minNum,int maxNum);
    void addType(String typeName,int index);
    void selectType(int index);
    void removeCurrentType();
    TypeRecordFormat getRecordFormat();
    boolean checkHaveSavedData();
}
