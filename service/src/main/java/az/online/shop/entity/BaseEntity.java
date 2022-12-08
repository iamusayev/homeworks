package az.online.shop.entity;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@MappedSuperclass
@Data
@Getter
@Setter

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private T id;
}
