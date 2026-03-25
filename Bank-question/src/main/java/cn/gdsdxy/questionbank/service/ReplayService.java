package cn.gdsdxy.questionbank.service;

import cn.gdsdxy.questionbank.dto.manage.SearchPageDto;
import cn.gdsdxy.questionbank.entity.Message;
import cn.gdsdxy.questionbank.entity.manage.Replay;
import cn.gdsdxy.questionbank.repository.MessageRepository;
import cn.gdsdxy.questionbank.repository.ReplayRepository;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class ReplayService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    ReplayRepository replayRepository;

    public void addReplay(Replay replay) {
        replayRepository.insert(replay);
    }

    public List<Replay> searchReplay(Integer messageId) {
        // 使用ReplayRepository中的方法来根据关键字搜索回复
        List<Replay> replayList = replayRepository.selectList(new QueryWrapper<Replay>().eq("messageId", messageId));
        System.out.println(replayList);
        return  replayList;
    }
}