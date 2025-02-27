package RailwayReservation;
import java.util.*;

public class ticketBooking {
	
	private static int berthLimit = 0;
	private static int racLimit = 0;
	private static int waitingListLimit = 0;
	
	
	private static int upperSeatNumber = 1;
	private static int middleSeatNumber = 2;
	private static int lowerSeatNumber = 3;
	
	static ArrayList<Passenger> confirmedList = new ArrayList<>();
	static ArrayList<Passenger> upperList = new ArrayList<>();
	static ArrayList<Passenger> MiddleList = new ArrayList<>();
	static ArrayList<Passenger> LowerList = new ArrayList<>();
	
	static Queue<Passenger>racQueue = new LinkedList<>();
	static Queue<Passenger>waitingQueue = new LinkedList<>();
	
	public static void bookTicket(Passenger p)
	{
		if(upperList.size() == berthLimit && LowerList.size() == berthLimit && MiddleList.size() == berthLimit)
		{
			if(updateRacQueue(p))
			{
				System.out.println("Added to Rac \n Your ticket id is "+p.getId());
				
			}
			else if(updateWaitingQueue(p))
			{
				System.out.println("Added to waiting List\n Your ticket id is"+p.getId());
			}
			else
			{
				p.setId(p.getId());
				System.out.println("Tickets not available");
			}
		}
		
		else if(checkAvailability(p))
		{
			System.out.println("Booking confirmed ticket is id"+p.getId());
			p.setTicketType("berth");
			confirmedList.add(p);
		}
		else
		{
			System.out.println(p.getPreference()+"is not available");
			p.setId(p.getId()-1);
			availableList();
		}
	}
	
	public static boolean checkAvailability(Passenger p)
	{
		Map<Integer,Character>map = TicketCancellimg.getSeatNumber();
		
		if(p.getPreference() == 'U')
		{
			if(upperList.size()<berthLimit)
			{
				if(!map.isEmpty())
				{
					getSeatDetails(map,p);
				}
				else
				{
					p.setSeatNumber(upperSeatNumber);
					upperSeatNumber+=3;
				}
				upperList.add(p);
				return true;
			}
		}
		else if(p.getPreference() == 'M')
		{
			if(MiddleList.size() < berthLimit)
			{
				if(!map.isEmpty())
				{
					getSeatDetails(map,p);
				}
				else
				{
					p.setSeatNumber(middleSeatNumber);
					middleSeatNumber+=3;
				}
				MiddleList.add(p);
				return true;
			}
		}
		
		else
		{
			if(LowerList.size()<berthLimit)
			{
				if(!map.isEmpty())
				{
					getSeatDetails(map,p);
				}
				else
				{
					p.setSeatNumber(lowerSeatNumber);
					lowerSeatNumber+=3;
				}
				LowerList.add(p);
				return true;
			}
		}
		return false;
	}
	
	public static void getSeatDetails(Map<Integer,Character>map, Passenger p)
	{
		int seatNumber = checkForPreferenceAvail(map,p.getPreference());
		p.setSeatNumber(seatNumber);
		map.remove(seatNumber);
		
	}
	
	public static int checkForPreferenceAvail(Map<Integer,Character>map,char preference)
	{
		int seatNumber = 0;
		for(Map.Entry<Integer,Character>entry: map.entrySet())
		{
			if(preference == (char)entry.getValue())
			{
				seatNumber = (int)entry.getKey();
				break;
			}
		}
		return seatNumber;
	}
	
	public static void displayConfirmed()
	{
		System.out.println("--------------------------------------");
		for(Passenger p: confirmedList)
		{
			System.out.println(p.toString());
			System.out.println("------------------------");
		}
	}
	
	private static boolean updateRacQueue(Passenger p)
	{
		if(racQueue.size()<racLimit)
		{
			p.setTicketType("rac");
			racQueue.add(p);
			return true;
		}
		return false;
	}
	
	private static boolean updateWaitingQueue(Passenger p)
	{
		if(waitingQueue.size()<waitingListLimit)
		{
			p.setTicketType("waitingList");
			waitingQueue.add(p);
			return true;
		}
		return false;
	}
	
	
}
