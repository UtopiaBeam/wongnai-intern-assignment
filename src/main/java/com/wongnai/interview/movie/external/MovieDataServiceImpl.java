package com.wongnai.interview.movie.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
public class MovieDataServiceImpl implements MovieDataService {
    public static final String MOVIE_DATA_URL
            = "https://raw.githubusercontent.com/prust/wikipedia-movie-data/master/movies.json";

    @Autowired
    private RestOperations restTemplate = new RestTemplate();

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public MoviesResponse fetchAll() {
        // TODO:
        // Step 1 => Implement this method to download data from MOVIE_DATA_URL and fix any error you may found.
        // Please noted that you must only read data remotely and only from given source,
        // do not download and use local file or put the file anywhere else.

		// Add message converter
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_PLAIN));
		((RestTemplate) restTemplate).getMessageConverters().add(converter);

		try {
			MoviesResponse response = restTemplate.getForObject(MOVIE_DATA_URL, MoviesResponse.class);
			return response;
		} catch (Exception e) {
			return null;
		}
    }
}
