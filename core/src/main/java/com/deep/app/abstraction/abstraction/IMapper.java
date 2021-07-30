package com.deep.app.abstraction.abstraction;

/**
 * Created by Deepesh on 23-09-2017.
 */

public interface IMapper<T, V> {
    V toViewModel(T t);
}
