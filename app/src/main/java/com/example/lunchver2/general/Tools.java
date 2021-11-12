package com.example.lunchver2.general;

import android.app.AlertDialog;
import android.content.Context;

import com.example.lunchver2.structObject.DialogBuilder;

public class Tools {

    /// <summary>
    /// 將字串中的全形字元轉換為半形
    /// </summary>
    public static String ToBj(String s)
    {
        if (s == null || s.trim().isEmpty()) return s;

        StringBuilder sb = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++)
        {
            if (s.charAt(i) == '\u3000')
                sb.append('\u0020');
            else if (IsQjChar(s.charAt(i)))
                sb.append((char)((int)s.charAt(i) - 65248));
            else
                sb.append(s.charAt(i));
        }

        return sb.toString();
    }

    /// <summary>
    /// 判斷字元是否全形字元或標點
    /// </summary>
    /// <remarks>
    /// <para>全形字元 - 65248 = 半形字元</para>
    /// <para>全形空格例外</para>
    /// </remarks>
    private static boolean IsQjChar(char c)
    {
        if (c == '\u3000') return true;

        int i = (int)c - 65248;
        if (i < 32) return false;
        return IsBjChar((char)i);
    }

    /// <summary>
    /// 判斷字元是否英文半形字元或標點
    /// </summary>
    /// <remarks>
    /// 32    空格
    /// 33-47    標點
    /// 48-57    0~9
    /// 58-64    標點
    /// 65-90    A~Z
    /// 91-96    標點
    /// 97-122    a~z
    /// 123-126  標點
    /// </remarks>
    private static boolean IsBjChar(char c)
    {
        int i = (int)c;
        return i >= 32 && i <= 126;
    }
}
