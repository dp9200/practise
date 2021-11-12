package com.example.lunchver2.myInterface;

public interface IResultDisplayer {
    void displayUsingItem(String[] items);
    void displayExcludeItem(String[] items);
    void displayPickResult(String[] results);
    void showMsg(String msg);
    void addTypeToSpinner(String typeName);
}
