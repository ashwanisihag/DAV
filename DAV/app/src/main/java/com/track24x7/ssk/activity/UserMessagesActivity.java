package com.davians.earth.activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.Gson;
import com.davians.earth.R;
import com.davians.earth.adapter.MessagesAdapter;
import com.davians.earth.adapter.UsersAdapter;
import com.davians.earth.app.config;
import com.davians.earth.pojo.JobsPOJO;
import com.davians.earth.pojo.MessagesPOJO;
import com.davians.earth.pojo.UserListPOJO;
import com.davians.earth.progress.ProgressHUD;
import com.davians.earth.util.Headers;
import com.davians.earth.util.Pref;
import com.davians.earth.util.StringUtils;
import com.davians.earth.util.TagUtils;
import com.davians.earth.util.ToastClass;
import com.davians.earth.util.UtilFunction;
import com.davians.earth.webservice.WebServicesUrls;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserMessagesActivity extends BaseMenuActivity {

    @BindView(R.id.rv_messages)
    RecyclerView rv_messages;
    private InterstitialAd mInterstitialAd;
    ProgressHUD mProgressHUD;

    public void alert(String str) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Alert!").setMessage(str).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_messages);
        ButterKnife.bind(this);
        mProgressHUD = ProgressHUD.show(UserMessagesActivity.this,"Getting messages...", true,true,null);

        if( Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_PAID,false)==false) {

            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId("ca-app-pub-6593186571085283/3767867502");
            mInterstitialAd.loadAd(new AdRequest.Builder().build());

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                    }
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    // Code to be executed when an ad request fails.
                }

                @Override
                public void onAdOpened() {
                    // Code to be executed when the ad is displayed.
                }

                @Override
                public void onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                }

                @Override
                public void onAdClosed() {
                    // Code to be executed when when the interstitial ad is closed.
                    Intent  intent=new Intent(UserMessagesActivity.this,PurchaseActivity.class);
                    startActivity(intent);
                }
            });
        }
        attachAdapter();
        getMessages();
    }

    MessagesAdapter messageListAdapter;
    List<MessagesPOJO> messageStrings = new ArrayList<>();

    public void attachAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_messages.setHasFixedSize(true);
        rv_messages.setLayoutManager(linearLayoutManager);
        messageListAdapter = new MessagesAdapter(this, null, messageStrings);
        rv_messages.setAdapter(messageListAdapter);
        rv_messages.setNestedScrollingEnabled(false);
        rv_messages.setItemAnimator(new DefaultItemAnimator());
    }

    public void getMessages() {
        try {
            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest getRequest = new StringRequest(Request.Method.GET, WebServicesUrls.USER_MESSAGES,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TagUtils.getTag(), "response:-" + response.toString());
                            if(response.contains("Sequence")) {
                                alert("Response contains no elements");
                            }
                            else {
                                parseResponse(response);
                            }
                            mProgressHUD.dismiss();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            mProgressHUD.dismiss();
                            NetworkResponse response = error.networkResponse;
                            if (error instanceof ServerError && response != null) {
                                try {
                                    String res = new String(response.data,
                                            HttpHeaderParser.parseCharset(response.headers));
                                    // Now you can use any deserializer to make sense of data
                                    JSONObject obj = new JSONObject(res);
                                    ToastClass.showShortToast(getApplicationContext(), "Server error");
                                    alert("Server error");
                                } catch (Exception e1) {
                                    ToastClass.showShortToast(getApplicationContext(), e1.getMessage());
                                    // Couldn't properly decode data to string
                                    e1.printStackTrace();
                                }
                            }
                            else
                            {
                                 ToastClass.showShortToast(getApplicationContext(),"failed to connect, please try to logout and login again.");
                                alert("failed to connect, please try to logout and login again.");
                            }
                        }
                    }
            ) {


                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", Pref.GetStringPref(getApplicationContext(), StringUtils.TOKEN_TYPE, "") + " " + Pref.GetStringPref(getApplicationContext(), StringUtils.ACCESS_TOKEN, ""));
                    UtilFunction.printAllValues(headers);
                    return headers;
                }
            };
            queue.add(getRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void parseResponse(String response){
       if(response.contains("\"No records found\""))
        {
            ToastClass.showShortToast(getApplicationContext(), "No data to display");
            alert("No data to display");
        }
        try{
            messageStrings.clear();
            JSONArray jsonArray=new JSONArray(response);
            List<MessagesPOJO> messagePOJOS=new ArrayList<>();
            for(int i=0;i<jsonArray.length();i++){
                messagePOJOS.add(new Gson().fromJson(jsonArray.optJSONObject(i).toString(),MessagesPOJO.class));
            }
            if(messagePOJOS.isEmpty())
            {
                ToastClass.showShortToast(getApplicationContext(), "No data to display");
                alert("No data to display");
            }
            messageStrings.addAll(messagePOJOS);
            messageListAdapter.notifyDataSetChanged();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
