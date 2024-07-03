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

    @PostMapping("/signIn")
    public ResponseEntity<CommonResponse<String>> signIn(@RequestBody UserDto userDto) {
        /*
            로그인을 한다면 무엇이 필요할까?
            1. 프론트에서 전송한 userId와 password가 DB에 있는 값과 일치여부 확인
            2. JWT토큰 생성 및 발급
            3.
         */
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getUserID(), userDto.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userDetails.getUsername());



        String token = userService.signIn(userDto);
        return ResponseUtils.buildSuccessResponse(token);
    }
}
