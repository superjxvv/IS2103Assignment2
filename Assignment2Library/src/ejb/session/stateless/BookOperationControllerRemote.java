package ejb.session.stateless;

import entity.BookEntity;
import entity.FineEntity;
import entity.LendingEntity;
import entity.MemberEntity;
import java.util.Date;
import java.util.List;
import util.exception.BookNotFoundException;
import util.exception.MemberNotFoundException;

public interface BookOperationControllerRemote {

    public List<LendingEntity> viewLentBooks(String identityNum) throws MemberNotFoundException;

    public Date extendBook(Long lendingId);

    public void returnBook(Long lendingId);

    public void reserveBook(String identityNum, Long bookId) throws MemberNotFoundException;

    public boolean isAvailable(Long bookId);

    public boolean isReservedByHimself(String identityNum, Long bookId) throws MemberNotFoundException;

    public boolean isLoanedByMember(String identityNum, Long bookId) throws MemberNotFoundException;

    public boolean hasFine(String identityNum) throws MemberNotFoundException;

    public MemberEntity retrieveMemberByIdentityNum(String identityNum) throws MemberNotFoundException;

    public List<BookEntity> searchBook(String input);

    public BookEntity createNewBook(BookEntity newBookEntity);

    public BookEntity retrieveBookById(Long bookId) throws BookNotFoundException;

    public List<BookEntity> retrieveAllBooks();

    public void updateBook(BookEntity bookEntity);

    public boolean deleteBook(Long bookId);

    public Boolean memberHasExistingFines(String identityNum) throws MemberNotFoundException;

    public Boolean memberBorrowedThreeBooks(String identityNum) throws MemberNotFoundException;

    public Boolean bookReservedByOtherMember(Long bookId, String identityNum) throws BookNotFoundException;

    public void lendBook(Long bookId, String identityNum) throws BookNotFoundException, MemberNotFoundException;

    public List<FineEntity> getFines(String identityNum) throws MemberNotFoundException;

    public void payFines(String identityNum, Long bookId);

    public List<LendingEntity> getReservations(Long bookId) throws BookNotFoundException;

    public void deleteReservations() throws BookNotFoundException;

    public boolean isReserved(Long bookId);

    public boolean isOverdue(Long lendingId);

    public Date getBookAvailability(Long bookId);

    public void deleteReservation(Long reservationId) throws BookNotFoundException;
    
    public List<BookEntity> viewBooksLent(String identityNum) throws MemberNotFoundException;

}
