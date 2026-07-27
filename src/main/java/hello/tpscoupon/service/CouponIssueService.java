package hello.tpscoupon.service;

import hello.tpscoupon.domain.Coupon;
import hello.tpscoupon.domain.CouponIssue;
import hello.tpscoupon.domain.User;
import hello.tpscoupon.dto.response.CouponResponse;
import hello.tpscoupon.repository.CouponIssueRepository;
import hello.tpscoupon.repository.CouponRepository;
import hello.tpscoupon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CouponIssueService {

    private final CouponRepository couponRepository;
    private final CouponIssueRepository couponIssueRepository;
    private final UserRepository userRepository;

    @CacheEvict(value = "coupon", key = "#couponId")
    public void issue(Long couponId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));

        coupon.increaseIssuedQuantity();

        try {
            couponIssueRepository.save(CouponIssue.issue(user, coupon));
        } catch (DataIntegrityViolationException e) {
            // DataIntegrityViolationException: DB 무결성 제약을 위반했을 때 발생
            throw new IllegalStateException("이미 발급받은 쿠폰입니다.");
        }
    }

    @Cacheable(value = "coupon", key = "#couponId")
    public CouponResponse getCoupon(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));

        return CouponResponse.from(coupon);
    }
}
