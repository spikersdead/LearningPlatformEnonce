package mooc;

import java.util.ArrayList;
import java.util.List;

public class Course
{
    private String label;
    private int volume;
    private List<Book> books;
    
    public Course(String name, int hours, List<Book> books)
    {
        if (null == name)
            throw new IllegalArgumentException("name is null");
        if (hours <= 0)
            throw new IllegalArgumentException("hours is negative");
        if (books == null) 
            throw new IllegalArgumentException("books list is null");
        this.label = name;
        this.volume = hours;
        this.books = new ArrayList<>(books);
    }
    
    public String getLabel() { return label;  }
    
    public int getVolume() { return volume; }

    public List<Book> getBooks() { return books; }
    
    @Override
    public String toString() { return "Course: " + getLabel(); }

}
