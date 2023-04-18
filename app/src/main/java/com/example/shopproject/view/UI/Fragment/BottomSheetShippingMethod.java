package com.example.shopproject.view.UI.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

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

public class BottomSheetShippingMethod extends BottomSheetDialogFragment {

    private CallbackFragment callbackFragment;

    public BottomSheetShippingMethod() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callbackFragment = (CallbackFragment) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_shipping_method, null);
        dialog.setContentView(view);

        RecyclerView rcv_shippingMethod = view.findViewById(R.id.rcv_shippingMethod);
        List<ShippingMethod> list = new ArrayList<>();
        list.add(new ShippingMethod("Giao hàng nhanh", 40000, "3-5"));
        list.add(new ShippingMethod("Giao hàng tiết kiệm", 35000, "5-7"));
        ShippingMethodAdapter adapter = new ShippingMethodAdapter(getActivity(), list, new clickListener() {
            @Override
            public void onClickShippingMethod(ShippingMethod shippingMethod) {
                callbackFragment.passShippingMethod(shippingMethod);
                dialog.dismiss();
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rcv_shippingMethod.setLayoutManager(manager);
        rcv_shippingMethod.setAdapter(adapter);

        return dialog;
    }
}
