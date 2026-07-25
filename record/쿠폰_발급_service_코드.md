## 쿠폰 발급

​
```
public void issue(Long couponId, Long userId) {
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
    Coupon coupon = couponRepository.findById(couponId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰입니다."));

    coupon.increaseIssuedQuantity();

    try {
        couponIssueRepository.save(CouponIssue.issue(user, coupon));
    } catch (DataIntegrityViolationException e) {
        throw new IllegalStateException("이미 발급받은 쿠폰입니다.");
    }
}
```
​

### 처리 순서

1. `userId`로 유저를 조회한다. 존재하지 않으면 `IllegalArgumentException`을 던진다. (→ `404 Not Found`로 매핑)
2. `couponId`로 쿠폰을 조회한다. 존재하지 않으면 `IllegalArgumentException`을 던진다. (→ `404 Not Found`로 매핑)
3. `coupon.increaseIssuedQuantity()`를 호출해 발급 수량을 1 증가시킨다. 이때 쿠폰 엔티티 내부에서 재고(`issuedQuantity >= totalQuantity`)를 검증하고, 소진 상태면 `IllegalStateException`을 던진다. (→ `409 Conflict`로 매핑)
4. `CouponIssue`를 생성해 저장한다. `(user_id, coupon_id)` unique 제약을 위반하면(이미 발급받은 유저) `DataIntegrityViolationException`이 발생하고, 이를 잡아서 `IllegalStateException("이미 발급받은 쿠폰입니다.")`로 변환한다. (→ `409 Conflict`로 매핑)

### 설계 포인트

- **검증 책임 분리**: "존재 여부" 검증(user/coupon)은 Service가, "재고가 남았는가"라는 도메인 규칙은 `Coupon` 엔티티가 스스로 검증한다.
- **예외 → 도메인 의미로 변환**: DB 레벨 예외(`DataIntegrityViolationException`)를 그대로 노출하지 않고, 클라이언트가 이해할 수 있는 메시지(`IllegalStateException`)로 감싸서 던진다.
- **동시성 제어**: `Coupon`에 `@Version`(낙관적 락)이 걸려있어, 동시에 여러 요청이 재고를 수정하면 그중 하나만 성공하고 나머지는 충돌 예외(`ObjectOptimisticLockingFailureException`)가 발생한다. (현재는 별도 처리 없이 500으로 노출됨 — 개선 필요)
