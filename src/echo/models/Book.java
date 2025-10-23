package echo.models;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private boolean borrowed;

    public Book(String isbn, String title, String author){
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.borrowed = false;
    }

    public String getIsbn(){ return isbn; }
    public String getTitle(){ return title; }
    public String getAuthor(){ return author; }
    public boolean isBorrowed(){ return borrowed; }
    public void setBorrowed(boolean b){ this.borrowed = b; }

    @Override
    public String toString(){
        return String.format("%s | %s | %s | borrowed=%s", isbn, title, author, borrowed);
    }
}