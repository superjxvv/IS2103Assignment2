package latclient;

import ejb.session.stateless.BookOperationControllerRemote;
import ejb.session.stateless.MemberOperationControllerRemote;
import entity.FineEntity;
import entity.LendingEntity;
import entity.StaffEntity;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import util.enumeration.StatusEnum;
import util.exception.BookNotFoundException;
import util.exception.MemberNotFoundException;

public class StaffLibraryOperationModule {
    
    private BookOperationControllerRemote bookOperationControllerRemote;
    
    private MemberOperationControllerRemote memberOperationControllerRemote;
    
    private StaffEntity currentStaffEntity;

    public StaffLibraryOperationModule(StaffEntity currentStaffEntity, BookOperationControllerRemote bookOperationControllerRemote, MemberOperationControllerRemote memberOperationControllerRemote) {
        this.bookOperationControllerRemote = bookOperationControllerRemote;
        
        this.currentStaffEntity = currentStaffEntity;
        
        this.memberOperationControllerRemote = memberOperationControllerRemote;
    }
    
    public void menuLibraryOperation() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("*** ILS :: Library Operation ***\n");
            System.out.println("1: Lend Book");
            System.out.println("2: View Lent Books");
            System.out.println("3: Return Book");
            System.out.println("4: Extend Book");
            System.out.println("5: Pay Fines");
            System.out.println("6: Manage Reservations");
            System.out.println("7: Back\n");
            response = 0;
            
            while(response < 1 || response > 7)
            {
                System.out.print("> ");

                response = scanner.nextInt();
                if(response == 7) {
                    break;
                }
                
                switch(response) {
                    case 1:
                        doLendBook();
                        break;
                    case 2:
                        doViewLentBooks();
                        break;
                    case 3:
                        doReturnBook();
                        break;
                    case 4:
                        doExtendBook();
                        break;
                    case 5:
                        doPayFines();
                        break;
                    case 6:
                        doManageReservations();
                        break;
                    default:
                        System.out.println("Invalid option, please try again!\n");
                }
            }
            
