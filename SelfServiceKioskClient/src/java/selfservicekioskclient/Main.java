package selfservicekioskclient;

import ejb.session.stateless.BookOperationControllerRemote;
import ejb.session.stateless.MemberOperationControllerRemote;
import ejb.session.stateless.StaffOperationControllerRemote;
import javax.ejb.EJB;

public class Main {

    @EJB
    private static StaffOperationControllerRemote staffOperationControllerRemote;
    @EJB
    private static MemberOperationControllerRemote memberOperationControllerRemote;
    @EJB
    private static BookOperationControllerRemote bookOperationControllerRemote;

    public static void main(String[] args) {
        MainApp mainApp = new MainApp(staffOperationControllerRemote, memberOperationControllerRemote, bookOperationControllerRemote);
        mainApp.runApp();
    }

}
