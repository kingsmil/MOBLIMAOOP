package Boundary.Staff;
import Boundary.Boundary;
import Boundary.SupportFunctions;
import Boundary.MovieGoer.DisplayMovieListBoundary;
import Entity.*;
import Entity.TheatreEnums.Cineplex;

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
    Boundary/View Class for Admin personnel to add, modify, delete a showSchedule
    @version 1.0
    @since 2022-10-23
 */
public class AddScreeningSchedule extends Boundary {
    /** movie class to be passed in */
    Movie movie;

    /** simpleDateFormat for user to input  */
    public static final SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    /**
     * Constructor to pass in the movie for the admin to refer to which movie to add the showscheduling list to 
     * @param movie
     */
    public AddScreeningSchedule(Movie movie){
        this.movie= movie;
    }

    /**
     * overriden start method from Boundary abstract class
     */
    @Override
    protected void start() {
        display();
    }

    /** display menu with 3 choices, either to add, edit, remove  */
    private void display(){
        SupportFunctions.clearScreen();
        ArrayList<ShowSchedule> scheduleForMovie =retrieveMovieShowSchedule(movie);
        printHeader("Show Scheduling for "+movie.getMovieName());

        if (scheduleForMovie == null) {
            System.out.println("No schedule for "+this.movie.getMovieName()+" Found");
            System.out.println("1. Add scheduling for "+this.movie.getMovieName());
            System.out.println("2. Return ");
            int choice = readChoice(1, 2);
            if (choice ==1) addScheduling(movie);
            else if (choice == 2) end();
        }

        else {
            for (ShowSchedule showSchedule: scheduleForMovie){
                System.out.println(showSchedule.getMovie().getMovieName()+"      "+ showSchedule.getTheatre().getCineplex()
                    +"      "+showSchedule.getTheatre().getCode()+" "+showSchedule.getTheatre().getTheatreClass()+" "+((showSchedule.getTheatre().isIs3D()) ? "3D":"  ")+"   "+"ShowTime: "+showSchedule.getTime());

            }
            System.out.println("1. Add scheduling for "+this.movie.getMovieName());
            System.out.println("2. Modify scheduling for "+this.movie.getMovieName());
            System.out.println("3. Remove scheduling for "+this.movie.getMovieName());
            System.out.println("4. Return ");
            int choice = readChoice(1, 4);
            if (choice == 1) addScheduling(movie);
            else if (choice ==2) modifyScheduling(movie);
            else if (choice == 3) removeScheduling(movie);
            else if (choice == 4) end();
        }
    }

    
    /** 
     * method to add a scheduling for a particular movie
     * @param movie
     */
    public void addScheduling(Movie movie){
        SupportFunctions.clearScreen();
        Date toAppendDate=null;
        Scanner sc= new Scanner (System.in);

        Theatre theatreToUse;

        printHeader("Show Scheduler");

        System.out.println("Enter your Theatre Code(EG. GJ1): ");
        String code= sc.nextLine();
        theatreToUse= getTheatreThroughCode(code);

        toAppendDate=dateInput();

        System.out.println(dateOutput(toAppendDate));
        System.out.println();

        ShowSchedule toAppend= new ShowSchedule();
        toAppend.setTime(toAppendDate);
        toAppend.setMovie(movie);
        toAppend.setTheatre(theatreToUse);

        try {
            addMovieShowSchedule(toAppend);
            System.out.println("Successfully added showtime and location for " + movie.getMovieName());
            System.out.println("Press any key to return");
            sc.nextLine();
        }
        catch (IOException ex) {
            System.out.println("Failed to add Showtime and location for "+ movie.getMovieName());
        }
        finally {
            clearScreen();
            end();
        }

    }

    
    /** 
     * method to modify a particular scheduling
     * @param movie
     */
    public void modifyScheduling(Movie movie){

        Scanner sc = new Scanner(System.in);

        SupportFunctions.clearScreen();
        printHeader("Modifying Showtime");
        ShowSchedule showScheduleToModify = new ShowSchedule();

        System.out.println("Which ShowSchedule would you like to modify?");

        String code= readString("Enter the Theatre Code(EG. GJ1): ").toUpperCase();
        Theatre theatre = getTheatreThroughCode(code);


        System.out.println("Enter the movie ShowTime: ");
        Date showTime = dateInput();
        
        for (ShowSchedule showSchedule: retrieveMovieShowSchedule(movie)){
            if(showSchedule.getMovie().getMovieName().equals(movie.getMovieName())
                && showSchedule.getTheatre().getCode().equals(code) && showSchedule.getTime().equals(showTime)){
                    showScheduleToModify = showSchedule;
                }
        }

        if (showScheduleToModify.checkAllSeatsEmpty()==0){
            System.out.println("This schedule has already been booked by customer(s)");
            System.out.println("Cannot be amended!");
            System.out.println("Press any key to return");
            String r= sc.nextLine();
            display();
        }

        int moreupdates=1;

        while(moreupdates==1){

        printHeader("Modifying this Showtime");

        System.out.println("1. Update Theatre");
        System.out.println("2. Update Show Time");
        System.out.println("3. Exit");
        System.out.print("Choice: ");
        int choice = readChoice(1,3);

        switch (choice){


        case 1:
        code= readString("Enter the Theatre Code(EG. GJ1): ").toUpperCase();
        theatre = getTheatreThroughCode(code);
        showScheduleToModify.setTheatre(theatre);
        break;

        case 2:
        System.out.println("Enter the new ShowTime: ");
        showTime = dateInput();
        showScheduleToModify.setTime(showTime);
        break;

        case 3:
        moreupdates=0;
        break;

        default:
        end();

        }
        
    }

        try {
        	updateMovieShowSchedule();
            System.out.println("Successfully updated ShowTime.");
            System.out.println("Press any key to continue");
            sc.nextLine();
        }
        catch (IOException ex) {
            System.out.println("Failed to update ShowTime.");
        }
        finally {
            clearScreen();
            end();
        }
    }

    
    /** 
     * method to remove a Scheduling
     * @param movie
     */
    public void removeScheduling(Movie movie){

//        printMenu("Enter the cineplex: ",
//        "1.GV",
//        "2.Cathay",
//        "3.FilmGarde");
//        String selection = "";
//        int choice = readChoice(1, 3);
//        if (choice == 1) selection="GV";
//        else if (choice ==2) selection="Cathay";
//        else if (choice ==3) selection="FilmGarde";
//        TheatreEnums.Cineplex cineplex = readCineplex(selection);

        System.out.println("Enter the Theatre Code(EG. GJ1): ");
        Scanner sc= new Scanner (System.in);
        String code= sc.nextLine().toUpperCase();

//        System.out.println("Enter the movie name: ");
//        String name = sc.nextLine();

        System.out.println("Enter the movie ShowTime: ");
        Date showTime = dateInput();
        
        for (ShowSchedule showSchedule: retrieveMovieShowSchedule(movie)){
            if(showSchedule.getMovie().getMovieName().equals(movie.getMovieName()) && 
            		showSchedule.getTheatre().getCode().equals(code) && showSchedule.getTime().equals(showTime)){
                    try{
                        removeMovieShowSchedule(showSchedule);
                        updateMovieShowSchedule();
                        System.out.println("Removed successfully!");
                    } 
                    catch(IOException i){
                        System.out.println("Failed to remove the show schedule.");
                    } 
                    finally{
                        end();
                    }
                }
        }

    }

    
    /** 
     * method to get the theatre object through the code
     * @param code
     * @return Theatre
     */
    public Theatre getTheatreThroughCode(String code){
        for (TheatreEnums.Cineplex cineplex :TheatreEnums.Cineplex.values()){
            if(retrieveTheatreList(cineplex)==null) continue;
            for (Theatre theatre : retrieveTheatreList(cineplex)) {
                if (theatre.getCode().equals(code)) return theatre;
            }
        }
        return null;  // not found
    }

     

}