            if(response == 7) {
                break;
            }
        }
    }
        
    private void doLendBook() {
        try {
            Scanner scanner = new Scanner(System.in);
            
            System.out.println("*** ILS :: Library Operation :: Lend Book ***\n");
            System.out.print("Enter Member Identity Number> ");
            final String idNumber = scanner.nextLine().trim();
            System.out.print("Enter Book ID> ");
            final Long bookId = scanner.nextLong();
            if (bookOperationControllerRemote.memberHasExistingFines(idNumber)) {
                System.out.println("Lending failed. You have unpaid fines.");
            } else if (bookOperationControllerRemote.memberBorrowedThreeBooks(idNumber)) {
                System.out.println("Lending failed. You have already lent 3 books.");
            } else if (bookOperationControllerRemote.bookReservedByOtherMember(bookId, idNumber)) {
                System.out.println("Lending failed. Book " + bookId + " is reserved by another member with higher priority.");
            } else if (bookOperationControllerRemote.isLoanedByMember(idNumber, bookId)) {
                System.out.println("Lending failed. You have already lent the book.");
            } else if (!bookOperationControllerRemote.isAvailable(bookId)) {
                System.out.println("Lending failed. Book is loaned by another member.");
            } else {
                List<LendingEntity> lendings = memberOperationControllerRemote.retrieveMemberByIdentityNum(idNumber).getLendings();
                for (int i = 0; i < lendings.size(); i++) {
                    if (lendings.get(i).getBook().getId().equals(bookId) && lendings.get(i).getStatus() == StatusEnum.RESERVED) {
                        bookOperationControllerRemote.deleteReservation(lendings.get(i).getId());
                    }
                }
                bookOperationControllerRemote.lendBook(bookId, idNumber);
                Calendar c = Calendar.getInstance();
                c.add(Calendar.DAY_OF_MONTH, 14);
            
                System.out.println("Successfully lent book to member. Due Date: " + new SimpleDateFormat("yyyy-MM-dd").format(c.getTime()) + ".");
            } 
        } catch (MemberNotFoundException ex) {
            System.out.println("An error has occurred while retrieving member: " + ex.getMessage() + "\n");
        } catch (BookNotFoundException ex) {
            System.out.println("An error has occurred while retrieving book: " + ex.getMessage() + "\n");
        }
    }
    
    private void doViewLentBooks() {
        System.out.println("*** ILS :: Library Operation :: View Lent Books ***\n");
        listLentBooksByIdentityNumber();
        System.out.print("Press ENTER to continue> ");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine().trim();
    }
    
    private List<LendingEntity> listLentBooksByIdentityNumber() {
        Scanner scanner = new Scanner(System.in);
        String idNumber = "";
        
        try {
            System.out.print("Enter Member Identity Number> ");
            idNumber = scanner.nextLine().trim();
            
            List<LendingEntity> lendingEntities = bookOperationControllerRemote.viewLentBooks(idNumber);
            
            String format = "%-4s |%-50s |%-10s %n";
            System.out.println("Currently Lent Books:");
            System.out.format(format,"Id","Title","Due Date");
            lendingEntities.forEach((lendingEntity) -> {
                System.out.format(format,lendingEntity.getBook().getId(),lendingEntity.getBook().getTitle(),new SimpleDateFormat("yyyy-MM-dd").format(lendingEntity.getDueDate()));
            });
            return lendingEntities;
        }
        catch(MemberNotFoundException ex) {
            System.out.println("Member Identity Number entered does not belong to an existing member.\n");
        }
        return null;
    }
    
    private void doReturnBook() {
        System.out.println("*** ILS :: Library Operation :: Return Book ***\n");
        List<LendingEntity> lendingEntities = listLentBooksByIdentityNumber();
        
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.print("Enter Book to Return> ");
            final Long bookId = scanner.nextLong();
            
            bookOperationControllerRemote.returnBook(lendingEntities.stream().filter(lending -> bookId.equals(lending.getBook().getId())).findAny().orElse(null).getId());
            System.out.println("Book successfully returned.");
        }
        catch(Exception ex) {
            System.out.println("An error has occurred while executing return book: " + ex.getMessage() + "\n");
        }
    }
    
    private void doExtendBook() {
        System.out.println("*** ILS :: Library Operation :: Extend Book ***\n");
        List<LendingEntity> lendingEntities = listLentBooksByIdentityNumber();
        
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.print("Enter Book to Extend> ");
            final Long bookId = scanner.nextLong();
            Date dueDate = bookOperationControllerRemote.extendBook(lendingEntities.stream().filter(lending -> bookId.equals(lending.getBook().getId())).findAny().orElse(null).getId());
            System.out.println("Book successfully extended. New due date: " + new SimpleDateFormat("yyyy-MM-dd").format(dueDate));
        }
        catch(Exception ex) {
            System.out.println("An error has occurred while executing extend book: " + ex.getMessage() + "\n");
        }
    }
    
    private void doPayFines() {
        System.out.println("*** ILS :: Library Operation :: Pay Fines ***\n");
        Scanner scanner = new Scanner(System.in);
        String idNumber = "";
        int payMode = 0;
        
        try {
            System.out.print("Enter Member Identity Number> ");
            idNumber = scanner.nextLine().trim();
            
            List<FineEntity> fineEntities = bookOperationControllerRemote.getFines(idNumber);
            System.out.println("Unpaid Fines for Member:");
            System.out.println("Id\t|Amount");
            fineEntities.forEach((fineEntity) -> {
                System.out.println(fineEntity.getId() + "\t| $" + fineEntity.getAmount().setScale(2, RoundingMode.HALF_UP));
            });
            
            System.out.print("Enter Fine to Settle> ");
            final Long fineId = scanner.nextLong();
            System.out.print("Select Payment Method (1: Cash, 2: Card)> ");
            payMode = scanner.nextInt();
            
            bookOperationControllerRemote.payFines(idNumber, fineEntities.stream().filter(fine -> fineId.equals(fine.getId())).findAny().orElse(null).getBookID());
            System.out.println("Fine successfully paid.");
        }
        catch(MemberNotFoundException ex) {
            System.out.println("Member Identity Number entered does not belong to an existing member.\n");
        }
        catch(Exception ex) {
            System.out.println("An error has occurred while executing pay fines: " + ex.getMessage() + "\n");
        }
    }
    
    private void doManageReservations() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("*** ILS :: Library Operation :: Manage Reservations ***\n");
            System.out.println("1: View Reservations for Book");
            System.out.println("2: Delete Reservation");
            System.out.println("3: Back\n");
            int response = 0;

            while(response < 1 || response > 3)
            {
                System.out.print("> ");

                response = scanner.nextInt();
                if(response == 3) {
                    break;
                }

                switch(response) {
                    case 1:
                        listReservationsByBookId();
                        break;
                    case 2:
                        doDeleteReservation();
                        break;
                    default:
                        System.out.println("Invalid option, please try again!\n");
                }
            }
            
            if(response == 3) {
                break;
            }
        }
    }
    
    private void listReservationsByBookId() {
        Scanner scanner = new Scanner(System.in);
        Long bookId = 0L;
        
        try {
            System.out.println("*** ILS :: Library Operation :: Manage Reservations :: View Reservations for Book ***\n");
            System.out.print("Enter Book ID> ");
            bookId = scanner.nextLong();
            scanner.nextLine().trim();
            
            List<LendingEntity> lendingEntities = bookOperationControllerRemote.getReservations(bookId);
            
            System.out.println("Current Reservations:");
            System.out.println("Id\t|Member Identity Number");
            lendingEntities.forEach((lendingEntity) -> {
                System.out.println(lendingEntity.getId() + "\t|" + lendingEntity.getMember().getIdentityNum());
            });
            
            System.out.print("Press ENTER to continue> ");
            scanner.nextLine().trim();
        }
        catch(BookNotFoundException ex) {
            System.out.println("Book ID entered does not belong to an existing book.\n");
        }
    }
    
    private void doDeleteReservation() {
        Scanner scanner = new Scanner(System.in);
        Long reservationId = 0L;
        
        try {
            System.out.println("*** ILS :: Library Operation :: Manage Reservations :: Delete Reservation ***\n");
            System.out.print("Enter Reservation ID> ");
            reservationId = scanner.nextLong();
            
            bookOperationControllerRemote.deleteReservation(reservationId);
            System.out.println("Successfully delete reservation.");
        }
        catch(BookNotFoundException ex) {
            System.out.println("Reservation ID entered does not belong to an existing reservation.\n");
        }
    }
    
}
