// 新建 dto/LoginResultDTO.java
package cn.gdsdxy.questionbank.dto;
import lombok.Data;

@Data
public class LoginResultDto{
    private Integer userType; // 0:管理员, 1:教师, 2:学生
    private Integer userId;   // 真实用户ID（对应 adminId/teacherId/studentId）
}