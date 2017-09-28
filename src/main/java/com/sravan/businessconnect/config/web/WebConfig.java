package com.sravan.businessconnect.config.web;

import java.util.List;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		
		converters.add(createXmlHttpMessageConverter());
		//converters.add(new MappingJackson2HttpMessageConverter());
		
		super.configureMessageConverters(converters);
	}
	
	public HttpMessageConverter<Object> createXmlHttpMessageConverter() {
		MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();
		XStreamMarshaller xstreamMarshaller = new XStreamMarshaller();
		xmlConverter.setMarshaller(xstreamMarshaller);
		xmlConverter.setUnmarshaller(xstreamMarshaller);
		
		return xmlConverter;
	}
	
	/*
	 * Below filter gives Etag capabilities
	 * 
	 * Note : if you application is configured with spring security then Spring Security 
	 * invalidated Etags in the response. It seems that if you want to use both 
	 * Spring Security and ETag support, you need to declare "http.headers().cacheControl().disable()"
	 * in security configuration class
	 */
	@Bean
    public Filter shallowEtagHeaderFilter() {
		
        return new ShallowEtagHeaderFilter();
    }
		

}
