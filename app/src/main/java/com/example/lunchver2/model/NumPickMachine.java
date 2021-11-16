package com.example.lunchver2.model;

import com.example.lunchver2.general.Rule;

public class NumPickMachine {

    public String[] pickNum(int num1, int num2) {
        String[] result = null;
        if (num1 == num2) {
            result = new String[]{Integer.toString(num1)};
        } else {
            int minNum = getMinNum(num1, num2);
            int maxNum = getMaxNum(num1, num2);
            int count = maxNum - minNum + 1;
            int temp = 0;
            result = new String[Rule.pickCount];

            for (int i = 0; i < Rule.pickCount; ++i) {
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
