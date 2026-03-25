package cn.gdsdxy.questionbank.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.gdsdxy.questionbank.entity.ChatRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 聊天记录数据访问接口
 * 继承MyBatis-Plus的BaseMapper，无需编写XML即可实现基本CRUD操作
 */
@Mapper
public interface ChatRecordMapper extends BaseMapper<ChatRecord> {
    // MyBatis-Plus的BaseMapper已经提供了以下常用方法：
    // insert: 插入一条记录
    // selectById: 根据ID查询
    // selectList: 根据条件查询列表
    // updateById: 根据ID更新
    // deleteById: 根据ID删除
    // 如需复杂查询，可以在此处添加自定义方法
}