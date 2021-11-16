package com.example.lunchver2.controller;

import com.example.lunchver2.general.Rule;
import com.example.lunchver2.model.ClientMgr;
import com.example.lunchver2.model.NumPickMachine;
import com.example.lunchver2.myInterface.IResultDisplayer;

public class RandomNumProcessor {

    private NumPickMachine pickMachine = null;
    private IResultDisplayer resultDisplayer = null;

    public RandomNumProcessor(IResultDisplayer resultDisplayer)
    {
        pickMachine = new NumPickMachine();
        this.resultDisplayer = resultDisplayer;
    }

    public void pickNum(int num1, int num2) {
        String[] result = pickMachine.pickNum(num1, num2);
        String pickType = Rule.EPickType.PickNum.toString();
        uploadResult(result, pickType);
        resultDisplayer.displayPickResult(result);
    }

    private void uploadResult(String[] result,String type)
    {
        int lastIndex = result.length-1;
        ClientMgr.getInstance().sendResult(result[lastIndex],type);
    }
}
