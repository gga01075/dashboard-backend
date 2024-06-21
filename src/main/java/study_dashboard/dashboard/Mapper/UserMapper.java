package study_dashboard.dashboard.Mapper;

import study_dashboard.dashboard.Dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    List<String> findUserNameList(String userID);
    void insertUser(UserDto userDto);
}
