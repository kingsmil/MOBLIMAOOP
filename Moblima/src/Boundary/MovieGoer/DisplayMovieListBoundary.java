package Boundary.MovieGoer;

import Boundary.Boundary;
import Boundary.SupportFunctions;
import Entity.Movie;
import Entity.MovieEnums;

import static Controller.CRUDMovies.*;
import static Controller.MiscMethods.*;


import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import static Controller.MiscMethods.*;

/**
    Boundary/View Class to list all the movies/ top 5 movies to the users,
    then afterwards users can proceed to check on the showschedules pertaining to the movie chosen
    @version 1.0
    @since 2022-10-23
 */
public class DisplayMovieListBoundary extends Boundary {
    /**  userID to be passed in if logged in as member */
	private String userId;
	/**
     * Constructor to pass in userID
     * @param userId
     */
	public DisplayMovieListBoundary(String userId) {
		this.userId=userId;
	}
	
    //private boolean isTopFive =false;
    /**
     * overriden start method from Boundary abstract class
     */
    protected void start() {
        display();
    }

    /** method to display the menu
     * 1. Search movie by name
     * 2. List all movies
     * 3. List top 5 by either rating, salesnumber or both
     * (the display of top 5 can be tweaked at the admin side)
     */
    private void display() {
        
        SupportFunctions.clearScreen();
        printHeader("Search or List Movies");
        printMenu("1. Search movies",
                "2. List all movies",
                "3. List the top 5 movies",
                "4. Go back","");
        int choice = readChoice(1, 4);
        switch (choice) {
            case 1:
                direct(this, new DisplaySearchMovieBoundary(userId));
                end();
                break;
            case 2:
                movieListingView();
                break;
            case 3:
                direct(this, new DisplayTop5MoviesBoundary(userId));
                end();
                break;
            case 4:
                end();
                break;
        }
    }


    /** method to show the listing of movies according to display() function's choice  */
    private void movieListingView() {

        Date today = new Date();
        
        SupportFunctions.clearScreen();
        ArrayList<Movie> listOfMovie;
        
        listOfMovie = retrieveMovieList();
        printHeader("Movies");
        if (listOfMovie.isEmpty()) {
            printMenu("Movie listing is not available");
            movieListingView();
            return;
        }

        

        

        //TO FILTER OUT THE END_OF_SHOWING MOVIES, AS WELL AS TO SET TO COMING SOON and NOW SHOWING
        for (Movie movie:listOfMovie){
            if(movie.getMovieStatus().toString()=="END OF SHOWING") continue;
            if(today.after(movie.getTakeDownDate())) {movie.setMovieStatus(readMovieStatus("END OF SHOWING")); }
            else if (today.before(movie.getReleaseDate())) {
                if (movie.getMovieStatus().toString()=="PREVIEW") {
                    movie.setMovieStatus(readMovieStatus("PREVIEW"));
                }
                else{
                    movie.setMovieStatus(readMovieStatus("COMING SOON"));
                }
            }
            else {
                movie.setMovieStatus(readMovieStatus("NOW SHOWING"));
            }
        }


        //TO FILTER OUT THE END OF SHOWING MOVIES
        ArrayList<Movie> moviesCurrentlyAvailList=  new ArrayList<>();
        for (Movie movie: listOfMovie){
            if ((movie.getMovieStatus().toString()=="PREVIEW")||(movie.getMovieStatus().toString()=="NOW SHOWING")||(movie.getMovieStatus().toString()=="COMING SOON")) moviesCurrentlyAvailList.add(movie);
        }
            

        
        //DISPLAY THE FILTERED LIST OF MOVIE
        int index = 0;
            for (Movie movie : moviesCurrentlyAvailList) {
//                if (movie.getMovieStatus().equals(MovieEnums.MovieStatus.END_OF_SHOWING)) {
//                    ++index;
//                    continue;}
                printMenu(++index + ". " + movie.getMovieName() + generateSpaces(47 - movie.getMovieName().length())
                        + "(" + movie.getMovieStatus().toString() + ") " +
                        "[" + (getAvgMovieRating(movie) == 0.0 ? "NA" : getAvgMovieRating(movie)) + "]"+"\tSalesNumber: " + movie.getSalesNum());

            }


        printMenu(index + 1 + ". Go back", "");

        int choice = readChoice(1, index + 1);

        if (choice == index + 1) end();
        else {
            Movie movie = moviesCurrentlyAvailList.get(choice - 1);

            direct(this, new DisplayMovieDetailsBoundary(movie,userId));
        }
    }
}

