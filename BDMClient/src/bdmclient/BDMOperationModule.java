package bdmclient;

import java.util.List;
import java.util.Scanner;
import javax.xml.datatype.XMLGregorianCalendar;
import ws.client.BookEntity;
import ws.client.FineEntity;
import ws.client.LendingEntity;
import ws.client.MemberEntity;
import ws.client.MemberNotFoundException_Exception;

public class BDMOperationModule {

    private MemberEntity memberEntity;

    public BDMOperationModule() {
    }

    public BDMOperationModule(MemberEntity memberEntity) {
        this.memberEntity = memberEntity;
    }

    public void viewLentBooks() {
        System.out.println("*** BDM Client :: View Lent Books ***\n");
        System.out.println("Currently Lent Books:");
        System.out.printf("%2s |%40s |%11s %n", "Id", "Title", "Due Date");
        try {
            List<LendingEntity> lendings = viewLentBooks_1(memberEntity.getIdentityNum());
            List<BookEntity> books = viewBooksLent(memberEntity.getIdentityNum());
            for (int i = 0; i < lendings.size(); i++) {
                System.out.printf("%2s |%40s | %3$tY-%3$tm-%3$td %n", books.get(i).getId(), books.get(i).getTitle(), lendings.get(i).getDueDate().toGregorianCalendar().getTime());
            }
        } catch (MemberNotFoundException_Exception ex) {
            System.out.println("An error has occurred while retrieving member: " + ex.getMessage() + "\n");
        }

    }

