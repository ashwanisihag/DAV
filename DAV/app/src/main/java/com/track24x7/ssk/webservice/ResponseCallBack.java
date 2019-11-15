package com.davians.earth.webservice;


import com.davians.earth.pojo.ResponsePOJO;

/**
 * Programmed by Sihag.
 */

public interface ResponseCallBack<T> {
    public void onGetMsg(ResponsePOJO<T> responsePOJO);
}
