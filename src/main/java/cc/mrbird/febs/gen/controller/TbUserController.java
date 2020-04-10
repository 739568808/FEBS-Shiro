package cc.mrbird.febs.gen.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.gen.entity.TbIntegralFlow;
import cc.mrbird.febs.gen.entity.TbUser;
import cc.mrbird.febs.gen.service.ITbIntegralFlowService;
import cc.mrbird.febs.gen.service.ITbUserService;
import cc.mrbird.febs.job.entity.Job;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *  Controller
 *
 * @author LHY
 * @date 2020-04-09 22:17:08
 */
@Slf4j
@Validated
@Controller
public class TbUserController extends BaseController {

    @Autowired
    private ITbUserService tbUserService;
    @Autowired
    private ITbIntegralFlowService tbIntegralFlowService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "tbUser")
    public String tbUserIndex(){
        return FebsUtil.view("tbUser/tbUser");
    }

    @GetMapping("tbUser")
    @ResponseBody
    @RequiresPermissions("tbUser:list")
    public FebsResponse getAllTbUsers(TbUser tbUser) {
        return new FebsResponse().success().data(tbUserService.findTbUsers(tbUser));
    }

    @GetMapping("tbUser/list")
    @ResponseBody
    @RequiresPermissions("tbUser:list")
    public FebsResponse tbUserList(QueryRequest request, TbUser tbUser) {
        Map<String, Object> dataTable = getDataTable(this.tbUserService.findTbUsers(request, tbUser));
        return new FebsResponse().success().data(dataTable);
    }

    @ControllerEndpoint(operation = "新增TbUser", exceptionMessage = "新增TbUser失败")
    @PostMapping("tbUser")
    @ResponseBody
    @RequiresPermissions("tbUser:add")
    public FebsResponse addTbUser(@Valid TbUser tbUser) {
        this.tbUserService.createTbUser(tbUser);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "删除TbUser", exceptionMessage = "删除TbUser失败")
    @GetMapping("tbUser/delete")
    @ResponseBody
    @RequiresPermissions("tbUser:delete")
    public FebsResponse deleteTbUser(TbUser tbUser) {
        this.tbUserService.deleteTbUser(tbUser);
        return new FebsResponse().success();
    }



    @ControllerEndpoint(operation = "修改TbUser", exceptionMessage = "修改TbUser失败")
    @PostMapping("tbUser/update")
    @ResponseBody
    @RequiresPermissions("tbUser:update")
    public FebsResponse updateTbUser(TbUser tbUser) {
        this.tbUserService.updateTbUser(tbUser);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改TbUser", exceptionMessage = "修改TbUser失败")
    @PostMapping("tbUser/updIntegral/{type}/{id}/{addSub}/{integral}")
    @ResponseBody
    @RequiresPermissions("tbUser:update")
    @Transactional
    public FebsResponse updIntegral(@PathVariable String type,@PathVariable Integer id,@PathVariable Integer addSub,@PathVariable Integer integral) {
        //TODO 判断是否有未处理的流水
        //SELECT count(1) FROM tb_integral_flow WHERE id=#{id} AND integral_type=1 AND deal=0
        TbIntegralFlow paramFlow = new TbIntegralFlow();
        paramFlow.setUserId(id);
        paramFlow.setIntegralType((byte)1);
        paramFlow.setDeal((byte)0);
        List<TbIntegralFlow> tbIntegralFlows = tbIntegralFlowService.findTbIntegralFlows(paramFlow);
        if (CollectionUtils.isNotEmpty(tbIntegralFlows)){
            return new FebsResponse().code(HttpStatus.FAILED_DEPENDENCY).message("该用户有未完成的提现申请，请先处理");
        }
        TbUser user = tbUserService.getById(id);
        TbUser parmaUser = new TbUser();
        parmaUser.setId(id);
        if (type.equals("active")){
            if (addSub.equals(0)){
                parmaUser.setActiveIntegral(user.getActiveIntegral()+integral);
            }else {
                if (user.getActiveIntegral()<integral){
                    return new FebsResponse().code(HttpStatus.FAILED_DEPENDENCY).message("可用积分不足");
                }
                parmaUser.setActiveIntegral(user.getActiveIntegral()-integral);
            }
        }else if (type.equals("expand")){
            if (addSub.equals(0)){
                parmaUser.setExpandIntegral(user.getExpandIntegral()+integral);
            }else {
                if (user.getExpandIntegral()<integral){
                    return new FebsResponse().code(HttpStatus.FAILED_DEPENDENCY).message("可用积分不足");
                }
                parmaUser.setExpandIntegral(user.getExpandIntegral()-integral);
            }
        }
        this.tbUserService.updateTbUser(parmaUser);
        TbIntegralFlow flow = new TbIntegralFlow();
        flow.setUserId(user.getId());
        flow.setPhone(user.getPhone());
        flow.setIntegralType(addSub.byteValue());
        flow.setIntegral(integral);
        flow.setDeal((byte)1);
        String typeStr= type.equals("active")?"活动积分":"推广积分";
        String addSubStr = addSub==0?"增加":"减掉";
        flow.setRemark("管理员"+typeStr+addSubStr+integral+"积分");
        flow.setCreateDate(new Date());
        tbIntegralFlowService.updateTbIntegralFlow(flow);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改TbUser", exceptionMessage = "导出Excel失败")
    @PostMapping("tbUser/excel")
    @ResponseBody
    @RequiresPermissions("tbUser:export")
    public void export(QueryRequest queryRequest, TbUser tbUser, HttpServletResponse response) {
        List<TbUser> tbUsers = this.tbUserService.findTbUsers(queryRequest, tbUser).getRecords();
        ExcelKit.$Export(TbUser.class, response).downXlsx(tbUsers, false);
    }
}
