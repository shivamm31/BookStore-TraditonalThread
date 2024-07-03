package com.example.BookStore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/store")
public class BookStoreController {

    @Autowired
    private BookCollection bookColn;

    @GetMapping("/book")
    public Book getBookByName(@RequestParam String name) {

        // a delay of 5 secs for testing
        delayOf5Secs();

        return bookColn.findBook(name);
    }

    private void delayOf5Secs() {
        try {
            Thread.sleep(5000); // Traditional thread sleep with milliseconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}