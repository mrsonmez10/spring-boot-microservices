package com.mrsonmez.bookcatalog.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.mrsonmez.bookcatalog.models.Book;
import com.mrsonmez.bookcatalog.models.CatalogItem;
import com.mrsonmez.bookcatalog.models.UserRating;


@RestController
@RequestMapping("/catalog")
public class BookCatalogResource {

	@Autowired
	private RestTemplate restTemplate;
	
//	@Autowired
//	private DiscoveryClient discoveryClient; // For advance loadBalance..
	
//	@Autowired
//	private WebClient.Builder webClientBuilder;
	
	/* You should avoid returning lists in API's cause if you add a new param it will be a
	 *  object then it's become a struggle...
	 *  
	 *  @ Look example of (Rating data Source for this)  
	 *  	public UserRating getUserRating(@PathVariable("userId") String userId)
	 */
	
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId)
	{
		
		// Webclient!
		// Add dependencey webflux for building a webclient!
		//WebClient.Builder builder = WebClient.builder();
		// Dont use here create just 1 instance not for every request! Solution is bean!!
		
		// Since its rest call make it autowired for safety!
		// RestTemplate restTemplate = new RestTemplate(); 
		// Book book = restTemplate.getForObject("http://localhost:8082/books/fs", Book.class);
		
		// Get all rated book IDs (Think that it comes from rest api)
//		List<Rating> ratings = Arrays.asList(
//				new Rating("American Psycho", 3),
//				new Rating("Batman", 5)
//				);
		// Here is the api version..
		//UserRating ratings = restTemplate.getForObject("http://localhost:8083/ratingsdata/users/foo" + userId, UserRating.class);
		// After Eureka
		UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/foo" + userId, UserRating.class);
		
		// Rest Call -> Get all rated book IDs
		// // For each book ID, call book info service and get details
		return ratings.getUserRating().stream().map(rating -> { 
			// Before Eureka!
			//	Book book = restTemplate.getForObject("http://localhost:8082/books/" + rating.getBookId(), Book.class);
			// After Eureka!
			Book book = restTemplate.getForObject("http://book-info-service/books/" + rating.getBookId(), Book.class);
			// put them all together
			return new CatalogItem(book.getBookName(), "Killing is my job", rating.getRating());
		}).collect(Collectors.toList());
		
		
		// WebClient
//		return ratings.stream().map(rating -> { 
//			//Book book = restTemplate.getForObject("http://localhost:8082/books/" + rating.getBookId(), Book.class);
//			Book book = webClientBuilder.build()
//					.get()
//					.uri("http://localhost:8082/books/" + rating.getBookId())
//					.retrieve()
//					.bodyToMono(Book.class)
//					.block();
//			
//			// bodyToMono used for asynch call.
//				// Message will come eventually do other thing. (Idea)
//			
//			return new CatalogItem(book.getBookName(), "Test", rating.getRating());
//		}).collect(Collectors.toList());
		
	}
	
}
