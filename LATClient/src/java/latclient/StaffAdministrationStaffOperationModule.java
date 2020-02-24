/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latclient;

import ejb.session.stateless.StaffOperationControllerRemote;
import entity.BookEntity;
import entity.StaffEntity;
import java.util.List;
import java.util.Scanner;
import util.exception.StaffNotFoundException;

/**
 *
 * @author benjaminchin
 */
public class StaffAdministrationStaffOperationModule {
    
    private StaffOperationControllerRemote staffOperationControllerRemote;

    public StaffAdministrationStaffOperationModule(StaffOperationControllerRemote staffOperationControllerRemote) {
        this.staffOperationControllerRemote = staffOperationControllerRemote;
    }
    
    public void menuStaffManagement() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("*** ILS :: Administration Operation :: Staff Management ***\n");
            System.out.println("1: Add Staff");
            System.out.println("2: View Staff Details");
            System.out.println("3: Update Staff");
            System.out.println("4: Delete Staff");
            System.out.println("5: View All Staffs");
            System.out.println("6: Back\n");
            response = 0;
            
            while(response < 1 || response > 6)
            {
                System.out.print("> ");

                response = scanner.nextInt();
                if(response == 6) {
                    break;
                }
                
                switch(response) {
                    case 1:
                        doAddStaff();
                        break;
                    case 2:
                        doViewStaffDetails();
                        break;
                    case 3:
                        doUpdateStaff();
                        break;
                    case 4:
                        doDeleteStaff();
                        break;
                    case 5:
                        doViewAllStaffs();
                        break;
                    default:
                        System.out.println("Invalid option, please try again!\n");
                }
            }
            
            if(response == 6) {
                break;
            }
        }
    }
    
    private void doAddStaff() {
        Scanner scanner = new Scanner(System.in);
        StaffEntity staffEntity = new StaffEntity();
         
        try {
            System.out.println("*** ILS :: Staff Management :: Add Staff ***\n");
            System.out.print("Enter First Name> ");
            staffEntity.setFirstName(scanner.nextLine().trim());
            System.out.print("Enter Last Name> ");
            staffEntity.setLastName(scanner.nextLine().trim());
            System.out.print("Enter User Name> ");
            staffEntity.setUserName(scanner.nextLine().trim());
            System.out.print("Enter Password> ");
            staffEntity.setPassword(scanner.nextLine().trim());
        
            staffOperationControllerRemote.createNewStaff(staffEntity);
            System.out.println("Staff has been registered successfully!\n");
        }
        catch (Exception ex) {
            System.out.println("An error has occurred while creating the new staff: " + ex.getMessage() + "\n");
        }
    }
    
    private void doViewStaffDetails() {
        System.out.println("*** ILS :: Staff Management :: View Staff Details ***\n");
        Scanner scanner = new Scanner(System.in);
        
        try {
            Long staffId = 0L;
            System.out.print("Enter Staff ID> ");
            staffId = scanner.nextLong();
            scanner.nextLine();
            
            StaffEntity staffEntity = staffOperationControllerRemote.retrieveStaffByStaffId(staffId);
            
            String format = "%-4s |%-15s |%-15s |%-15s |%-15s %n";
            System.out.format(format,"Id","First Name","Last Name","User Name","Password");
            System.out.format(format,staffEntity.getId(),staffEntity.getFirstName(),staffEntity.getLastName(),staffEntity.getUserName(),staffEntity.getPassword());
            
            System.out.print("Press ENTER to continue> ");
            scanner.nextLine();
        }
        catch(StaffNotFoundException ex) {
            System.out.println("Staff ID entered does not belong to an existing staff.\n");
        }
    }
    
    private void doUpdateStaff() {
        Scanner scanner = new Scanner(System.in);
        StaffEntity staffEntity = new StaffEntity();
         
        try {
            System.out.println("*** ILS :: Staff Management :: Update Staff ***\n");
            System.out.print("Enter Staff ID to Update> ");
            staffEntity.setId(scanner.nextLong());
            scanner.nextLine();
            System.out.print("Enter First Name> ");
            staffEntity.setFirstName(scanner.nextLine().trim());
            System.out.print("Enter Last Name> ");
            staffEntity.setLastName(scanner.nextLine().trim());
            System.out.print("Enter User Name> ");
            staffEntity.setUserName(scanner.nextLine().trim());
            System.out.print("Enter Password> ");
            staffEntity.setPassword(scanner.nextLine().trim());
            
            staffOperationControllerRemote.updateStaff(staffEntity);
            System.out.println("Staff has been updated successfully!\n");
        }
        catch (Exception ex) {
            System.out.println("An error has occurred while updating the staff: " + ex.getMessage() + "\n");
        }
    }
    
    private void doDeleteStaff() {
        System.out.println("*** ILS :: Staff Management :: Delete Staff ***\n");
        Scanner scanner = new Scanner(System.in);
        
        try {
            StaffEntity staffEntity = new StaffEntity();
            System.out.print("Enter Staff ID> ");
            staffEntity.setId(scanner.nextLong());
            
            staffOperationControllerRemote.deleteStaff(staffEntity.getId());
            System.out.println("Staff has been deleted successfully!\n");
        }
        catch(StaffNotFoundException ex) {
            System.out.println("Staff ID entered does not belong to an existing staff.\n");
        }
        catch(Exception ex) {
            System.out.println("An error has occurred while deleting the staff: " + ex.getMessage() + "\n");
        }
    }
    
    private void doViewAllStaffs() {
        System.out.println("*** ILS :: Staff Management :: View All Staff ***\n");
        Scanner scanner = new Scanner(System.in);
        
        try {
            
            List<StaffEntity> staffEntities = staffOperationControllerRemote.retrieveAllStaff();
            
            String format = "%-4s |%-15s |%-15s |%-15s |%-15s %n";
            System.out.format(format,"Id","First Name","Last Name","User Name","Password");
            staffEntities.forEach((staffEntity) -> {
                System.out.format(format,staffEntity.getId(),staffEntity.getFirstName(),staffEntity.getLastName(),staffEntity.getUserName(),staffEntity.getPassword());
            });
            
            System.out.print("Press ENTER to continue> ");
            scanner.nextLine();
        }
        catch(Exception ex) {
            System.out.println("An error has occurred while retrieving staffs: " + ex.getMessage() + "\n");
        }
    }
}
