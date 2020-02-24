package latclient;

import ejb.session.stateless.BookOperationControllerRemote;
import ejb.session.stateless.MemberOperationControllerRemote;
import ejb.session.stateless.StaffOperationControllerRemote;
import entity.StaffEntity;
import java.util.Scanner;
import util.exception.InvalidLoginCredentialException;

public class MainApp {
    
    private StaffOperationControllerRemote staffOperationControllerRemote;
    private MemberOperationControllerRemote memberOperationControllerRemote;
    private BookOperationControllerRemote bookOperationControllerRemote;
    
    private StaffRegistrationOperationModule staffRegistrationOperationModule;
    private StaffLibraryOperationModule staffLibraryOperationModule;
    private StaffAdministrationOperationModule staffAdministrationOperationModule;
    
    private StaffEntity currentStaffEntity;
    
    public MainApp(StaffOperationControllerRemote staffOperationControllerRemote, MemberOperationControllerRemote memberOperationControllerRemote, BookOperationControllerRemote bookOperationControllerRemote) 
    {
        this.staffOperationControllerRemote = staffOperationControllerRemote;
        this.memberOperationControllerRemote = memberOperationControllerRemote;
        this.bookOperationControllerRemote = bookOperationControllerRemote;
    }
    
    public void runApp()
    {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("*** Welcome to Library Admin Terminal ***\n");
            System.out.println("1: Login");
            System.out.println("2: Exit\n");
            response = 0;
            
            while(response < 1 || response > 2)
            {
                System.out.print("> ");

                response = scanner.nextInt();
                if (response == 2) {
                    break;
                }

                switch(response) {
                    case 1:
                        try {
                            doLogin();
                            staffRegistrationOperationModule = new StaffRegistrationOperationModule(currentStaffEntity, memberOperationControllerRemote);
                            staffLibraryOperationModule = new StaffLibraryOperationModule(currentStaffEntity, bookOperationControllerRemote, memberOperationControllerRemote);
                            staffAdministrationOperationModule = new StaffAdministrationOperationModule(currentStaffEntity, memberOperationControllerRemote, bookOperationControllerRemote, staffOperationControllerRemote);
                            menuMain();
                        }
                        catch(InvalidLoginCredentialException ex) {
                            System.out.println(ex.getMessage());
                        }
                        break;
                    default:
                        System.out.println("Invalid option, please try again!\n");
                }
            }
            
            if (response == 2) {
                break;
            }
        }
    }
    
    private void doLogin() throws InvalidLoginCredentialException
    {
        Scanner scanner = new Scanner(System.in);
        String username = "";
        String password = "";
        
        System.out.println("*** ILS :: Login ***\n");
        System.out.print("Enter username> ");
        username = scanner.nextLine().trim();
        System.out.print("Enter password> ");
        password = scanner.nextLine().trim();
        
        if(username.length() > 0 && password.length() > 0)
        {
            try
            {
                currentStaffEntity = staffOperationControllerRemote.staffLogin(username, password);
                System.out.println("Login successful!\n");
            }
            catch (InvalidLoginCredentialException ex)
            {
                throw new InvalidLoginCredentialException("Invalid login credential: " + ex.getMessage() + "\n");
            }           
        }
        else
        {
            System.out.println("Invalid login credential!\n");
        }
    }
    
    private void menuMain()
    {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        
        while(true)
        {
            System.out.println("*** ILS :: Main ***\n");
            System.out.println("You are login as " + currentStaffEntity.getFirstName() + " " + currentStaffEntity.getLastName() + "\n");
            System.out.println("1: Registration Operation");
            System.out.println("2: Library Operation");
            System.out.println("3: Administration Operation");
            System.out.println("4: Logout\n");
            response = 0;
            
            while(response < 1 || response > 4) {
                System.out.print("> ");

                response = scanner.nextInt();
                if(response == 4) {
                    break;
                }
                
                switch(response) {
                    case 1:
                        staffRegistrationOperationModule.menuRegistrationOperation();
                        break;
                    case 2:
                        staffLibraryOperationModule.menuLibraryOperation();
                        break;
                    case 3:
                        staffAdministrationOperationModule.menuAdministrationOperation();
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
