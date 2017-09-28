package com.sravan.businessconnect.hateos.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

public class ResourceCreatedEvent extends ApplicationEvent {
	
	private static final long serialVersionUID = -5529443380217725262L;
	private final HttpServletResponse response;
	private final long idOfNewResource;

	public ResourceCreatedEvent(Object source , HttpServletResponse response, long idOfNewResource) {
		super(source);
		this.response = response;
		this.idOfNewResource = idOfNewResource;
	}
	
	public HttpServletResponse getResponse() {
        return response;
    }

    public long getIdOfNewResource() {
        return idOfNewResource;
    }

}
