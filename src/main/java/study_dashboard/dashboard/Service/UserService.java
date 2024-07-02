package study_dashboard.dashboard.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import study_dashboard.dashboard.Dto.UserDto;
import study_dashboard.dashboard.Mapper.UserMapper;


import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public boolean checkUserExists(String userID) {
        String userNamesOptional = userMapper.findUserIdList(userID);
        return StringUtils.hasText(userNamesOptional);
    }

    public void signUp(UserDto userDto) {
        userMapper.insertUser(userDto);
    }

    public void signIn(UserDto userDto) {

    }

}
