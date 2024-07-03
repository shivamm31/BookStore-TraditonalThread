package com.example.LoomDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/virtualstore")
public class BestDealBookController {

    @Autowired
    private BookRetrievalService retrievalService;

    @Autowired
    private RestCallStatistics restCallStatistics;

    @GetMapping("/book")
    public BestDealResult getBestPriceForBook(@RequestParam String name) {
        long start = System.currentTimeMillis();

        try {
            List<Book> books = retrievalService.getBookFromAllStores(name);

            // Use reflection to access private field 'cost'
            Book bestPriceBook = books.stream()
                    .min(Comparator.comparing(book -> {
                        try {
                            Field costField = Book.class.getDeclaredField("cost");
                            costField.setAccessible(true);
                            return (int) costField.get(book);
                        } catch (Exception e) {
                            throw new RuntimeException("Exception while accessing cost field", e);
                        }
                    }))
                    .orElseThrow();

            return new BestDealResult(restCallStatistics.getTimeMap(), bestPriceBook, books);
        } catch (Exception e) {
            throw new RuntimeException("Exception while calling getBestPrice", e);
        } finally {
            long end = System.currentTimeMillis();
            restCallStatistics.addTiming("Best deal Store", end - start);
        }
    }
}
