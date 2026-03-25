package cn.gdsdxy.questionbank.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class AudioFiles {
    @TableId
    private Integer id;

    private Integer paperId;

    private String filePath;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date created;

    private String source;
    @TableField("content")
    private String content;

    private String photo;
}