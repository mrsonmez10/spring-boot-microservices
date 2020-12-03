package com.mrsonmez.bookinfo.resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mrsonmez.bookinfo.models.Book;

@RestController
@RequestMapping("/books")
public class BookResource {

    @Value("${api.key}")
    private String apiKey;
    
    
//  @Autowired
//  private RestTemplate restTemplate;

  @RequestMapping("/{bookId}")
  public Book getMovieInfo(@PathVariable("bookId") String bookId) {
      //BookSummary bookSummary = restTemplate.getForObject("https://api.thebookdb.org/3/book/" + bookId + "?api_key=" +  apiKey, BookSummary.class);
     // BookSummary bookSummary = restTemplate.getForObject("https://api.thebookdb.org/3/book/" + bookId + "?api_key=" +  apiKey, BookSummary.class);
  	// Book bookSummary = restTemplate.getForObject("https://book-catalog/catalog/" + bookId, Book.class);
     // return new Book(bookId, bookSummary.getBookId(), bookSummary.getName());
  	return new Book(bookId, "Şibumi", "Şibumi");

  }
    
}
