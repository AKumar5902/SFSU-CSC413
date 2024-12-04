package dao;

import dto.TransactionDto;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DaoTestStore {

    @Test(singleThreaded = true)
    public void testStore(){
        TransactionDao.reset();
        TransactionDao transactionDao = TransactionDao.getInstance();
        String id = String.valueOf(Math.random());
        TransactionDto transactionDto = new TransactionDto(id);
        transactionDao.put(transactionDto);
        // id's that do not exist should be empty
        Assert.assertNull(transactionDao.get(String.valueOf(Math.random())));
        // ids that do exist should return the object
        Assert.assertEquals(transactionDao.get(id), transactionDto);
        Assert.assertNull(transactionDao.get(String.valueOf(Math.random())));
    }
}
