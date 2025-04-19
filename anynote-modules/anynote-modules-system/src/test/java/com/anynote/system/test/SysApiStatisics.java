package com.anynote.system.test;

import com.anynote.system.api.model.po.SysApiStatisticsPO;
import com.anynote.system.service.SysApiStatisticsService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class SysApiStatisics {


    @Resource
    private SysApiStatisticsService sysApiStatisticsService;

    //@Test
    public void test() {
        // 定义时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 设置开始和结束时间
        LocalDateTime start = LocalDateTime.of(2024, 8, 1, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 4, 18, 23, 59, 59);

        // 当前时间从开始时间开始
        LocalDateTime current = start;

        List<SysApiStatisticsPO> sysApiStatisticsPOS = new ArrayList<>();

        Date now = new Date();

        // 遍历每一分钟
        while (current.isBefore(end)) {
            sysApiStatisticsPOS.add(SysApiStatisticsPO.builder()
                    .startTime(Date.from(current.withSecond(0).atZone(ZoneId.systemDefault()).toInstant()))
                    .endTime(Date.from(current.withSecond(59).atZone(ZoneId.systemDefault()).toInstant()))
                    .usageCount(new Random().nextInt(500))
                    .type(0)
                    .statisticsInterval(0)
                    .deleted(0)
                    .createTime(now)
                    .updateTime(now)
                    .build());
            sysApiStatisticsPOS.add(SysApiStatisticsPO.builder()
                    .startTime(Date.from(current.withSecond(0).atZone(ZoneId.systemDefault()).toInstant()))
                    .endTime(Date.from(current.withSecond(59).atZone(ZoneId.systemDefault()).toInstant()))
                    .usageCount(new Random().nextInt(500))
                    .type(1)
                    .statisticsInterval(0)
                    .deleted(0)
                    .createTime(now)
                    .updateTime(now)
                    .build());
            // 增加一分钟
            current = current.plusMinutes(1);
        }
        sysApiStatisticsService.saveBatch(sysApiStatisticsPOS);
    }
}
