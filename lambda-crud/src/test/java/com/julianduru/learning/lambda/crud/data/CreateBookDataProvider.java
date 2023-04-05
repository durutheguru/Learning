package com.julianduru.learning.lambda.crud.data;

import com.julianduru.learning.lambda.crud.dto.CreateBookRequest;
import com.julianduru.learning.lambda.crud.repository.PublisherRepository;

import java.time.LocalDate;

/**
 * created by julian on 05/02/2023
 */
public class CreateBookDataProvider implements DataProvider<CreateBookRequest> {

    public PublisherRepository publisherRepository = new PublisherRepository();

    @Override
    public CreateBookRequest provide() {
        var bookRequest = new CreateBookRequest();

        bookRequest.setTitle(faker.book().title());
        bookRequest.setAuthor(faker.book().author());
        bookRequest.setIsbn(faker.code().isbn13());
        bookRequest.setPublishedDate(LocalDate.now());
        bookRequest.setDescription(faker.lorem().sentence());
        bookRequest.setThumbnail(faker.internet().avatar());
        bookRequest.setSmallThumbnail(faker.internet().avatar());
        bookRequest.setLanguage(faker.lorem().word());
        bookRequest.setPreviewLink(faker.internet().url());
//        bookRequest.setPublisherId(
//            publisherRepository
//                .findAll()
//                .stream()
//                .findAny()
//                .orElseThrow()
//                .getId()
//        );

        return bookRequest;
    }


}
