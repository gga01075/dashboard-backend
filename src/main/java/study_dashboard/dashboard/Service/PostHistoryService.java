package study_dashboard.dashboard.Service;

import org.springframework.stereotype.Service;
import study_dashboard.dashboard.Dto.PostHistoryDto;
import study_dashboard.dashboard.Mapper.PostHistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class PostHistoryService {

    @Autowired
    PostHistoryMapper postHistoryMapper;

    public List<PostHistoryDto> getPostTitleList() {
           return postHistoryMapper.getPostTitleList();
    }

    public void registerPost(PostHistoryDto postHistoryDto) {
        postHistoryMapper.insertPost(postHistoryDto);
    }
}
