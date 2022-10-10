package az.online.shop.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "product")
@Data
@EqualsAndHashCode(exclude = "orderProducts")
@ToString(exclude = "orderProducts")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product extends BaseEntity<Integer> {

    private String name;

    private String description;

    private BigDecimal price;

    private Integer remainingQuantity;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @Builder.Default
    private List<OrderProduct> orderProducts = new ArrayList<>();
}
