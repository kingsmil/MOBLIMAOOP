package Boundary.MovieGoer;

import Boundary.Boundary;
import Entity.Seat;
import Entity.ShowSchedule;
import static Controller.MiscMethods.*;

import java.util.ArrayList;
import java.util.Calendar;

public class DisplayShowtimeDetailMenu extends Boundary {
	private ShowSchedule showtime;
	private ArrayList<Seat> chosenSeats;
	
	public DisplayShowtimeDetailMenu(ShowSchedule showtime) {
		this.showtime=showtime;
		this.chosenSeats=new ArrayList<Seat>();
	}
	
	protected void start() {
        display();
    }
	
	private void display() {
		int numStudent=0;
		int numSeniors=0;
		int count=0;
		
		showPricing();
		printMenu("\nPlease enter number of seats(Press 0 to go back)");
		int numSeats=readChoice(0,134);
		
		if (numSeats==0) {
			end();
		}
		
		Calendar cal=Calendar.getInstance();
    	cal.setTime(showtime.getTime());
    	
    	int dayOfWeek=cal.get(Calendar.DAY_OF_WEEK);
    	int hourOfDay=cal.get(Calendar.HOUR_OF_DAY);
    	
    	if (dayOfWeek>=2 && dayOfWeek<=6 && hourOfDay<18) {
    		printMenu("Choose number of student tickets");
    		numStudent=readChoice(0,numSeats);
    		
    		printMenu("Choose number of Senior Citizen tickets");
    		numSeniors=readChoice(0,numSeats-numStudent);
    	}
    	
    	printHeader("Screen");
		showtime.showSeatLayout();
		printHeader("Entrace");
    	
		while (count<numSeats) {
			printMenu("Please select your seats: "+"("+(numSeats-count)+" left)");
			
			printMenu("\nSelect your row(choose a number between 1-9)");
			int row=readChoice(1,9);
			
			printMenu("\nSelect your Column(choose a number between 1-17, excluding 8)");
			int column=readChoice(1,17);
			
			if (showtime.getSpecificSeat(row, column)==null) {
				printMenu("\nNo such seat exists. Please pick another seat.");
			}
			
			else if (showtime.getSpecificSeat(row, column).isAssigned()) {
				printMenu("\nSeat is already occupied. Please pick another seat.");
				continue;
			}
			else {
				showtime.getSpecificSeat(row,column).assignSeat();
				chosenSeats.add(showtime.getSpecificSeat(row, column));
				printMenu("\nSeat succesfully selected.");
				count++;
			}
			printHeader("Screen");
			showtime.showSeatLayout();
			printHeader("Entrace");
		}
		if (showtime.getMovie().getAgeAdvisory().toString()=="M18" ||showtime.getMovie().getAgeAdvisory().toString()=="R21") {
			printMenu("Please note that this movie is rated "+showtime.getMovie().getAgeAdvisory().toString());
			printMenu("MOBLIMA welcomes all guests aged 18 and above to the Platinum Movie Suites.");
		}
		direct(this, new DisplayBookingConfirmation(showtime,chosenSeats,numStudent,numSeniors));
		end();
	}
	
	public static void showPricing() {
		System.out.println("Ticket Type\t\t Regular&Digital Movies\t 3D Movies");
		System.out.println("\nSenior Citizens*\t $4.00\t\t\t N.A");
		System.out.println("Mon-Fri, before 6pm");
		System.out.println();
		System.out.println("Students**\t\t $7.00\t\t\t $9.00");
		System.out.println("Mon-Fri, before 6pm");
		System.out.println();
		System.out.println("Mon-Wed#\t\t $8.50\t\t\t $11.00");
		System.out.println("All Sessions");
		System.out.println();
		System.out.println("Thu\t\t\t $9.50\t\t\t $11.00");
		System.out.println("All Sessions");
		System.out.println();
		System.out.println("Fri\t\t\t $9.50\t\t\t $15.00");
		System.out.println("Sessions before 6pm");
		System.out.println();
		System.out.println("Fri\t\t\t $11.00\t\t\t $15.00");
		System.out.println("Sessions after 6pm");
		System.out.println();
		System.out.println("Sat & Sun^\t\t $11.00\t\t\t $15.00");
		System.out.println("All Sessions");
		System.out.println();
		System.out.println("\n**Not valid on PH/eve of PH | ^Include PH?eve of PH and weekend sneaks | *For patrons 55 years & older. Not valid on PH/eve of PH");
	}
}
