package cn.gdsdxy.questionbank.service;

import cn.gdsdxy.questionbank.dto.MessageDto;
import cn.gdsdxy.questionbank.dto.manage.ExamDto;
import cn.gdsdxy.questionbank.dto.manage.SearchPageDto;
import cn.gdsdxy.questionbank.entity.Message;
import cn.gdsdxy.questionbank.entity.manage.ExamManage;
import cn.gdsdxy.questionbank.entity.manage.Replay;
import cn.gdsdxy.questionbank.repository.MessageRepository;
import cn.gdsdxy.questionbank.repository.ReplayRepository;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    ReplayRepository replayRepository;

    public PageInfo<Message> getAllMessage(SearchPageDto searchPageDto) throws Exception{
        PageHelper.startPage(searchPageDto.getPage(), searchPageDto.getSize());
        QueryWrapper<Message> messageQueryWrapper = new QueryWrapper<>();
        List<Message> messageList = messageRepository.selectList(messageQueryWrapper);
        PageInfo<Message> PageInfo = new PageInfo(messageList);
        PageInfo.setList(messageList);
        return PageInfo;
    }
    public void addMessage(Message message) {
        messageRepository.insert(message);
    }

    public PageInfo<MessageDto> getAllMessageDto(SearchPageDto searchPageDto) throws Exception {
        List<MessageDto> messageDtoList = new ArrayList<>();

        PageHelper.startPage(searchPageDto.getPage(), searchPageDto.getSize());

        List<Message> messageList = messageRepository.selectList(null);

        for (Message message : messageList) {
            MessageDto messageDto = new MessageDto();
            messageDto.setId(message.getId());
            messageDto.setTitle(message.getTitle());
            messageDto.setContent(message.getContent());
            messageDto.setTime(message.getTime());

            QueryWrapper<Replay> replayQueryWrapper = new QueryWrapper<>();
            replayQueryWrapper.eq("messageId", message.getId());
            List<Replay> replayList = replayRepository.selectList(replayQueryWrapper);
            MessageDto newMessageDto = new MessageDto();

            if(replayList.size()==0){
                messageDtoList.add(messageDto);
            }else {
                for (Replay replay : replayList) {
                    newMessageDto.setId(messageDto.getId());
                    newMessageDto.setTitle(messageDto.getTitle());
                    newMessageDto.setContent(messageDto.getContent());
                    newMessageDto.setTime(messageDto.getTime());

                    // 更新新的MessageDto对象的回复信息
                    if (newMessageDto.getId() == replay.getMessageId() && newMessageDto.getReplay() != null) {
                        newMessageDto.setReplay(newMessageDto.getReplay() + "," + replay.getReplay());
                    } else {
                        newMessageDto.setReplay(replay.getReplay());
                        newMessageDto.setReplayTime(replay.getReplayTime());
                    }
                    messageDtoList.add(newMessageDto);
                }
            }
        }

        List<MessageDto> filteredList = messageDtoList.stream()
                .collect(Collectors.toMap(MessageDto::getId, messageDto1 -> messageDto1, (existing, replacement) -> existing))
                .values().stream()
                .collect(Collectors.toList());
        PageInfo<MessageDto> pageInfo = new PageInfo<>(filteredList);
        pageInfo.setList(filteredList);
        return pageInfo;
    }

}