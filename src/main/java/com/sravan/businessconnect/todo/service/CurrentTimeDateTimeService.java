package com.sravan.businessconnect.todo.service;

import java.util.Date;

public class CurrentTimeDateTimeService implements DateTimeService{

	@Override
	public Date getCurrentDateAndTime() {
		return new Date();
	}

}
