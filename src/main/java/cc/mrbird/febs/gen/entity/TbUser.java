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
 * @date 2020-04-09 22:17:08
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
     * 推广积分
     */
    @TableField("expand_integral")
    private Integer expandIntegral;

    /**
     * 活动积分免费送的
     */
    @TableField("active_integral")
    private Integer activeIntegral;

    /**
     * 
     */
    @TableField("pid")
    private Integer pid;

    /**
     * 
     */
    @TableField("real_name")
    private String realName;

    /**
     * 
     */
    @TableField("zfb")
    private String zfb;

    /**
     * 
     */
    @TableField("create_date")
    private Date createDate;

}
