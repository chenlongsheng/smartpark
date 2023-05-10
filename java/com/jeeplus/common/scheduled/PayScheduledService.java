/**
 *
 */
package com.jeeplus.common.scheduled;

import java.util.List;

import com.jeeplus.modules.smartpark.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jeeplus.common.persistence.MapEntity;
import com.jeeplus.modules.smartpark.dao.BalanceDao;

/**
 * @author admin
 */
@Service
@Lazy(false)
@EnableScheduling
public class PayScheduledService {


    @Autowired
    BalanceService balanceService;

    @Scheduled(cron = "0 */5 * * * ?")
    private void loadLoopData() {


        try {
            balanceService.updateData();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
