package com.example.lunchver2.myInterface;

import android.content.SharedPreferences;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface IMainProcessor {
    IPickSystemProcessor registerDisplayer(IResultDisplayer resultDisplayer);
    void saveData(SharedPreferences sharedPreferences) throws JsonProcessingException;
    void deleteData(SharedPreferences  sharedPreferences);
    void initSavedData(SharedPreferences sharedPreferences);
}
