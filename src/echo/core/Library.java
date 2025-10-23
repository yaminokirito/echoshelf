package echo.core;

import echo.models.Book;
import echo.models.User;
import echo.fines.FineStrategy;
import echo.notify.NotificationService;
import java.util.*;

public class Library {
    private Map<String, Book> booksByIsbn = new HashMap<>();
    private Map<String, User> usersById = new HashMap<>();
    private FineStrategy fineStrategy;
    private NotificationService notifier;
    private int borrowLimit = 3;

    public Library(FineStrategy fineStrategy, NotificationService notifier){
        this.fineStrategy = fineStrategy;
        this.notifier = notifier;
    }

    public void addBook(Book b){
        booksByIsbn.put(b.getIsbn(), b);
    }

    public Book findByIsbn(String isbn){
        return booksByIsbn.get(isbn);
    }

    public List<Book> searchByTitle(String q){
        List<Book> res = new ArrayList<>();
        for(Book b : booksByIsbn.values()){
            if(b.getTitle().toLowerCase().contains(q.toLowerCase())){
                res.add(b);
            }
        }
        return res;
    }

    public void addUser(User u){
        usersById.put(u.getId(), u);
    }

    public String borrowBook(String userId, String isbn){
        User u = usersById.get(userId);
        if(u == null) return "User not found";
        Book b = booksByIsbn.get(isbn);
        if(b == null) return "Book not found";
        if(b.isBorrowed()) return "Book already borrowed";
        if(u.getBorrowed().size() >= borrowLimit) return "Borrow limit reached";

        b.setBorrowed(true);
        u.borrow(isbn);
        notifier.notifyUser(userId, "You borrowed: " + b.getTitle());
        return "Success";
    }

    public String returnBook(String userId, String isbn, int daysKept){
        User u = usersById.get(userId);
        Book b = booksByIsbn.get(isbn);
        if(u == null || b == null) return "User or Book not found";
        if(!b.isBorrowed()) return "Book was not marked borrowed";

        b.setBorrowed(false);
        u.returned(isbn);
        int overdue = Math.max(0, daysKept - 14); // 14-day borrow period
        double fine = fineStrategy.calculateFine(overdue);
        String msg = "Returned: " + b.getTitle() + ". Fine: " + fine;
        notifier.notifyUser(userId, msg);
        return msg;
    }

    public List<Book> listAll(){
        return new ArrayList<>(booksByIsbn.values());
    }
}
