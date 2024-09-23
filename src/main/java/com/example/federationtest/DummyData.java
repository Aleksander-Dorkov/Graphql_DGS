package com.example.federationtest;

import com.example.netflixdgs.codegen.types.Actor;
import com.example.netflixdgs.codegen.types.Movie;
import com.example.netflixdgs.codegen.types.Show;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class DummyData {

    public static final List<Actor> actors = List.of(
            Actor.newBuilder().actorId("actor1").name("Tom Hanks").build(),
            Actor.newBuilder().actorId("actor2").name("Meryl Streep").build(),
            Actor.newBuilder().actorId("actor3").name("Denzel Washington").build(),
            Actor.newBuilder().actorId("actor4").name("Viola Davis").build(),
            Actor.newBuilder().actorId("actor5").name("Leonardo DiCaprio").build()
    );
    public static final List<Show> shows = List.of(
            Show.newBuilder()
                    .showId("show1")
                    .title("Stranger Things")
                    .actors(Arrays.asList(actors.get(0), actors.get(1)))
                    .build(),
            Show.newBuilder()
                    .showId("show2")
                    .title("The Crown")
                    .actors(Arrays.asList(actors.get(2), actors.get(3)))
                    .build(),
            Show.newBuilder()
                    .showId("show3")
                    .title("Breaking Bad")
                    .actors(Arrays.asList(actors.get(4), actors.get(0)))
                    .build()
    );
    public static final List<Movie> movies = List.of(
            Movie.newBuilder()
                    .movieId("movie1")
                    .title("Inception")
                    .actors(Arrays.asList(actors.get(4), actors.get(1)))
                    .build(),
            Movie.newBuilder()
                    .movieId("movie2")
                    .title("The Post")
                    .actors(Arrays.asList(actors.get(0), actors.get(1)))
                    .build(),
            Movie.newBuilder()
                    .movieId("movie3")
                    .title("Fences")
                    .actors(Arrays.asList(actors.get(2), actors.get(3)))
                    .build()
    );

    public static Map<String, List<Actor>> getAllActorsForShows(List<String> showIds) {
        return shows.stream()
                .filter(show -> showIds.contains(show.getShowId()))
                .collect(Collectors.toMap(Show::getShowId, Show::getActors));
    }
}
