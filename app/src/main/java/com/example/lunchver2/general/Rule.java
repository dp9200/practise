package com.example.lunchver2.general;

public class Rule {
    public enum EPickType
    {
        Defult,
        PickNum,
        PickItem
    }

    public static final int pickCount = 10;
    public static final String saveKey = "item_data";
    public static final String emptyData = "NoData";
    public static final String emptyItemMsg = "沒有可抽的項目";
    public static final String repeatType = "已經有該類別了";
    public static final String typeError = "錯誤，沒有該類";
    public static final String typeNotSave = "此種類尚未儲存";
    public static final String apiErrorMsg = "失敗：%1$s ， Msg：%2$s";
    public static final String apiSuccessMsg = "成功";
    public static final String dataSplitSing = ";";
}
