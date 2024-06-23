package study_dashboard.dashboard.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study_dashboard.dashboard.Dto.UserDto;
import study_dashboard.dashboard.Mapper.UserMapper;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public boolean checkUserExists(String userID) {
        Optional<List<String>> userNamesOptional = userMapper.findUserNameList(userID);
        return userNamesOptional.isPresent() && !userNamesOptional.get().isEmpty();
    }

    public void signUp(UserDto userDto) {
        userMapper.insertUser(userDto);
    }

}
