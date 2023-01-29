package com.julianduru.learning.libraryeventsconsumer.jpa;

import com.julianduru.learning.libraryeventsconsumer.entity.LibraryEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * created by julian on 15/11/2022
 */
@Repository
public interface LibraryEventsRepository extends CrudRepository<LibraryEvent, Integer> {



}
