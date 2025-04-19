//
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.annotation.Resource;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//@SpringBootTest
//public class Test {
//
//    @Resource
//    private
//
//
//
//    @org.junit.jupiter.api.Test
//    public void test() {
//        // 定义时间格式
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//
//        // 设置开始和结束时间
//        LocalDateTime start = LocalDateTime.of(2025, 4, 1, 0, 0, 0);
//        LocalDateTime end = LocalDateTime.of(2025, 4, 18, 23, 59, 59);
//
//        // 当前时间从开始时间开始
//        LocalDateTime current = start;
//
//        // 遍历每一分钟
//        while (current.isBefore(end)) {
//            // 输出每分钟的0秒
//            System.out.println(current.withSecond(0).format(formatter));
//
//            // 输出每分钟的59秒
//            System.out.println(current.withSecond(59).format(formatter));
//
//            // 增加一分钟
//            current = current.plusMinutes(1);
//        }
//
//        // 处理最后一分钟（如果结束时间正好是某分钟的59秒）
//        if (end.getSecond() == 59) {
//            System.out.println(end.withSecond(0).format(formatter));
//            System.out.println(end.format(formatter));
//        }
//    }
//
//}
