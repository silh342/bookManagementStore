package com.younes.reskilingproject.bookManagement.restController;

import com.younes.reskilingproject.bookManagement.dto.BookRequestBody;
import com.younes.reskilingproject.bookManagement.dto.FavoriteBook;
import com.younes.reskilingproject.bookManagement.dto.ReviewRequestBody;
import com.younes.reskilingproject.bookManagement.entity.Book;
import com.younes.reskilingproject.bookManagement.entity.Category;
import com.younes.reskilingproject.bookManagement.entity.Inventory;
import com.younes.reskilingproject.bookManagement.entity.Review;
import com.younes.reskilingproject.bookManagement.service.BookService.ImplBookService;
import com.younes.reskilingproject.bookManagement.service.CategoryService.ImplCategoryService;
import com.younes.reskilingproject.bookManagement.service.InventoryService.ImplInventoryService;
import com.younes.reskilingproject.bookManagement.service.ReviewService.ReviewService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Book Endpoints")
public class BookController {
    private final ImplBookService bookService;
    private final ImplInventoryService inventoryService;
    private final ImplCategoryService categoryService;
    private final ReviewService reviewService;

    @Autowired
    public BookController(ImplBookService bookService,
                          ImplInventoryService inventoryService,
                          ImplCategoryService categoryService, ReviewService reviewService) {
        this.bookService = bookService;
        this.inventoryService = inventoryService;
        this.categoryService = categoryService;
        this.reviewService = reviewService;
    }

    // Get Requests for book entity
    @GetMapping("/books")
    @Operation(summary = "Get list of all available books")
    public List<Book> findAllBooks() {
        return bookService.findAllBooks();
    }
    @Operation(summary = "Get book by id")
    @GetMapping("/books/{id}")
    public Book findBook(@PathVariable Long id) {
        return bookService.findBookById(id);
    }
    @Operation(summary = "Get list of books sorted ascending")
    @GetMapping("/books/sortedbytitle")
    public List<Book> getBooksByOrderAsc() {
        return bookService.findAllBooksOrderByTitleAsc();
    }

    @Operation(summary = "Get list of books sorted descending")
    @GetMapping("/books/sortedbytitledesc")
    public List<Book> getBooksByOrderDesc() {
        return bookService.findAllBooksOrderByTitleDesc();
    }

    @Operation(summary = "Get list of books by a given category name")
    @GetMapping("/books/category/{name}")
    public List<Book> findBooksByCategory(@PathVariable String name) {
        return bookService.findBooksByCategoryName(name);
    }
    @Operation(summary = "Get list of books by a given author name")
    @GetMapping("/books/author/{name}")
    public List<Book> findBooksByAuthor(@PathVariable String name) {
        return bookService.findBooksByAuthor(name);
    }
    @Hidden
    @GetMapping("/books/incrementViews/{id}")
    public void findBookIncrementViews(@PathVariable Long id) {
        bookService.incrementViews(id);
    }
    @Hidden
    @GetMapping("/books/search/{keyword}")
    public List<Book> findBooksByAllFields(@PathVariable("keyword") String keyword) {
        return bookService.findBooksByAllFields(keyword);
    }
    @Operation(summary = "Get list of favorite books of a given username")
    @GetMapping("/books/favoritesbyuser/{username}")
    public List<Book> getFavoriteBooksByUser(@PathVariable String username) {
        return bookService.getFavoriteBooksByUser(username);
    }
    // GET inventory of given book id
    @Operation(summary = "Get inventory entry of a book")
    @GetMapping("/books/inventory/{id}")
    public Inventory getBookInventory(@PathVariable long id) {
        return inventoryService.getEntry(id);
    }
    // GET reviews of given book id
    @Operation(summary = "Get the reviews of a given book")
    @GetMapping("/books/reviews/{id}")
    public List<Review> getBookReviews(@PathVariable long id) {
        Book book = bookService.findBookById(id);
        return reviewService.findAllReviewsByBook(book);
    }
    @Operation(summary = "List all categories")
    @GetMapping("/categories")
    public List<Category> findAllCategories() {
        return categoryService.findAllCategories();
    }

    // POST requests for Book entity
    @Operation(summary = "Add a new book")
    @PostMapping(value = "/books")
    public Book addBook(@RequestBody BookRequestBody reqBody){
        // Add author and category if they don't exist
        return bookService.saveBook(reqBody);
    }
    @Operation(summary = "Add book to user's favorite books")
    @PostMapping("/books/favorite")
    public Book addBookToFavorites(@RequestBody FavoriteBook reqBody) {
        return bookService.addBookToFavorites(reqBody.getBookId(), reqBody.getUsername(), reqBody.isAddOrRemove());
    }
    @Operation(summary = "Add review to a given book")
    @PostMapping("/books/review")
    public Review addReview(@RequestBody ReviewRequestBody requestBody) {
        return reviewService.addReview(requestBody.getBody(),
                requestBody.getRating(),
                requestBody.getUsername(),
                requestBody.getBookId());
    }
    // PUT requests
    @Operation(summary = "Update Book")
    @PutMapping("/books/{id}")
    public Book editBook(@PathVariable long id, @RequestBody BookRequestBody requestBody) {
        return bookService.editBook(id, requestBody);
    }
    // DELETE requests
    @Operation(summary = "Delete Book")
    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable long id) {
        bookService.deleteBook(id);
    }
    @Hidden
    @DeleteMapping("/inventory/{id}")
    public void deleteInventory(@PathVariable long id) {
        inventoryService.deleteEntry(id);
    }
    @Operation(summary = "Delete review")
    @DeleteMapping("/books/review/{id}")
    public void deleteReview(@PathVariable long id) {
        reviewService.deleteReview(id);
    }
}
