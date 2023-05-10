package com.jeeplus.modules.smartpark.entity;

import com.jeeplus.common.persistence.DataEntity;
import lombok.Data;

import java.io.Serializable;

@Data
public class Customer extends DataEntity<Customer> implements Serializable {


    private String name;        //名称
    private String electricityUnitId;
    private String  electricityUnitName;
    private String phone;
    private String addr;
    private String createUser;
    private String cancelDate;
    private String  remark;
    private String  customerNo;









}
