package com.example.lunchver2.structObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

public class DialogBuilder {
    private EditText editText = null;
    private AlertDialog.Builder builder = null;

    public DialogBuilder(String message, Context context)
    {
        this("",message,context);
    }

    public DialogBuilder(String title, String message, Context context)
    {
        builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
    }

    public String getInputText()
    {
        String result = "";
        if (editText != null)
        {
            result = editText.getText().toString();
        }
        return result;
    }

    public DialogBuilder setPositiveButton(String btnTxt, DialogInterface.OnClickListener clickListener)
    {
        builder.setPositiveButton(btnTxt,clickListener);
        return this;
    }

    public DialogBuilder setNegativeButton(String btnTxt, DialogInterface.OnClickListener clickListener)
    {
        builder.setNegativeButton(btnTxt,clickListener);
        return this;
    }

    public DialogBuilder setNeutralButton(String btnTxt, DialogInterface.OnClickListener clickListener)
    {
        builder.setNeutralButton(btnTxt,clickListener);
        return  this;
    }

    public DialogBuilder addEditText(Context context)
    {
        editText = new EditText(context);
        builder.setView(editText);
        return this;
    }

    public DialogBuilder showCancelBtn()
    {
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        return this;
    }

    public void show()
    {
        builder.create().show();
    }
}
