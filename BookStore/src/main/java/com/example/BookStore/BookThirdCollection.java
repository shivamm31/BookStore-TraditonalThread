package com.example.BookStore;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Profile("BOOKTHIRD")
public class BookThirdCollection implements BookCollection {

    private static final long serialVersionUID = -4133976058136487131L;

    @Value("${book.store.name}")
    private String storeName;

    private final ArrayList<Book> books = new ArrayList<>();

    @PostConstruct
    public void initialize() {

        books.add(new Book(storeName, "And Then There Were None", "Agatha Christie", 10));
        books.add(new Book(storeName, "A Study in Scarlet", "Arthur Conan Doyle", 10));
        books.add(new Book(storeName, "The Day of the Jackal", "Frederick Forsyth", 11));
        books.add(new Book(storeName, "The Wisdom of Father Brown", "G.K. Chesterton", 7));
        books.add(new Book(storeName, "The Poet", "Michael Connelly", 19));

    }

    public Book findBook(String name) {

        return books.stream()
                .filter(b -> b.bookName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow();
    }

}