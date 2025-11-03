package com.example.bruteforceauth.service;

import com.example.bruteforceauth.model.ImdbTop250;
import com.example.bruteforceauth.repository.ImdbTop250Repository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImdbTop250ParserService {

    private final ImdbTop250Repository top250Repository;

    public ImdbTop250ParserService(ImdbTop250Repository top250Repository) {
        this.top250Repository = top250Repository;
    }

    public List<ImdbTop250> fetchAndSaveTop250() throws Exception {
        Document doc = Jsoup.connect("https://www.imdb.com/search/title/?groups=top_250&sort=user_rating,desc")
                .userAgent("Mozilla/5.0")
                .get();

        Elements rows = doc.select("li.ipc-metadata-list-summary-item");

        List<ImdbTop250> saved = new ArrayList<>();
        int rank = 1;

        System.out.println("[");

        for (Element row : rows) {
            try {
                // Название
                Element titleElement = row.selectFirst("h3.ipc-title__text");
                if (titleElement == null) titleElement = row.selectFirst("h3"); // на всякий случай
                if (titleElement == null) {
                    continue;
                }
                String title = titleElement.text();

                // Год — первый span с классом dli-title-metadata-item
                Elements yearElements = row.select("span.dli-title-metadata-item");
                if (yearElements.isEmpty()) {
                    continue;
                }
                String year = yearElements.get(0).text();

                // Рейтинг
                Element ratingElement = row.selectFirst("span.ipc-rating-star--rating");
                if (ratingElement == null) {
                    continue;
                }
                String rating = ratingElement.text();

                if (!top250Repository.existsByTitleAndYear(title, year)) {
                    ImdbTop250 movie = new ImdbTop250(rank, title, year, rating);
                    top250Repository.save(movie);
                    saved.add(movie);

                    System.out.printf("  {\"rank\": %d, \"title\": \"%s\", \"year\": \"%s\", \"rating\": \"%s\"},\n",
                            movie.getRank(), movie.getTitle(), movie.getYear(), movie.getRating());
                }

                rank++;
            } catch (Exception e) {
                System.err.println("❌ Parse err: " + e.getMessage());
            }
        }

        System.out.println("{}");
        System.out.println("]");

        return saved;
    }

    public List<ImdbTop250> getAllMovies() {
        return top250Repository.findAll();
    }
}