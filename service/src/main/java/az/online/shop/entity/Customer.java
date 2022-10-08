package az.online.shop.entity;

import az.online.shop.model.Role;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "customer")
@Data
@EqualsAndHashCode(exclude = {"order", "info"})
@ToString(exclude = {"order", "info"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer extends BaseEntity<Integer> {

    private String firstName;

    private String surname;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Order order;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private Info info;

    public void setInfo(Info info) {
        info.setCustomer(this);
        this.info = info;
    }

    public void setOrder(Order order) {
        order.setCustomer(this);
        this.order = order;
    }
}