package az.online.shop.entity;

import az.online.shop.model.Role;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
    private PersonalInfo personalInfo;

    @Builder.Default
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    public void setPersonalInfo(PersonalInfo info) {
        info.setCustomer(this);
        this.personalInfo = info;
    }

    public void addOrder(Order order) {
        orders.add(order);
        order.setCustomer(this);
    }

}