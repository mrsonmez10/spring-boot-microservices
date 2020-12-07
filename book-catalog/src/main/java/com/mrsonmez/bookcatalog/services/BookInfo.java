package com.mrsonmez.bookcatalog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mrsonmez.bookcatalog.models.Book;
import com.mrsonmez.bookcatalog.models.CatalogItem;
import com.mrsonmez.bookcatalog.models.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class BookInfo {
	
	@Autowired
	public RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "getFallbackCatalogItem",
			threadPoolKey = "bookInfoPool",
			threadPoolProperties = 
			{
					@HystrixProperty(name = "coreSize", value = "20"),
					@HystrixProperty(name = "maxQueueSize", value = "10")
			})
	public CatalogItem getCatalogItem(Rating rating)
	{
		Book book = restTemplate.getForObject("http://book-info-service/books/" + rating.getBookId(), Book.class);
		return new CatalogItem(book.getBookName(), "Killing is my job", rating.getRating());
	}
	
	public CatalogItem getFallbackCatalogItem(Rating rating)
	{
		return new CatalogItem("Book name not found", "", rating.getRating());
	}

}
