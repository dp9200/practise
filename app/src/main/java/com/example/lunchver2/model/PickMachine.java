package com.example.lunchver2.model;

import java.util.ArrayList;

public class PickMachine {

    private static final int pickCount = 10;

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
        String[] result = new String[pickCount];

        while (tempItemList.size() < pickCount) {
            randomIndex = (int) (Math.random() * itemCount);
            if (tempItemList.size() == 0 || !tempItemList.get(lastIndex).equals(items[randomIndex])) {
                tempItemList.add(items[randomIndex]);
                lastIndex = tempItemList.size() - 1;
            }
        }

        return tempItemList.toArray(result);
    }

    public String[] pickNum(int num1, int num2) {
        String[] result = null;
        if (num1 == num2) {
            result = new String[]{Integer.toString(num1)};
        } else {
            int minNum = getMinNum(num1, num2);
            int maxNum = getMaxNum(num1, num2);
            int count = maxNum - minNum + 1;
            int temp = 0;
            result = new String[pickCount];

            for (int i = 0; i < pickCount; ++i) {
                temp = (int) ((Math.random() * count) + minNum);
                result[i] = Integer.toString(temp);
            }
        }
        return result;
    }

    private int getMinNum(int num1, int num2) {
        int result = 0;
        if (num1 > num2) {
            result = num2;
        } else {
            result = num1;
        }

        return result;
    }

    private int getMaxNum(int num1, int num2) {
        int result = 0;
        if (num1 > num2) {
            result = num1;
        } else {
            result = num2;
        }

        return result;
    }
}
