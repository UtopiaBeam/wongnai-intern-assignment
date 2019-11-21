package com.wongnai.interview.movie.sync;

import javax.transaction.Transactional;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.external.MovieData;
import com.wongnai.interview.movie.external.MoviesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.MovieRepository;
import com.wongnai.interview.movie.external.MovieDataService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Component
public class MovieDataSynchronizer {
	@Autowired
	private MovieDataService movieDataService;

	@Autowired
	private MovieRepository movieRepository;

	public static HashMap<String, HashSet<Long>> cache = new HashMap<>();

	@Transactional
	public void forceSync() {
		if (movieRepository.count() > 0)		return;
		MoviesResponse response = movieDataService.fetchAll();
		List<Movie> movies = new ArrayList();
		for (MovieData m: response) {
			Movie movie = new Movie(m.getTitle(), m.getCast());
			movies.add(movie);
		}
		movieRepository.saveAll(movies);

		for (Movie movie: movieRepository.findAll()) {
			String[] words = movie.getName().toLowerCase().split(" ");
			for (String key: words) {
				if (cache.get(key) == null) {
					cache.put(key, new HashSet<>());
				}
				cache.get(key).add(movie.getId());
			}
		}
	}
}
