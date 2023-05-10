package com.jeeplus.modules.warm.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeeplus.common.utils.ServletUtils;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.warm.service.PdfPrincipalService;

/**
 * Created by ZZUSER on 2018/12/7.
 */
@Controller
@RequestMapping("/testhistory")
public class test extends BaseController {

    @Autowired
    PdfPrincipalService pdfPrincipalService;

 

    @RequestMapping("/selecthis")
    @ResponseBody
    public String selecthis() {

        pdfPrincipalService.selecthis();

        return ServletUtils.buildRs(true, "", "");
    }

}
