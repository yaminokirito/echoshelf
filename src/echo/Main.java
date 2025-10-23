package echo;

import echo.models.Book;
import echo.models.User;
import echo.fines.FixedFineStrategy;
import echo.notify.ConsoleNotificationService;
import echo.core.Library;
import java.io.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        Library lib = new Library(new FixedFineStrategy(2.0), new ConsoleNotificationService());

        // Load sample books from CSV
        try(BufferedReader br = new BufferedReader(new FileReader("books.csv"))){
            String line;
            while((line = br.readLine()) != null){
                String[] parts = line.split(",");
                if(parts.length >= 3){
                    lib.addBook(new Book(parts[0].trim(), parts[1].trim(), parts[2].trim()));
                }
            }
        } catch(FileNotFoundException e){
            System.out.println("books.csv not found in working directory. Proceeding with empty library.");
        }

        // Create users
        lib.addUser(new User("u1", "Kavya"));
        lib.addUser(new User("u2", "Laya"));

        System.out.println("--- All Books ---");
        for(Book b : lib.listAll()){
            System.out.println(b);
        }

        System.out.println("\n--- Search 'Java' ---");
        List<Book> results = lib.searchByTitle("Java");
        for(Book b : results) System.out.println(b);

        System.out.println("\n--- Borrow Flow ---");
        System.out.println(lib.borrowBook("u1", "978-0135166307"));
        System.out.println(lib.borrowBook("u1", "978-0135166307")); // try again to show fail

        System.out.println("\n--- Return Flow (kept 20 days) ---");
        System.out.println(lib.returnBook("u1", "978-0135166307", 20));

        System.out.println("\n--- Final Book List ---");
        for(Book b : lib.listAll()){
            System.out.println(b);
        }
    }
}
