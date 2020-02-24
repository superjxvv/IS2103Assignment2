package ejb.session.ws;

import ejb.session.stateless.BookOperationControllerLocal;
import ejb.session.stateless.MemberOperationControllerLocal;
import entity.BookEntity;
import entity.FineEntity;
import entity.LendingEntity;
import entity.MemberEntity;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import util.exception.InvalidLoginCredentialException;
import util.exception.MemberNotFoundException;

@WebService(serviceName = "BDMWebService")
@Stateless()
public class BDMWebService {

    @EJB
    private BookOperationControllerLocal bookOperationController;

    @EJB
    private MemberOperationControllerLocal memberOperationController;

    @WebMethod(operationName = "memberLogin")
    public MemberEntity memberLogin(@WebParam(name = "identityNum") String identityNum, @WebParam(name = "securityCode") String securityCode) throws InvalidLoginCredentialException {
        return memberOperationController.memberLogin(identityNum, securityCode);

    }

    @WebMethod(operationName = "extendBook")
    public Date extendBook(@WebParam(name = "lendingId") long lendingId) {
        return bookOperationController.extendBook(lendingId);
    }

    @WebMethod(operationName = "returnBook")
    public void returnBook(@WebParam(name = "lendingId") long lendingId) {
        bookOperationController.returnBook(lendingId);
    }

    @WebMethod(operationName = "viewLentBooks")
    public List<LendingEntity> viewLentBooks(@WebParam(name = "identityNum") String identityNum) throws MemberNotFoundException {
        return bookOperationController.viewLentBooks(identityNum);
    }

    @WebMethod(operationName = "viewBooksLent")
    public List<BookEntity> viewBooksLent(@WebParam(name = "identityNum") String identityNum) throws MemberNotFoundException {
        return bookOperationController.viewBooksLent(identityNum);
    }
    
    @WebMethod(operationName = "payFines")
    public void payFines(@WebParam(name = "identityNumber") String identityNumber, @WebParam(name = "bookId") Long bookId) {
        bookOperationController.payFines(identityNumber, bookId);
    }

    @WebMethod(operationName = "reserveBook")
    public void reserveBook(@WebParam(name = "identityNum") String identityNum, @WebParam(name = "bookId") Long bookId) throws MemberNotFoundException {
        bookOperationController.reserveBook(identityNum, bookId);
    }

    @WebMethod(operationName = "isReserved")
    public boolean isReserved(@WebParam(name = "bookId") Long bookId){
        return bookOperationController.isReserved(bookId);
    }

    @WebMethod(operationName = "isReservedByHimself")
    public boolean isReservedByHimself(@WebParam(name = "identityNum") String identityNum, @WebParam(name = "bookId") Long bookId) throws MemberNotFoundException {
        return bookOperationController.isReservedByHimself(identityNum, bookId);
    }

    @WebMethod(operationName = "isAvailable")
    public boolean isAvailable(@WebParam(name = "bookId") Long bookId){
        return bookOperationController.isAvailable(bookId);
    }

    @WebMethod(operationName = "isLoanedByMember")
    public boolean isLoanedByMember(@WebParam(name = "identityNum") String identityNum, @WebParam(name = "bookId") Long bookId) throws MemberNotFoundException {
        return bookOperationController.isLoanedByMember(identityNum, bookId);
    }

    @WebMethod(operationName = "hasFine")
    public boolean hasFine(@WebParam(name = "identityNum") String identityNum) throws MemberNotFoundException {
        return bookOperationController.hasFine(identityNum);
    }

    @WebMethod(operationName = "getFines")
    public List<FineEntity> getFines(@WebParam(name = "identityNum") String identityNum) throws MemberNotFoundException {
        return bookOperationController.getFines(identityNum);
    }

    @WebMethod(operationName = "searchBook")
    public List<entity.BookEntity> searchBook(@WebParam(name = "search") String search) {
        return bookOperationController.searchBook(search);
    }
    
    @WebMethod(operationName = "isOverdue")
    public boolean isOverdue(@WebParam(name = "lendingId") Long lendingId) throws MemberNotFoundException {
        return bookOperationController.isOverdue(lendingId);
    }
    
    @WebMethod(operationName = "getBookAvailability")
    public Date getBookAvailability(@WebParam(name = "bookId") Long bookId){
        return bookOperationController.getBookAvailability(bookId);
    }
    
    
}
