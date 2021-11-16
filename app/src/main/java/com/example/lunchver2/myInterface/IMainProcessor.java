package com.example.lunchver2.myInterface;

import android.content.SharedPreferences;

import com.example.lunchver2.controller.Processor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface IMainProcessor {
    Processor registerDisplayer(IRandomItemDisplayer resultDisplayer);
    void saveData(SharedPreferences sharedPreferences) throws JsonProcessingException;
    void deleteData(SharedPreferences  sharedPreferences);
    void initSavedData(SharedPreferences sharedPreferences);
}
