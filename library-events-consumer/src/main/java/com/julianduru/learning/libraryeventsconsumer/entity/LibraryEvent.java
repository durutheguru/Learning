package com.julianduru.learning.libraryeventsconsumer.entity;

import lombok.*;

import javax.persistence.*;

/**
 * created by julian on 06/11/2022
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class LibraryEvent {


    @Id
    @GeneratedValue
    private Integer libraryEventId;

    @Enumerated(EnumType.STRING)
    private LibraryEventType eventType;

    @OneToOne(mappedBy = "libraryEvent", cascade = {CascadeType.ALL})
    @ToString.Exclude
    private Book book;


}

