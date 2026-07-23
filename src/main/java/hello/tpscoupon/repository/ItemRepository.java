package hello.tpscoupon.repository;

import hello.tpscoupon.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
