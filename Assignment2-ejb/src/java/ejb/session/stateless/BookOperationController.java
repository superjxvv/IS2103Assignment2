package ejb.session.stateless;

import entity.BookEntity;
import entity.FineEntity;
import entity.LendingEntity;
import entity.MemberEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.enumeration.StatusEnum;
import util.exception.BookNotFoundException;
import util.exception.MemberNotFoundException;

@Stateless
@Local(BookOperationControllerLocal.class)
@Remote(BookOperationControllerRemote.class)
public class BookOperationController implements BookOperationControllerRemote, BookOperationControllerLocal {

    @PersistenceContext(unitName = "Assignment2-ejbPU")
    private EntityManager em;

    @Override
    public List<LendingEntity> viewLentBooks(String identityNum) throws MemberNotFoundException {
        MemberEntity member = retrieveMemberByIdentityNum(identityNum);
        List<LendingEntity> lendings = member.getLendings();
        List<LendingEntity> lentBooks = new ArrayList<>();
        for (int i = 0; i < lendings.size(); i++) {
            if (lendings.get(i).getStatus().equals(StatusEnum.ACTIVE)) {
                lentBooks.add(lendings.get(i));
            }
        }
        return lentBooks;
    }
    
    @Override
    public List<BookEntity> viewBooksLent(String identityNum) throws MemberNotFoundException {
        List<LendingEntity> lendings = viewLentBooks(identityNum);
        List<BookEntity> books = new ArrayList<>();
        for(int i = 0; i < lendings.size(); i++){
            books.add(lendings.get(i).getBook());
        }
        return books;
    }
    
    @Override
    public Date extendBook(Long lendingId) {
        LendingEntity lending = em.find(LendingEntity.class, lendingId);
        BookEntity book = lending.getBook();
        Calendar c = Calendar.getInstance();
        try {
            if (isReserved(book.getId()) | hasFine(lending.getMember().getIdentityNum()) | isOverdue(lendingId)) {
                return null;
            } else {
                c.setTime(lending.getDueDate());
                c.add(Calendar.DAY_OF_MONTH, 14);
                lending.setDueDate(c.getTime());
                return lending.getDueDate();
            }
        } catch (MemberNotFoundException ex) {
            System.out.println("MemberNotFoundException: " + ex);
        }
        return null;
    }

    @Override
    public boolean isOverdue(Long lendingId) {
        LendingEntity lending = em.find(LendingEntity.class, lendingId);
        return daysOverdue(lending) > 0;
    }

    @Override
    public void returnBook(Long lendingId) {
        LendingEntity lending = em.find(LendingEntity.class, lendingId);
        MemberEntity member = em.find(MemberEntity.class, lending.getMember().getId());
        if (daysOverdue(lending) > 0) {
            FineEntity fine = new FineEntity(lending.getBook().getId(), BigDecimal.valueOf(daysOverdue(lending)));
            em.persist(fine);
            em.flush();
            member.addFines(fine);
        }
        lending.setStatus(StatusEnum.RETURNED);
    }

    @Override
    public List<entity.BookEntity> searchBook(String search) {
        Query query = em.createQuery("SELECT b FROM BookEntity b WHERE b.title LIKE CONCAT('%', :search, '%')");
        query.setParameter("search", search);

        return query.getResultList();
    }

    @Override
    public void reserveBook(String identityNum, Long bookId) throws MemberNotFoundException {
        if (!hasFine(identityNum) && !isLoanedByMember(identityNum, bookId) && !isReservedByHimself(identityNum, bookId) && !isAvailable(bookId)) {
            BookEntity book = em.find(BookEntity.class, bookId);
            MemberEntity member = retrieveMemberByIdentityNum(identityNum);
            LendingEntity lending = new LendingEntity(book, member, new Date(), StatusEnum.RESERVED);
            member.addLending(lending);
            book.addLending(lending);
            em.persist(lending);
            em.flush();
        }
    }

