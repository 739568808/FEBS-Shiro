package cc.mrbird.febs.gen.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.gen.entity.TbIntegralFlow;
import cc.mrbird.febs.gen.service.ITbIntegralFlowService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
