package com.mrsonmez.bookrating.resource;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrsonmez.bookrating.models.Rating;
import com.mrsonmez.bookrating.models.UserRating;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsDataResource {
	
	@RequestMapping("/{bookId}")
	public Rating getRating(@PathVariable("bookId") String bookId)
	{
		return new Rating(bookId, 150);
	}
	
	@RequestMapping("users/{userId}")
	public UserRating getUserRating(@PathVariable("userId") String userId)
	{
		List<Rating> ratings = Arrays.asList(
				new Rating("Şibumi", 5),
				new Rating("Batman", 5)
				);
		
		UserRating userRating = new UserRating();
		userRating.setUserRating(ratings);
		
		return userRating;
	}

}
