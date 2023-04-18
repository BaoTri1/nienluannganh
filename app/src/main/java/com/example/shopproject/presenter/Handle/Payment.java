package com.example.shopproject.presenter.Handle;

import android.content.Context;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;
import com.stripe.android.paymentsheet.PaymentSheetResultCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Payment {

    private String Publishablekey = "pk_test_51MvsGiJt8JECIDWIKv3yyWHydmbxUc0dhnIOM4LLDVG1BIoIjf8HLel9Bg4Wzy1Mrg8Ze4o7MCQOwdStEhKW1DZZ00fXE9rN9m";
    private String Secretkey = "sk_test_51MvsGiJt8JECIDWIRfaw3ZzEYX19cAJRZ29xfftrPbT9BddaGxEtS7HysPBwH7KI54h2rU6AVEsJjVvOmhkOrbs700NqRrz2n9";
    private String customerId;
    private String EphericalKey;
    private String ClientSecret;
    private PaymentSheet paymentSheet;
    private Context context;
    private CallbackPayment callbackPayment;

    public Payment(Context context, CallbackPayment callbackPayment) {
        this.context = context;
        this.callbackPayment = callbackPayment;
        PaymentConfiguration.init(this.context, Publishablekey);
        this.paymentSheet = new PaymentSheet((ComponentActivity) this.context, new PaymentSheetResultCallback() {
            @Override
            public void onPaymentSheetResult(@NonNull PaymentSheetResult paymentSheetResult) {
                if(paymentSheetResult instanceof PaymentSheetResult.Completed){
                    //Toast.makeText(Payment.this.context, "Payment Success", Toast.LENGTH_SHORT).show();
                    callbackPayment.PaymentByCardSuccess("Thanh toán thành công.");
                }else {
                    callbackPayment.PaymentByCardFailue("Thanh toán thất bại.");
                }
            }
        });
    }

    public void paymentFlow() {
        PaymentSheet.Configuration configuration = new PaymentSheet.Configuration("Example, Inc.");
        paymentSheet.presentWithPaymentIntent(ClientSecret, configuration);
    }

    public void createCustomer(int price){
        StringRequest request = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/customers",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);

                            customerId = object.getString("id");

                            Toast.makeText(context, customerId, Toast.LENGTH_SHORT).show();

                            getEmphericalKey(price);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + Secretkey);


                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

    private void getEmphericalKey(int price) {
        StringRequest request = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/ephemeral_keys",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);

                            EphericalKey = object.getString("id");

                            Toast.makeText(context, customerId, Toast.LENGTH_SHORT).show();

                            getClientSecret(customerId, EphericalKey, price);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + Secretkey);
                headers.put("Stripe-Version", "2022-11-15");

                return headers;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("customer", customerId);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);

    }

    private void getClientSecret(String customerId, String ephericalKey, int price) {
        StringRequest request = new StringRequest(Request.Method.POST, "https://api.stripe.com/v1/payment_intents",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);

                            ClientSecret = object.getString("client_secret");
                            Toast.makeText(context, ClientSecret, Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + Secretkey);

                return headers;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("customer", customerId);
                params.put("amount", String.valueOf(price));
                params.put("currency", "VND");
                params.put("automatic_payment_methods[enabled]", "true");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
}