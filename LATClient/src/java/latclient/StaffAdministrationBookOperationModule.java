/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latclient;

import ejb.session.stateless.BookOperationControllerRemote;
import entity.BookEntity;
import entity.MemberEntity;
import java.util.List;
import java.util.Scanner;
import util.exception.BookNotFoundException;
import util.exception.MemberNotFoundException;

/**
 *
 * @author benjaminchin
 */
public class StaffAdministrationBookOperationModule {
    
    private BookOperationControllerRemote bookOperationControllerRemote;

    public StaffAdministrationBookOperationModule(BookOperationControllerRemote bookOperationControllerRemote) {
        this.bookOperationControllerRemote = bookOperationControllerRemote;
    }
    
    public void menuBookManagement() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("*** ILS :: Administration Operation :: Book Management ***\n");
            System.out.println("1: Add Book");
            System.out.println("2: View Book Details");
            System.out.println("3: Update Book");
            System.out.println("4: Delete BOok");
            System.out.println("5: View All Books");
            System.out.println("6: Back\n");
            response = 0;
            
            while(response < 1 || response > 6)
            {
                System.out.print("> ");

                response = scanner.nextInt();
                if(response == 6) {
                    break;
                }
                
                switch(response) {
                    case 1:
                        doAddBook();
                        break;
                    case 2:
                        doViewBookDetails();
                        break;
                    case 3:
                        doUpdateBook();
                        break;
                    case 4:
                        doDeleteBook();
                        break;
                    case 5:
                        doViewAllBooks();
                        break;
                    default:
                        System.out.println("Invalid option, please try again!\n");
                }
            }
            
            if(response == 6) {
                break;
            }
        }
    }
    
    private void doAddBook() {
        Scanner scanner = new Scanner(System.in);
        BookEntity bookEntity = new BookEntity();
         
        try {
            System.out.println("*** ILS :: Book Management :: Add Book ***\n");
            System.out.print("Enter Title> ");
            bookEntity.setTitle(scanner.nextLine().trim());
            System.out.print("Enter ISBN> ");
            bookEntity.setIsbn(scanner.nextLine().trim());
            System.out.print("Enter Year> ");
            bookEntity.setYr(scanner.nextInt());
        
            bookOperationControllerRemote.createNewBook(bookEntity);
            System.out.println("Book has been registered successfully!\n");
        }
        catch (Exception ex) {
            System.out.println("An error has occurred while creating the new book: " + ex.getMessage() + "\n");
        }
    }
    
    private void doViewBookDetails() {
        System.out.println("*** ILS :: Book Management :: View Book Details ***\n");
        Scanner scanner = new Scanner(System.in);
        
        try {
            Long bookId = 0L;
            System.out.print("Enter Book ID> ");
            bookId = scanner.nextLong();
            scanner.nextLine();
            
            BookEntity bookEntity = bookOperationControllerRemote.retrieveBookById(bookId);
            
            String format = "%-4s |%-50s |%-10s |%-4s %n";
            System.out.format(format,"Id","Title","ISBN","Year");
            System.out.format(format,bookEntity.getId(),bookEntity.getTitle(),bookEntity.getIsbn(),bookEntity.getYr());
            
            System.out.print("Press ENTER to continue> ");
            scanner.nextLine();
        }
        catch(BookNotFoundException ex) {
            System.out.println("Book ID entered does not belong to an existing book.\n");
        }
    }
    
    private void doUpdateBook() {
        Scanner scanner = new Scanner(System.in);
        BookEntity bookEntity = new BookEntity();
         
        try {
            System.out.println("*** ILS :: Book Management :: Update Book ***\n");
            System.out.print("Enter Book ID to Update> ");
            bookEntity.setId(scanner.nextLong());
            scanner.nextLine();
            System.out.print("Enter Title> ");
            bookEntity.setTitle(scanner.nextLine().trim());
            System.out.print("Enter ISBN> ");
            bookEntity.setIsbn(scanner.nextLine().trim());
            System.out.print("Enter Year> ");
            bookEntity.setYr(scanner.nextInt());
            
            bookOperationControllerRemote.updateBook(bookEntity);
            System.out.println("Book has been updated successfully!\n");
        }
        catch (Exception ex) {
            System.out.println("An error has occurred while updating the book: " + ex.getMessage() + "\n");
        }
    }
    
    private void doDeleteBook() {
        System.out.println("*** ILS :: Book Management :: Delete Book ***\n");
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.print("Enter Book ID> ");
            Long bookId = scanner.nextLong();
            
            if (bookOperationControllerRemote.deleteBook(bookId)) {
                System.out.println("Book has been deleted successfully!\n");
            } else {
                System.out.println("Book " + bookId +" does not exist.");
            }
        }
        catch(Exception ex) {
            System.out.println("An error has occurred while deleting the book: " + ex.getMessage() + "\n");
        }
    }
    
    private void doViewAllBooks() {
        System.out.println("*** ILS :: Book Management :: View All Books ***\n");
        Scanner scanner = new Scanner(System.in);
        
        try {
            
            List<BookEntity> bookEntities = bookOperationControllerRemote.retrieveAllBooks();
            
            String format = "%-4s |%-50s |%-10s |%-4s %n";
            System.out.format(format,"Id","Title","ISBN","Year");
            bookEntities.forEach((bookEntity) -> {
                System.out.format(format,bookEntity.getId(),bookEntity.getTitle(),bookEntity.getIsbn(),bookEntity.getYr());
            });
            
            System.out.print("Press ENTER to continue> ");
            scanner.nextLine();
        }
        catch(Exception ex) {
            System.out.println("An error has occurred while retrieving books: " + ex.getMessage() + "\n");
        }
    }
}
