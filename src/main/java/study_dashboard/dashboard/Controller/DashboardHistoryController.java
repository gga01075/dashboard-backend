
package study_dashboard.dashboard.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import study_dashboard.dashboard.Dto.PostHistoryDto;
import study_dashboard.dashboard.Service.PostHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class DashboardHistoryController {

    @Autowired
    PostHistoryService postHistoryService;

    @GetMapping("/")
    public List<PostHistoryDto> postHistory () {
        return postHistoryService.getPostTitleList();
    }

}
