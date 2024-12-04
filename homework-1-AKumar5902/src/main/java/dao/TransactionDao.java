package dao;

import dto.TransactionDto;

import java.util.ArrayList;
import java.util.List;

// TODO fill this out
public class TransactionDao implements BaseDao<TransactionDto> {

    ArrayList<TransactionDto> transactionDtoList = new ArrayList<TransactionDto>();

    private static TransactionDao instance = new TransactionDao();

    private TransactionDao(){

    }

    public static TransactionDao getInstance() {

        return instance;
    }

    // TODO fill this out
    @Override
    public void put(TransactionDto transactionDto) {
        transactionDtoList.add(transactionDto);

    }

    // TODO fill this out
    @Override
    public TransactionDto get(String uniqueId) {
        for( TransactionDto transaction : transactionDtoList){
            if(transaction.getUniqueId().equals(uniqueId)){
                return transaction;
            }
        }
        return null;
    }

    // TODO fill this out
    @Override
    public List<TransactionDto> getAll() {

        return transactionDtoList;
    }

    // only for testing, do not call this method
    public static void reset() {

        instance = new TransactionDao();
    }
}