    @Override
    public boolean isAvailable(Long bookId) {
        BookEntity book = em.find(BookEntity.class, bookId);
        List<LendingEntity> lendingsForBook = book.getLendings();
        for (int i = 0; i < lendingsForBook.size(); i++) {
            if (lendingsForBook.get(i).getStatus() == StatusEnum.ACTIVE) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isReserved(Long bookId) {
        BookEntity bookEntity = em.find(BookEntity.class, bookId);
        List<LendingEntity> lendings = bookEntity.getLendings();
        for (int i = 0; i < lendings.size(); i++) {
            if (lendings.get(i).getStatus() == StatusEnum.RESERVED) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isReservedByHimself(String identityNum, Long bookId) throws MemberNotFoundException {
        MemberEntity member = retrieveMemberByIdentityNum(identityNum);
        List<LendingEntity> lendingsForMember = member.getLendings();
        for (int i = 0; i < lendingsForMember.size(); i++) {
            if (Objects.equals(lendingsForMember.get(i).getBook().getId(), bookId) && lendingsForMember.get(i).getStatus() == StatusEnum.RESERVED) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isLoanedByMember(String identityNum, Long bookId) throws MemberNotFoundException {
        MemberEntity member = retrieveMemberByIdentityNum(identityNum);
        List<LendingEntity> lendingsForMember = member.getLendings();
        for (int i = 0; i < lendingsForMember.size(); i++) {
            if (Objects.equals(lendingsForMember.get(i).getBook().getId(), bookId) && lendingsForMember.get(i).getStatus() == StatusEnum.ACTIVE) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Date getBookAvailability(Long bookId) {
        BookEntity bookEntity = em.find(BookEntity.class, bookId);
        List<LendingEntity> lendings = bookEntity.getLendings();
        for (int i = 0; i < lendings.size(); i++) {
            if (lendings.get(i).getStatus() == StatusEnum.ACTIVE) {
                return lendings.get(i).getDueDate();
            }
        }
        return new Date();
    }

    @Override
    public boolean hasFine(String identityNum) throws MemberNotFoundException {
        MemberEntity member = retrieveMemberByIdentityNum(identityNum);
        return !member.getFines().isEmpty();
    }

    private long daysOverdue(LendingEntity lending) {
        Date date = new Date();
        Date dueDate = lending.getDueDate();
        Long diffInMillies = Math.abs(date.getTime() - dueDate.getTime());
        Long days = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        if (date.compareTo(dueDate) > 0) {
            return days;
        } else {
            return -days;
        }
    }

    @Override
    public MemberEntity retrieveMemberByIdentityNum(String identityNum) throws MemberNotFoundException {
        Query query = em.createQuery("SELECT m FROM MemberEntity m WHERE m.identityNum = :inIdentityNum");
        query.setParameter("inIdentityNum", identityNum);
        try {
            MemberEntity member = (MemberEntity) query.getSingleResult();
            member.getFines().size();
            member.getLendings().size();
            return member;
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new MemberNotFoundException("Member Identity Number " + identityNum + " does not exist!");
        }
    }

    @Override
    public BookEntity createNewBook(BookEntity newBookEntity) {
        em.persist(newBookEntity);
        em.flush();

        return newBookEntity;
    }

    @Override
    public BookEntity retrieveBookById(Long bookId) throws BookNotFoundException {
        BookEntity bookEntity = em.find(BookEntity.class, bookId);

        if (bookEntity != null) {
            bookEntity.getLendings().size();
            return bookEntity;
        } else {
            throw new BookNotFoundException("Book ID " + bookId + " does not exist!");
        }
    }

    @Override
    public List<BookEntity> retrieveAllBooks() {
        Query query = em.createQuery("SELECT s FROM BookEntity s");
        List<BookEntity> books = query.getResultList();

        return books;
    }

    @Override
    public void updateBook(BookEntity bookEntity) {
        em.merge(bookEntity);
    }

    @Override
    public boolean deleteBook(Long bookId) {
        BookEntity bookEntity = em.find(BookEntity.class, bookId);
        if (bookEntity != null) {
            em.remove(bookEntity);
            return true;
        }
        return false;
    }

    @Override
    public Boolean memberHasExistingFines(String identityNum) throws MemberNotFoundException {
        MemberEntity member = retrieveMemberByIdentityNum(identityNum);
        List<FineEntity> fines = member.getFines();
        return !fines.isEmpty();
    }

    @Override
    public Boolean memberBorrowedThreeBooks(String identityNum) throws MemberNotFoundException {
        MemberEntity member = retrieveMemberByIdentityNum(identityNum);
        List<LendingEntity> lendings = member.getLendings();
        int count = 0;
        for (int i = 0; i < lendings.size(); i++) {
            if (lendings.get(i).getStatus() == StatusEnum.ACTIVE) {
                count++;
            }
        }
        return count == 3;
    }

    @Override
    public Boolean bookReservedByOtherMember(Long bookId, String identityNum) throws BookNotFoundException {
        BookEntity book = retrieveBookById(bookId);
        List<LendingEntity> lendings = book.getLendings();

        for (int i = 0; i < lendings.size(); i++) {
            if (lendings.get(i).getStatus() == StatusEnum.RESERVED) {
                return !lendings.get(i).getMember().getIdentityNum().equals(identityNum);
            }
        }
        return false;
    }

    @Override
    public void lendBook(Long bookId, String identityNum) throws BookNotFoundException, MemberNotFoundException {
        try {
            MemberEntity member = retrieveMemberByIdentityNum(identityNum);
            BookEntity book = retrieveBookById(bookId);

            Date loanDate = new Date();
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, 14);
            Date dueDate = c.getTime();
            LendingEntity lending = new LendingEntity(book, member, loanDate, StatusEnum.ACTIVE, dueDate);

            book.addLending(lending);
            member.addLending(lending);
            //updateLending
            em.persist(lending);
            em.flush();
        } catch (BookNotFoundException ex) {
            throw new BookNotFoundException("Book id" + bookId + "is not found.");
        } catch (MemberNotFoundException ex) {
            throw new MemberNotFoundException("Member" + identityNum + "does not exist.");
        }
    }

    @Override
    public List<FineEntity> getFines(String identityNum) throws MemberNotFoundException {
        MemberEntity member = retrieveMemberByIdentityNum(identityNum);
        List<FineEntity> fines = member.getFines();
        List<FineEntity> newFines = new ArrayList<>();
        for (int i = 0; i < fines.size(); i++) {
            newFines.add(fines.get(i));
        }
        List<LendingEntity> lendings = member.getLendings();
        for (int i = 0; i < lendings.size(); i++) {
            Long dayOverdue = daysOverdue(lendings.get(i));
            if (dayOverdue > 0 && lendings.get(i).getStatus() == StatusEnum.ACTIVE) {
                FineEntity fine = new FineEntity(lendings.get(i).getBook().getId(), new BigDecimal(dayOverdue));
                newFines.add(fine);
            }
        }
        return newFines;
    }

    @Override
    public void payFines(String identityNum, Long bookId) {
        try {
            MemberEntity member = retrieveMemberByIdentityNum(identityNum);
            List<FineEntity> fines = member.getFines();
            FineEntity fine = null;

            for (int i = 0; i < fines.size(); i++) {
                fine = em.find(FineEntity.class, fines.get(i).getId());
                if (Objects.equals(fine.getBookID(), bookId)) {
                    //updateMember
                    fines.remove(i);
                    member.setFines(fines);
                    //updateMember(member)
                    break;
                }
            }

            if (fine == null) {
                List<LendingEntity> lendings = member.getLendings();
                Boolean borrowed = false;
                for (int i = 0; i < lendings.size(); i++) {
                    if (lendings.get(i).getBook().getId().equals(bookId)) {
                        if (daysOverdue(lendings.get(i)) < 1) {
                            System.out.println("Book is not overdue.");
                        } else {
                            System.out.println("Book must be returned before the fine can be paid.");
                        }
                        borrowed = true;
                        break;
                    }
                }
                if (!borrowed) {
                    System.out.println("Book " + bookId + "is not borrowed by member " + identityNum);
                }
            }
        } catch (MemberNotFoundException ex) {
            System.out.println("Member identity number " + identityNum + " does not exist.");
        }
    }

    @Override
    public List<LendingEntity> getReservations(Long bookId) throws BookNotFoundException {
        BookEntity book = retrieveBookById(bookId);
        List<LendingEntity> lendings = book.getLendings();
        List<LendingEntity> reservations = new ArrayList<>();

        for (int i = 0; i < lendings.size(); i++) {
            if (lendings.get(i).getStatus() == StatusEnum.RESERVED) {
                reservations.add(lendings.get(i));
            }
        }
        return reservations;
    }

    @Override
    public void deleteReservations() throws BookNotFoundException {
        Query query = em.createQuery("SELECT l from LendingEntity l WHERE l.status = :inStatus");
        query.setParameter("inStatus", StatusEnum.RESERVED);
        List<LendingEntity> lendings = query.getResultList();
        for (int i = 0; i < lendings.size(); i++) {
            lendings.get(i).getBook().removeLending(lendings.get(i));
            lendings.get(i).getMember().removeLending(lendings.get(i));
            lendings.get(i).setBook(null);
            lendings.get(i).setMember(null);
            em.remove(lendings.get(i));
        }
    }

    @Override
    public void deleteReservation(Long reservationId) throws BookNotFoundException {
        Query query = em.createQuery("SELECT l from LendingEntity l WHERE l.id = :id AND l.status = :inStatus");
        query.setParameter("id", reservationId);
        query.setParameter("inStatus", StatusEnum.RESERVED);
        List<LendingEntity> lendings = query.getResultList();
        for (int i = 0; i < lendings.size(); i++) {
            lendings.get(i).getBook().removeLending(lendings.get(i));
            lendings.get(i).getMember().removeLending(lendings.get(i));
            lendings.get(i).setBook(null);
            lendings.get(i).setMember(null);
            em.remove(lendings.get(i));
        }
    }
}
