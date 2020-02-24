package selfservicekioskclient;

import ejb.session.stateless.BookOperationControllerRemote;
import ejb.session.stateless.MemberOperationControllerRemote;
import ejb.session.stateless.StaffOperationControllerRemote;
import entity.BookEntity;
import entity.FineEntity;
import entity.LendingEntity;
import entity.MemberEntity;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import util.enumeration.StatusEnum;
import util.exception.BookNotFoundException;
import util.exception.MemberNotFoundException;

class MemberOperationModule {

    private StaffOperationControllerRemote staffOperationControllerRemote;
    private MemberOperationControllerRemote memberOperationControllerRemote;
    private BookOperationControllerRemote bookOperationControllerRemote;
    private MemberEntity currentMemberEntity;

    public MemberOperationModule() {
    }

    public MemberOperationModule(StaffOperationControllerRemote staffOperationControllerRemote, MemberOperationControllerRemote memberOperationControllerRemote, BookOperationControllerRemote bookOperationControllerRemote, MemberEntity currentMemberEntity) {
        this.staffOperationControllerRemote = staffOperationControllerRemote;
        this.memberOperationControllerRemote = memberOperationControllerRemote;
        this.bookOperationControllerRemote = bookOperationControllerRemote;
        this.currentMemberEntity = currentMemberEntity;
    }

    public void menuMemberOperations(int response) {
        if (response == 1) {
            doBorrowBook();
        } else if (response == 2) {
            doViewLentBook();
        } else if (response == 3) {
            doReturnBook();
        } else if (response == 4) {
            doExtendBook();
        } else if (response == 5) {
            doPayFines();
        } else if (response == 6) {
            doSearchBook();
        } else {
            doReserveBook();
        }
    }

    private void doBorrowBook() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Self-Service Kiosk :: Borrow Book ***\n");
        System.out.print("Enter Book ID: ");

