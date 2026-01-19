package com.luv2code.BooksNew.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.BooksNew.DTO.BookRequest;
import com.luv2code.BooksNew.Exception.BookErrorResponse;
import com.luv2code.BooksNew.Exception.BookNotFoundException;
import com.luv2code.BooksNew.entity.Book;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
//Getmapping is to fecth the data/details
//PostMapping is to create data
//PutMapping is to update/modify the data
//@Tag is swagger annotation that is used to add name and description as purpose for what purpose we are creating this project such a things we can mention by using @Tag annotation
//@Operation annotation is used to add summary and description for the http methods, when we start the application then in browser infront of each http request we can see these summary and when we click on those http endpoints then we can see the description.these are used to set the purpose for what purpose we are using

@Tag(name="Books RestAPI endpoints", description="Operations related to books")
@RestController
@RequestMapping("/api/books")
public class BookController {

	private final List<Book> books = new ArrayList<>();	
	
	public BookController() {
		initializeBooks();
	}
	
	
	private void initializeBooks() {
			books.addAll(List.of(
				new Book(1,"Computer Science","XYZ","CS",5),
				new Book(2,"Java","Erik","CS",5),
				new Book(3,"SpringBoot","Mark","CS",4),
				new Book(4,"Grow","Bib","Novel",3),
				new Book(5,"Become Rich","Rubi","Historical",4),
				new Book(6,"SpringMaster","John","Technical",5)
        ));
}
	//approach to get all the book details/to display all the books details
	@GetMapping()
	public List<Book> getBooks(){
		return books;
	}
	
	@Operation(summary="Get all books",description="retrieve a list of all available books")
	@GetMapping("/api-endpoint")
	public String sayHello() {
		return "Hello Savita";
	}
	/*These commented methods are used to fetch the book details by using GET,POST,PUT and DELETE mapping and by using title and category as end points
	 * //approach to get the particular book details by using title
	 * 
	 * @GetMapping("/api/books/{title}") public Book getBookByTitle(@PathVariable
	 * String title) { for(Book book:books) {
	 * if(book.getTitle().equalsIgnoreCase(title)) { return book; } } return null; }
	 * 
	 * //another approach to get the particular book details by using title and
	 * streams //@GetMapping("/api/books/{title}")
	 * 
	 * @GetMapping("/{title}") public Book getBookByTitle(@PathVariable String
	 * title) { return books.stream().filter(book
	 * ->book.getTitle().equalsIgnoreCase(title)) .findFirst() .orElse(null); }
	 * 
	 * //approach to get the book details by using category
	 * 
	 * @GetMapping("/api/books") public List<Book>
	 * getBooksByCategory(@RequestParam(required=false)String category){ if(category
	 * == null) { return books; } List<Book> filteredBooks = new ArrayList<>();
	 * for(Book book:books) { if(book.getCategory().equalsIgnoreCase(category)) {
	 * filteredBooks.add(book); } } return filteredBooks; } //another approach to
	 * get the book details by using category by using stream in logic
	 * //@GetMapping("/api/books")
	 * 
	 * @GetMapping() public List<Book>
	 * getBooksByCategory(@RequestParam(required=false)String category){ if(category
	 * == null) { return books; } return books.stream().filter(book ->
	 * book.getCategory().equalsIgnoreCase(category)).toList();
	 * 
	 * }
	 * 
	 * //post methods are used to add some more data but make sure that data title
	 * should be unique //approach to create one more book
	 * //@PostMapping("/api/books/")
	 * @PostMapping() public void createBook(@RequestBody Book newBook) { for(Book
	 * book:books) { if(book.getTitle().equalsIgnoreCase(newBook.getTitle())) {
	 * return; } } books.add(newBook); }
	 * 
	 * 
	 * //@PutMapping("api/books/{title}")
	 * @PutMapping("/{title}") public void updateBook(@PathVariable String
	 * title,@RequestBody Book updatedBook) { for(int i=0;i<books.size();i++) {
	 * if(books.get(i).getTitle().equalsIgnoreCase(title)) { books.set(i,
	 * updatedBook); return; } }
	 * 
	 * }
	 * 
	 * //@DeleteMapping("/api/books/{title}") 
	 * @DeleteMapping("/{title}") public void deleteBook(@PathVariable String title)
	 * { books.removeIf(book -> book.getTitle().equalsIgnoreCase(title));
	 * 
	 * }
	 */
	
	
	//below methods are alternative methods to get book details by using id
	//in springboot the @Responsestatus annotation is used to get the Http responses when we perform any of these below operations, then it will sends/displays that http message written in braces.
	/*@Operation(summary="Get Book by id",description="Retrieve a specific book by id")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{id}")
	public Book getBookById(@PathVariable @Min(value=1)long id) {//here we have added Jakarta validation for id that is @Min(value=1), hwne we try to fetch the book details for the id less than one then it will throws an error Value must be greater than 1 so here that @Min works as condition 
		return books.stream().filter(book -> book.getId() == id)
				.findFirst()
				.orElse(null);
	}*/
	
