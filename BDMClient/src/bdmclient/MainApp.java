package bdmclient;

import java.util.Scanner;
import ws.client.InvalidLoginCredentialException_Exception;
import ws.client.MemberEntity;

public class MainApp {

    private MemberEntity memberEntity;
    private BDMOperationModule bDMOperationModule;

    public MainApp() {
    }

    void runApp() {
        Scanner scanner = new Scanner(System.in);
        Integer response;

        while (true) {
            System.out.println("*** Welcome to BDM Client ***\n");
            System.out.println("1: Login");
            System.out.println("2: Exit");

            response = 0;
            while (response < 1 || response > 2) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    try {
                        doLogin();
                        bDMOperationModule = new BDMOperationModule(memberEntity);
                        menuMain();
                    } catch (ws.client.InvalidLoginCredentialException_Exception ex) {
                    }
                } else if (response == 2) {
                    break;
                } else {
                    System.out.print("Invalid option, please try again!\n");
                }
            }

            if (response == 2) {
                break;
            }
        }
    }

    private void doLogin() throws InvalidLoginCredentialException_Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** BDM Client :: Login ***");
        System.out.print("Enter Identity Number> ");
        String identityNum = scanner.nextLine().trim();
        System.out.print("Enter Security Code> ");
        String securityCode = scanner.nextLine().trim();
        if (identityNum.length() > 0 && securityCode.length() > 0) {
            try {
                memberEntity = memberLogin(identityNum, securityCode);
                System.out.println("Login successful!\n");
            } catch (ws.client.InvalidLoginCredentialException_Exception ex) {
                System.out.println("Invalid login credential: " + ex.getMessage() + "\n");
                throw ex;
            }
        } else {
            System.out.println("Invalid login credential!");
        }
    }

    private void menuMain() {
        Scanner scanner = new Scanner(System.in);
        Integer response;

        while (true) {
            System.out.println("*** BDM Client :: Main ***\n");
            System.out.println("You are login as " + memberEntity.getFirstName() + " " + memberEntity.getLastName() + "\n");
            System.out.println("1: View Lent Books");
            System.out.println("2: Return Book");
            System.out.println("3: Extend Book");
            System.out.println("4: Pay Fines");
            System.out.println("5: Reserve Book");
            System.out.println("6: Logout\n");
            response = 0;

            while (response < 1 || response > 6) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    bDMOperationModule.viewLentBooks();
                } else if (response == 2) {
                    bDMOperationModule.returnBook();
                } else if (response == 3) {
                    bDMOperationModule.extendBook();
                } else if (response == 4) {
                    bDMOperationModule.payFines();
                } else if (response == 5) {
                    bDMOperationModule.reserveBook();
                } else if (response == 6) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 6) {
                break;
            }
        }
    }

    private static MemberEntity memberLogin(java.lang.String identityNum, java.lang.String securityCode) throws InvalidLoginCredentialException_Exception {
        ws.client.BDMWebService_Service service = new ws.client.BDMWebService_Service();
        ws.client.BDMWebService port = service.getBDMWebServicePort();
        return port.memberLogin(identityNum, securityCode);
    }


}
