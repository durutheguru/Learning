package com.julianduru.learning.lambda.crud.data;

import com.github.javafaker.Faker;
import com.julianduru.learning.lambda.crud.util.NullAwareBeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * created by julian on 03/02/2023
 */

public interface DataProvider<T> {

    Faker faker = new Faker();


    T provide();


    default Faker getFaker() {
        return faker;
    }


    default T provide(T sample) {
        T data = this.provide();
        NullAwareBeanUtils.copy(sample, data);
        return data;
    }


    default List<T> provide(int count) {
        List<T> list = new ArrayList();

        for(int i = 0; i < count; ++i) {
            list.add(this.provide());
        }

        return list;
    }


    default List<T> provide(T sample, int count) {
        List<T> list = new ArrayList();

        for(int i = 0; i < count; ++i) {
            list.add(this.provide(sample));
        }

        return list;
    }


}
