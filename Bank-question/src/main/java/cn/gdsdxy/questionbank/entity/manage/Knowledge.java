package cn.gdsdxy.questionbank.entity.manage;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("knowledge")
public class Knowledge {
    @TableId(type = IdType.AUTO)
    private Long id;  // 知识点ID

    private String name;  // 知识点名称

    private Long parentId;  // 父知识点ID

    private String subject;  // 所属科目
}