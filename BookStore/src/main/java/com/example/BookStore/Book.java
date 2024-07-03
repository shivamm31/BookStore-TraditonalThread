package com.example.BookStore;

/**
 * Represents a book 
 */
public record Book(String bookStore,
                   String bookName,
                   String author,
                   int cost) {

}