	@Operation(summary="Get Book by id",description="Retrieve a specific book by id")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{id}")
	public Book getBookById(@PathVariable @Min(value=1)long id) {//here we have added Jakarta validation for id that is @Min(value=1), hwne we try to fetch the book details for the id less than one then it will throws an error Value must be greater than 1 so here that @Min works as condition 
		return books.stream().filter(book -> book.getId() == id)
				.findFirst()
				.orElseThrow(() -> new BookNotFoundException("Book not found"+id));//instead of throwing null when we search for unavailable book, this line will throw this exception for the same.
	}
	
	
	/*@PutMapping("/{id}")
	public void updateBookById(@PathVariable long id,@RequestBody Book updateBookById) {
		for(int i=0;i<books.size();i++) {
			if(books.get(i).getId()==id) {
				books.set(i, updateBookById);
				return;				
			}
		}
	}
	
	@PostMapping()
	public void createBook(@RequestBody Book newBook) {
		boolean isNewBook = books.stream().noneMatch(book ->book.getTitle().equalsIgnoreCase(newBook.getTitle()));
	if(isNewBook) {
		books.add(newBook);
	}
	}*/
	
	//This post method will not ask for Id, without Id by including other 4 fields we can create new book details in browesr
	//and it will saves that newly created book after all the existing books in the list
	/*@PostMapping()
	public void createBook(@RequestBody BookRequest bookRequest) {
       long id;
       if(books.isEmpty()) {
    	   id =1;
       }else {
    	   id = books.get(books.size()-1).getId()+1;
       }
       
       Book book=new Book(id,
    		   bookRequest.getTitle(),
    		   bookRequest.getAuthor(),
    		   bookRequest.getCategory(),
    		   bookRequest.getRating());		
	
	books.add(book);
	}*/
	
	/*@Operation(summary="Delete a Book",description="Remove a book")
	@ResponseStatus(HttpStatus.NO_CONTENT)		
	@DeleteMapping("/{id}")
	public void deleteBookById(@PathVariable @Min(value=1)long id) {
		books.removeIf(book -> book.getId()==id);
		}*/
	
	@Operation(summary="Delete a Book",description="Remove a book")
	@ResponseStatus(HttpStatus.NO_CONTENT)		
	@DeleteMapping("/{id}")
	public void deleteBookById(@PathVariable @Min(value=1)long id) {
		books.stream().filter(book -> book.getId() == id)
		.findFirst()
		.orElseThrow(() -> new BookNotFoundException("Book not found"+id));
		
		books.removeIf(book -> book.getId()==id);
		}
	
	
	private Book convertBook(long id,BookRequest bookRequest) {
		return new Book(id,
				bookRequest.getTitle(),
				bookRequest.getAuthor(),
				bookRequest.getCategory(),
				bookRequest.getRating());
	}
	
	@Operation(summary="Create a new book",description="Add a new book to the list")
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping()
	public void createBook(@Valid @RequestBody BookRequest bookRequest) {
       /*long id;
       if(books.isEmpty()) {
    	   id =1;
       }else {
    	   id = books.get(books.size()-1).getId()+1;
       }*/
		//this above if-else condition is written by like this below line
		long id= books.isEmpty() ? 1:books.get(books.size()-1).getId()+1;
       
       Book book=convertBook(id,bookRequest);//creating new book based on convertBook method		
	
	books.add(book);
	}
	
	@Operation(summary="Update a book", description="Update the details of an existing books")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping("/{id}")
	public Book updateBookById(@PathVariable @Min(value=1)long id,@Valid @RequestBody BookRequest bookRequest) {
		for(int i=0;i<books.size();i++) {
			if(books.get(i).getId()==id) {
				Book updatedBook = convertBook(id,bookRequest);
				books.set(i, updatedBook);
				return updatedBook;				
			}
		}
		throw new BookNotFoundException("Book not found "+id);
	}
	
	
	//by using this below function when we search for unavailable book then it will now throws an exception with statuscode,message and timestamp
	@ExceptionHandler
	public ResponseEntity<BookErrorResponse> handleException(BookNotFoundException exc){
		BookErrorResponse bookErrorRespone=new BookErrorResponse(
				HttpStatus.NOT_FOUND.value(),
				exc.getMessage(),
				System.currentTimeMillis());
		return new ResponseEntity<>(bookErrorRespone,HttpStatus.NOT_FOUND);
		
	}
	
	}
	

