package study_dashboard.dashboard.Mapper;

import study_dashboard.dashboard.Dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserMapper {
    String findUserIdList(String userID);
    void insertUser(UserDto userDto);

}
