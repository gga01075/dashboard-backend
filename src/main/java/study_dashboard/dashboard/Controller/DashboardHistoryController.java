
package study_dashboard.dashboard.Controller;
import org.springframework.web.bind.annotation.*;
import study_dashboard.dashboard.Dto.CommonResponse;
import study_dashboard.dashboard.Dto.PostHistoryDto;
import study_dashboard.dashboard.Service.PostHistoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import study_dashboard.dashboard.Utils.ResponseUtils;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/posts")
public class DashboardHistoryController {

    @Autowired
    PostHistoryService postHistoryService;

    @GetMapping
    public ResponseEntity<CommonResponse<List<PostHistoryDto>>> getPostsList () {
        List<PostHistoryDto> postTitleList = postHistoryService.getPostTitleList();
        if (postTitleList.isEmpty()) {
            return ResponseUtils.buildErrorResponse("No content found", HttpStatus.NO_CONTENT);
        }
        return ResponseUtils.buildSuccessResponse(postTitleList);
    }

    @PostMapping("/register")
    public ResponseEntity<CommonResponse<String>> registerPost(@RequestBody PostHistoryDto postHistoryDto) {
        try {
            postHistoryService.registerPost(postHistoryDto);
            return ResponseUtils.buildCreatedResponse("Post registered successfully!");
        } catch (Exception e) {
            return ResponseUtils.buildErrorResponse("Failed to register post", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
