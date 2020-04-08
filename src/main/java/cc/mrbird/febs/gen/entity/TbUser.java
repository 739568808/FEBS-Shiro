package cc.mrbird.febs.gen.entity;


import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 *  Entity
 *
 * @author LHY
 * @date 2020-04-08 14:33:47
 */
@Data
@TableName("tb_user")
public class TbUser {

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField("phone")
    private Long phone;

    /**
     * 
     */
    @TableField("password")
    private String password;

    /**
     * 
     */
    @TableField("vip_id")
    private Integer vipId;

    /**
     * 总积分
     */
    @TableField("integral")
    private Integer integral;

    /**
     * 
     */
    @TableField("sub_integral")
    private String subIntegral;

}
