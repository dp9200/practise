package com.example.lunchver2;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.lunchver2.aniObject.DisplayAni;
import com.example.lunchver2.controller.Processor;
import com.example.lunchver2.databinding.FragmentFirstBinding;
import com.example.lunchver2.myInterface.IRandomItemDisplayer;
import com.example.lunchver2.myInterface.IResultDisplayer;
import com.example.lunchver2.structObject.DialogBuilder;
import com.example.lunchver2.structObject.TypeRecordFormat;

import java.util.ArrayList;
import java.util.Arrays;

public class FirstFragment extends Fragment implements IRandomItemDisplayer {

    private FragmentFirstBinding binding;
    private Processor processor = null;
    private Context context = null;
    private DisplayAni displayAni = null;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        processor = Processor.getMainProcessor().registerDisplayer(this);
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        context = this.getActivity();
        initView();
    }

    private void initView() {
        initListView();
        initSpinner();
        initAddTypeBtn();
        initAddItemBtn();
        initClearItemBtn();
        initPickBtn();
        initRemoveTypeBtn();
        initTypeIfHaveSaved();
    }

    private void initListView() {
        ArrayAdapter unusiedAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1);
        ArrayAdapter usingAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1);
        binding.excludeList.setAdapter(unusiedAdapter);
        binding.poolList.setAdapter(usingAdapter);
        binding.poolList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                processor.excludeItem(item);
            }
        });
        binding.excludeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                String msg = String.format(getString(R.string.item_select_msg), item);
                DialogBuilder format = new DialogBuilder(getString(R.string.item_select_option),msg,context);
                format.setPositiveButton(getString(R.string.back_to_pool), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        processor.recycleItem(item);
                    }
                });
                format.setNegativeButton(getString(R.string.remove_action), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        processor.removeItem(item);
                    }
                });
                format.setNeutralButton(getString(R.string.cancel_action), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                format.show();
            }
        });
    }

    private void initSpinner() {
        ArrayAdapter adapter = new ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, new ArrayList<String>(Arrays.asList("空")));
        binding.typeSpinner.setAdapter(adapter);
        binding.typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                processor.selectType(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initAddTypeBtn() {
        binding.addTypeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddTypeDialog();
            }
        });
    }

    private void showAddTypeDialog() {
        DialogBuilder format = new DialogBuilder(getString(R.string.type_dialog_title), getString(R.string.type_dialog_msg), context);
        format.addEditText(context)
                .showCancelBtn()
                .setPositiveButton(getString(R.string.confirm_action), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addNewType(format.getInputText());
                    }
                });
        format.show();
    }

    private void addNewType(String typeName) {
        SpinnerAdapter adapter = binding.typeSpinner.getAdapter();
        int typeIndex = adapter.getCount();

        processor.addType(typeName, typeIndex);
        binding.typeSpinner.setSelection(typeIndex);
    }

    private void initAddItemBtn() {
        binding.addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddItemDialog();
            }
        });
    }

    private void showAddItemDialog() {
        DialogBuilder format = new DialogBuilder(getString(R.string.add_dialog_title), getString(R.string.add_dialog_msg), context);
        format.addEditText(context)
                .showCancelBtn()
                .setPositiveButton(getString(R.string.confirm_action), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        processor.addNewItem(format.getInputText());
                    }
                });
        format.show();
    }

    private void initClearItemBtn() {

        binding.clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogBuilder format = new DialogBuilder(getString(R.string.remove_item_msg), context);
                format.setPositiveButton(getString(R.string.confirm_action), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        processor.clearItem();
                    }
                });
                format.setNegativeButton(getString(R.string.cancel_action), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                format.show();
            }
        });

    }

    private void initPickBtn() {
        binding.startRandomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (displayAni == null || displayAni.checkIsFinish()) {
                    processor.pickItem();
                }
            }
        });
    }

    private void initRemoveTypeBtn() {
        binding.removeTypeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processor.removeCurrentType();
                binding.typeSpinner.setSelection(0);
            }
        });
    }

    private void initTypeIfHaveSaved() {
        if (processor.checkHaveSavedData()) {
            TypeRecordFormat format = processor.getRecordFormat();
            setSavedType(format.typeNames, format.currentTypeIndex);
        }
    }

    private void setSavedType(String[] typeNames, int selectedIndex) {
        for (String s : typeNames) {
            addTypeToSpinner(s);
        }
        binding.typeSpinner.setSelection(selectedIndex);
    }

    @Override

    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void displayUsingItem(String[] items) {
        ArrayAdapter adapter = (ArrayAdapter) binding.poolList.getAdapter();
        ReplaceListViewItem(adapter, items);
    }

    @Override
    public void displayExcludeItem(String[] items) {
        ArrayAdapter adapter = (ArrayAdapter) binding.excludeList.getAdapter();
        ReplaceListViewItem(adapter, items);
    }

    @Override
    public void displayPickResult(String[] results) {
        Handler aniHandler = new Handler();
        displayAni = new DisplayAni(results, aniHandler, binding.resultText);
        aniHandler.postDelayed(displayAni, DisplayAni.frameSpacing);
    }

    @Override
    public void showMsg(String msg) {
        DialogBuilder format = new DialogBuilder(msg,context);
        format.setPositiveButton(getString(R.string.confirm_action), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        format.show();
    }

    private void ReplaceListViewItem(ArrayAdapter adapter, String[] newItems) {
        adapter.clear();
        adapter.addAll(newItems);
    }

    @Override
    public void addTypeToSpinner(String typeName) {
        Spinner spinner = binding.typeSpinner;

        ArrayAdapter tempAdapter = (ArrayAdapter) spinner.getAdapter();
        tempAdapter.add(typeName);
    }
}