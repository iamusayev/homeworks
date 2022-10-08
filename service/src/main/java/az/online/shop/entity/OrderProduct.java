package az.online.shop.entity;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "order_product")
public class OrderProduct extends BaseEntity<Integer> {


    @ManyToOne
    private Order order;

    @ManyToOne
    private Product product;

    private LocalDate createdAt;

    private Integer count;

    public void setOrder(Order order) {
        this.order = order;
        this.order.getOrderProducts().add(this);
    }

    public void setProduct(Product product) {
        this.product = product;
        this.product.getOrderProducts().add(this);
    }

}
