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
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class BookController {

    private ImplBookService bookService;
    private ImplInventoryService inventoryService;
    private ImplCategoryService categoryService;
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

    @ApiOperation(value = "getbook by id")
    @GetMapping("/books/{id}")
    public Book findBook(@PathVariable Long id) {
        return bookService.findBookById(id);
    }
    @GetMapping("/books/incrementViews/{id}")
    public void findBookIncrementViews(@PathVariable Long id) {
        bookService.incrementViews(id);
    }
    @ApiOperation(value = "getbooks by category name")
    @GetMapping("/books/category/{name}")
    public List<Book> findBooksByCategory(@PathVariable String name) {
        return bookService.findBooksByCategoryName(name);
    }

    @ApiOperation(value = "getbooks by author name")
    @GetMapping("/books/author/{name}")
    public List<Book> findBooksByAuthor(@PathVariable String name) {
        return bookService.findBooksByAuthor(name);
    }

    @ApiOperation(value = "findBooksByAllfields")
    @GetMapping("/books/search/{keyword}")
    public List<Book> findBooksByAllFields(@PathVariable("keyword") String keyword) {
        return bookService.findBooksByAllFields(keyword);
    }

    @ApiOperation(value = "findAllBooks")
    @GetMapping("/books")
    public List<Book> findAllBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping("/books/favoritesbyuser/{username}")
    public List<Book> getFavoriteBooksByUser(@PathVariable String username) {
        return bookService.getFavoriteBooksByUser(username);
    }

    @ApiOperation(value = "getAllCategories")
    @GetMapping("/categories")
    public List<Category> findAllCategories() {
        return categoryService.findAllCategories();
    }
    @ApiOperation(value = "sortBooksBytitle ascending")
    @GetMapping("/books/sortedbytitle")
    public List<Book> getBooksByOrderAsc() {
        return bookService.findAllBooksOrderByTitleAsc();
    }

    @ApiOperation(value = "sortBooksBytitle descending")
    @GetMapping("/books/sortedbytitledesc")
    public List<Book> getBooksByOrderDesc() {
        return bookService.findAllBooksOrderByTitleDesc();
    }

    @ApiOperation(value = "add a book")
    @PostMapping(value = "/books")
    public Book addBook(@RequestBody BookRequestBody reqBody){
        // Add author and category if they don't exist
        System.out.println(reqBody);
        return bookService.saveBook(reqBody.getBook(),
                    reqBody.getAuthorName(), reqBody.getCategoryName(), reqBody.getQuantity());
    }
    @PostMapping("/books/favorite")
    public Book addBookToFavorites(@RequestBody FavoriteBook reqBody) {
        return bookService.addBookToFavorites(reqBody.getBookId(), reqBody.getUsername(), reqBody.isAddOrRemove());
    }
    @ApiOperation(value = "update a book")
    @PutMapping("/books/{id}")
    public Book editBook(@PathVariable long id, @RequestBody BookRequestBody requestBody) {
        return bookService.editBook(id, requestBody);
    }
    @ApiOperation(value = "delete a book")
    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable long id) {
        bookService.deleteBook(id);
    }
    // for Inventory routes

    @ApiOperation(value = "getEntryBook")
    @GetMapping("/books/inventory/{id}")
    public Inventory getBookInventory(@PathVariable long id) {
        return inventoryService.getEntry(id);
    }

    @ApiOperation(value = "delete entry")
    @DeleteMapping("/inventory/{id}")
    public void deleteInventory(@PathVariable long id) {
        inventoryService.deleteEntry(id);
    }

    // Reviews feature
    @GetMapping("/books/reviews/{id}")
    public List<Review> getBookReviews(@PathVariable long id) {
        Book book = bookService.findBookById(id);
        return reviewService.findAllReviewsByBook(book);
    }
    @PostMapping("/books/review")
    public Review addReview(@RequestBody ReviewRequestBody requestBody) {
        return reviewService.addReview(requestBody.getBody(),
                requestBody.getRating(),
                requestBody.getUsername(),
                requestBody.getBookId());
    }
    @DeleteMapping("/books/review/{id}")
    public void deleteReview(@PathVariable long id) {
        reviewService.deleteReview(id);
    }
}
