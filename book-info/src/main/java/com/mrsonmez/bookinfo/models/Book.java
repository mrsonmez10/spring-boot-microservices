package com.mrsonmez.bookinfo.models;

public class Book {
	
	private String bookId;
	private String bookName;
	private String description;
	
	public Book() {
		// TODO Auto-generated constructor stub
	}
	
	public Book(String bookId, String bookName, String description) {
		super();
		this.bookId = bookId;
		this.bookName = bookName;
		this.description = description;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
