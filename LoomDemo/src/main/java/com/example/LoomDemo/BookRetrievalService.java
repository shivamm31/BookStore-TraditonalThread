package com.example.LoomDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class BookRetrievalService {

    @Value("#{${book.store.baseUrls}}")
    private Map<String, String> storeUrlMap;

    @Autowired
    private RestCallStatistics restCallStatistics;

    private RestTemplate restTemplate = new RestTemplate();

    public List<Book> getBookFromAllStores(String bookName) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(storeUrlMap.size());
        List<Book> books = new ArrayList<>();

        try {
            for (Map.Entry<String, String> entry : storeUrlMap.entrySet()) {
                String name = entry.getKey();
                String url = entry.getValue();
                executorService.execute(() -> {
                    long start = System.currentTimeMillis();
                    Book book = getBookFromStore(name, url, bookName);
                    long end = System.currentTimeMillis();
                    restCallStatistics.addTiming(name, end - start); // Collect timing data
                    synchronized (books) {
                        books.add(book);
                    }
                });
            }

            executorService.shutdown();
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } finally {
            executorService.shutdownNow();
        }

        return books;
    }

    private Book getBookFromStore(String storeName, String url, String bookName) {
        try {
            return restTemplate.getForObject(url + "/store/book?name={name}", Book.class, bookName);
        } catch (RestClientException e) {
            e.printStackTrace();
            return null;
        }
    }
}
