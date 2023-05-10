/**
 *
 */
package com.jeeplus.modules.smartpark.web;

import com.jeeplus.common.persistence.MapEntity;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.ServletUtils;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.smartpark.entity.Customer;
import com.jeeplus.modules.smartpark.service.CustomerService;
import com.jeeplus.modules.sys.utils.UserUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.TryCatchFinally;
import java.util.List;

/**
 * @author admin
 */
@Controller
@RequestMapping(value = "customer")
public class CustomerController extends BaseController {

    @Autowired
    private CustomerService customerService;


    @RequestMapping(value = {"getCustomerList"})
    @ResponseBody
    public String getCustomerList(HttpServletRequest request, HttpServletResponse response, String bureauId,
                                  String iphone, String electricityUnitName, String customerName, String beginTime,
                                  String endTime, String cannelBeginTime, String cannelEndTime) {

        Page<MapEntity> page = null;

        MapEntity entity = new MapEntity();
        entity.put("bureauId", bureauId);
        entity.put("customerName", customerName);
        entity.put("phone", iphone);
        entity.put("electricityUnitName", electricityUnitName);
        entity.put("beginTime", beginTime);
        entity.put("endTime", endTime);
        entity.put("cannelBeginTime", cannelBeginTime);
        entity.put("cannelEndTime", cannelEndTime);

        try {
            page = customerService.getCustomerList(new Page<MapEntity>(request, response), entity);
        } catch (Exception e) {
            e.printStackTrace();
            return ServletUtils.buildRs(false, "失败", page);
        }
        return ServletUtils.buildRs(true, "成功", page);
    }


    @RequestMapping(value = {"insertCustomer"})
    @ResponseBody
    public String insertCustomer(Customer customer) {

        try {
            if (customer.getId() == null || customer.getId() == "") {
                customer.setCreateUser(UserUtils.getUser().getId());
                customerService.insertCustomer(customer);
            } else {
                customerService.updateCustomerById(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ServletUtils.buildRs(false, "失败", "");
        }
        return ServletUtils.buildRs(true, "成功", "");
    }


    @RequestMapping(value = {"deleteCustomerById"})
    @ResponseBody
    public String deleteCustomerById(String id) {

        try {
            customerService.deleteCustomerById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return ServletUtils.buildRs(true, "失败", "");
        }

        return ServletUtils.buildRs(true, "成功", "");

    }

}
