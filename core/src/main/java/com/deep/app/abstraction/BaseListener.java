package com.deep.app.abstraction;

/**
 * Created by deepesh on 19/7/17.
 */

public interface BaseListener<T> {
    void clicked(int position, T t);
}
