package study_dashboard.dashboard.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import study_dashboard.dashboard.Dto.CommonResponse;
import study_dashboard.dashboard.Dto.UserDto;
import study_dashboard.dashboard.Service.UserService;
import study_dashboard.dashboard.Utils.ResponseUtils;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signUp")
    public ResponseEntity<CommonResponse<String>> signUp(@RequestBody UserDto userDto) {
        boolean isUserExists = userService.checkUserExists(userDto.getUserID());
        if (isUserExists) {
            return ResponseUtils.buildErrorResponse("이미 존재하는 ID입니다", HttpStatus.CONFLICT);
        }
        try {
            userService.signUp(userDto);
            return ResponseUtils.buildCreatedResponse("USER signUp successfully!");
        } catch (Exception e) {
            return ResponseUtils.buildErrorResponse("Failed to signUp user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
