package handler;

import request.ParsedRequest;

public class HandlerFactory {
    // routes based on the path. Add your custom handlers here
    public static BaseHandler getHandler(ParsedRequest request) {
        switch (request.getPath()) {
            case "/createUser":
                return new CreateUserHandler();

            case "/transfer":
                return new TransferHandler();

            case "/getTransactions":
                return new GetTransactionsHandler();

            case "/createDeposit":
                return new CreateDepositHandler();

            case "/withdraw":
                return new WithdrawHandler();

            case "/login":
                return new LoginHandler();

            default:
                return new FallbackHandler();
        }
    }

}
