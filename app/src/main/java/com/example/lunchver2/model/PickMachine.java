package com.example.lunchver2.model;

import com.example.lunchver2.general.Rule;

import java.util.ArrayList;

public class PickMachine {


    public String[] pickItem(String[] items) {
        String[] result = null;
        if (items.length > 1) {
            result = getRandomItem(items);
        } else {
            result = items;
        }

        return result;
    }

    private String[] getRandomItem(String[] items) {
        int itemCount = items.length;
        int randomIndex = 0;
        int lastIndex = 0;
        ArrayList<String> tempItemList = new ArrayList<>();
        String[] result = new String[Rule.pickCount];

        while (tempItemList.size() < Rule.pickCount) {
            randomIndex = (int) (Math.random() * itemCount);
            if (tempItemList.size() == 0 || !tempItemList.get(lastIndex).equals(items[randomIndex])) {
                tempItemList.add(items[randomIndex]);
                lastIndex = tempItemList.size() - 1;
            }
        }

        return tempItemList.toArray(result);
    }
}
