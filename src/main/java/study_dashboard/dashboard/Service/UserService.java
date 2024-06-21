package study_dashboard.dashboard.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study_dashboard.dashboard.Dto.UserDto;
import study_dashboard.dashboard.Mapper.UserMapper;

import java.util.List;

@Service
public class UserService {
    UserMapper userMapper;

    public void signUp(UserDto userDto) {
        userMapper.insertUser(userDto);
    }

    public boolean checkUserExists(String userID) {
        return userMapper.findUserNameList(userID) != null;
    }

}
