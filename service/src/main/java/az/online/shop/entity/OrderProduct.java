package az.online.shop.entity;

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

    private Product product;

    private LocalDate createdAt;

    private Integer count;


        this.order = order;
        this.order.getOrderProducts().add(this);
    }
        this.product = product;
        this.product.getOrderProducts().add(this);
    }