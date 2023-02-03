package com.julianduru.learning.lambda.crud.data;

import com.julianduru.learning.lambda.crud.models.Publisher;

/**
 * created by julian on 03/02/2023
 */
public class PublisherDataProvider implements DataProvider<Publisher> {


    @Override
    public Publisher provide() {
        var publisher = new Publisher();

        publisher.setName(faker.name().fullName() + " Publishing House");
        publisher.setAddress(faker.address().fullAddress());

        return publisher;
    }


}
