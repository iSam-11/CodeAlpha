import java.util.ArrayList;
import java.util.Scanner;

class Book {
    private String title;
    private String author;
    private int id;

    public Book(String title, String author, int id) {
        this.title = title;
        this.author = author;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getId() {
        return id;
    }
}

class Library {
    private ArrayList<Book> books;

    public Library() {
        books = new ArrayList<>();
    }

    public void addBook(String title, String author, int id) {
        Book book = new Book(title, author, id);
        books.add(book);
        System.out.println("Book added successfully.");
    }

    public void deleteBook(int id) {
        boolean found = false;
        for (Book book : books) {
            if (book.getId() == id) {
                books.remove(book);
                System.out.println("Book deleted successfully.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Book not found.");
        }
    }

    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
        } else {
            System.out.println("Books in the library:");
            for (Book book : books) {
                System.out.println("Title: " + book.getTitle() + ", Author: " + book.getAuthor() + ", ID: " + book.getId());
            }
        }
    }

    public void searchBook(String title) {
        boolean found = false;
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                System.out.println("Book found:");
                System.out.println("Title: " + book.getTitle() + ", Author: " + book.getAuthor() + ", ID: " + book.getId());
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Book not found.");
        }
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);
        int choice;
        
        do {
            System.out.println("--------- Library Management System ---------");
            System.out.println("1. Add Book");
            System.out.println("2. Delete Book");
            System.out.println("3. Display All Books");
            System.out.println("4. Search Book");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    scanner.nextLine(); // Consume newline character
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter book ID: ");
                    int id = scanner.nextInt();
                    library.addBook(title, author, id);
                    break;
                case 2:
                    System.out.print("Enter book ID: ");
                    id = scanner.nextInt();
                    library.deleteBook(id);
                    break;
                case 3:
                    library.displayAllBooks();
                    break;
                case 4:
                    scanner.nextLine(); // Consume newline character
                    System.out.print("Enter book title: ");
                    title = scanner.nextLine();
                    library.searchBook(title);
                    break;
                case 5:
                    System.out.println("Thank you for using the Library Management System.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
            System.out.println();
        } while (choice != 5);
        
        scanner.close();
    }
}