        Long response = scanner.nextLong();
        try {
            String identityNum = currentMemberEntity.getIdentityNum();
            if (bookOperationControllerRemote.memberHasExistingFines(identityNum)) {
                System.out.println("Lending failed. You have unpaid fines.");
            } else if (bookOperationControllerRemote.memberBorrowedThreeBooks(identityNum)) {
                System.out.println("Lending failed. You have already lent 3 books.");
            } else if (bookOperationControllerRemote.bookReservedByOtherMember(response, identityNum)) {
                System.out.println("Lending failed. Book " + response + " is reserved by another member with higher priority.");
            } else if (bookOperationControllerRemote.isLoanedByMember(identityNum, response)) {
                System.out.println("Lending failed. You have already lent the book.");
            } else if (!bookOperationControllerRemote.isAvailable(response)) {
                System.out.println("Lending failed. Book is loaned by another member.");
            } else {
                List<LendingEntity> lendings = currentMemberEntity.getLendings();
                for (int i = 0; i < lendings.size(); i++) {
                    if (lendings.get(i).getBook().getId().equals(response) && lendings.get(i).getStatus() == StatusEnum.RESERVED) {
                        bookOperationControllerRemote.deleteReservation(lendings.get(i).getId());
                    }
                }
                bookOperationControllerRemote.lendBook(response, currentMemberEntity.getIdentityNum());
                Date dueDate = bookOperationControllerRemote.getBookAvailability(response);
                System.out.printf("Successfully lent book. Due Date: %1$tY-%1$tm-%1$td. %n", dueDate.getTime());
            }
        } catch (MemberNotFoundException ex) {
            System.out.println("An error has occurred while retrieving member: " + ex.getMessage() + "\n");
        } catch (BookNotFoundException ex) {
            System.out.println("An error has occurred while retrieving book: " + ex.getMessage() + "\n");
        }
    }

    private void doViewLentBook() {
        System.out.println("*** Self-Service Kiosk :: View Lent Books ***\n");
        System.out.println("Currently Lent Books:");
        System.out.printf("%2s |%40s |%11s %n", "Id", "Title", "Due Date");
        try {
            List<LendingEntity> lendings = bookOperationControllerRemote.viewLentBooks(currentMemberEntity.getIdentityNum());
            for (int i = 0; i < lendings.size(); i++) {
                BookEntity bookEntity = lendings.get(i).getBook();
                System.out.printf("%2s |%40s | %3$tY-%3$tm-%3$td %n", bookEntity.getId(), bookEntity.getTitle(), lendings.get(i).getDueDate().getTime());
            }
        } catch (MemberNotFoundException ex) {
            System.out.println("An error has occurred while retrieving member: " + ex.getMessage() + "\n");
        }
    }

    private void doReturnBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Self-Service Kiosk :: Return Book ***\n");
        System.out.println("Currently Lent Books:");
        System.out.printf("%2s |%40s |%11s %n", "Id", "Title", "Due Date");
        try {
            List<LendingEntity> lendings = bookOperationControllerRemote.viewLentBooks(currentMemberEntity.getIdentityNum());
            if (lendings.size() > 0) {
                for (int i = 0; i < lendings.size(); i++) {
                    BookEntity bookEntity = lendings.get(i).getBook();
                    System.out.printf("%2s |%40s | %3$tY-%3$tm-%3$td %n", bookEntity.getId(), bookEntity.getTitle(), lendings.get(i).getDueDate().getTime());
                }
                System.out.println("");
                System.out.println("Enter Book to Return> ");
                Long response = scanner.nextLong();
                for (int i = 0; i < lendings.size(); i++) {
                    if (Objects.equals(lendings.get(i).getBook().getId(), response)) {
                        bookOperationControllerRemote.returnBook(lendings.get(i).getId());
                        System.out.println("Book successfully returned.");
                        return;
                    }
                }
                System.out.println("Invalid book ID");
            } else {
                System.out.println("No books lent");
            }
        } catch (MemberNotFoundException ex) {
            System.out.println("An error has occurred while retrieving member: " + ex.getMessage() + "\n");
        }
    }

    private void doExtendBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("*** Self-Service Kiosk :: Extend Book ***\n");
        System.out.println("Currently Lent Books:");
        System.out.printf("%2s |%40s |%11s %n", "Id", "Title", "Due Date");
        try {
            List<LendingEntity> lendings = bookOperationControllerRemote.viewLentBooks(currentMemberEntity.getIdentityNum());
            if (lendings.size() > 0) {
                for (int i = 0; i < lendings.size(); i++) {
                    BookEntity bookEntity = lendings.get(i).getBook();
                    System.out.printf("%2s |%40s | %3$tY-%3$tm-%3$td %n", bookEntity.getId(), bookEntity.getTitle(), lendings.get(i).getDueDate().getTime());
                }
                System.out.println("");
                System.out.println("Enter Book to Extend> ");
                Long response = scanner.nextLong();
                for (int i = 0; i < lendings.size(); i++) {
                    if (Objects.equals(lendings.get(i).getBook().getId(), response)) {
                        if (bookOperationControllerRemote.isReserved(response)) {
                            System.out.println("Cannot extend book: The book has been reserved.");
                            return;
                        } else if (bookOperationControllerRemote.hasFine(currentMemberEntity.getIdentityNum())) {
                            System.out.println("Cannot extend book: You still have an outstanding fine.");
                            return;
                        } else if (bookOperationControllerRemote.isOverdue(response)) {
                            System.out.println("Cannot extend book: The book is already overdue.");
                            return;
                        } else {
                            System.out.printf("Book successfully extended. New due date: %1$tY-%1$tm-%1$td %n", bookOperationControllerRemote.extendBook(lendings.get(i).getId()));
                            return;
                        }
                    }
                }
                System.out.println("Invalid book ID");
            } else {
                System.out.println("No books lent");
            }
        } catch (MemberNotFoundException ex) {
            System.out.println("An error has occurred while retrieving member: " + ex.getMessage() + "\n");
        }
    }

    private void doPayFines() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Self-Service Kiosk :: Pay Fines ***\n");
        System.out.println("Unpaid Fines for Member:");
        System.out.printf("%2s |%6s %n", "Id", "Amount");
        List<FineEntity> fines;
        try {
            fines = bookOperationControllerRemote.getFines(currentMemberEntity.getIdentityNum());
            for (int i = 0; i < fines.size(); i++) {
                FineEntity fineEntity = fines.get(i);
                System.out.printf("%2s | %6s %n", fineEntity.getId(), ("$" + fineEntity.getAmount().setScale(2)));
            }

            System.out.println("");
            if (fines.size() > 0) {
                System.out.println("Enter Fine ID to Settle> ");
                Long fineId = scanner.nextLong();
                boolean hasFine = false;
                for (int i = 0; i < fines.size(); i++) {
                    FineEntity fine = fines.get(i);
                    if (fine.getId().equals(fineId)) {
                        scanner.nextLine();
                        System.out.println("Enter Name of Card> ");
                        String name = scanner.nextLine();
                        System.out.println("Enter Card Number> ");
                        String cardNum = scanner.nextLine();
                        System.out.println("Enter Card Expiry (MMYYYY)> ");
                        String expiry = scanner.nextLine();
                        System.out.println("Enter Pin> ");
                        String pin = scanner.nextLine();
                        bookOperationControllerRemote.payFines(currentMemberEntity.getIdentityNum(), fine.getBookID());
                        System.out.println("Fine successfully paid.");
                        hasFine = true;
                        break;
                    }
                }
                if (!hasFine) {
                    System.out.println("Fine ID is invalid.");
                }
            } else {
                System.out.println("You have no fines.");
            }
        } catch (MemberNotFoundException ex) {
            System.out.println("An error has occurred while retrieving member: " + ex.getMessage() + "\n");
        }
    }

    private void doSearchBook() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Self-Service Kiosk :: Search Book ***\n");
        System.out.print("Enter Title to Search> ");
        String response = scanner.nextLine();
        System.out.println("");
        System.out.printf("%2s |%40s |%13s %n", "Id", "Title", "Availability");

        List<BookEntity> books = bookOperationControllerRemote.searchBook(response);
        for (int i = 0; i < books.size(); i++) {
            BookEntity bookEntity = books.get(i);
            if (bookOperationControllerRemote.isAvailable(bookEntity.getId())) {
                System.out.printf("%2s |%40s | currently available %n", bookEntity.getId(), bookEntity.getTitle());
            } else if (bookOperationControllerRemote.isReserved(bookEntity.getId())) {
                System.out.printf("%2s |%40s | on hold with reservation %n", bookEntity.getId(), bookEntity.getTitle());
            } else {
                System.out.printf("%2s |%40s | Due on %3$tY-%3$tm-%3$td %n", bookEntity.getId(), bookEntity.getTitle(), bookOperationControllerRemote.getBookAvailability(bookEntity.getId()).getTime());
            }
        }
    }

    private void doReserveBook() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Self-Service Kiosk :: Reserve Book ***\n");
        System.out.println("Enter Title to Search> ");
        String response = scanner.nextLine();
        System.out.println("");
        System.out.printf("%2s |%40s |%13s %n", "Id", "Title", "Availability");

        List<BookEntity> books = bookOperationControllerRemote.searchBook(response);
        for (int i = 0; i < books.size(); i++) {
            BookEntity bookEntity = books.get(i);
            if (bookOperationControllerRemote.isAvailable(bookEntity.getId())) {
                System.out.printf("%2s |%40s | currently available (Cannot reserve) %n", bookEntity.getId(), bookEntity.getTitle());
            } else if (bookOperationControllerRemote.isReserved(bookEntity.getId())) {
                System.out.printf("%2s |%40s | on hold with reservation %n", bookEntity.getId(), bookEntity.getTitle());
            } else {
                System.out.printf("%2s |%40s | Due on %3$tY-%3$tm-%3$td %n", bookEntity.getId(), bookEntity.getTitle(), bookOperationControllerRemote.getBookAvailability(bookEntity.getId()).getTime());
            }
        }

        System.out.println("Enter Book ID to Reserve: ");
        Long bookId = scanner.nextLong();
        try {
            if (bookOperationControllerRemote.hasFine(currentMemberEntity.getIdentityNum())) {
                System.out.println("Cannot reserve book: You still have outstanding fines.");
            } else if (bookOperationControllerRemote.isLoanedByMember(currentMemberEntity.getIdentityNum(), bookId)) {
                System.out.println("Cannot reserve book: You have loaned the book.");
            } else if (bookOperationControllerRemote.isReservedByHimself(currentMemberEntity.getIdentityNum(), bookId)) {
                System.out.println("Cannot reserve book: You have reserved the book.");
            } else if (bookOperationControllerRemote.isAvailable(bookId)) {
                System.out.println("Cannot reserve book: The book is available for loan.");
            } else {
                bookOperationControllerRemote.reserveBook(currentMemberEntity.getIdentityNum(), bookId);
                System.out.println("Book successfully reserved.");
            }
        } catch (MemberNotFoundException ex) {
            System.out.println("An error has occurred while retrieving member: " + ex.getMessage() + "\n");
        }
    }
}
