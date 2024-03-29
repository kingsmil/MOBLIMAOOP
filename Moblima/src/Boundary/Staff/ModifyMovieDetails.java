package Boundary.Staff;

import Boundary.Boundary;
import Boundary.SupportFunctions;
import Boundary.MovieGoer.DisplayMovieListBoundary;
import Entity.*;
import static Controller.CRUDMovies.*;
import static Controller.CRUDTheatre.*;
import static Controller.CRUDShowSchedule.*;
import static Controller.MiscMethods.*;
import static Boundary.SupportFunctions.*;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
    Boundary/View Class for an admin to modify the details of a particular movie
    @version 1.0
    @since 2022-10-30
 */
public class ModifyMovieDetails extends Boundary {

    /** movie object to be passed in for edit and update */
    Movie movie;

    /**
     * Constructor to pass in the movie 
     * @param movie
     */
    public ModifyMovieDetails(Movie movie){
        this.movie= movie;
    }

    /**
     * overriden start method from Boundary abstract class
     */
    @Override
    protected void start() {
        display();
    }

    /** method to display the movie edit view to the admins, 
     * allow admin to enter their edits, to edit the attributes of the movie
     */
    private void display(){
        SupportFunctions.clearScreen();
        printHeader("Modifying Details for "+movie.getMovieName());

        int moreupdates=1;
        String name, director, synopsis;
        ArrayList<String> cast;
        MovieEnums.AgeAdvisory ageAdvisory;
        MovieEnums.MovieStatus movieStatus;

        // name = movie.getMovieName();
        // ageAdvisory = movie.getAgeAdvisory();
        // director = movie.getMovieDirector();
        // movieStatus = movie.getMovieStatus();
        // synopsis = movie.getMovieDesc();
        // id = movie.getMovieId();
        // cast = movie.getMovieCast();

        while(moreupdates==1){
        // System.out.println("1. Update Movie Name");
        System.out.println("1. Update Age Restriction");
        System.out.println("2. Update Movie Director");
        System.out.println("3. Update Movie Synopsis");
        System.out.println("4. Update Movie Cast");
        System.out.println("5. Update Movie Status");
        System.out.println("6. Update Release Date");
        System.out.println("7. Update Takedown Date");
        System.out.println("8. Exit");
        System.out.print("Choice: ");
        int choice = readChoice(1,8);

        switch (choice){

        // case 1:
        // name = readString("Enter the new movie name:");
        // break;

        case 1:
        String input = readString("Enter new age restriction, please enter one of the following:",
            "G, PG, PG13, NC16, M18, R21").toUpperCase();
        ageAdvisory = readAgeAdvisory(input);
        movie.setAgeAdvisory(ageAdvisory);
        break;

        case 2:
        director = readString("Enter new director:");
        movie.setMovieDirector(director);
        break;

        case 3:
        synopsis = readString("Enter new synopsis:");
        movie.setMovieDesc(synopsis);
        break;

        case 4:
        String[] casts = readString("Enter new casts, separate with comma(,), Last CAST NO NEED!").split(",");
        cast = new ArrayList<>();
        for (int i = 0; i < casts.length; i++) cast.add(casts[i]);
        movie.setMovieCast(cast);
        break;

        case 5:
        input = readString("Enter new movie status, please enter one of the following:",
            "COMING SOON, PREVIEW, NOW SHOWING, END OF SHOWING").toUpperCase();
        movie.setMovieStatus(readMovieStatus(input));
        break;
        
        case 6:
        System.out.println("Enter the new Release Date for "+ movie.getMovieName());
        movie.setReleaseDate();
        break;

        case 7:
        System.out.println("Enter the new Take Down Date for "+ movie.getMovieName());
        movie.setTakeDownDate();
        break;

        case 8:
        moreupdates=0;
        break;

        default:
        end();

        }
    }

        // Movie newMovie = new Movie(id,name,synopsis,director,cast,movieStatus,ageAdvisory);
        // System.out.println("Confirm the Release Date for "+ newMovie.getMovieName());
        // newMovie.setReleaseDate();
        // System.out.println("Confirm the Take Down Date for "+ newMovie.getMovieName());
        // newMovie.setTakeDownDate();

        try {
            // addMovieIntoList(newMovie);
            // updateReviews(movie, newMovie);
            // removeMovieFromList(movie);
            updateMovieList();
            System.out.println("Successfully updated movie " + movie.getMovieName());
        }
        catch (IOException ex) {
            System.out.println("Failed to update the movie.");
        }
        finally {
            end();
        }

    }

    // public void updateReviews(Movie movie, Movie newMovie){

    //     ArrayList<Review> listOfReview = retrieveReviewList(movie);
    //     if(!(listOfReview==null)){
    //         for (Review ratings : listOfReview) {
    //             Review review1 = new Review(ratings.getRating(), ratings.getReview(), this.movie, ratings.getName());
    //             try {
    //                 addReviewIntoList(newMovie, review1);
    //             }
    //             catch (IOException ex) {
    //                 System.out.println(ex);
    //             }
    //         }
    //     }

    // }

}