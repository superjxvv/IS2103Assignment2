package selfservicekioskclient;

import ejb.session.stateless.BookOperationControllerRemote;
import ejb.session.stateless.MemberOperationControllerRemote;
import ejb.session.stateless.StaffOperationControllerRemote;
import entity.MemberEntity;
import java.util.ArrayList;
import java.util.Scanner;
import util.exception.InvalidLoginCredentialException;
import util.exception.MemberNotFoundException;

public class MainApp {

    private StaffOperationControllerRemote staffOperationControllerRemote;
    private MemberOperationControllerRemote memberOperationControllerRemote;
    private BookOperationControllerRemote bookOperationControllerRemote;
    private MemberEntity currentMemberEntity;

    private MemberOperationModule memberOperationModule;

    public MainApp() {
    }

    public MainApp(StaffOperationControllerRemote staffOperationControllerRemote, MemberOperationControllerRemote memberOperationControllerRemote, BookOperationControllerRemote bookOperationControllerRemote) {
        this.staffOperationControllerRemote = staffOperationControllerRemote;
        this.memberOperationControllerRemote = memberOperationControllerRemote;
        this.bookOperationControllerRemote = bookOperationControllerRemote;
    }

    public void runApp() {
        Scanner scanner = new Scanner(System.in);
        Integer response;

        while (true) {
            System.out.println("*** Welcome to Self-Service Kiosk ***\n");
            System.out.println("1: Register");
            System.out.println("2: Login");
            System.out.println("3: Exit\n");

            response = 0;

            while (response < 1 || response > 3) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    try {
                        doRegisterMember();
                    } catch (MemberNotFoundException ex) {

                    }
                } else if (response == 2) {
                    try {
                        doLogin();
                        memberOperationModule = new MemberOperationModule(staffOperationControllerRemote, memberOperationControllerRemote, bookOperationControllerRemote, currentMemberEntity);
                        menuMain();
                    } catch (InvalidLoginCredentialException ex) {

                    }
                } else if (response == 3) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 3) {
                break;
            }
        }
    }

    private void doLogin() throws InvalidLoginCredentialException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** POS System :: Login ***\n");
        System.out.print("Enter Identity Number> ");
        String identityNum = scanner.nextLine().trim();
        System.out.print("Enter Security Code> ");
        String securityCode = scanner.nextLine().trim();

        if (identityNum.length() > 0 && securityCode.length() > 0) {
            try {
                currentMemberEntity = memberOperationControllerRemote.memberLogin(identityNum, securityCode);
                System.out.println("Login successful!\n");
            } catch (InvalidLoginCredentialException ex) {
                System.out.println("Invalid login credential: " + ex.getMessage() + "\n");

                throw new InvalidLoginCredentialException();
            }
        } else {
            System.out.println("Invalid login credential!");
        }
    }

    private void doRegisterMember() throws MemberNotFoundException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Self-Service Kiosk :: Register ***\n");
        System.out.print("Enter Identity Number> ");
        String identityNum = scanner.nextLine().trim();
        System.out.print("Enter Security Code> ");
        String securityCode = scanner.nextLine().trim();
        System.out.print("Enter First Name> ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Enter Last Name> ");
        String lastName = scanner.nextLine().trim();
        System.out.print("Enter Gender> ");
        String gender = scanner.nextLine().trim();
        System.out.print("Enter Age> ");
        Integer age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Phone> ");
        String phone = scanner.nextLine().trim();
        System.out.print("Enter Address> ");
        String address = scanner.nextLine().trim();

        if (identityNum.length() > 0 && securityCode.length() > 0 && firstName.length() > 0 && lastName.length() > 0 && gender.length() > 0
                && phone.length() > 0 && address.length() > 0 && age > 0) {
            try {
                memberOperationControllerRemote.retrieveMemberByIdentityNum(identityNum);
                System.out.println("Member " + identityNum + " exists. Please login instead.");
            } catch (MemberNotFoundException ex) {
                MemberEntity member = new MemberEntity(new ArrayList<>(), new ArrayList<>(), firstName, lastName, gender, age, identityNum, phone, address, securityCode);
                memberOperationControllerRemote.createNewMember(member);
                System.out.println("You have been registered successfully!");
            }
        } else {
            System.out.println("Registration failed. Please enter valid inputs for all fields.");
        }
    }

    private void menuMain() {
        Scanner scanner = new Scanner(System.in);
        Integer response;

        while (true) {
            System.out.println("*** Self-Service Kiosk :: Main ***\n");
            System.out.println("You are login as " + currentMemberEntity.getFirstName() + " " + currentMemberEntity.getLastName() + "\n");
            System.out.println("1: Borrow Book");
            System.out.println("2: View Lent Books");
            System.out.println("3: Return Book");
            System.out.println("4: Extend Book");
            System.out.println("5: Pay Fines");
            System.out.println("6: Search Book");
            System.out.println("7: Reserve Book");
            System.out.println("8: Logout\n");
            response = 0;

            while (response < 1 || response > 8) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response >= 1 && response <= 7) {
                    memberOperationModule.menuMemberOperations(response);
                } else if (response == 8) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 8) {
                break;
            }
        }
    }
}
