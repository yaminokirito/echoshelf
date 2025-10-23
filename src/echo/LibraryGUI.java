package echo;

import echo.models.Book;
import echo.models.User;
import echo.fines.FixedFineStrategy;
import echo.notify.ConsoleNotificationService;
import echo.core.Library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.List;

public class LibraryGUI extends JFrame {
    private Library library;
    private JTextArea outputArea;
    private JTextField searchField, userField, isbnField, daysField;

    public LibraryGUI() {
        setTitle("EchoShelf - Library Management");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialize library
        library = new Library(new FixedFineStrategy(2.0), new ConsoleNotificationService());
        loadBooks();
        library.addUser(new User("u1", "Kavya"));
        library.addUser(new User("u2", "Laya"));

        // Top Panel: Search bar
        JPanel top = new JPanel();
        top.add(new JLabel("Search:"));
        searchField = new JTextField(20);
        JButton searchBtn = new JButton("Go");
        searchBtn.addActionListener(e -> searchBooks());
        top.add(searchField);
        top.add(searchBtn);

        // Center: Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(outputArea);

        // Bottom Panel: Actions
        JPanel bottom = new JPanel(new GridLayout(2, 1));

        JPanel borrowPanel = new JPanel();
        borrowPanel.add(new JLabel("User ID:"));
        userField = new JTextField(5);
        borrowPanel.add(userField);
        borrowPanel.add(new JLabel("ISBN:"));
        isbnField = new JTextField(10);
        borrowPanel.add(isbnField);
        JButton borrowBtn = new JButton("Borrow");
        borrowBtn.addActionListener(e -> borrowBook());
        JButton returnBtn = new JButton("Return");
        returnBtn.addActionListener(e -> returnBook());
        borrowPanel.add(borrowBtn);
        borrowPanel.add(returnBtn);

        JPanel finePanel = new JPanel();
        finePanel.add(new JLabel("Days Kept:"));
        daysField = new JTextField(5);
        finePanel.add(daysField);

        bottom.add(borrowPanel);
        bottom.add(finePanel);

        add(top, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        showAllBooks();
    }

    private void loadBooks() {
        try (BufferedReader br = new BufferedReader(new FileReader("books.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    library.addBook(new Book(parts[0].trim(), parts[1].trim(), parts[2].trim()));
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "books.csv not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showAllBooks() {
        outputArea.setText("--- All Books ---\n");
        for (Book b : library.listAll()) {
            outputArea.append(b.toString() + "\n");
        }
    }

    private void searchBooks() {
        String q = searchField.getText().trim();
        outputArea.setText("--- Search Results for '" + q + "' ---\n");
        List<Book> results = library.searchByTitle(q);
        if (results.isEmpty()) {
            outputArea.append("No books found.\n");
        } else {
            for (Book b : results) outputArea.append(b.toString() + "\n");
        }
    }

    private void borrowBook() {
        String userId = userField.getText().trim();
        String isbn = isbnField.getText().trim();
        if (userId.isEmpty() || isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter both User ID and ISBN.");
            return;
        }
        String msg = library.borrowBook(userId, isbn);
        outputArea.append("\n[Borrow Result] " + msg + "\n");
        showAllBooks();
    }

    private void returnBook() {
        String userId = userField.getText().trim();
        String isbn = isbnField.getText().trim();
        int daysKept = 14;
        try {
            daysKept = Integer.parseInt(daysField.getText().trim());
        } catch (Exception ignored) {}

        String msg = library.returnBook(userId, isbn, daysKept);
        outputArea.append("\n[Return Result] " + msg + "\n");
        showAllBooks();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibraryGUI().setVisible(true));
    }
}
