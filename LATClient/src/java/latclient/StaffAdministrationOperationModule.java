package latclient;

import ejb.session.stateless.BookOperationControllerRemote;
import ejb.session.stateless.MemberOperationControllerRemote;
import ejb.session.stateless.StaffOperationControllerRemote;
import entity.MemberEntity;
import entity.StaffEntity;
import java.util.List;
import java.util.Scanner;
import util.exception.MemberNotFoundException;

public class StaffAdministrationOperationModule {
    
    private MemberOperationControllerRemote memberOperationControllerRemote;
    private BookOperationControllerRemote bookOperationControllerRemote;
    private StaffOperationControllerRemote staffOperationControllerRemote;
    
    private StaffAdministrationMemberOperationModule staffAdministrationMemberOperationModule;
    private StaffAdministrationBookOperationModule staffAdministrationBookOperationModule;
    private StaffAdministrationStaffOperationModule staffAdministrationStaffOperationModule;
    
    private StaffEntity currentStaffEntity;

    public StaffAdministrationOperationModule(StaffEntity currentStaffEntity, MemberOperationControllerRemote memberOperationControllerRemote, BookOperationControllerRemote bookOperationControllerRemote, StaffOperationControllerRemote staffOperationControllerRemote) {
        this.memberOperationControllerRemote = memberOperationControllerRemote;
        this.bookOperationControllerRemote = bookOperationControllerRemote;
        this.staffOperationControllerRemote = staffOperationControllerRemote;
        
        staffAdministrationMemberOperationModule = new StaffAdministrationMemberOperationModule(memberOperationControllerRemote);
        staffAdministrationBookOperationModule = new StaffAdministrationBookOperationModule(bookOperationControllerRemote);
        staffAdministrationStaffOperationModule = new StaffAdministrationStaffOperationModule(staffOperationControllerRemote);
        
        this.currentStaffEntity = currentStaffEntity;
    }
    
    public void menuAdministrationOperation() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("*** ILS :: Administration Operation ***\n");
            System.out.println("1: Member Management");
            System.out.println("2: Book Management");
            System.out.println("3: Staff Management");
            System.out.println("4: Back\n");
            response = 0;
            
            while(response < 1 || response > 4)
            {
                System.out.print("> ");

                response = scanner.nextInt();
                if(response == 4) {
                    break;
                }
                
                switch(response) {
                    case 1:
                        staffAdministrationMemberOperationModule.menuMemberManagement();
                        break;
                    case 2:
                        staffAdministrationBookOperationModule.menuBookManagement();
                        break;
                    case 3:
                        staffAdministrationStaffOperationModule.menuStaffManagement();
                        break;
                    default:
                        System.out.println("Invalid option, please try again!\n");
                }
            }
            
            if(response == 4) {
                break;
            }
        }
    }
    
}
