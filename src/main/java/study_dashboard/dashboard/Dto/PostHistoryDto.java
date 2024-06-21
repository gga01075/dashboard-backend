package study_dashboard.dashboard.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostHistoryDto {
    private String postTitle;
    private String userID;
    private String postID;
    private String content;
}
