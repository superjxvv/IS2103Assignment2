package ejb.session.singleton;

import ejb.session.stateless.BookOperationControllerLocal;
import ejb.session.stateless.MemberOperationControllerLocal;
import ejb.session.stateless.StaffOperationControllerLocal;
import entity.BookEntity;
import entity.MemberEntity;
import entity.StaffEntity;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.StaffNotFoundException;

@Singleton
@LocalBean
@Startup
public class DataInitializationSessionBean {

    @EJB
    private BookOperationControllerLocal bookOperationControllerLocal;
    @EJB
    private StaffOperationControllerLocal staffOperationControllerLocal;
    @EJB
    private MemberOperationControllerLocal memberOperationControllerLocal;
    
    @PersistenceContext(unitName = "Assignment2-ejbPU")
    private EntityManager em;

    public DataInitializationSessionBean() {
    }

    @PostConstruct
    public void postConstruct() {
        try {
            staffOperationControllerLocal.retrieveStaffByUsername("manager");
        } catch (StaffNotFoundException ex) {
            initializeData();
        }
    }

    private void initializeData() {
        staffOperationControllerLocal.createNewStaff(new StaffEntity("Linda", "Chua", "manager", "password"));
        staffOperationControllerLocal.createNewStaff(new StaffEntity("Barbara", "Durham", "assistant", "password"));

        memberOperationControllerLocal.createNewMember(new MemberEntity(new ArrayList<>(), new ArrayList<>(), "Tony", "Teo", "Male", 44, "S7483027A", "87297373", "11 Tampines Ave 3", "password"));
        memberOperationControllerLocal.createNewMember(new MemberEntity(new ArrayList<>(), new ArrayList<>(), "Wendy", "Tan", "Female", 35, "S8381028X", "97502837", "15 Computing Dr", "password"));

        bookOperationControllerLocal.createNewBook(new BookEntity(new ArrayList<>(), "Le Petit Prince", "S64921", 1943));
        bookOperationControllerLocal.createNewBook(new BookEntity(new ArrayList<>(), "Harry Potter and the Philosopher's Stone", "S38101", 1997));
        bookOperationControllerLocal.createNewBook(new BookEntity(new ArrayList<>(), "The Hobbit", "S19527", 1937));
        bookOperationControllerLocal.createNewBook(new BookEntity(new ArrayList<>(), "And Then There Were None", "S63288", 1939));
        bookOperationControllerLocal.createNewBook(new BookEntity(new ArrayList<>(), "Dream of the Red Chamber", "S32187", 1791));
        bookOperationControllerLocal.createNewBook(new BookEntity(new ArrayList<>(), "The Lion, the Witch and the Wardrobe", "S74569", 1950));
    }

}
