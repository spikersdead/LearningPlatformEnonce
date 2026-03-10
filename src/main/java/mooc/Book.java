package mooc;

public class Book {

    private String title;
    private String editor;

    public Book(String title, String editor) {
        if (title == null || editor == null) {
            throw new IllegalArgumentException("Le titre et l'éditeur ne peuvent pas être null");
        }
        this.title = title;
        this.editor = editor;
    }

    public String getTitle() {
        return title;
    }

    public String getEditor() {
        return editor;
    }

}
