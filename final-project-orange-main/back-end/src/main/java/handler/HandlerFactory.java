package handler;

import request.ParsedRequest;

public class HandlerFactory {
    // routes based on the path. Add your custom handlers here
    public static BaseHandler getHandler(ParsedRequest request) {
        String path = request.getPath();

        return switch (path) {
            case "/createUser" -> new CreateUserHandler();
            case "/login" -> new LoginHandler();
            case "/createDeposit" -> new CreateDepositHandler();
            case "/getTransactions" -> new GetTransactionsHandler();
            case "/withdraw" -> new WithdrawHandler();
            case "/transfer" -> new TransferHandler();
            case "/logout" -> new MiscHandlers.LogOutHandler();
            case "/sendMessage" -> new MessageHandler();

            case "/addContact" -> new AddContactHandler();
            case "/getContacts" -> new GetContactsHandler();
            case "/deleteContact" -> new DeleteContactsHandler();

            default -> new FallbackHandler();


        };
    }

}