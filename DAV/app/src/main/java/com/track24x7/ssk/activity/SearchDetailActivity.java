package com.davians.earth.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.google.gson.Gson;
import com.davians.earth.R;
import com.davians.earth.adapter.SearchAdapter;
import com.davians.earth.pojo.SearchPOJO;
import com.davians.earth.progress.ProgressHUD;
import com.davians.earth.util.Pref;
import com.davians.earth.util.StringUtils;
import com.davians.earth.util.TagUtils;
import com.davians.earth.util.ToastClass;
import com.davians.earth.webservice.WebServicesUrls;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchDetailActivity extends BaseMenuActivity {
    @BindView(R.id.rv_search)
    RecyclerView rv_search;

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
        setContentView(R.layout.activity_search_detail);
        ButterKnife.bind(this);
        Bundle b = getIntent().getExtras();
        String value = b.getString("Screen");
        getSupportActionBar().setTitle(value);
        attachAdapter();
        try {
            final ProgressHUD mProgressHUD = ProgressHUD.show(SearchDetailActivity.this,"Please wait, hope you will get something....", true,true,null);
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("Profession", b.getString("Profession"));
            jsonObject.put("Batch", b.getString("Batch"));
            jsonObject.put("School",  b.getString("School"));
            jsonObject.put("RollNo",  b.getString("RollNo"));
            jsonObject.put("Department",  b.getString("Department"));
            jsonObject.put("FirstName",  b.getString("FirstName"));
            jsonObject.put("LastName",  b.getString("LastName"));
            jsonObject.put("Designation", b.getString("Designation"));
            jsonObject.put("City",b.getString("City"));
            Log.d(TagUtils.getTag(), "json Object:-" + jsonObject.toString());

            RequestQueue queue = Volley.newRequestQueue(this);

            StringRequest getRequest = new StringRequest(Request.Method.PUT, WebServicesUrls.SEARCH,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Log.d(TagUtils.getTag(), "response:-" + response.toString());
                            parseResponse(response);
                            mProgressHUD.dismiss();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            mProgressHUD.dismiss();
                            Log.d(TagUtils.getTag(), "error:-" + error.toString());
//                            error.printStackTrace();
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
                public byte[] getBody() throws AuthFailureError {
                    return jsonObject.toString().getBytes();
                }

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", Pref.GetStringPref(getApplicationContext(), StringUtils.TOKEN_TYPE, "") + " " + Pref.GetStringPref(getApplicationContext(), StringUtils.ACCESS_TOKEN, ""));
                    headers.put("Content-Type", "application/json");
//                    UtilityFunction.printAllValues(headers);
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
            userStrings.clear();
            JSONArray jsonArray=new JSONArray(response);
            List<SearchPOJO> searchByPOJOS=new ArrayList<>();
            for(int i=0;i<jsonArray.length();i++){
                searchByPOJOS.add(new Gson().fromJson(jsonArray.optJSONObject(i).toString(),SearchPOJO.class));
            }
            if(searchByPOJOS.isEmpty())
            {
                ToastClass.showShortToast(getApplicationContext(), "No records found, please check search fields");
                alert("No records found, please check search fields");
            }
            else
            {
                ToastClass.showShortToast(getApplicationContext(), "Touch member for more details");
            }
            userStrings.addAll(searchByPOJOS);
            userListAdapter.notifyDataSetChanged();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    SearchAdapter userListAdapter;
    List<SearchPOJO> userStrings = new ArrayList<>();

    public void attachAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_search.setHasFixedSize(true);
        rv_search.setLayoutManager(linearLayoutManager);
        userListAdapter = new SearchAdapter(this, null, userStrings);
        rv_search.setAdapter(userListAdapter);
        rv_search.setNestedScrollingEnabled(false);
        rv_search.setItemAnimator(new DefaultItemAnimator());
    }

}
