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
import java.beans.Transient;
import java.util.List;
import java.util.Map;

/**
 *  Controller
 *
 * @author LHY
 * @date 2020-04-09 22:32:28
 */
@Slf4j
@Validated
@Controller
public class TbIntegralFlowController extends BaseController {

    @Autowired
    private ITbIntegralFlowService tbIntegralFlowService;
    @Autowired
    private ITbUserService tbUserService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "tbIntegralFlow")
    public String tbIntegralFlowIndex(){
        return FebsUtil.view("tbIntegralFlow/tbIntegralFlow");
    }

    @GetMapping("tbIntegralFlow")
    @ResponseBody
    @RequiresPermissions("tbIntegralFlow:list")
    public FebsResponse getAllTbIntegralFlows(TbIntegralFlow tbIntegralFlow) {
        return new FebsResponse().success().data(tbIntegralFlowService.findTbIntegralFlows(tbIntegralFlow));
    }

    @GetMapping("tbIntegralFlow/list")
    @ResponseBody
    @RequiresPermissions("tbIntegralFlow:list")
    public FebsResponse tbIntegralFlowList(QueryRequest request, TbIntegralFlow tbIntegralFlow) {
        Map<String, Object> dataTable = getDataTable(this.tbIntegralFlowService.findTbIntegralFlows(request, tbIntegralFlow));
        return new FebsResponse().success().data(dataTable);
    }

    @ControllerEndpoint(operation = "新增TbIntegralFlow", exceptionMessage = "新增TbIntegralFlow失败")
    @PostMapping("tbIntegralFlow")
    @ResponseBody
    @RequiresPermissions("tbIntegralFlow:add")
    public FebsResponse addTbIntegralFlow(@Valid TbIntegralFlow tbIntegralFlow) {
        this.tbIntegralFlowService.createTbIntegralFlow(tbIntegralFlow);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "删除TbIntegralFlow", exceptionMessage = "删除TbIntegralFlow失败")
    @GetMapping("tbIntegralFlow/delete")
    @ResponseBody
    @RequiresPermissions("tbIntegralFlow:delete")
    public FebsResponse deleteTbIntegralFlow(TbIntegralFlow tbIntegralFlow) {
        this.tbIntegralFlowService.deleteTbIntegralFlow(tbIntegralFlow);
        return new FebsResponse().success();
    }




    @GetMapping("tbIntegralFlow/updateDeal/{id}")
    @ResponseBody
    @RequiresPermissions("tbIntegralFlow:update")
    @Transactional
    public FebsResponse updateDeal(@PathVariable Integer id) {
        TbIntegralFlow flow = tbIntegralFlowService.getById(id);

        TbIntegralFlow param = new TbIntegralFlow();
        param.setId(id);
        param.setDeal((byte)1);

        //减去用户相关的积分
        TbUser userParam = new TbUser();
        userParam.setId(flow.getUserId());
        userParam.setPhone(flow.getPhone());

        TbUser user = tbUserService.findTbUsers(userParam).get(0);
        userParam.setPhone(null);
        //待提取的积分
        Integer integral = flow.getIntegral();
        //活动积分
        Integer activeIntegral = user.getActiveIntegral();
        //推广积分
        Integer expandIntegral = user.getExpandIntegral();
        if ((activeIntegral+expandIntegral)<integral){
            return new FebsResponse().code(HttpStatus.FAILED_DEPENDENCY).message("可用积分不足");
        }
        //先扣除活动积分的
        if (activeIntegral>0){
            //如果活动积分大于提现积分
            if (activeIntegral>=integral){
                //直接扣除活动积分，不在继续执行
                userParam.setActiveIntegral(activeIntegral-integral);
                tbUserService.updateTbUser(userParam);
                //更新流水
                param.setRemark(flow.getRemark()+"|扣除活动积分"+integral);
                tbIntegralFlowService.updateTbIntegralFlow(param);
                return new FebsResponse().success();
            }else{
                //提现积分剩余
                int sub = integral-activeIntegral;
                //余下的从推广积分扣除
                userParam.setExpandIntegral(expandIntegral-sub);//剩余
                userParam.setActiveIntegral(0);//应该没有了
                tbUserService.updateTbUser(userParam);
                //更新流水
                param.setRemark(flow.getRemark()+"|扣除活动积分"+activeIntegral+"|扣除推广积分"+sub);
                tbIntegralFlowService.updateTbIntegralFlow(param);
                return new FebsResponse().success();

            }
        }else{
            //活动积分小于等于0，只扣除推广积分
            userParam.setExpandIntegral(expandIntegral-integral);
            tbUserService.updateTbUser(userParam);
            //更新流水
            param.setRemark(flow.getRemark()+"|扣除推广积分"+integral);
            tbIntegralFlowService.updateTbIntegralFlow(param);
            return new FebsResponse().success();
        }

    }

    @GetMapping("tbIntegralFlow/update/{id}")
    @RequiresPermissions("tbIntegralFlow:update")
    public String jobUpdate(@NotBlank(message = "{required}") @PathVariable Integer id, Model model) {
        TbIntegralFlow flow = tbIntegralFlowService.getById(id);
        model.addAttribute("flow", flow);
        return FebsUtil.view("tbIntegralFlow/update");
    }

    @ControllerEndpoint(operation = "修改TbIntegralFlow", exceptionMessage = "修改TbIntegralFlow失败")
    @PostMapping("tbIntegralFlow/update")
    @ResponseBody
    @RequiresPermissions("tbIntegralFlow:update")
    public FebsResponse updateTbIntegralFlow(TbIntegralFlow tbIntegralFlow) {
        this.tbIntegralFlowService.updateTbIntegralFlow(tbIntegralFlow);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改TbIntegralFlow", exceptionMessage = "导出Excel失败")
    @PostMapping("tbIntegralFlow/excel")
    @ResponseBody
    @RequiresPermissions("tbIntegralFlow:export")
    public void export(QueryRequest queryRequest, TbIntegralFlow tbIntegralFlow, HttpServletResponse response) {
        List<TbIntegralFlow> tbIntegralFlows = this.tbIntegralFlowService.findTbIntegralFlows(queryRequest, tbIntegralFlow).getRecords();
        ExcelKit.$Export(TbIntegralFlow.class, response).downXlsx(tbIntegralFlows, false);
    }
}
