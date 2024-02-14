package com.younes.reskilingproject.bookManagement.restController;

import com.younes.reskilingproject.bookManagement.dto.BookRequestBody;
import com.younes.reskilingproject.bookManagement.entity.Book;
import com.younes.reskilingproject.bookManagement.entity.Category;
import com.younes.reskilingproject.bookManagement.entity.Inventory;
import com.younes.reskilingproject.bookManagement.service.BookService.ImplBookService;
import com.younes.reskilingproject.bookManagement.service.CategoryService.ImplCategoryService;
import com.younes.reskilingproject.bookManagement.service.InventoryService.ImplInventoryService;
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

    @Autowired
    public BookController(ImplBookService bookService,
                          ImplInventoryService inventoryService,
                          ImplCategoryService categoryService) {
        this.bookService = bookService;
        this.inventoryService = inventoryService;
        this.categoryService = categoryService;
    }
    @GetMapping("/books/{id}")
    public Book findBook(@PathVariable Long id) {
        return bookService.findBookById(id);
    }
    @GetMapping("/books/category/{name}")
    public List<Book> findBooksByCategory(@PathVariable String name) {
        return bookService.findBooksByCategoryName(name);
    }
    @GetMapping("/books/author/{name}")
    public List<Book> findBooksByAuthor(@PathVariable String name) {
        return bookService.findBooksByAuthor(name);
    }
    @GetMapping("/books/search/{keyword}")
    public List<Book> findBooksByAllFields(@PathVariable("keyword") String keyword) {
        return bookService.findBooksByAllFields(keyword);
    }
    @GetMapping("/books")
    public List<Book> findAllBooks() {
        return bookService.findAllBooks();
    }
    @GetMapping("/categories")
    public List<Category> findAllCategories() {
        return categoryService.findAllCategories();
    }
    @GetMapping("/books/sortedbytitle")
    public List<Book> getBooksByOrderAsc() {
        return bookService.findAllBooksOrderByTitleAsc();
    }
    @GetMapping("/books/sortedbytitledesc")
    public List<Book> getBooksByOrderDesc() {
        return bookService.findAllBooksOrderByTitleDesc();
    }
    @PostMapping(value = "/books")
    public Book addBook(@RequestBody BookRequestBody reqBody){
        // Add author and category if they don't exist
        System.out.println(reqBody);
        return bookService.saveBook(reqBody.getBook(),
                    reqBody.getAuthorName(), reqBody.getCategoryName(), reqBody.getQuantity());
    }
    @PutMapping("/books/{id}")
    public Book editBook(@PathVariable long id, @RequestBody BookRequestBody requestBody) {
        return bookService.editBook(id, requestBody);
    }
    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable long id) {
        bookService.deleteBook(id);
    }
    // for Inventory routes
    @GetMapping("/books/inventory/{id}")
    public Inventory getBookInventory(@PathVariable long id) {
        return inventoryService.getEntry(id);
    }
    @PostMapping("/books/inventory/{id}")
    public Inventory editBookInventory(@PathVariable long id, @RequestBody int quantity) {
        return inventoryService.updateEntry(id, quantity);
    }
    @DeleteMapping("/inventory/{id}")
    public void deleteInventory(@PathVariable long id) {
        inventoryService.deleteEntry(id);
    }
}
