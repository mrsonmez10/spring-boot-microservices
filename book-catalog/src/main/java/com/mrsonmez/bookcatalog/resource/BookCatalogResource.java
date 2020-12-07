package com.mrsonmez.bookcatalog.resource;

import java.util.Arrays;
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
import com.mrsonmez.bookcatalog.models.Rating;
import com.mrsonmez.bookcatalog.models.UserRating;
import com.mrsonmez.bookcatalog.service.UserRatingInfo;
import com.mrsonmez.bookcatalog.services.BookInfo;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


@RestController
@RequestMapping("/catalog")
public class BookCatalogResource {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	BookInfo bookInfo;
	
	@Autowired
	UserRatingInfo userRatingInfo;
	
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
	// @HystrixCommand(fallbackMethod = "getFallbackCatalog") Remove when you add 2 hystric command for each api call dont need this 
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
		// UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/foo" + userId, UserRating.class);
		// Hystric Better Design
		UserRating ratings = userRatingInfo.getUserRating(userId);
		
		// Rest Call -> Get all rated book IDs
		// // For each book ID, call book info service and get details
		return ratings.getUserRating().stream().map(rating -> { 
			// Before Eureka!
			//	Book book = restTemplate.getForObject("http://localhost:8082/books/" + rating.getBookId(), Book.class);
			
			
			// MAKE METHOD HERE!
			// After Eureka!
//			Book book = restTemplate.getForObject("http://book-info-service/books/" + rating.getBookId(), Book.class);
//			// put them all together
//			return new CatalogItem(book.getBookName(), "Killing is my job", rating.getRating());
			// METHOD for Hystric Commands!
			return bookInfo.getCatalogItem(rating);

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
	
	/*
	 * Hys. work in proxy class so you need to autowired it! Write service for those fall back mechanism
	 * 
	 * 
	@HystrixCommand(fallbackMethod = "getFallbackCatalogItem")
	private CatalogItem getCatalogItem(Rating rating)
	{
		Book book = restTemplate.getForObject("http://book-info-service/books/" + rating.getBookId(), Book.class);
		return new CatalogItem(book.getBookName(), "Killing is my job", rating.getRating());
	}
	
	private CatalogItem getFallbackCatalogItem(Rating rating)
	{
		return new CatalogItem("Book name not found", "", rating.getRating());
	}
	*/
	
	
	/*
	 *  Hys. work in proxy class so you need to autowired it! Write service for those fall back mechanism
	@HystrixCommand(fallbackMethod = "getFallbackUserRating")
	private UserRating getUserRating(@PathVariable("userId") String userId)
	{
	// TODO Auto-generated method stub
	return restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/foo" + userId, UserRating.class);
	}
	
	private UserRating getFallbackUserRating(@PathVariable("userId") String userId)
	{
		UserRating userRating = new UserRating();
		userRating.setUserId(userId);
		userRating.setUserRating(Arrays.asList(
				new Rating("0", 0)
				));
		
		return userRating;
		
	}
	 */

//	public List<CatalogItem> getFallbackCatalog(@PathVariable("userId") String userId) {
//		// You can create cache mechanism and you can take old data when the service down..
//		// Or you can basically return an error but it's not a good way to solve this problem..
//		return Arrays.asList(new CatalogItem("No Book avaliable", "", 0));
//	}
	
}