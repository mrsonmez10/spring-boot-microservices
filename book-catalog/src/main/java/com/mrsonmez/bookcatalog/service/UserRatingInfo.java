package com.mrsonmez.bookcatalog.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.mrsonmez.bookcatalog.models.Rating;
import com.mrsonmez.bookcatalog.models.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class UserRatingInfo {
	
	@Autowired
	public RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "getFallbackUserRating",
			commandProperties = 
			{
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMillisecond", value ="2000"),
					@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value ="5"),
					@HystrixProperty(name = "circuitBreaker.errorThreholdPercentage", value ="50"),
					@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value ="5000")
			})
	public UserRating getUserRating(@PathVariable("userId") String userId)
	{
	// TODO Auto-generated method stub
	return restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/foo" + userId, UserRating.class);
	}
	
	public UserRating getFallbackUserRating(@PathVariable("userId") String userId)
	{
		UserRating userRating = new UserRating();
		userRating.setUserId(userId);
		userRating.setUserRating(Arrays.asList(
				new Rating("0", 0)
				));
		
		return userRating;
		
	}

}
