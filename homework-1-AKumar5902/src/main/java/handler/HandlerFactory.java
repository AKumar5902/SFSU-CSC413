package handler;

import request.ParsedRequest;

public class HandlerFactory {
    // routes based on the path. Add your custom handlers here
    public static BaseHandler getHandler(ParsedRequest request) {
        //String path = request.getPath();
        switch (request.getPath()) {
            case "/createUser":
                return new CreateUserHandler();

            case "/transfer":
                return new TransferHandler();

            case "/getTransactions":
                return new GetTransactionsHandler();

            case "/createDeposit":
                return new CreateDepositHandler();

            default:
                return new FallbackHandler();
        }
    }
}
