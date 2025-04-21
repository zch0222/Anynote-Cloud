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

    @Test
    public void test() {
        // 定义时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 设置开始和结束时间
        LocalDateTime start = LocalDateTime.of(2025, 3, 1, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2025, 4, 26, 23, 59, 59);

        // 当前时间从开始时间开始
        LocalDateTime current = start;

        List<SysApiStatisticsPO> sysApiStatisticsPOS = new ArrayList<>();

        Date now = new Date();

        // 遍历每一分钟
        while (current.isBefore(end)) {

            LocalDateTime nextDay = current.plusDays(1);
            int llmDayUsageCount = 0;
            int whisperDayUsageCount = 0;

            while (current.isBefore(nextDay)) {
                LocalDateTime nextHour = current.plusHours(1);
                int llmHourUsageCount = 0;
                int whisperHourUsageCount = 0;
                while (current.isBefore(nextHour)) {
                    int llmMinuteUsageCount = new Random().nextInt(3);
                    int whisperMinuteUsageCount = new Random().nextInt(3);
                    llmHourUsageCount += llmMinuteUsageCount;
                    whisperHourUsageCount += whisperMinuteUsageCount;
                    sysApiStatisticsPOS.add(SysApiStatisticsPO.builder()
                            .startTime(Date.from(current.withSecond(0).atZone(ZoneId.systemDefault()).toInstant()))
                            .endTime(Date.from(current.withSecond(59).atZone(ZoneId.systemDefault()).toInstant()))
                            .usageCount(llmMinuteUsageCount)
                            .type(0)
                            .statisticsInterval(0)
                            .deleted(0)
                            .createTime(now)
                            .updateTime(now)
                            .build());
                    sysApiStatisticsPOS.add(SysApiStatisticsPO.builder()
                            .startTime(Date.from(current.withSecond(0).atZone(ZoneId.systemDefault()).toInstant()))
                            .endTime(Date.from(current.withSecond(59).atZone(ZoneId.systemDefault()).toInstant()))
                            .usageCount(whisperMinuteUsageCount)
                            .type(1)
                            .statisticsInterval(0)
                            .deleted(0)
                            .createTime(now)
                            .updateTime(now)
                            .build());
                    current = current.plusMinutes(1);
                }
                sysApiStatisticsPOS.add(SysApiStatisticsPO.builder()
                        .startTime(Date.from(nextHour.minusHours(1).withMinute(0).withSecond(0).atZone(ZoneId.systemDefault()).toInstant()))
                        .endTime(Date.from(nextHour.minusHours(1).withMinute(59).withSecond(59).atZone(ZoneId.systemDefault()).toInstant()))
                        .usageCount(llmHourUsageCount)
                        .type(0)
                        .statisticsInterval(1)
                        .deleted(0)
                        .createTime(now)
                        .updateTime(now)
                        .build());
                sysApiStatisticsPOS.add(SysApiStatisticsPO.builder()
                        .startTime(Date.from(nextHour.minusHours(1).withMinute(0).withSecond(0).atZone(ZoneId.systemDefault()).toInstant()))
                        .endTime(Date.from(nextHour.minusHours(1).withMinute(59).withSecond(59).atZone(ZoneId.systemDefault()).toInstant()))
                        .usageCount(whisperHourUsageCount)
                        .type(1)
                        .statisticsInterval(1)
                        .deleted(0)
                        .createTime(now)
                        .updateTime(now)
                        .build());
                llmDayUsageCount += llmHourUsageCount;
                whisperDayUsageCount += whisperHourUsageCount;
            }
            sysApiStatisticsPOS.add(SysApiStatisticsPO.builder()
                    .startTime(Date.from(nextDay.minusDays(1).withHour(0).withMinute(0).withSecond(0).atZone(ZoneId.systemDefault()).toInstant()))
                    .endTime(Date.from(nextDay.minusDays(1).withHour(23).withMinute(59).withSecond(59).atZone(ZoneId.systemDefault()).toInstant()))
                    .usageCount(llmDayUsageCount)
                    .type(0)
                    .statisticsInterval(2)
                    .deleted(0)
                    .createTime(now)
                    .updateTime(now)
                    .build());
            sysApiStatisticsPOS.add(SysApiStatisticsPO.builder()
                    .startTime(Date.from(nextDay.minusDays(1).withMinute(0).withSecond(0).atZone(ZoneId.systemDefault()).toInstant()))
                    .endTime(Date.from(nextDay.minusDays(1).withHour(23).withMinute(59).withSecond(59).atZone(ZoneId.systemDefault()).toInstant()))
                    .usageCount(whisperDayUsageCount)
                    .type(1)
                    .statisticsInterval(2)
                    .deleted(0)
                    .createTime(now)
                    .updateTime(now)
                    .build());
        }
        sysApiStatisticsService.saveBatch(sysApiStatisticsPOS);
    }
}
