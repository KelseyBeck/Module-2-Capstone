package com.techelevator.tenmo.services;


import com.techelevator.tenmo.App;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.UserService;

import java.math.BigDecimal;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public int promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }


    ////////// Our print menus -----


    public void printAllUsers(AuthenticatedUser authenticatedUser,UserService userService){
        System.out.println("-------------------------------");
        System.out.println("Users");
        System.out.println("ID          UserName         ");
        System.out.println("-------------------------------");

        for (User user:userService.getUsers(authenticatedUser)){
            //// print all users from the database except the current user
            if (!(user.equals(authenticatedUser.getUser())))
            System.out.println(user.getId()+"        "+user.getUsername());
        }
        System.out.println("-------------------------------");
    }

    public void printAllTransactions(AuthenticatedUser authenticatedUser,TransferService transferService){
        System.out.println("-------------------------------");
        System.out.println("Transactions");
        System.out.println("Transfer ID            Amount         ");
        System.out.println("-------------------------------");

        for (Transfer transfer:transferService.getTransfersByUserId(authenticatedUser)){
            System.out.println(transfer.getTransferId()+"          "+ transfer.getAmount());
        }
        System.out.println("-------------------------------");
    }

    public void printTransferDetails(AuthenticatedUser authenticatedUser,TransferService transferService,Long transferId){
        Transfer transfer=transferService.getTransfersByTransferId(authenticatedUser,transferId);
        System.out.println("-------------------------------");
        System.out.println("Transaction");
        System.out.println("-------------------------------");
        System.out.println("Transfer ID:" +transfer.getTransferId());
        System.out.println("Transfer Type: " + transferService.getTransfersTypeByTransferId(authenticatedUser,transferId));
        System.out.println("Transfer Status: " + transferService.getTransactionStatusByTransferId(authenticatedUser,transferId));
        System.out.println("The sender: " + transferService.getTransactionSenderByTransferId(authenticatedUser,transferId));
        System.out.println("The recipient: " + transferService.getTransactionRecipientByTransferId(authenticatedUser,transferId));
        System.out.println("The amount: "+ transfer.getAmount() + " $");
        System.out.println("-------------------------------");
    }

    public void printSelectedUser(User user){
        System.out.println("You are sending money to this user: ");
        System.out.println("User id: "+user.getId()+"  Username: "+ user.getUsername());
    }

    public boolean checkIfUserIdExists(AuthenticatedUser authenticatedUser,UserService userService,Long userIdEntered){
        User userToBeChecked=null;
        try {
            userToBeChecked = userService.findUser(authenticatedUser,userIdEntered);
            return true;
        } catch (Exception e) {
            System.out.println("Wrong user id, please enter a valid user id");
            return false;
        }
    }






}
