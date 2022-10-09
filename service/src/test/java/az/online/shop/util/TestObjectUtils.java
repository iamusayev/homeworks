package az.online.shop.util;

import az.online.shop.entity.Customer;
import az.online.shop.entity.PersonalInfo;
import az.online.shop.entity.Order;
import az.online.shop.entity.Product;
import az.online.shop.model.Gender;
import az.online.shop.model.Role;
import az.online.shop.model.Status;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestObjectUtils {

    public static final Integer EXISTING_ID = 1;

    public static final Customer CUSTOMER = Customer.builder()
            .role(Role.USER)
            .firstName("test1")
            .email("test1")
            .password("test1")
            .surname("test1")
            .build();

    public static final PersonalInfo INFO = PersonalInfo.builder()
            .phoneNumber("test1")
            .address("test1")
            .gender(Gender.FEMALE)
            .build();

    public static final Order ORDER = Order.builder()
            .registrationDate(LocalDate.of(1999,1,11))
            .closingDate(LocalDate.of(2000,1,11))
            .status(Status.INACTIVE)
            .build();


    public static final Product PRODUCT = Product.builder()
            .name("test1")
            .description("test1")
            .price(BigDecimal.valueOf(16, 3))
            .remainingQuantity(11)
            .build();


    public static Customer getCustomer() {
        return Customer.builder()
                .surname("test")
                .password("test1337")
                .firstName("test")
                .email("test@gmail.com")
                .role(Role.ADMIN)
                .build();
    }


    public static PersonalInfo getInfo() {
        return PersonalInfo.builder()
                .phoneNumber("+994516089260")
                .address("test")
                .gender(Gender.MALE)
                .build();
    }

    public static Order getOrder() {
        return Order.builder()
                .registrationDate(LocalDate.of(2020, 1, 11))
                .closingDate(LocalDate.of(2020, 1, 15))
                .status(Status.ACTIVE)
                .build();
    }

    public static Product getProduct() {
        return Product.builder()
                .name("test")
                .description("test")
                .price(BigDecimal.valueOf(16, 2))
                .remainingQuantity(10)
                .build();
    }
}