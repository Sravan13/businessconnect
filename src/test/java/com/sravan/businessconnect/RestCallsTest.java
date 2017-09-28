package com.sravan.businessconnect;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.hamcrest.core.AnyOf;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.sravan.businessconnect.app.dao.Category;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;





@RunWith(SpringRunner.class)
//@ContextConfiguration(classes = {App.class})
public class RestCallsTest {
	
	
	String authorizationHeaderName ;
	String authorizationHeaderValue ;
	int port=8080;
	
	RestTemplate restTemplate = new RestTemplate();
	
	/*TestRestTemplate testRestTemplate;*/

	@Before
	public void setUp() throws UnsupportedEncodingException {
		//this.testRestTemplate = new TestRestTemplate("admin", "secret");
		
		String credentials = "admin:secret";
		String base64EncodedCredentials = Base64.encode(credentials.getBytes("UTF-8"));

		authorizationHeaderName = "Authorization";
		authorizationHeaderValue = "Basic " + base64EncodedCredentials;
	}
	
	/*@Test
	public void givenAuthenticatedByBasicAuth_whenAResourceIsCreated_then201IsReceived() throws MalformedURLException{
		
		
		Response response = RestAssured.given()
				.auth().preemptive().basic( "admin", "secret" ).accept("application/json")
				.post("http://localhost:8080/abc/categories" );
		
		System.out.println(response.getStatusCode());
		
		assertEquals(response.getStatusCode()+"", "201");
		
	}*/
	
/*	@Test
	public void givenAuthenticatedByDigestAuth_whenAResourceIsCreated_then201IsReceived() {
		
	}*/
	
	
	/*@Test
	public void testGetFoo() {
		String URI = "http://localhost:8080/abc/foos/{id}";
		RestTemplate restTemplate = new RestTemplate();
		Foo foo = restTemplate.getForObject(URI, Foo.class, "1");
		
		//Assert.assertEquals(new Integer(1), foo.getId());
	}*/
	
	@Ignore
	@Test
	public void testWithRestTemplate() throws UnsupportedEncodingException {
		/*String credentials = "admin:secret";
		String base64EncodedCredentials = Base64.encode(credentials.getBytes("UTF-8"));

		String authorizationHeaderName = "Authorization";
		String authorizationHeaderValue = "Basic " + base64EncodedCredentials;*/

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(authorizationHeaderName, authorizationHeaderValue);
		httpHeaders.set("Accept", "application/json");
		

		HttpEntity<Void> request = new HttpEntity<>(httpHeaders);
		ResponseEntity<List<Category>> response = this.restTemplate.exchange(
				"http://localhost:{port}/abc/categories", HttpMethod.GET, request,  new ParameterizedTypeReference<List<Category>>() {
	            }, this.port);
		List<Category> rates = response.getBody();
		
		
		//assertThat(response.getBody().getContent(), is("Hello, World!"));

		/*response = this.restTemplate.exchange(
				"http://localhost:{port}/greeting?name=Johnny",
				HttpMethod.GET, request, Greeting.class, this.port);
		assertThat(response.getBody().getContent(), is("Hello, Johnny!"));*/
	}
	
	@Test
	public void whenInvalidPOSTIsSentToValidURIOfResource_thenAllowHeaderListsTheAllowedActions(){
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.set(authorizationHeaderName, authorizationHeaderValue);
		httpHeaders.set("Accept", "application/json");
		

		HttpEntity<Void> request = new HttpEntity<>(httpHeaders);
		
		ResponseEntity<List<Category>> response = this.restTemplate.exchange(
				"http://localhost:{port}/abc/categories", HttpMethod.GET, request,  new ParameterizedTypeReference<List<Category>>() {
	            }, this.port);
		
		
		
		String allowHeader = response.getHeaders().get(HttpHeaders.ALLOW).get(0);
		
		System.out.println("\n #########################sravan : "+allowHeader+" #############\n");
		
		assertThat( allowHeader, AnyOf.<String> anyOf(
		containsString("GET"), containsString("PUT"), containsString("DELETE") ) );
		
	}
	

}
