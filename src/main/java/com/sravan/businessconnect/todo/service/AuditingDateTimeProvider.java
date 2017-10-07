package com.sravan.businessconnect.todo.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.data.auditing.DateTimeProvider;

/*
 * The auditing infrastructure of Spring Data JPA uses the DateTimeProvider interface 
when it needs to get the current date and time. This means that if we want to integrate 
our DateTimeService with Spring Data JPA, we need to implement that interface
*/
public class AuditingDateTimeProvider implements DateTimeProvider {
	
	private final DateTimeService dateTimeService;

	public AuditingDateTimeProvider(DateTimeService dateTimeService) {
		this.dateTimeService = dateTimeService;
	}


	/*
	 * We need to fetch the current date and time by using the DateTimeService 
	 * object and return a new GregorianCalendar object.
	 * @see org.springframework.data.auditing.DateTimeProvider#getNow()
	 */
	@Override
	public Calendar getNow() {
		ZonedDateTime z = ZonedDateTime.ofInstant(dateTimeService.getCurrentDateAndTime().toInstant(), ZoneId.systemDefault());
		return GregorianCalendar.from(z);
	}

}
