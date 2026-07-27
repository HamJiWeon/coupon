package hello.tpscoupon.controller;

import hello.tpscoupon.dto.response.CouponResponse;
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

    @GetMapping("/{couponId}")
    public ResponseEntity<CouponResponse> getCoupon(@PathVariable Long couponId) {
        return ResponseEntity.ok(couponIssueService.getCoupon(couponId));
    }
}
