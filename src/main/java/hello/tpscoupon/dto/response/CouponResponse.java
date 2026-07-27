package hello.tpscoupon.dto.response;

import hello.tpscoupon.domain.Coupon;

public record CouponResponse(
        Long id,
        String name,
        int totalQuantity,
        int issuedQuantity
) {

    public static CouponResponse from(Coupon coupon) {
        return new CouponResponse(
                coupon.getId(),
                coupon.getName(),
                coupon.getTotalQuantity(),
                coupon.getIssuedQuantity()
        );
    }
}
