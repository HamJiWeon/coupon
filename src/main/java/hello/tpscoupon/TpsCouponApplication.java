package hello.tpscoupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TpsCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(TpsCouponApplication.class, args);
    }

}
