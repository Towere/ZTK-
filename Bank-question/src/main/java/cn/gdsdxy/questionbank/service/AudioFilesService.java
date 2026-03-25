package cn.gdsdxy.questionbank.service;

import cn.gdsdxy.questionbank.dto.manage.SearchPageDto;
import cn.gdsdxy.questionbank.entity.AudioFiles;
import cn.gdsdxy.questionbank.entity.manage.Score;
import cn.gdsdxy.questionbank.entity.questions.Wrong;
import cn.gdsdxy.questionbank.repository.AudioFilesRepository;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AudioFilesService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    AudioFilesRepository audioFilesRepository;

    public List<AudioFiles> searchAudioFiles(Integer paperId) {
        // 使用AudioFilesRepository中的方法来根据paperId查询音频
        List<AudioFiles> audioFilesList = audioFilesRepository.selectList(new QueryWrapper<AudioFiles>().eq("paperId", paperId));
        System.out.println(audioFilesList);
        return audioFilesList;
    }

    public PageInfo<AudioFiles> searchAllAudio(SearchPageDto searchPageDto){
        PageHelper.startPage(searchPageDto.getPage(), searchPageDto.getSize());
        QueryWrapper<AudioFiles> audioFilesQueryWrapper = new QueryWrapper<>();
        List<AudioFiles> audioFilesList = audioFilesRepository.selectList(audioFilesQueryWrapper);
        PageInfo<AudioFiles> PageInfo = new PageInfo(audioFilesList);
        PageInfo.setList(audioFilesList);
        return PageInfo;
    }

}