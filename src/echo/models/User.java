package echo.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String name;
    private List<String> borrowedIsbns;

    public User(String id, String name){
        this.id = id;
        this.name = name;
        this.borrowedIsbns = new ArrayList<>();
    }

    public String getId(){ return id; }
    public String getName(){ return name; }
    public List<String> getBorrowed(){ return borrowedIsbns; }

    public void borrow(String isbn){
        borrowedIsbns.add(isbn);
    }

    public void returned(String isbn){
        borrowedIsbns.remove(isbn);
    }

    @Override
    public String toString(){
        return String.format("%s - %s (borrowed: %d)", id, name, borrowedIsbns.size());
    }
}
