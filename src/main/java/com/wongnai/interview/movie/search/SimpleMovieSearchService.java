package com.wongnai.interview.movie.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.wongnai.interview.movie.external.MovieData;
import com.wongnai.interview.movie.external.MoviesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.MovieSearchService;
import com.wongnai.interview.movie.external.MovieDataService;

@Component("simpleMovieSearchService")
public class SimpleMovieSearchService implements MovieSearchService {
    @Autowired
    private MovieDataService movieDataService;

    @Override
    public List<Movie> search(String queryText) {
        MoviesResponse movies = movieDataService.fetchAll();

        List<Movie> result = new ArrayList();
        for (MovieData movie : movies) {
            // Get lowercase words
            List<String> words = Arrays.asList(movie.getTitle().split(" "));
            for (int i = 0; i < words.size(); i++) {
                words.set(i, words.get(i).toLowerCase());
            }

            if (words.contains(queryText.toLowerCase())) {
                Movie newMovie = new Movie(movie.getTitle());
                newMovie.setActors(movie.getCast());
                result.add(newMovie);
            }
        }
        return result;
    }
}
