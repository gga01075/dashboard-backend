package study_dashboard.dashboard.Mapper;

import study_dashboard.dashboard.Dto.PostHistoryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostHistoryMapper {
    List<PostHistoryDto> getPostTitleList();
}
