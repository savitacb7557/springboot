package com.luv2code.books.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.ArrayList;
import com.luv2code.books.entiry.Book;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/books")//used to add commonly used endpoints, so that we dont have to add it for each http request, here in this controller the commonly used endpoint is /api/books, hence added that here in this RequestMapping itself
public class BookController {

	@GetMapping("/api-endpoint")
	public String firstAPI() {
		return "Hello savita";
	}
	
	private final List<Book> books = new ArrayList<>();
	
	private BookController() {
		initializeBooks();
	}
	private void initializeBooks() {
		books.addAll(List.of(
				new Book("title one","author one","science"),
				new Book("title two","author two","science"),
				new Book("title three","author three","History"),
				new Book("title four","author four","Math"),
				new Book("title five","author five","Math"),
				new Book("title six","author six","Math")
				));
	}
	



	//approach 1
	//get mapping request to get the book details
	/*@GetMapping("/api/books")
	public List<Book> getBooks(){
		return books;
	}*/
	
	//approach 2
	//get mapping request only to get particular index book details
	/*@GetMapping("/api/books/{id}")
	public Book getBookByBookIndex(@PathVariable int id) {
		return books.get(0);//here which id we will give, that index books details will be displayed in browser(localhost:portnum/api/books/0
	}*/
	
	//approach 3
	//get mapping request to get book details by its title
	/*@GetMapping("/api/books/{title}")
	public Book getBookByTitle(@PathVariable String title) {
		for(Book book:books) {
			if(book.getTitle().equalsIgnoreCase(title)) {
				return book;
			}
		}
		return null;
	}*/
	
	
	//approach 4
	//get mapping request to get book details by title by using streams approach
	/*@GetMapping("/api/books/{title}")
	public Book getBookByBookTitle(@PathVariable String title) {
		return books.stream().filter(book ->
		book.getTitle().equalsIgnoreCase(title))
				.findFirst()
				.orElse(null);
	}*/
	
	//approach 5
	//getmapping request to get the book details by using book category
	/*@GetMapping("/api/books")
	public List<Book> getBooks(@RequestParam(required=false) String category){
		if(category == null) {
			return books;
		}
		List<Book> filteredBooks = new ArrayList<>();
		for(Book book:books) {
			if(book.getCategory().equalsIgnoreCase(category)) {
				filteredBooks.add(book);
			}
		}
		return filteredBooks;
	}*/
	
	//approach 6
	//getmapping request to get the book details by using book title and PathVariable
	/*@GetMapping("/api/books/{title}")
	public Book getBookByTitle(@PathVariable String title) {
		for(Book book:books) {
			if(book.getTitle().equalsIgnoreCase(title)) {
				return book;
			}
			}

			return null;
		}*/
	
	//@GetMapping("/api/books/{title}")----replaced this line with below line as we already added commonly used endpoints in the beginning of the class by using @RequestMapping annotation
	@GetMapping("/{title}")
	public Book getBookByTitle(@PathVariable String title) {
	return books.stream().filter(book -> book.getTitle().equalsIgnoreCase(title))
			.findFirst()
			.orElse(null);
	}
	
	/*
	@GetMapping("/api/books")
	public List<Book> getBookByCategory(@RequestParam(required=false)String category) {
		if(category == null) {
			return books;
		}
		List<Book> filteredBook = new ArrayList<>();
		
		for(Book book:books) {
			if(book.getCategory().equalsIgnoreCase(category)) {
				filteredBook.add(book);
			}
		}
		return filteredBook;
	}*/
	
	//@GetMapping("/api/books")
	@GetMapping
	public List<Book> getBookByCategory(@RequestParam(required=false)String category) {
		if(category == null) {
			return books;
		}
		return books.stream()
				.filter(book -> book.getCategory().equalsIgnoreCase(category))
				.toList();				
	}
	
	
	//PostMapping is used to create new books in existing book list, we can add new book details in browser/swagger by clicking on Post button->add new book details->execute
	//to verify, goto Get button/request in the browser-> click on execute->now you will see existing book details with newly added book details
	//new book details will only appear untill we rerun the server, once we rerun the server then whatever book details we are adding newly in browser will not be available after rerunning the server, as we are not using db connection as of now 
	/*@PostMapping("/api/books")
	public void createBook(@RequestBody Book newBook) {
		for(Book book:books) {
			if(book.getTitle().equalsIgnoreCase(newBook.getTitle())) {
				return;
			}
		}
		books.add(newBook);
	}*/
	
	
	//@PostMapping("/api/books")
	@PostMapping
	public void createBook(@RequestBody Book newBook) {
		boolean isNewBook = books.stream()
				.noneMatch(book -> book.getTitle().equalsIgnoreCase(newBook.getTitle()));
		if(isNewBook) {
			books.add(newBook);
		}
	}
	
	
	//put request is to update the existing data/body of the app request
	//here in this project we can update book details by using put request
	//we can update existing details by uisng put request in browser by adding new details(ex:changed the book3 details)
	//we can verify the result by clicking on Get request(/api/books) in browser->execute
	 //new details will only appear untill we rerun the server, once we rerun the server then whatever book details we are updating in browser will not be available after rerunning the server, as we are not using db connection as of now 
	//@PutMapping("/api/books/{title}")
	@PutMapping("/{title}")
	public void updateBook(@PathVariable String title, @RequestBody Book updatedBook) {
		for(int i=0;i<books.size();i++) {
			if(books.get(i).getTitle().equalsIgnoreCase(title)) {
				books.add(i,updatedBook);
				return;
			}
		}
	}

	//Delete request is used to delete particular details, here we have wrote the code to delete the particular details of the book based on title
	//ex: in browser wehn we give the title as "title four" then it will delete the book4(that is having title 4) details
	//this below code will checks the title given in browser->Delete request is present or not, if the given title is  present then it will delete the details of the book which is having that title
	//@DeleteMapping("/api/books/{title}")
	@DeleteMapping("/{title}")
	public void deleteBook(@PathVariable String title) {
	books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
	
}
}
	
	
	
