package cc.mrbird.febs.gen.entity;

import java.math.BigDecimal;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 *  Entity
 *
 * @author LHY
 * @date 2020-04-09 22:17:10
 */
@Data
@TableName("tb_vip")
public class TbVip {

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 等级
     */
    @TableField("level")
    private Integer level;

    /**
     * 等级名称
     */
    @TableField("level_name")
    private String levelName;

    /**
     * 直推奖励
     */
    @TableField("reward")
    private BigDecimal reward;
    /**
     * 升级标准
     */
    @TableField("upgrade_standard")
    private Integer upgradeStandard;

    /**
     * 多个竖分逗号分割一位0小于1大于二表示金额三提比例
     */
    @TableField("team_remuneration")
    private String teamRemuneration;

    /**
     * 
     */
    @TableField("top")
    private Byte top;

    /**
     * 
     */
    @TableField("flag")
    private Byte flag;

}
