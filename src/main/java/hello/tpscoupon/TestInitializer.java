package hello.tpscoupon;

import hello.tpscoupon.domain.Coupon;
import hello.tpscoupon.domain.User;
import hello.tpscoupon.repository.CouponRepository;
import hello.tpscoupon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TestInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CouponRepository couponRepository;


    @Override
    public void run(String... args) throws Exception {
        for (int i = 1; i <= 50; i++) {
            userRepository.save(new User("user" + i + "@example.com", "user" + i));
        }

        couponRepository.save(new Coupon(
                "선착순 3명",
                3,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(1)
        ));
    }
}
