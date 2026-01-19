package com.luv2code.BooksNew.Exception;

public class BookNotFoundException extends RuntimeException{

	//these below 3 constructors are created by right click->source->Generate Constructors from superclass
	public BookNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public BookNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public BookNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	
}
