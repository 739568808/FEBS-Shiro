package cc.mrbird.febs.gen.entity;

import java.util.Date;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 *  Entity
 *
 * @author LHY
 * @date 2020-04-08 16:48:24
 */
@Data
@TableName("tb_integral_flow")
public class TbIntegralFlow {

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 类型（0进账，1出账）
     */
    @TableField("integral_type")
    private Byte integralType;

    /**
     * 积分
     */
    @TableField("integral")
    private Integer integral;

    /**
     * 0未出来，1已处理
     */
    @TableField("deal")
    private Byte deal;

    /**
     * 
     */
    @TableField("create_date")
    private Date createDate;

}
