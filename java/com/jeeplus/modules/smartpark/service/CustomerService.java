/**
 *
 */
package com.jeeplus.modules.smartpark.service;

import com.jeeplus.common.persistence.MapEntity;
import com.jeeplus.common.persistence.Page;
import com.jeeplus.modules.smartpark.dao.BalanceDao;
import com.jeeplus.modules.smartpark.dao.CustomerDao;
import com.jeeplus.modules.smartpark.dao.MunicipalDao;
import com.jeeplus.modules.smartpark.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author admin
 */
@Service
@Transactional(readOnly = true)
public class CustomerService {

    @Autowired
    CustomerDao customerDao;


    public Page<MapEntity> getCustomerList(Page<MapEntity> page, MapEntity entity) {
        entity.setPage(page);
        List<MapEntity> list = customerDao.getCustomerList(entity);
        page.setList(list);
        return page;
    }

    @Transactional(readOnly = false)
    public Integer insertCustomer(Customer customer) {


        int c = customerDao.getCustomerById(customer.getElectricityUnitId());

        if (c > 0) {
            throw new RuntimeException();
        }

        customerDao.insertHistorytimeByunitId(customer.getElectricityUnitId());  //向t_history_time 插入 新客户电表起始时间

        customerDao.insertCustomer(customer);
        return 1;

    }

    @Transactional(readOnly = false)
    public void updateCustomerById(Customer customer) {

        customerDao.updateCustomerById(customer);

    }

    @Transactional(readOnly = false)
    public void deleteCustomerById(String id) {

        customerDao.deleteCustomerById(id);

    }


}
