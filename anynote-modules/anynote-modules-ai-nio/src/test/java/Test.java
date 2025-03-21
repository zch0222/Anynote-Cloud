import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class Test {
    public static void main(String[] args) {
        log.info("Starting execution in thread: {}", Thread.currentThread().getName());

        Mono.fromCallable(() -> {
                    log.info("Executing FROM--------- in thread: {}", Thread.currentThread().getName());
                    return Mono.just("HI");
                })
                .publishOn(Schedulers.boundedElastic()) // 切换线程
                .doOnSubscribe(subscription -> log.info("Subscribed in thread: {}", Thread.currentThread().getName()))
                .doOnSuccess(result -> log.info("Succeeded in thread: {}", Thread.currentThread().getName()))
                .subscribe();

        log.info("Ending execution in thread: {}", Thread.currentThread().getName());
    }
}
