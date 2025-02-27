package RailwayReservation;
import java.util.*;
public class TicketCancellimg extends ticketBooking{
	private static char preferenceTracker = '\0';
	private static int canceledSeatNumber = 0;
	private static Map<Integer,Character> seatNumberWithBerth = new HashMap<Integer,Character>();
	
	public static String cancelling(int id)
	{
		for(Passenger p: confirmedList)
		
		{
			if(p.getId() == id)
			{
				cancel(p);
				return "success";
				
			}
		}
		for(Passenger p : racQueue)
		{
			if(p.getId() == id) {
				cancel(p);
				return "Success";
			}
		}
		
		for(Passenger p : waitingQueue)
		{
			if(p.getId() == id) {
				cancel(p);
				return "Success";
			}
		}
		
		return "Invalid Id";
	}
	
	public static void cancel(Passenger p)
	{
		if(p.getTicketType() == "berth")
		{
			preferenceTracker = p.getPreference();
			canceledSeatNumber = p.getSeatNumber();
			
			seatNumberWithBerth.put(canceledSeatNumber,preferenceTracker);
			
			deleteFromAllLists(p);
			addRacToBerth(racQueue.poll());
			addWaitingToRac(waitingQueue.poll());
		}
		else if(p.getTicketType() == "rac")
		{
			racQueue.remove(p);
			addWaitingToRac(waitingQueue.poll());
		}
		else
		{
			waitingQueue.remove(p);
		}
	}
	
}
