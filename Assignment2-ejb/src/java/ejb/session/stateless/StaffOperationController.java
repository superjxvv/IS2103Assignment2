package ejb.session.stateless;

import entity.StaffEntity;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.InvalidLoginCredentialException;
import util.exception.StaffNotFoundException;

@Stateless
@Local(StaffOperationControllerLocal.class)
@Remote(StaffOperationControllerRemote.class)
public class StaffOperationController implements StaffOperationControllerRemote, StaffOperationControllerLocal {

    @PersistenceContext(unitName = "Assignment2-ejbPU")
    private EntityManager em;

    @Override
    public StaffEntity createNewStaff(StaffEntity newStaffEntity) {
        em.persist(newStaffEntity);
        em.flush();

        return newStaffEntity;
    }

    @Override
    public List<entity.StaffEntity> retrieveAllStaff() {
        Query query = em.createQuery("SELECT s FROM StaffEntity s");

        return query.getResultList();
    }

    @Override
    public StaffEntity retrieveStaffByStaffId(long staffId) throws StaffNotFoundException {
        StaffEntity staffEntity = em.find(StaffEntity.class, staffId);

        if (staffEntity != null) {
            return staffEntity;
        } else {
            throw new StaffNotFoundException("Staff ID " + staffId + " does not exist!");
        }
    }

    @Override
    public StaffEntity retrieveStaffByUsername(String username) throws StaffNotFoundException {
        Query query = em.createQuery("SELECT s FROM StaffEntity s WHERE s.userName = :inUsername");
        query.setParameter("inUsername", username);

        try {
            return (StaffEntity) query.getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            throw new StaffNotFoundException("Staff Username " + username + " does not exist!");
        }
    }

    @Override
    public StaffEntity staffLogin(String username, String password) throws InvalidLoginCredentialException {
        try {
            StaffEntity staffEntity = retrieveStaffByUsername(username);

            if (staffEntity.getPassword().equals(password)) {
                return staffEntity;
            } else {
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        } catch (StaffNotFoundException ex) {
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }
    }

    @Override
    public void updateStaff(StaffEntity staffEntity) {
        em.merge(staffEntity);
    }

    @Override
    public void deleteStaff(long staffId) throws StaffNotFoundException {
        StaffEntity staffEntityToRemove = retrieveStaffByStaffId(staffId);
        em.remove(staffEntityToRemove);
    }

}
