package hello.tpscoupon.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int totalQuantity; // 전체 쿠폰이 몇 장인지

    private int issuedQuantity; // 지금까지 발급된 쿠폰 수

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    @Version
    private Long version;

    // 테스트용
    public Coupon(String name, int totalQuantity, LocalDateTime startAt, LocalDateTime endAt) {
        this.name = name;
        this.totalQuantity = totalQuantity;
        this.issuedQuantity = 0;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public void increaseIssuedQuantity() {
        if (this.issuedQuantity >= this.totalQuantity) {
            throw new IllegalStateException("쿠폰 소진");
        }

        issuedQuantity++;
    }
}
