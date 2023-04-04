package com.example.shopproject.view.UI.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopproject.R;
import com.example.shopproject.mode.Color;
import com.example.shopproject.mode.Items;
import com.example.shopproject.mode.Product;
import com.example.shopproject.mode.Size;
import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.presenter.BottomSheetPresenter;
import com.example.shopproject.view.BottomSheetView;
import com.example.shopproject.view.UI.Fragment.callback.CallbackFragment;
import com.example.shopproject.view.adapter.ColorAdapter;
import com.example.shopproject.view.adapter.SizeAdapter;
import com.example.shopproject.view.adapter.interfaceListenerAdapter.clickListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.stfalcon.imageviewer.StfalconImageViewer;
import com.stfalcon.imageviewer.loader.ImageLoader;

import java.util.Collections;
import java.util.List;

public class BottomSheetProductFragment extends BottomSheetDialogFragment implements BottomSheetView {

    private String slug;
    private String typeButton;
    private Context context;
    private String colorName;
    private int indexColor;
    private String sizeName;
    private int indexSize;
    private int NumProduct;
    private ImageView imgProduct;
    private TextView txtNameProduct;
    private TextView txtPriceProduct;
    private TextView txtColor;
    private TextView txtSize;
    private LinearLayout layoutColor;
    private RecyclerView rcv_color;
    private ColorAdapter adapterColor;
    private RelativeLayout layoutSize;
    private RecyclerView rcv_size;
    private SizeAdapter adapterSize;
    private Button btnDecrement;
    private EditText editDisplay;
    private Button btnIncrement;
    private AppCompatButton btnAction;
    private BottomSheetPresenter bottomSheetPresenter;
    private CallbackFragment callbackFragment;

    public BottomSheetProductFragment(Context context, String slug, String typeButton) {
        this.context = context;
        this.typeButton = typeButton;
        this.slug = slug;
        callbackFragment = (CallbackFragment) getActivity();
        this.bottomSheetPresenter = new BottomSheetPresenter(context, this);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callbackFragment = (CallbackFragment) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View myView = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_select_product_layout, null);
        bottomSheetDialog.setContentView(myView);

        initView(myView);

        bottomSheetPresenter.getProduct(slug);

        editDisplay.setText("1");
        this.NumProduct = 1;
        this.colorName = "";
        this.sizeName = "";

        if(typeButton.equals("Thêm vào giỏ hàng")){
            btnAction.setText(typeButton);
            btnAction.setOnClickListener(view -> {
                int quatity = Integer.parseInt(editDisplay.getText().toString().trim());
                bottomSheetPresenter.addCart(quatity, colorName, sizeName, indexColor, indexSize);
                DisplayMessage();
                bottomSheetDialog.dismiss();
            });
        }else if(typeButton.equals("Mua ngay")){
            btnAction.setText(typeButton);
            btnAction.setOnClickListener(view -> {
                int quatity = Integer.parseInt(editDisplay.getText().toString().trim());
                Toast.makeText(getActivity(), typeButton, Toast.LENGTH_SHORT).show();
            });
            bottomSheetDialog.dismiss();
        }

        btnDecrement.setOnClickListener(view -> {
            //int quatity = Integer.parseInt(editDisplay.getText().toString().trim());
            bottomSheetPresenter.decrementQuantity(NumProduct);
        });

        btnIncrement.setOnClickListener(view -> {
            //int quatity = Integer.parseInt(editDisplay.getText().toString().trim());
            bottomSheetPresenter.incrementQuantity(slug, NumProduct, indexColor, indexSize);
        });

        imgProduct.setOnClickListener(view -> {
            bottomSheetPresenter.OpenImageViewer();
        });

        editDisplay.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                int num = Integer.parseInt(editDisplay.getText().toString().trim());
                bottomSheetPresenter.FillinQuantity(slug, num, indexColor, indexSize);
                hideSoftKeyboard();
                editDisplay.clearFocus();
                return true;
            }
        });

        return bottomSheetDialog;
    }

    private void initView(View view){
        imgProduct = view.findViewById(R.id.imgSP);
        txtNameProduct = view.findViewById(R.id.txtNameProduct_bottom);
        txtPriceProduct = view.findViewById(R.id.txtGiaSP);
        txtColor = view.findViewById(R.id.txtColor_bottom);
        txtSize = view.findViewById(R.id.txtSize_bottom);
        layoutColor = view.findViewById(R.id.layoutContain_color);
        rcv_color = view.findViewById(R.id.rcv_color);
        layoutSize = view.findViewById(R.id.layoutContain_size);
        rcv_size = view.findViewById(R.id.rcv_size);
        btnDecrement = view.findViewById(R.id.decrement_bottom);
        btnIncrement = view.findViewById(R.id.increment_bottom);
        editDisplay = view.findViewById(R.id.display_bottom);
        btnAction = view.findViewById(R.id.btnAction);
    }

    //Hàm ẩn bạn phím khi nhập xong
    private void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editDisplay.getWindowToken(), 0);
    }

    private void DisplayMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_layout, null);
        builder.setView(layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 1700);
    }

    @Override
    public void DisplayProduct(Product product) {
        Glide.with(getContext())
                .load(product.getImage())
                .placeholder(R.mipmap.aohoodie1)
                .into(imgProduct);

        txtNameProduct.setText(product.getName());
        txtPriceProduct.setText(Publics.formatGia(product.getPrice()));
    }

    @Override
    public void DisplayColorsAndSize(List<Color> colors) {
        indexColor = -1;
        indexSize = -1;
        if(colors.isEmpty() || colors == null){
            layoutColor.setVisibility(View.GONE);
            layoutSize.setVisibility(View.GONE);
            return;
        }
        adapterColor = new ColorAdapter(getActivity(), colors, new clickListener() {
            @Override
            public void itemColorClick(int index, Color color) {
                indexColor = index;
                colorName= color.getName();
                txtColor.setText("Màu sắc: " + color.getName());
                if(color.getSize().isEmpty() || color.getSize() == null){
                    layoutSize.setVisibility(View.GONE);
                    return;
                }
                adapterSize = new SizeAdapter(getActivity(), color.getSize(), new clickListener() {
                    @Override
                    public void itemSizeClick(int index, Size size) {
                        indexSize = index;
                        sizeName = size.getName();
                        txtSize.setText("Size: " + size.getName());
                        DisplayQuantity(String.valueOf(1));
                    }

                });
                GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
                rcv_size.setLayoutManager(manager);
                rcv_size.setHasFixedSize(true);
                rcv_size.setAdapter(adapterSize);
            }
        });

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 10);
        rcv_color.setLayoutManager(manager);
        rcv_color.setHasFixedSize(true);
        rcv_color.setAdapter(adapterColor);
    }

    @Override
    public void DisplayQuantity(String quantity) {
        editDisplay.setText(quantity);
        this.NumProduct = Integer.parseInt(quantity);
    }

    @Override
    public void DisplayQuantityError(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Thông báo");
        builder.setMessage(message);

        AlertDialog dialog = builder.create();
        dialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 1500);
    }

    @Override
    public void DisplayImgageDialog(String image) {
        new StfalconImageViewer.Builder<String>(getContext(), Collections.singletonList(image), new ImageLoader<String>() {
            @Override
            public void loadImage(ImageView imageView, String image) {
                Glide.with(getActivity())
                        .load(image)
                        .placeholder(R.mipmap.anh2)
                        .into(imageView);
            }
        }).show();
    }

    @Override
    public void PassItemCart(Items items) {
        callbackFragment.AddCartSuccess(items);
    }

    @Override
    public void DisplayError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        onDestroy();
    }
}
