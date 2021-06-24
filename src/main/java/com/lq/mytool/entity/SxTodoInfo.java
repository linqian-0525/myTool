package com.lq.mytool.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 待办事项
 * </p>
 *
 * @author LQ
 * @since 2021-06-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SxTodoInfo extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 主题
     */
    private String subject;

    /**
     * 模块 1:联合授信，2:银团贷款，3:债委会，4：联合惩戒逃废债
     */
    private Integer module;

    /**
     * 状态1:待处理，2:已处理
     */
    private Integer status;

    /**
     * 待办类型 
1:牵头行变更,2:联合授信额度增加,3:联合授信额度减少,4:机构用信额度增加,5:机构用信额度减少,6:授信体成立,7:成员行加入,8:风险监测预警新增 9风险监测预警解除
10: 信息审核，11:信息变更，12:在线会商
     */
    private Integer todoType;

    /**
     * 前端跳转url
     */
    private String skipUrl;

    /**
     * 流程编号
     */
    private String flowNo;

    /**
     * 提交原因
     */
    private String remark;

    /**
     * 拒绝原因
     */
    private String refuse;

    /**
     * 投票结果 1:通过，2:不通过
     */
    private Integer voteStatus;

    /**
     * 企业名称
     */
    private String enterpriseName;

    /**
     * 统一社会信用代码
     */
    private String uscId;

    /**
     * 经办人（提交人）
     */
    private String createOrgName;

    /**
     * 经办人Id （银行）
     */
    private Long createOrgId;

    /**
     * 处理行
     */
    private Long handleOrgId;

    /**
     * 处理人
     */
    private Long handleUserId;

    /**
     * 关联数据log表id
     */
    private Long logId;

    /**
     * 代办人角色
     */
    private String roleName;

    /**
     * 乐观锁
     */
    private Integer version;

    /**
     * 其他子流程编号或者记录id编号
     */
    private String subFlowNo;

    private Integer maskDeleted;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 是否强提醒1:是，0:否
     */
    private Integer maskRemind;

    /**
     * 提醒截止时间
     */
    private LocalDateTime remindEndTime;

    /**
     * 处理人姓名
     */
    private String handleUserName;

    private String handleUserCode;

    /**
     * 创建用户
     */
    private Long createUserId;

    /**
     * 创建任务用户名
     */
    private String createUserName;

    /**
     * 债委会客户id(债委会新增)
     */
    private Long custId;

    /**
     * 债委会数据log id(债委会新增)
     */
    private Long debtCommitteeRecordId;

    /**
     * 债委会在线会商id(债委会新增)
     */
    private Long onlineProcessRecordId;

    /**
     * 处理id(债委会新增)
     */
    private Long examineId;

    /**
     * 处理行机构 数据迁移使用
     */
    private String handleOrgName;


}
