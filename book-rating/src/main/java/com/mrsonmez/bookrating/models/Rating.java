package com.mrsonmez.bookrating.models;

public class Rating {
	
	private String bookId;
	private int rating;
	
	public Rating(String bookId, int rating) {
		super();
		this.bookId = bookId;
		this.rating = rating;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	
}
