
package ws.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ws.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetFinesResponse_QNAME = new QName("http://ws.session.ejb/", "getFinesResponse");
    private final static QName _GetBookAvailabilityResponse_QNAME = new QName("http://ws.session.ejb/", "getBookAvailabilityResponse");
    private final static QName _HasFineResponse_QNAME = new QName("http://ws.session.ejb/", "hasFineResponse");
    private final static QName _IsReservedResponse_QNAME = new QName("http://ws.session.ejb/", "isReservedResponse");
    private final static QName _MemberLogin_QNAME = new QName("http://ws.session.ejb/", "memberLogin");
    private final static QName _IsOverdueResponse_QNAME = new QName("http://ws.session.ejb/", "isOverdueResponse");
    private final static QName _ReturnBook_QNAME = new QName("http://ws.session.ejb/", "returnBook");
    private final static QName _SearchBookResponse_QNAME = new QName("http://ws.session.ejb/", "searchBookResponse");
    private final static QName _PayFinesResponse_QNAME = new QName("http://ws.session.ejb/", "payFinesResponse");
    private final static QName _IsLoanedByMember_QNAME = new QName("http://ws.session.ejb/", "isLoanedByMember");
    private final static QName _ExtendBookResponse_QNAME = new QName("http://ws.session.ejb/", "extendBookResponse");
    private final static QName _GetBookAvailability_QNAME = new QName("http://ws.session.ejb/", "getBookAvailability");
    private final static QName _IsAvailable_QNAME = new QName("http://ws.session.ejb/", "isAvailable");
    private final static QName _ReserveBookResponse_QNAME = new QName("http://ws.session.ejb/", "reserveBookResponse");
    private final static QName _ViewLentBooksResponse_QNAME = new QName("http://ws.session.ejb/", "viewLentBooksResponse");
    private final static QName _ViewBooksLentResponse_QNAME = new QName("http://ws.session.ejb/", "viewBooksLentResponse");
    private final static QName _ViewLentBooks_QNAME = new QName("http://ws.session.ejb/", "viewLentBooks");
    private final static QName _ReturnBookResponse_QNAME = new QName("http://ws.session.ejb/", "returnBookResponse");
    private final static QName _MemberNotFoundException_QNAME = new QName("http://ws.session.ejb/", "MemberNotFoundException");
    private final static QName _HasFine_QNAME = new QName("http://ws.session.ejb/", "hasFine");
    private final static QName _MemberLoginResponse_QNAME = new QName("http://ws.session.ejb/", "memberLoginResponse");
    private final static QName _IsAvailableResponse_QNAME = new QName("http://ws.session.ejb/", "isAvailableResponse");
    private final static QName _IsOverdue_QNAME = new QName("http://ws.session.ejb/", "isOverdue");
    private final static QName _ReserveBook_QNAME = new QName("http://ws.session.ejb/", "reserveBook");
    private final static QName _PayFines_QNAME = new QName("http://ws.session.ejb/", "payFines");
    private final static QName _IsReservedByHimselfResponse_QNAME = new QName("http://ws.session.ejb/", "isReservedByHimselfResponse");
    private final static QName _ViewBooksLent_QNAME = new QName("http://ws.session.ejb/", "viewBooksLent");
    private final static QName _SearchBook_QNAME = new QName("http://ws.session.ejb/", "searchBook");
    private final static QName _GetFines_QNAME = new QName("http://ws.session.ejb/", "getFines");
    private final static QName _InvalidLoginCredentialException_QNAME = new QName("http://ws.session.ejb/", "InvalidLoginCredentialException");
    private final static QName _ExtendBook_QNAME = new QName("http://ws.session.ejb/", "extendBook");
    private final static QName _IsReservedByHimself_QNAME = new QName("http://ws.session.ejb/", "isReservedByHimself");
    private final static QName _IsLoanedByMemberResponse_QNAME = new QName("http://ws.session.ejb/", "isLoanedByMemberResponse");
    private final static QName _IsReserved_QNAME = new QName("http://ws.session.ejb/", "isReserved");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ws.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetBookAvailability }
     * 
     */
    public GetBookAvailability createGetBookAvailability() {
        return new GetBookAvailability();
    }

    /**
     * Create an instance of {@link IsAvailable }
     * 
     */
    public IsAvailable createIsAvailable() {
        return new IsAvailable();
    }

    /**
     * Create an instance of {@link ReserveBookResponse }
     * 
     */
    public ReserveBookResponse createReserveBookResponse() {
        return new ReserveBookResponse();
    }

    /**
     * Create an instance of {@link ViewLentBooksResponse }
     * 
     */
    public ViewLentBooksResponse createViewLentBooksResponse() {
        return new ViewLentBooksResponse();
    }

    /**
     * Create an instance of {@link ViewBooksLentResponse }
     * 
     */
    public ViewBooksLentResponse createViewBooksLentResponse() {
        return new ViewBooksLentResponse();
    }

    /**
     * Create an instance of {@link ExtendBookResponse }
     * 
     */
    public ExtendBookResponse createExtendBookResponse() {
        return new ExtendBookResponse();
    }

    /**
     * Create an instance of {@link ReturnBook }
     * 
     */
    public ReturnBook createReturnBook() {
        return new ReturnBook();
    }

    /**
     * Create an instance of {@link SearchBookResponse }
     * 
     */
    public SearchBookResponse createSearchBookResponse() {
        return new SearchBookResponse();
    }

    /**
     * Create an instance of {@link PayFinesResponse }
     * 
     */
    public PayFinesResponse createPayFinesResponse() {
        return new PayFinesResponse();
    }

    /**
     * Create an instance of {@link IsLoanedByMember }
     * 
     */
    public IsLoanedByMember createIsLoanedByMember() {
        return new IsLoanedByMember();
    }

    /**
     * Create an instance of {@link GetFinesResponse }
     * 
     */
    public GetFinesResponse createGetFinesResponse() {
        return new GetFinesResponse();
    }

    /**
     * Create an instance of {@link GetBookAvailabilityResponse }
     * 
     */
    public GetBookAvailabilityResponse createGetBookAvailabilityResponse() {
        return new GetBookAvailabilityResponse();
    }

    /**
     * Create an instance of {@link HasFineResponse }
     * 
     */
    public HasFineResponse createHasFineResponse() {
        return new HasFineResponse();
    }

    /**
     * Create an instance of {@link IsReservedResponse }
     * 
     */
    public IsReservedResponse createIsReservedResponse() {
        return new IsReservedResponse();
    }

    /**
     * Create an instance of {@link MemberLogin }
     * 
     */
    public MemberLogin createMemberLogin() {
        return new MemberLogin();
    }

    /**
     * Create an instance of {@link IsOverdueResponse }
     * 
     */
    public IsOverdueResponse createIsOverdueResponse() {
        return new IsOverdueResponse();
    }

    /**
     * Create an instance of {@link InvalidLoginCredentialException }
     * 
     */
    public InvalidLoginCredentialException createInvalidLoginCredentialException() {
        return new InvalidLoginCredentialException();
    }

    /**
     * Create an instance of {@link ExtendBook }
     * 
     */
    public ExtendBook createExtendBook() {
        return new ExtendBook();
    }

    /**
     * Create an instance of {@link IsReservedByHimself }
     * 
     */
    public IsReservedByHimself createIsReservedByHimself() {
        return new IsReservedByHimself();
    }

    /**
     * Create an instance of {@link IsLoanedByMemberResponse }
     * 
     */
    public IsLoanedByMemberResponse createIsLoanedByMemberResponse() {
        return new IsLoanedByMemberResponse();
    }

    /**
     * Create an instance of {@link IsReserved }
     * 
     */
    public IsReserved createIsReserved() {
        return new IsReserved();
    }

    /**
     * Create an instance of {@link IsReservedByHimselfResponse }
     * 
     */
    public IsReservedByHimselfResponse createIsReservedByHimselfResponse() {
        return new IsReservedByHimselfResponse();
    }

    /**
     * Create an instance of {@link ViewBooksLent }
     * 
     */
    public ViewBooksLent createViewBooksLent() {
        return new ViewBooksLent();
    }

    /**
     * Create an instance of {@link SearchBook }
     * 
     */
    public SearchBook createSearchBook() {
        return new SearchBook();
    }

    /**
     * Create an instance of {@link GetFines }
     * 
     */
    public GetFines createGetFines() {
        return new GetFines();
    }

    /**
     * Create an instance of {@link HasFine }
     * 
     */
    public HasFine createHasFine() {
        return new HasFine();
    }

    /**
     * Create an instance of {@link MemberLoginResponse }
     * 
     */
    public MemberLoginResponse createMemberLoginResponse() {
        return new MemberLoginResponse();
    }

    /**
     * Create an instance of {@link IsAvailableResponse }
     * 
     */
    public IsAvailableResponse createIsAvailableResponse() {
        return new IsAvailableResponse();
    }

    /**
     * Create an instance of {@link IsOverdue }
     * 
     */
    public IsOverdue createIsOverdue() {
        return new IsOverdue();
    }

    /**
     * Create an instance of {@link ReserveBook }
     * 
     */
    public ReserveBook createReserveBook() {
        return new ReserveBook();
    }

    /**
     * Create an instance of {@link PayFines }
     * 
     */
    public PayFines createPayFines() {
        return new PayFines();
    }

    /**
     * Create an instance of {@link ViewLentBooks }
     * 
     */
    public ViewLentBooks createViewLentBooks() {
        return new ViewLentBooks();
    }

    /**
     * Create an instance of {@link ReturnBookResponse }
     * 
     */
    public ReturnBookResponse createReturnBookResponse() {
        return new ReturnBookResponse();
    }

    /**
     * Create an instance of {@link MemberNotFoundException }
     * 
     */
    public MemberNotFoundException createMemberNotFoundException() {
        return new MemberNotFoundException();
    }

    /**
     * Create an instance of {@link BookEntity }
     * 
     */
    public BookEntity createBookEntity() {
        return new BookEntity();
    }

    /**
     * Create an instance of {@link FineEntity }
     * 
     */
    public FineEntity createFineEntity() {
        return new FineEntity();
    }

    /**
     * Create an instance of {@link LendingEntity }
     * 
     */
    public LendingEntity createLendingEntity() {
        return new LendingEntity();
    }

    /**
     * Create an instance of {@link MemberEntity }
     * 
     */
    public MemberEntity createMemberEntity() {
        return new MemberEntity();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFinesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "getFinesResponse")
    public JAXBElement<GetFinesResponse> createGetFinesResponse(GetFinesResponse value) {
        return new JAXBElement<GetFinesResponse>(_GetFinesResponse_QNAME, GetFinesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBookAvailabilityResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "getBookAvailabilityResponse")
    public JAXBElement<GetBookAvailabilityResponse> createGetBookAvailabilityResponse(GetBookAvailabilityResponse value) {
        return new JAXBElement<GetBookAvailabilityResponse>(_GetBookAvailabilityResponse_QNAME, GetBookAvailabilityResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HasFineResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "hasFineResponse")
    public JAXBElement<HasFineResponse> createHasFineResponse(HasFineResponse value) {
        return new JAXBElement<HasFineResponse>(_HasFineResponse_QNAME, HasFineResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsReservedResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "isReservedResponse")
    public JAXBElement<IsReservedResponse> createIsReservedResponse(IsReservedResponse value) {
        return new JAXBElement<IsReservedResponse>(_IsReservedResponse_QNAME, IsReservedResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MemberLogin }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "memberLogin")
    public JAXBElement<MemberLogin> createMemberLogin(MemberLogin value) {
        return new JAXBElement<MemberLogin>(_MemberLogin_QNAME, MemberLogin.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsOverdueResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "isOverdueResponse")
    public JAXBElement<IsOverdueResponse> createIsOverdueResponse(IsOverdueResponse value) {
        return new JAXBElement<IsOverdueResponse>(_IsOverdueResponse_QNAME, IsOverdueResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReturnBook }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "returnBook")
    public JAXBElement<ReturnBook> createReturnBook(ReturnBook value) {
        return new JAXBElement<ReturnBook>(_ReturnBook_QNAME, ReturnBook.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchBookResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "searchBookResponse")
    public JAXBElement<SearchBookResponse> createSearchBookResponse(SearchBookResponse value) {
        return new JAXBElement<SearchBookResponse>(_SearchBookResponse_QNAME, SearchBookResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PayFinesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "payFinesResponse")
    public JAXBElement<PayFinesResponse> createPayFinesResponse(PayFinesResponse value) {
        return new JAXBElement<PayFinesResponse>(_PayFinesResponse_QNAME, PayFinesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsLoanedByMember }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "isLoanedByMember")
    public JAXBElement<IsLoanedByMember> createIsLoanedByMember(IsLoanedByMember value) {
        return new JAXBElement<IsLoanedByMember>(_IsLoanedByMember_QNAME, IsLoanedByMember.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExtendBookResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "extendBookResponse")
    public JAXBElement<ExtendBookResponse> createExtendBookResponse(ExtendBookResponse value) {
        return new JAXBElement<ExtendBookResponse>(_ExtendBookResponse_QNAME, ExtendBookResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBookAvailability }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "getBookAvailability")
    public JAXBElement<GetBookAvailability> createGetBookAvailability(GetBookAvailability value) {
        return new JAXBElement<GetBookAvailability>(_GetBookAvailability_QNAME, GetBookAvailability.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsAvailable }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "isAvailable")
    public JAXBElement<IsAvailable> createIsAvailable(IsAvailable value) {
        return new JAXBElement<IsAvailable>(_IsAvailable_QNAME, IsAvailable.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReserveBookResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "reserveBookResponse")
    public JAXBElement<ReserveBookResponse> createReserveBookResponse(ReserveBookResponse value) {
        return new JAXBElement<ReserveBookResponse>(_ReserveBookResponse_QNAME, ReserveBookResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ViewLentBooksResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "viewLentBooksResponse")
    public JAXBElement<ViewLentBooksResponse> createViewLentBooksResponse(ViewLentBooksResponse value) {
        return new JAXBElement<ViewLentBooksResponse>(_ViewLentBooksResponse_QNAME, ViewLentBooksResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ViewBooksLentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "viewBooksLentResponse")
    public JAXBElement<ViewBooksLentResponse> createViewBooksLentResponse(ViewBooksLentResponse value) {
        return new JAXBElement<ViewBooksLentResponse>(_ViewBooksLentResponse_QNAME, ViewBooksLentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ViewLentBooks }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "viewLentBooks")
    public JAXBElement<ViewLentBooks> createViewLentBooks(ViewLentBooks value) {
        return new JAXBElement<ViewLentBooks>(_ViewLentBooks_QNAME, ViewLentBooks.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReturnBookResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "returnBookResponse")
    public JAXBElement<ReturnBookResponse> createReturnBookResponse(ReturnBookResponse value) {
        return new JAXBElement<ReturnBookResponse>(_ReturnBookResponse_QNAME, ReturnBookResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MemberNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "MemberNotFoundException")
    public JAXBElement<MemberNotFoundException> createMemberNotFoundException(MemberNotFoundException value) {
        return new JAXBElement<MemberNotFoundException>(_MemberNotFoundException_QNAME, MemberNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HasFine }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "hasFine")
    public JAXBElement<HasFine> createHasFine(HasFine value) {
        return new JAXBElement<HasFine>(_HasFine_QNAME, HasFine.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MemberLoginResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "memberLoginResponse")
    public JAXBElement<MemberLoginResponse> createMemberLoginResponse(MemberLoginResponse value) {
        return new JAXBElement<MemberLoginResponse>(_MemberLoginResponse_QNAME, MemberLoginResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsAvailableResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "isAvailableResponse")
    public JAXBElement<IsAvailableResponse> createIsAvailableResponse(IsAvailableResponse value) {
        return new JAXBElement<IsAvailableResponse>(_IsAvailableResponse_QNAME, IsAvailableResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsOverdue }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "isOverdue")
    public JAXBElement<IsOverdue> createIsOverdue(IsOverdue value) {
        return new JAXBElement<IsOverdue>(_IsOverdue_QNAME, IsOverdue.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReserveBook }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "reserveBook")
    public JAXBElement<ReserveBook> createReserveBook(ReserveBook value) {
        return new JAXBElement<ReserveBook>(_ReserveBook_QNAME, ReserveBook.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PayFines }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "payFines")
    public JAXBElement<PayFines> createPayFines(PayFines value) {
        return new JAXBElement<PayFines>(_PayFines_QNAME, PayFines.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsReservedByHimselfResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "isReservedByHimselfResponse")
    public JAXBElement<IsReservedByHimselfResponse> createIsReservedByHimselfResponse(IsReservedByHimselfResponse value) {
        return new JAXBElement<IsReservedByHimselfResponse>(_IsReservedByHimselfResponse_QNAME, IsReservedByHimselfResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ViewBooksLent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "viewBooksLent")
    public JAXBElement<ViewBooksLent> createViewBooksLent(ViewBooksLent value) {
        return new JAXBElement<ViewBooksLent>(_ViewBooksLent_QNAME, ViewBooksLent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchBook }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "searchBook")
    public JAXBElement<SearchBook> createSearchBook(SearchBook value) {
        return new JAXBElement<SearchBook>(_SearchBook_QNAME, SearchBook.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetFines }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "getFines")
    public JAXBElement<GetFines> createGetFines(GetFines value) {
        return new JAXBElement<GetFines>(_GetFines_QNAME, GetFines.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidLoginCredentialException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "InvalidLoginCredentialException")
    public JAXBElement<InvalidLoginCredentialException> createInvalidLoginCredentialException(InvalidLoginCredentialException value) {
        return new JAXBElement<InvalidLoginCredentialException>(_InvalidLoginCredentialException_QNAME, InvalidLoginCredentialException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExtendBook }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "extendBook")
    public JAXBElement<ExtendBook> createExtendBook(ExtendBook value) {
        return new JAXBElement<ExtendBook>(_ExtendBook_QNAME, ExtendBook.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsReservedByHimself }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "isReservedByHimself")
    public JAXBElement<IsReservedByHimself> createIsReservedByHimself(IsReservedByHimself value) {
        return new JAXBElement<IsReservedByHimself>(_IsReservedByHimself_QNAME, IsReservedByHimself.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsLoanedByMemberResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "isLoanedByMemberResponse")
    public JAXBElement<IsLoanedByMemberResponse> createIsLoanedByMemberResponse(IsLoanedByMemberResponse value) {
        return new JAXBElement<IsLoanedByMemberResponse>(_IsLoanedByMemberResponse_QNAME, IsLoanedByMemberResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsReserved }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.session.ejb/", name = "isReserved")
    public JAXBElement<IsReserved> createIsReserved(IsReserved value) {
        return new JAXBElement<IsReserved>(_IsReserved_QNAME, IsReserved.class, null, value);
    }

}
