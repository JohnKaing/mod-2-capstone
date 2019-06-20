package com.techelevator.reservation;

import java.util.Date;
import java.util.List;

public interface ReservationDAO {
	
	public Reservation createReservation(Reservation newReservation);

	public List<Reservation> returnAllReservations();

	public void updateReservation(Reservation updatedReservation);

	public boolean deleteReservation(Reservation ReservationToDelete);
	
	public List<Reservation> getReservationsByDate(Date arrivalDate, Date departureDate);

}
