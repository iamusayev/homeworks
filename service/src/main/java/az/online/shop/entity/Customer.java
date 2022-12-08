package az.online.shop.entity;

import az.online.shop.model.Role;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


public class Customer extends BaseEntity<Integer> {

    private String firstName;

    private String surname;

    private String email;

    private String password;


    }
}