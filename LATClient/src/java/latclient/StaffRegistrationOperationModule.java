package latclient;

import ejb.session.stateless.MemberOperationControllerRemote;
import entity.MemberEntity;
import entity.StaffEntity;
import java.util.Scanner;

public class StaffRegistrationOperationModule {
    
    private MemberOperationControllerRemote memberOperationControllerRemote;
    
    private StaffEntity currentStaffEntity;

    public StaffRegistrationOperationModule(StaffEntity currentStaffEntity, MemberOperationControllerRemote memberOperationControllerRemote) {
        this.memberOperationControllerRemote = memberOperationControllerRemote;
        
        this.currentStaffEntity = currentStaffEntity;
    }
    
    public void menuRegistrationOperation() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("*** ILS :: Registration Operation ***\n");
            System.out.println("1: Register New Member");
            System.out.println("2: Back\n");
            response = 0;
            
            while(response < 1 || response > 2)
            {
                System.out.print("> ");

                response = scanner.nextInt();
                if(response == 2) {
                    break;
                }
                
                if(response == 1) {
                    doRegisterNewMember();
                }
                else
                {
                    System.out.println("Invalid option, please try again!\n");                
                }
            }
            
            if(response == 2) {
                break;
            }
        }
    }
    
    private void doRegisterNewMember() {
        Scanner scanner = new Scanner(System.in);
        MemberEntity memberEntity = new MemberEntity();
        
        System.out.println("*** ILS :: Registration Operation :: Register New Member ***\n");
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
         
        try {
            memberEntity = memberOperationControllerRemote.createNewMember(memberEntity);
            System.out.println("Member has been registered successfully!\n");
        }
        catch (Exception ex) {
            System.out.println("An error has occurred while creating the new member: " + ex.getMessage() + "\n");
        }
    }
    
}
