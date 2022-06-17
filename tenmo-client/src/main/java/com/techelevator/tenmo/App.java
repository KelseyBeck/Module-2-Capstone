package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;

import java.math.BigDecimal;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AccountService accountService = new AccountService(API_BASE_URL);
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final UserService userService=new UserService(API_BASE_URL);
    private final TransferService transferService = new TransferService(API_BASE_URL);

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
        System.out.println("Your balance is: "+ accountService.getAccountByUser(currentUser,currentUser.getUser().getId()).getBalance());
	}

	private void viewTransferHistory() {
        ///////// printing all the transfers
        consoleService.printAllTransactions(currentUser,transferService);

        int transferChoice= consoleService.promptForInt("To exit pres 1, for transfer details press 2");

        if (transferChoice==1){
            consoleService.printMainMenu();
        }
        else if (transferChoice==2) {
            while (true) {
                long transferIdEntered = (long) consoleService.promptForInt("Please enter a transfer Id");
                try {
                    consoleService.printTransferDetails(currentUser, transferService, transferIdEntered);
                } catch (Exception e) {
                    System.out.println("Wrong transfer id, please try again");
                    this.viewTransferHistory();
                    break;
                }
            }
        }
        else {
            System.out.println("Invalid input");
            this.viewTransferHistory();

        }

	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
	}

	private void sendBucks() {
		// TODO Auto-generated method stub
        /// variables
        boolean inSending=true;
        long userIdEntered=1;
        Account fromAccount = new Account();
        Account toAccount = new Account();
        BigDecimal transferAmount=new BigDecimal(0.00);

        while (true){
            consoleService.printAllUsers(currentUser,userService);

            //////// handling the user id input
            userIdEntered= consoleService.promptForInt("Please enter a user ID to process the sending operation "+ "(0 to cancel)");
            if (userIdEntered==0) break;
            /////checking if the user id is valid and store it in a user object if it exists
            try {
                selectedUser = userService.findUser(currentUser,userIdEntered);
            } catch (Exception e) {
                System.out.println("Wrong user id, please enter a valid user id");
               this.sendBucks();
            }

            //// printing the selected user (confirmation)
            consoleService.printSelectedUser(selectedUser);

            ////// handling the transfer amount
            transferAmount = consoleService.promptForBigDecimal("Please enter the amount you want to  send  the sending. (0 to cancel)");
            if (transferAmount.compareTo(new BigDecimal(0))==0 ) {
                break;
            }

            //// instantiating accounts
            fromAccount=accountService.getAccountByUser(currentUser, currentUser.getUser().getId());
            toAccount = accountService.getAccountByUser(currentUser , userIdEntered);

            ////////////////
            if (this.checkingBeforeSending(currentUser.getUser(),selectedUser,transferAmount,userIdEntered)){

               this.handleTheSendingTransfer(currentUser,fromAccount,toAccount,transferAmount);
            }
            else {
                System.out.println("Please Try again");
                this.sendBucks();
            }

        }////while loop end

        this.mainMenu();
    }

	private void requestBucks() {
		// TODO Auto-generated method stub
	}
    //////////////// our APP's methods
    private boolean checkingBeforeSending(User fromUser,User toUser,BigDecimal transferAmount,Long userIdEntered){
        boolean isOk=true;

        if (!consoleService.checkIfUserIdExists(currentUser,userService,userIdEntered))  {
            System.out.println("You entered a wrong user id");
            isOk=false;
        }
            ///////
        if (currentUser.getUser().equals(toUser)){
            System.out.println("sorry, you can't do that,sending to your self");
            isOk=false;
        }
        if (accountService.getBalance(currentUser).compareTo(transferAmount)<0){
            System.out.println("you have insufficient funds");
            isOk=false;
        }
        if (transferAmount.compareTo(BigDecimal.valueOf(0))<0){
            System.out.println("You can't send negative amounts");
            isOk=false;
        }
        return isOk;
    }

    private void handleTheSendingTransfer(AuthenticatedUser authenticatedUser,Account fromAccount,Account toAccount,BigDecimal transferAmount){
        //// updating both accounts objects + update the database tables
        this.updatingAccounts(fromAccount,toAccount,transferAmount);
        //// creating a transfer object with a sending Type + approved
        Transfer transfer = new Transfer(2L,2L,fromAccount.getAccount_id(),toAccount.getAccount_id(),transferAmount);
        ////// sending creating the request (updating the database)
        transferService.createTransfer(currentUser,transfer);
    }
    private void updatingAccounts(Account fromAccount,Account toAccount,BigDecimal transferAmount){

        try {
            fromAccount.setBalance(fromAccount.getBalance().subtract(transferAmount));
            toAccount.setBalance(toAccount.getBalance().add(transferAmount));
            accountService.updateAccountBalance(currentUser,fromAccount);
            accountService.updateAccountBalance(currentUser,toAccount);
        } catch (Exception e) {
            System.out.println("Updating accounts failed");
        }
    }

}
