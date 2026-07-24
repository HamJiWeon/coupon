package hello.tpscoupon.controller;

import hello.tpscoupon.service.CouponIssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coupons")
@RequiredArgsConstructor
public class CouponIssueController {

    private final CouponIssueService couponIssueService;

    @PostMapping("/{couponId}/issue")
    public ResponseEntity<Void> issue(
            @PathVariable Long couponId,
            @RequestParam Long userId
    ) {
        couponIssueService.issue(couponId, userId);
        return ResponseEntity.ok().build();
    }
}