    public void returnBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** BDM Client :: Return Book ***\n");
        System.out.println("Currently Lent Books:");
        System.out.printf("%2s |%40s |%11s %n", "Id", "Title", "Due Date");
        try {
            List<LendingEntity> lendings = viewLentBooks_1(memberEntity.getIdentityNum());
            List<BookEntity> books = viewBooksLent(memberEntity.getIdentityNum());
            if (lendings.size() > 0) {
                for (int i = 0; i < lendings.size(); i++) {
                    System.out.printf("%2s |%40s | %3$tY-%3$tm-%3$td %n", books.get(i).getId(), books.get(i).getTitle(), lendings.get(i).getDueDate().toGregorianCalendar().getTime());
                }
                System.out.println("");
                System.out.print("Enter Book to Return> ");
                long bookId = scanner.nextLong();
                for (int i = 0; i < books.size(); i++) {
                    if (books.get(i).getId() == bookId) {
                        System.out.println("Please drop to machine complete return.");
                        returnBook_1(lendings.get(i).getId());
                        System.out.println("Book successfully returned.");
                        return;
                    }
                }
                System.out.println("Invalid book ID");
            } else {
                System.out.println("No books lent");
            }
        } catch (MemberNotFoundException_Exception ex) {
            System.out.println("An error has occurred while retrieving member: " + ex.getMessage() + "\n");
        }
    }

    public void extendBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** BDM Client :: Extend Book ***\n");
        System.out.println("Currently Lent Books:");
        System.out.printf("%2s |%40s |%11s %n", "Id", "Title", "Due Date");
        try {
            List<LendingEntity> lendings = viewLentBooks_1(memberEntity.getIdentityNum());
            List<BookEntity> books = viewBooksLent(memberEntity.getIdentityNum());
            if (lendings.size() > 0) {
                for (int i = 0; i < lendings.size(); i++) {
                    System.out.printf("%2s |%40s | %3$tY-%3$tm-%3$td %n", books.get(i).getId(), books.get(i).getTitle(), lendings.get(i).getDueDate().toGregorianCalendar().getTime());
                }
                System.out.println("");
                System.out.print("Enter Book to Extend> ");
                long bookId = scanner.nextLong();
                for (int i = 0; i < books.size(); i++) {
                    if (books.get(i).getId() == bookId) {
                        if (isReserved(bookId)) {
                            System.out.println("Cannot extend book: The book has been reserved.");
                            return;
                        } else if (hasFine(memberEntity.getIdentityNum())) {
                            System.out.println("Cannot extend book: You still have an outstanding fine.");
                            return;
                        } else if (isOverdue(bookId)) {
                            System.out.println("Cannot extend book: The book is already overdue.");
                            return;
                        } else {
                            System.out.printf("Book successfully extended. New due date: %1$tY-%1$tm-%1$td %n", extendBook_1(lendings.get(i).getId()).toGregorianCalendar().getTime());
                            return;
                        }
                    }
                }
                System.out.println("Invalid book ID");
            } else {
                System.out.println("No books lent");
            }
        } catch (MemberNotFoundException_Exception ex) {
            System.out.println("An error has occurred while retrieving member: " + ex.getMessage() + "\n");
        }
    }

    public void payFines() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** BDM Client :: Pay Fines ***\n");
        System.out.println("Unpaid Fines for Member:");
        System.out.printf("%2s |%6s %n", "Id", "Amount");
        List<FineEntity> fines;
        try {
            fines = getFines(memberEntity.getIdentityNum());
            for (int i = 0; i < fines.size(); i++) {
                System.out.printf("%2d |%6s %n", fines.get(i).getId(), ("$" + fines.get(i).getAmount().setScale(2)));
            }
            System.out.println("");
            if (fines.size() > 0) {
                System.out.println("Enter Fine ID to Settle> ");
                Long fineId = scanner.nextLong();
                for (int i = 0; i < fines.size(); i++) {
                    if (fines.get(i).getId().equals(fineId)) {
                        scanner.nextLine();
                        System.out.println("Enter Name of Card> ");
                        String name = scanner.nextLine();
                        System.out.println("Enter Card Number> ");
                        String cardNum = scanner.nextLine();
                        System.out.println("Enter Card Expiry (MMYYYY)> ");
                        String expiry = scanner.nextLine();
                        System.out.println("Enter Pin> ");
                        String pin = scanner.nextLine();
                        payFines_1(memberEntity.getIdentityNum(), fineId);
                        System.out.println("Fine successfully paid.");
                        return;
                    }
                }
                System.out.println("Fine ID is invalid.");
            } else {
                System.out.println("You have no fines.");
            }
        } catch (MemberNotFoundException_Exception ex) {
            System.out.println("MemberNotFoundException_Exception: " + ex);
        }

    }

    public void reserveBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** BDM Client :: Reserve Book ***\n");
        System.out.print("Enter Title to Search> ");
        String search = scanner.nextLine();
        System.out.println("");
        System.out.println("Search Results:");
        System.out.printf("%2s |%40s |%13s %n", "Id", "Title", "Availability");
        List<BookEntity> books = searchBook(search);
        for (int i = 0; i < books.size(); i++) {
            if (isAvailable(books.get(i).getId())) {
                System.out.printf("%2s |%40s | is available (Cannot reserve) %n", books.get(i).getId(), books.get(i).getTitle());
            } else if (isReserved(books.get(i).getId())) {
                System.out.printf("%2s |%40s | on hold with reservation %n", books.get(i).getId(), books.get(i).getTitle());
            } else {
                System.out.printf("%2s |%40s | Due on %3$tY-%3$tm-%3$td %n", books.get(i).getId(), books.get(i).getTitle(), getBookAvailability(books.get(i).getId()).toGregorianCalendar().getTime());
            }
        }
        System.out.println("");
        System.out.println("Enter Book ID to Reserve: ");
        long bookId = scanner.nextLong();
        try {
            if (hasFine(memberEntity.getIdentityNum())) {
                System.out.println("Cannot reserve book: You still have outstanding fines.");
            } else if (isLoanedByMember(memberEntity.getIdentityNum(), bookId)) {
                System.out.println("Cannot reserve book: You have loaned the book.");
            } else if (isReservedByHimself(memberEntity.getIdentityNum(), bookId)) {
                System.out.println("Cannot reserve book: You have reserved the book.");
            } else if (isAvailable(bookId)) {
                System.out.println("Cannot reserve book: The book is available for loan.");
            } else {
                reserveBook_1(memberEntity.getIdentityNum(), bookId);
                System.out.println("Book successfully reserved.");
            }
        } catch (MemberNotFoundException_Exception ex) {
            System.out.println("MemberNotFoundException_Exception: " + ex);
        }

    }

    private static XMLGregorianCalendar extendBook_1(long lendingId) {
        ws.client.BDMWebService_Service service = new ws.client.BDMWebService_Service();
        ws.client.BDMWebService port = service.getBDMWebServicePort();
        return port.extendBook(lendingId);
    }

    private static java.util.List<ws.client.FineEntity> getFines(java.lang.String identityNum) throws MemberNotFoundException_Exception {
        ws.client.BDMWebService_Service service = new ws.client.BDMWebService_Service();
        ws.client.BDMWebService port = service.getBDMWebServicePort();
        return port.getFines(identityNum);
    }

    private static boolean hasFine(java.lang.String identityNum) throws MemberNotFoundException_Exception {
        ws.client.BDMWebService_Service service = new ws.client.BDMWebService_Service();
        ws.client.BDMWebService port = service.getBDMWebServicePort();
        return port.hasFine(identityNum);
    }

    private static boolean isLoanedByMember(java.lang.String identityNum, java.lang.Long bookId) throws MemberNotFoundException_Exception {
        ws.client.BDMWebService_Service service = new ws.client.BDMWebService_Service();
        ws.client.BDMWebService port = service.getBDMWebServicePort();
        return port.isLoanedByMember(identityNum, bookId);
    }

    private static boolean isOverdue(java.lang.Long lendingId) throws MemberNotFoundException_Exception {
        ws.client.BDMWebService_Service service = new ws.client.BDMWebService_Service();
        ws.client.BDMWebService port = service.getBDMWebServicePort();
        return port.isOverdue(lendingId);
    }

    private static boolean isReserved(java.lang.Long bookId) {
        ws.client.BDMWebService_Service service = new ws.client.BDMWebService_Service();
        ws.client.BDMWebService port = service.getBDMWebServicePort();
        return port.isReserved(bookId);
    }

    private static boolean isReservedByHimself(java.lang.String identityNum, java.lang.Long bookId) throws MemberNotFoundException_Exception {
        ws.client.BDMWebService_Service service = new ws.client.BDMWebService_Service();
        ws.client.BDMWebService port = service.getBDMWebServicePort();
        return port.isReservedByHimself(identityNum, bookId);
    }

    private static void payFines_1(java.lang.String identityNumber, java.lang.Long bookId) {
        ws.client.BDMWebService_Service service = new ws.client.BDMWebService_Service();
        ws.client.BDMWebService port = service.getBDMWebServicePort();
        port.payFines(identityNumber, bookId);
    }

    private static void reserveBook_1(java.lang.String identityNum, java.lang.Long bookId) throws MemberNotFoundException_Exception {
        ws.client.BDMWebService_Service service = new ws.client.BDMWebService_Service();
        ws.client.BDMWebService port = service.getBDMWebServicePort();
        port.reserveBook(identityNum, bookId);
    }

    private static void returnBook_1(long lendingId) {
        ws.client.BDMWebService_Service service = new ws.client.BDMWebService_Service();
        ws.client.BDMWebService port = service.getBDMWebServicePort();
        port.returnBook(lendingId);
    }

    private static java.util.List<ws.client.BookEntity> searchBook(java.lang.String search) {
        ws.client.BDMWebService_Service service = new ws.client.BDMWebService_Service();
        ws.client.BDMWebService port = service.getBDMWebServicePort();
        return port.searchBook(search);
    }

    private static java.util.List<ws.client.LendingEntity> viewLentBooks_1(java.lang.String identityNum) throws MemberNotFoundException_Exception {
        ws.client.BDMWebService_Service service = new ws.client.BDMWebService_Service();
        ws.client.BDMWebService port = service.getBDMWebServicePort();
        return port.viewLentBooks(identityNum);
    }

    private static XMLGregorianCalendar getBookAvailability(java.lang.Long bookId) {
        ws.client.BDMWebService_Service service = new ws.client.BDMWebService_Service();
        ws.client.BDMWebService port = service.getBDMWebServicePort();
        return port.getBookAvailability(bookId);
    }

    private static boolean isAvailable(java.lang.Long bookId) {
        ws.client.BDMWebService_Service service = new ws.client.BDMWebService_Service();
        ws.client.BDMWebService port = service.getBDMWebServicePort();
        return port.isAvailable(bookId);
    }

    private static java.util.List<ws.client.BookEntity> viewBooksLent(java.lang.String identityNum) throws MemberNotFoundException_Exception {
        ws.client.BDMWebService_Service service = new ws.client.BDMWebService_Service();
        ws.client.BDMWebService port = service.getBDMWebServicePort();
        return port.viewBooksLent(identityNum);
    }

}
