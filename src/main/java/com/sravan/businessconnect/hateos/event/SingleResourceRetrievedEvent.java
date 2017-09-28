package com.sravan.businessconnect.hateos.event;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class SingleResourceRetrievedEvent extends ApplicationEvent {
	
	private static final long serialVersionUID = -2281933475359819338L;
	private final HttpServletResponse response;

	public SingleResourceRetrievedEvent(Object source , final HttpServletResponse response) {
		super(source);
		this.response = response;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

}
