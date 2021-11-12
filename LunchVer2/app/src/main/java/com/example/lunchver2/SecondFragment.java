package com.example.lunchver2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.lunchver2.aniObject.DisplayAni;
import com.example.lunchver2.controller.Processor;
import com.example.lunchver2.databinding.FragmentSecondBinding;
import com.example.lunchver2.myInterface.IPickSystemProcessor;
import com.example.lunchver2.myInterface.IResultDisplayer;

public class SecondFragment extends Fragment implements IResultDisplayer {

    private FragmentSecondBinding binding;
    private IPickSystemProcessor processor = null;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        processor = Processor.getMainProcessor().registerDisplayer(this);
        initStartRandomBtn();
        initToRandomItemBtn();
    }

    private void initStartRandomBtn()
    {
        binding.startRandomNumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkHaveInput(binding.num1Edit) && checkHaveInput(binding.num2Edit))
                {
                    int num1 = Integer.parseInt(binding.num1Edit.getText().toString());
                    int num2 = Integer.parseInt(binding.num2Edit.getText().toString());
                    processor.pickNum(num1,num2);
                }else
                {
                    showNumEmptyMsg();
                }
            }
        });
    }

    private void showNumEmptyMsg()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setMessage("數字不能為空的");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    private boolean checkHaveInput(EditText editText)
    {
        String result = editText.getText().toString();
        return result != null && !result.isEmpty();
    }

    private void initToRandomItemBtn()
    {
        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void displayUsingItem(String[] items) {

    }

    @Override
    public void displayExcludeItem(String[] items) {

    }

    @Override
    public void displayPickResult(String[] results) {
        Handler aniHandler = new Handler();
        Runnable aniRunnable = new DisplayAni(results,aniHandler, binding.numResultText);
        aniHandler.postDelayed(aniRunnable, DisplayAni.frameSpacing);
    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void addTypeToSpinner(String typeName) {

    }
}