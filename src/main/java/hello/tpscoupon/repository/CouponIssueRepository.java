package hello.tpscoupon.repository;

import hello.tpscoupon.domain.CouponIssue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponIssueRepository extends JpaRepository<CouponIssue, Long> {
}
