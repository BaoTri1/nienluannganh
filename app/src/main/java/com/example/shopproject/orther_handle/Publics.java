package com.example.shopproject.orther_handle;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;

import com.example.shopproject.R;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Publics {

    public static final String PAYMENT_NOMAL = "Thanh toán khi nhận hàng";
    public static final String PAYMENT_ONLINE = "Thanh toán bằng thẻ tín dụng";

    public static SharedPreferences PreLoginInfor;
    public static final String LOGININFOR_NAME = "LoginInfor";

    public static boolean isNetWorkAvaliable(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager == null)
            return false;

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            Network network = connectivityManager.getActiveNetwork();
            if(network == null){
                return true;
            }

            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
            return networkCapabilities != null && networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
        }
        else {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
    }

    public static void SaveToken(Context mContext, String token) {
        PreLoginInfor = mContext.getSharedPreferences(LOGININFOR_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = PreLoginInfor.edit();
        editor.putString("Token", token);
        editor.apply();
    }

    public static String GetToken(Context mContext){
        PreLoginInfor = mContext.getSharedPreferences(LOGININFOR_NAME, Context.MODE_PRIVATE);
        return "Bearer " + PreLoginInfor.getString("Token", "");
    }

    public static boolean isNameVaild(String name){
        Pattern pattern = Pattern.compile("^[a-zA-Z-]{3,16}$");
        return pattern.matcher(name).matches();
    }

    //"2023-04-18T15:07:11.395Z"
    public static String formatDate(String date){
        return date.substring(8, 10) + "/" + date.substring(5, 7) + "/" + date.substring(0, 4);
    }

    public static String formatGia(int gia){
        Locale localevn = new Locale("vn", "VN");
        NumberFormat format = NumberFormat.getCurrencyInstance(localevn);
        return format.format(gia);
    }

    public static int getImgRateforId(float rate){
        if(rate == 1.0f){
            return R.mipmap.motsao;
        }else if(rate > 1.0f && rate < 2.0f){
            return R.mipmap.lonhon_motsao;
        }else if(rate == 2.0f){
            return R.mipmap.haisao;
        }else if(rate > 2.0f &&rate < 3.0f){
            return R.mipmap.lonhon_haisao;
        }else if(rate == 3.0f){
            return R.mipmap.basao;
        }else if(rate > 3.0f &&rate < 4.0f){
            return R.mipmap.lonhon_basao;
        }else if(rate == 4.0f){
            return R.mipmap.bonsao;
        }else if(rate > 4.0f && rate < 5.0f){
            return R.mipmap.lonhon_bonsao;
        }else {
            return R.mipmap.namsao;
        }
    }

}
