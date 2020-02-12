package bg.sofia.uni.fmi.mjt.movies;

import bg.sofia.uni.fmi.mjt.movies.model.Actor;
import bg.sofia.uni.fmi.mjt.movies.model.Movie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class MoviesExplorer {

    private List<Movie> movies;

    public MoviesExplorer(InputStream moviesInput) throws InvalidStreamException
    {
        movies = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(moviesInput))) {
            movies = reader.lines().map(Movie::createMovie)
                    .collect(Collectors.toList()); // чете всички редове -> конвертира ги във филми -> стават на лист;
        }
        catch(IOException e){
            throw new InvalidStreamException("Invalid input stream!");
        }
    }
    public Collection<Movie> getMovies(){
        return this.movies;
    }
    public int countMoviesReleasedIn(int year){
        /*int finalNumber = 0;
        for(Movie movie: movies)
        {
            if(movie.getYear() == year){
                finalNumber++;
            }
        }
        return finalNumber;*/
        int count = (int) movies.stream().filter(movie -> year == movie.getYear()).count();
        return count;
    }
    public Movie findFirstMovieWithTitle(String title)
    {
        return movies.stream()
                .filter(movie -> movie.getTitle().contains(title))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
    public Collection<Actor> getAllActors()
    {
        return movies.stream()
                .flatMap(movie -> movie.getActors().stream())
                .collect(Collectors.toSet());
    }
    public int getFirstYear(){
        return movies.stream()
                .mapToInt(Movie::getYear)
                .min()
                .getAsInt();
    }
    public Collection<Movie> getAllMoviesBy(Actor actor){
        return movies.stream()
                .filter(movie -> movie.getActors().contains(actor))
                .collect(Collectors.toList());
    }
    public Collection<Movie> getMoviesSortedByReleaseYear(){
        return movies.stream()
                .sorted(Comparator.comparing(Movie::getYear))
                .collect(Collectors.toList());
    }
    public int findYearWithLeastNumberOfReleasedMovies(){
        return movies.stream()
                .collect(Collectors.groupingBy(Movie::getYear, Collectors.counting()))
                .entrySet()
                .stream()
                .min(Comparator.comparing(Map.Entry::getValue))
                .get()
                .getKey();
    }
    public Movie findMovieWithGreatestNumberOfActors(){
        return movies.stream()
                .max(Comparator.comparing(x->x.getActors().size()))
                .get();
    }
}
