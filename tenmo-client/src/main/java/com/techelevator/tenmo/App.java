package com.techelevator.tenmo;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.UserService;

import java.math.BigDecimal;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AccountService accountService = new AccountService(API_BASE_URL);
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final UserService userService=new UserService(API_BASE_URL);

    private User selectedUser;
    private AuthenticatedUser currentUser;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
		// TODO Auto-generated method stub

        System.out.println("Your balance is: "+ accountService.getAccount(currentUser,currentUser.getUser().getId()).getBalance());
	}

	private void viewTransferHistory() {
		// TODO Auto-generated method stub
	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
	}

	private void sendBucks() {
		// TODO Auto-generated method stub
        consoleService.printAllUsers(currentUser,userService);
        int userIdEntered=0;
        System.out.println("enter a user id");
        userIdEntered= consoleService.promptForInt("Please enter a user ID to process the sending operation");
        System.out.println(userIdEntered);

       selectedUser = userService.findUser(currentUser,userIdEntered);
        System.out.println("You are sending money to this user: ");
        System.out.println("User id: "+selectedUser.getId()+"  Username: "+ selectedUser.getUsername());
        BigDecimal transferAmount = BigDecimal.valueOf(Long.parseLong(consoleService.promptForString("Please enter the amount you want to  send  the sending." )));
        Account fromAccount = new Account();
        Account toAccount = new Account();
        fromAccount=accountService.getAccount(currentUser, currentUser.getUser().getId());
        toAccount = accountService.getAccount(currentUser , (long) userIdEntered);
        System.out.println("from account"+ fromAccount.getAccount_id()+"balance"+ fromAccount.getBalance());
        fromAccount.setBalance(fromAccount.getBalance().subtract(transferAmount));
        toAccount.setBalance(toAccount.getBalance().add(transferAmount));
//

        accountService.updateAccountBalance(currentUser,fromAccount);
        accountService.updateAccountBalance(currentUser,toAccount);


        System.out.println("the new balance of"+ currentUser.getUser().getUsername()+"is");
        System.out.println(fromAccount.getBalance());

        System.out.println("the new balance of"+ selectedUser.getUsername()+"is");
        System.out.println(toAccount.getBalance());

    }

	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}
    //////////////// our APP's methods
    private void sending(UserService userService,User selectedUser){
        if ((currentUser.getUser().equals(selectedUser))){
            System.out.println("You cannot send money to your own account");
        }

    }

}
