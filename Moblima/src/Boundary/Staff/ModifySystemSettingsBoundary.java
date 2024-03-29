package Boundary.Staff;
import Boundary.Boundary;
import Boundary.SupportFunctions;
import Entity.*;
import static Controller.CRUDMovies.*;
import static Controller.CRUDTheatre.*;
import static Controller.MiscMethods.*;
import static Controller.AdminController.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.*;

/**
    Boundary/View Class For the Admin to tweak the system settings,
    such as price, holidays and top five movie listings
    @version 1.0
    @since 2022-10-30
 */
public class ModifySystemSettingsBoundary extends Boundary {

     /**
     * overriden start method from Boundary abstract class
     */
    @Override
    protected void start() {
        display();
    }

    /** This method displays a list of menu
     * to tweak the selected prices,
     * holidays,
     * and the order for the top 5 movie listings
     * (Either by rating, sales number or by both)
     */
    private void display(){
        
        SupportFunctions.clearScreen();
        printHeader("Modify System Setting");
        printMenu("Select Choice",
        "1.Holidays Ticket Increment",
        "2.Child Ticket Discount",
        "3.Senior Ticket Discount",
        "4.Premium Ticket Price",
        "5.Standard Ticket Price",
        "6.3D Ticket Increment",
        "7.BlockBuster Ticket Increment",
        "8.Weekend Ticket Increment",
        "9.Add/Remove Holidays",
        "10.Modify Top Five Listing",
        "11.Save/Back");
        int choice = readChoice(1, 11);
        switch(choice){
            case 1:
            System.out.printf("Current Price: ");
            System.out.println(retrieveSystemSettings().getHolidaysIncrement());
            retrieveSystemSettings().setHolidaysIncrement(readDouble("New Price:"));
            printMenu("Price settings successfully changed!");
            display();
            break;
            case 2:
            System.out.printf("Current Price: ");
            System.out.println(retrieveSystemSettings().getChildDiscount());
            retrieveSystemSettings().setChildDiscount(readDouble("New Price:"));
            printMenu("Price settings successfully changed!");
            display();
            break;
            case 3:
            System.out.printf("Current Price: ");
            System.out.println(retrieveSystemSettings().getSeniorCitizenDiscount());
            retrieveSystemSettings().setSeniorCitizenDiscount(readDouble("New Price:"));
            printMenu("Price settings successfully changed!");
            display();
            break;
            case 4:
            System.out.printf("Current Price: ");
            System.out.println(retrieveSystemSettings().getPremiumPrice());
            retrieveSystemSettings().setPremiumPrice(readDouble("New Price:"));
            printMenu("Price settings successfully changed!");
            display();
            break;
            case 5:
            System.out.printf("Current Price: ");
            System.out.println(retrieveSystemSettings().getStandardPrice());
            retrieveSystemSettings().setStandardPrice(readDouble("New Price:"));
            printMenu("Price settings successfully changed!");
            display();
            break;
            case 6:
            System.out.printf("Current Price: ");
            System.out.println(retrieveSystemSettings().getThreeDIncrement());
            retrieveSystemSettings().setThreeDIncrement(readDouble("New Price:"));
            printMenu("Price settings successfully changed!");
            display();
            break;
            case 7:
            System.out.printf("Current Price: ");
            System.out.println(retrieveSystemSettings().getBlockBusterIncrement());
            retrieveSystemSettings().setBlockBusterIncrement(readDouble("New Price:"));
            printMenu("Price settings successfully changed!");
            display();
            break;
            case 8:
            System.out.printf("Current Price: ");
            System.out.println(retrieveSystemSettings().getWeekendIncrement());
            retrieveSystemSettings().setWeekendIncrement(readDouble("New Price:"));
            printMenu("Price settings successfully changed!");
            display();
            break;
            case 9:
            direct(this, new HolidayBoundary());
            display();
            break;
            case 10:
            System.out.println("Please set preferred Top five movie listing for viewing: ");
            System.out.println("0---- By Ratings \t1---- By SalesNumbers \t2---- By both  ");
            retrieveSystemSettings().setTopFivechoice(readChoice(0, 2));
            display();
            break;
            case 11:
            try{updateSystemSettings();}catch(IOException e){
                System.out.println(e);
            }
            end();
            break;
        }
    }
}
