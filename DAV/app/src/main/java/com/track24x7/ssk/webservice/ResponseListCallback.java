package com.davians.earth.webservice;


import com.davians.earth.pojo.ResponseListPOJO;

/**
 * Programmed by Sihag.
 */

public interface ResponseListCallback<T> {
    public void onGetMsg(ResponseListPOJO<T> responseListPOJO);
}
