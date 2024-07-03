package com.example.LoomDemo;

import java.util.List;
import java.util.Map;

public record BestDealResult(Map<String, Long> timeStatistics, Book bestPriceDeal, List<Book> allDeals) {
}
