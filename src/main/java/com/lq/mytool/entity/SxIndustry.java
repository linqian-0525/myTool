package com.lq.mytool.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 项目所属行业分类表
 * </p>
 *
 * @author LQ
 * @since 2021-06-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SxIndustry extends Model {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 行业编码
     */
    private String typeCode;

    /**
     * 行业名称
     */
    private String typeName;

    /**
     * 父级id
     */
    private Long parentId;

    private Integer lft;

    private Integer rig;


}
