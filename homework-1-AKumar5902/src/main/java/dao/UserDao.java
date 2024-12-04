package dao;

import dto.UserDto;
import java.util.ArrayList;
import java.util.List;

// TODO fill this out
public class UserDao implements BaseDao<UserDto> {

    ArrayList<UserDto> userDtoList = new ArrayList<UserDto>();

    private static UserDao instance = new UserDao();

    public static UserDao getInstance() {
        return instance;
    }

    // TODO fill this out
    @Override
    public void put(UserDto messageDto) {
        userDtoList.add(messageDto);

    }

    // TODO fill this out
    @Override
    public UserDto get(String uniqueId) {
        for( UserDto userDto : userDtoList){
            if(userDto.getUniqueId().equals(uniqueId)){
                return userDto;
            }
        }

        return null;
    }

    // TODO fill this out
    @Override
    public List<UserDto> getAll() {

        return userDtoList;
    }

    // only for testing, do not call this method
    public static void reset() {

        instance = new UserDao();
    }
}
