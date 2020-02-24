/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package latclient;

import ejb.session.stateless.MemberOperationControllerRemote;
import entity.MemberEntity;
import java.util.List;
import java.util.Scanner;
import util.exception.MemberNotFoundException;

/**
 *
 * @author benjaminchin
 */
public class StaffAdministrationMemberOperationModule {
    
    private MemberOperationControllerRemote memberOperationControllerRemote;

    public StaffAdministrationMemberOperationModule(MemberOperationControllerRemote memberOperationControllerRemote) {
        this.memberOperationControllerRemote = memberOperationControllerRemote;
    }
    
    public void menuMemberManagement() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("*** ILS :: Administration Operation :: Member Management ***\n");
            System.out.println("1: Add Member");
            System.out.println("2: View Member Details");
            System.out.println("3: Update Member");
            System.out.println("4: Delete Member");
            System.out.println("5: View All Members");
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
                        doAddMember();
                        break;
                    case 2:
                        doViewMemberDetails();
                        break;
                    case 3:
                        doUpdateMember();
                        break;
                    case 4:
                        doDeleteMember();
                        break;
                    case 5:
                        doViewAllMembers();
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
    
    private void doAddMember() {
        Scanner scanner = new Scanner(System.in);
        MemberEntity memberEntity = new MemberEntity();
         
        try {
            System.out.println("*** ILS :: Member Management :: Add Member ***\n");
            System.out.print("Enter Identity Number> ");
            memberEntity.setIdentityNum(scanner.nextLine().trim());
            System.out.print("Enter Security Code> ");
            memberEntity.setSecurityCode(scanner.nextLine().trim());
            System.out.print("Enter First Name> ");
            memberEntity.setFirstName(scanner.nextLine().trim());
            System.out.print("Enter Last Name> ");
            memberEntity.setLastName(scanner.nextLine().trim());
            System.out.print("Enter Gender> ");
            memberEntity.setGender(scanner.nextLine().trim());
            System.out.print("Enter Age> ");
            memberEntity.setAge(scanner.nextInt());
            scanner.nextLine();
            System.out.print("Enter Phone> ");
            memberEntity.setPhone(scanner.nextLine().trim());
            System.out.print("Enter Address> ");
            memberEntity.setAddress(scanner.nextLine().trim());
        
            memberEntity = memberOperationControllerRemote.createNewMember(memberEntity);
            System.out.println("Member has been registered successfully!\n");
        }
        catch (Exception ex) {
            System.out.println("An error has occurred while creating the new member: " + ex.getMessage() + "\n");
        }
    }
    
    private void doViewMemberDetails() {
        System.out.println("*** ILS :: Member Management :: View Member Details ***\n");
        Scanner scanner = new Scanner(System.in);
        
        try {
            String idNumber = "";
            System.out.print("Enter Member Identity Number> ");
            idNumber = scanner.nextLine().trim();
            
            MemberEntity memberEntity = memberOperationControllerRemote.retrieveMemberByIdentityNum(idNumber);
            
            String format = "%-4s |%-20s |%-15s |%-15s |%-15s |%-10s |%-3s |%-10s |%-70s %n";
            System.out.format(format,"Id","Identity Number","Security Code","First Name","Last Name","Gender","Age","Phone","Address");
            System.out.format(format,memberEntity.getId(),memberEntity.getIdentityNum(),memberEntity.getSecurityCode(),memberEntity.getFirstName(),memberEntity.getLastName(),memberEntity.getGender(),memberEntity.getAge(),memberEntity.getPhone(),memberEntity.getAddress());
        
            System.out.print("Press ENTER to continue> ");
            scanner.nextLine();
        }
        catch(MemberNotFoundException ex) {
            System.out.println("Member Identity Number entered does not belong to an existing member.\n");
        }
    }
    
    private void doUpdateMember() {
        Scanner scanner = new Scanner(System.in);
        MemberEntity memberEntity = new MemberEntity();
         
        try {
            System.out.println("*** ILS :: Member Management :: Update Member ***\n");
            System.out.print("Enter Member ID to Update> ");
            memberEntity.setId(scanner.nextLong());
            scanner.nextLine();
            System.out.print("Enter Identity Number> ");
            memberEntity.setIdentityNum(scanner.nextLine().trim());
            System.out.print("Enter Security Code> ");
            memberEntity.setSecurityCode(scanner.nextLine().trim());
            System.out.print("Enter First Name> ");
            memberEntity.setFirstName(scanner.nextLine().trim());
            System.out.print("Enter Last Name> ");
            memberEntity.setLastName(scanner.nextLine().trim());
            System.out.print("Enter Gender> ");
            memberEntity.setGender(scanner.nextLine().trim());
            System.out.print("Enter Age> ");
            memberEntity.setAge(scanner.nextInt());
            scanner.nextLine();
            System.out.print("Enter Phone> ");
            memberEntity.setPhone(scanner.nextLine().trim());
            System.out.print("Enter Address> ");
            memberEntity.setAddress(scanner.nextLine().trim());
            
            memberOperationControllerRemote.updateMember(memberEntity);
            System.out.println("Member has been updated successfully!\n");
        }
        catch (Exception ex) {
            System.out.println("An error has occurred while updating the member: " + ex.getMessage() + "\n");
        }
    }
    
    private void doDeleteMember() {
        System.out.println("*** ILS :: Member Management :: Delete Member ***\n");
        Scanner scanner = new Scanner(System.in);
        
        try {
            Long memberId = 0L;
            System.out.print("Enter Member ID> ");
            memberId = scanner.nextLong();
            
            memberOperationControllerRemote.deleteMember(memberId);
            System.out.println("Member has been deleted successfully!\n");
        }
        catch(MemberNotFoundException ex) {
            System.out.println("Member ID entered does not belong to an existing member.\n");
        }
        catch(Exception ex) {
            System.out.println("An error has occurred while deleting the member: " + ex.getMessage() + "\n");
        }
    }
    
    private void doViewAllMembers() {
        System.out.println("*** ILS :: Member Management :: View All Members ***\n");
        Scanner scanner = new Scanner(System.in);
        
        try {
            
            List<MemberEntity> memberEntities = memberOperationControllerRemote.retrieveAllMember();
            
            
            String format = "%-4s |%-20s |%-15s |%-15s |%-15s |%-10s |%-3s |%-10s |%-70s %n";
            System.out.format(format,"Id","Identity Number","Security Code","First Name","Last Name","Gender","Age","Phone","Address");
            memberEntities.forEach((memberEntity) -> {
                System.out.format(format,memberEntity.getId(),memberEntity.getIdentityNum(),memberEntity.getSecurityCode(),memberEntity.getFirstName(),memberEntity.getLastName(),memberEntity.getGender(),memberEntity.getAge(),memberEntity.getPhone(),memberEntity.getAddress());
            });
            
            System.out.print("Press ENTER to continue> ");
            scanner.nextLine();
        }
        catch(Exception ex) {
            System.out.println("An error has occurred while retrieving members: " + ex.getMessage() + "\n");
        }
    }
}
