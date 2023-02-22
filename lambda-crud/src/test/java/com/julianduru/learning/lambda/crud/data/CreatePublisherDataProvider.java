package com.julianduru.learning.lambda.crud.data;

import com.julianduru.learning.lambda.crud.dto.CreatePublisherRequest;

/**
 * created by julian on 05/02/2023
 */
public class CreatePublisherDataProvider implements DataProvider<CreatePublisherRequest> {


    @Override
    public CreatePublisherRequest provide() {
        var publisherRequest = new CreatePublisherRequest();

        publisherRequest.setName(faker.name().fullName() + " Publisher");
        publisherRequest.setAddress(faker.address().streetAddress());
        publisherRequest.setCity(faker.address().city());
        publisherRequest.setState(faker.address().state());
        publisherRequest.setZip(faker.address().zipCode());
        publisherRequest.setCountry(faker.address().country());
        publisherRequest.setPhone(faker.phoneNumber().phoneNumber());
        publisherRequest.setFax(faker.phoneNumber().cellPhone());
        publisherRequest.setEmail(faker.internet().emailAddress());
        publisherRequest.setWebsite(faker.internet().url());
        publisherRequest.setLogo(faker.internet().avatar());
        publisherRequest.setDescription(faker.lorem().sentence());
        publisherRequest.setNotes(faker.lorem().paragraph());

        return publisherRequest;
    }



}
