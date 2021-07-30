package com.deep.app.network;

/**
 * Created by Deepesh on 02-11-2017.
 */

public interface AbstractResponseListener<T> {

    void onSuccess(T response);

    void onFailure(ErrorResponse error);
}
