package com.example.lunchver2.myInterface;

public interface IRandomItemDisplayer extends IResultDisplayer{
    void displayUsingItem(String[] items);
    void displayExcludeItem(String[] items);
    void addTypeToSpinner(String typeName);
}
