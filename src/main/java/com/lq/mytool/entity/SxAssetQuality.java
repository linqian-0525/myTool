package com.lq.mytool.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 联合授信-企业在本机构资产质量情况
 * </p>
 *
 * @author astupidcoder
 * @since 2021-06-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SxAssetQuality extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 最近一笔业务金额（单位：万元）
     */
    private BigDecimal latestBusinessAmount;

    /**
     * 在本机构最近发生的一笔不良资产金额（单位：万元）
     */
    private BigDecimal latestBadAssertAmount;

    /**
     * 正常（单位：万元）
     */
    private BigDecimal normalAmount;

    /**
     * 次级（单位：万元）
     */
    private BigDecimal secondaryAmount;

    /**
     * 可疑（单位：万元）
     */
    private BigDecimal suspiciousAmount;

    /**
     * 关注（单位：万元）
     */
    private BigDecimal followAmount;

    /**
     * 损失（单位：万元）
     */
    private BigDecimal lossAmount;

    /**
     * 机构id
     */
    private Long organizationId;

    /**
     * 流程编号
     */
    private String flowNo;

    /**
     * 企业名称
     */
    private String enterpriseName;

    /**
     * 统一社会信用代码
     */
    private String enterpriseUniscid;

    /**
     * 是否有不良资产 1-有 0-没有
     */
    private Integer hasBadAssets;

    /**
     * 用户id
     */
    private Long userId;

    private Integer version;

    /**
     * 删除标记 0-正常 1-删除
     */
    private Integer maskDeleted;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 数据迁移使用
     */
    private String organizationName;


}
