package com.example.shopproject.view.UI.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopproject.R;
import com.example.shopproject.mode.ShippingMethod;
import com.example.shopproject.view.UI.Fragment.callback.CallbackFragment;
import com.example.shopproject.view.adapter.ShippingMethodAdapter;
import com.example.shopproject.view.adapter.interfaceListenerAdapter.clickListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class BottomSheetPaymentMethod extends BottomSheetDialogFragment {
    private CallbackFragment callbackFragment;

    public BottomSheetPaymentMethod() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callbackFragment = (CallbackFragment) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_payment_method, null);
        dialog.setContentView(view);

        TextView btnPaymentNomal = view.findViewById(R.id.btnPaymentnomal);
        TextView btnPaymentOnline = view.findViewById(R.id.btnPaymentonline);

        btnPaymentNomal.setOnClickListener(v -> {
            callbackFragment.passPaymentMethod(btnPaymentNomal.getText().toString().trim());
            dialog.dismiss();
        });

        btnPaymentOnline.setOnClickListener(v -> {
            callbackFragment.passPaymentMethod(btnPaymentOnline.getText().toString().trim());
            dialog.dismiss();
        });

        return dialog;
    }
}
