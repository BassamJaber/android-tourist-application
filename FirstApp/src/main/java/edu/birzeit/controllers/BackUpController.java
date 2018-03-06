package edu.birzeit.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import birzeit.edu.backup.Booking;
import birzeit.edu.backup.Customer;
import birzeit.edu.backup.Destination;
import birzeit.edu.backup.Flight;
import edu.birzeit.bo.Student;


@RestController
@RequestMapping("/")
public class BackUpController {

	ArrayList<Customer> customer;
	ArrayList<Destination> destination;
	ArrayList<Flight>flight;
	ArrayList<Booking> booking;
	
	ArrayList<Student> list;

	@RequestMapping(value = "/getAllFlights", method = RequestMethod.GET)
	public ArrayList<Flight> getAllFlights() {
		if (flight == null) {
			flight = new ArrayList<Flight>();
			flight.add(new Flight(0,"companyName",0,"1-1-2015","1-1-2015",new Destination(),0));
		}
		return flight;
	}
	
	@RequestMapping(value = "/getAllCustomers", method = RequestMethod.GET)
	public ArrayList<Customer> getAllCustomers() {
		if (customer == null) {
			customer = new ArrayList<Customer>();
			customer.add(new Customer(0,"Customer Name", 0,0,"path","address"));
		}
		return customer;
	}
	
	@RequestMapping(value = "/getAllDestinations", method = RequestMethod.GET)
	public ArrayList<Destination> getAllDestinations() {
		if (destination == null) {
			destination = new ArrayList<Destination>();
			destination.add(new Destination(0,"Destination","county",0,0,"path"));
		}
		return destination;
	}
	
	@RequestMapping(value = "/getAllBooking", method = RequestMethod.GET)
	public ArrayList<Booking> getAllBooking() {
		if (booking == null) {
			booking = new ArrayList<Booking>();
			booking.add(new Booking(0,0,0));
		}
		return booking;
	}

	@RequestMapping(value = "/addCustomerList", method = RequestMethod.POST)
	public ResponseEntity<List<Customer>> addCustomerList(@RequestBody List<Customer>list) {
		//Customer list should be saved to database or file , and return OK response for client
		//we save it in a local variable 
		customer=(ArrayList<Customer>) list;
		return new ResponseEntity<List<Customer>>(list,HttpStatus.OK);
	}
	

	@RequestMapping(value = "/addFlightList", method = RequestMethod.POST)
	public ResponseEntity<List<Flight>> addFlightList(@RequestBody List<Flight>list) {
		//Customer list should be saved to database or file , and return OK response for client
		//we save it in a local variable 
		flight=(ArrayList<Flight>) list;
		return new ResponseEntity<List<Flight>>(list,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/addDestinationList", method = RequestMethod.POST)
	public ResponseEntity<List<Destination>> addDestinationList(@RequestBody List<Destination>list) {
		//Customer list should be saved to database or file , and return OK response for client
		//we save it in a local variable 
		destination=(ArrayList<Destination>) list;
		return new ResponseEntity<List<Destination>>(list,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/addBookingList", method = RequestMethod.POST)
	public ResponseEntity<List<Booking>>addBookingList(@RequestBody List<Booking>list) {
		//Customer list should be saved to database or file , and return OK response for client
		//we save it in a local variable 
		booking=(ArrayList<Booking>) list;
		return new ResponseEntity<List<Booking>>(list,HttpStatus.OK);
	}
}
