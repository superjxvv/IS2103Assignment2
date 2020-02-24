package ejb.session.stateless;

import entity.StaffEntity;
import java.util.List;
import util.exception.InvalidLoginCredentialException;
import util.exception.StaffNotFoundException;

public interface StaffOperationControllerLocal {

    StaffEntity createNewStaff(StaffEntity newStaffEntity);

    List<entity.StaffEntity> retrieveAllStaff();

    StaffEntity retrieveStaffByStaffId(long staffId) throws StaffNotFoundException;

    StaffEntity staffLogin(String username, String password) throws InvalidLoginCredentialException;

    void updateStaff(StaffEntity staffEntity);

    void deleteStaff(long staffId) throws StaffNotFoundException;

    StaffEntity retrieveStaffByUsername(String username) throws StaffNotFoundException;

}
