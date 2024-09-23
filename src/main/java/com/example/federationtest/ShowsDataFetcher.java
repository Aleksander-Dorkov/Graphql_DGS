package com.example.federationtest;


import com.example.netflixdgs.codegen.types.Actor;
import com.example.netflixdgs.codegen.types.Movie;
import com.example.netflixdgs.codegen.types.Show;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsQuery;
import org.dataloader.DataLoader;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@DgsComponent
public class ShowsDataFetcher {

    @DgsQuery
    public List<Show> shows() {
        randomNumberGenerator();
        return DummyData.shows;
    }

    @DgsQuery
    public List<Movie> movies() {
        return DummyData.movies;
    }

    @DgsData(parentType = "Show", field = "actors")
    public CompletableFuture<List<Actor>> getActors(DgsDataFetchingEnvironment dfe) {
        DataLoader<String, List<Actor>> dataLoader = dfe.getDataLoader(ActorsDataLoader.class);
        Show show = dfe.getSource();
        return dataLoader.load(show.getShowId());
    }

    // Keep the movie actors method as is, or implement a similar data loader for movies
    @DgsData(parentType = "Movie", field = "actors")
    public List<Actor> getMovieActors(DgsDataFetchingEnvironment dfe) {
        Movie movie = dfe.getSource();
        return movie.getActors();
    }

    public void randomNumberGenerator() {
        int randomNumber = (int) (Math.random() * 100) + 1; // Generates a random number between 1 and 100

        if (randomNumber % 2 == 0) {
            throw new NullPointerException("Even number generated: " + randomNumber);
        }
        throw new IllegalArgumentException("Odd number generated: " + randomNumber);
    }
}

