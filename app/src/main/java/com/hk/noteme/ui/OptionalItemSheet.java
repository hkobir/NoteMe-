package com.hk.noteme.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.hk.noteme.R;

public class OptionalItemSheet extends BottomSheetDialogFragment {
    private String data;
    private String itemType;
    AppCompatImageView icon;
    EditText itemContent;

    public OptionalItemSheet() {
    }

    public OptionalItemSheet(String data, String itemType) {
        this.data = data;
        this.itemType = itemType;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.option_item_sheet, container);
        icon = view.findViewById(R.id.optionalItemIV);
        itemContent = view.findViewById(R.id.optionalItemET);

        if (itemType.equals("mail")) {
            icon.setImageResource(R.drawable.ic_email);
        } else if (itemType.equals("url")) {
            icon.setImageResource(R.drawable.ic_url);
        } else {
            icon.setImageResource(R.drawable.ic_phone);
        }

        //set content
        if (data.equals("null")) {
            itemContent.setText("N/A");
        } else {
            itemContent.setText(data);
        }
        return view;
    }


}
