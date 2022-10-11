package az.online.shop.util;

import az.online.shop.entity.Customer;
import az.online.shop.entity.Order;
import az.online.shop.entity.OrderProduct;
import az.online.shop.entity.PersonalInfo;
import az.online.shop.entity.Product;
import az.online.shop.model.Gender;
import az.online.shop.model.Role;
import az.online.shop.model.Status;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@UtilityClass
public class TestDataImporter {

    public static final Customer ISOBELLE = Customer.builder()
            .firstName("Isobelle")
            .surname("Valentine")
            .email("isabelle@gmail.com")
            .password("valentine123")
            .birthDate(LocalDate.of(1999, 1, 1))
            .role(Role.USER)
            .build();

    private static final Customer FINDLAY = Customer.builder()
            .firstName("Findlay")
            .surname("Miruna")
            .email("findlay@gmail.com")
            .password("miruna123")
            .birthDate(LocalDate.of(2020, 2, 2))
            .role(Role.USER)
            .build();


    private static final Customer CLEVELAND = Customer.builder()
            .firstName("Cleveland")
            .surname("Abida")
            .email("cleveland@gmail.com")
            .password("abida123")
            .birthDate(LocalDate.of(2001, 3, 3))
            .role(Role.USER)
            .build();


    private static final PersonalInfo ISOBELLE_PERSONAL_INFO = new PersonalInfo("6785435892", "test2", Gender.MALE, null);
    private static final PersonalInfo FINDLAY_PERSONAL_INFO = new PersonalInfo("8905368562", "test3", Gender.MALE, null);
    private static final PersonalInfo CLEVELAND_PERSONAL_INFO = new PersonalInfo("9876435792", "test4", Gender.MALE, null);


    private static final List<Order> ISOBELLE_ORDERS = List.of(
            Order.builder()
                    .registrationDate(LocalDate.of(2021, 5, 6))
                    .closingDate(LocalDate.of(2020, 5, 7))
                    .status(Status.ACTIVE)
                    .build(),
            Order.builder()
                    .registrationDate(LocalDate.of(2022, 6, 7))
                    .closingDate(LocalDate.of(2020, 5, 7))
                    .status(Status.ACTIVE)
                    .build(),
            Order.builder()
                    .registrationDate(LocalDate.of(2022, 6, 7))
                    .closingDate(LocalDate.of(2020, 5, 7))
                    .status(Status.ACTIVE)
                    .build(),
            Order.builder()
                    .registrationDate(LocalDate.of(2022, 6, 7))
                    .closingDate(LocalDate.of(2020, 5, 7))
                    .status(Status.ACTIVE)
                    .build()
    );


    private static final List<Order> FINDLAY_ORDERS = List.of(
            Order.builder()
                    .registrationDate(LocalDate.of(2021, 5, 6))
                    .closingDate(LocalDate.of(2020, 5, 7))
                    .status(Status.ACTIVE)
                    .build(),
            Order.builder()
                    .registrationDate(LocalDate.of(2022, 6, 7))
                    .closingDate(LocalDate.of(2020, 5, 7))
                    .status(Status.ACTIVE)
                    .build(),
            Order.builder()
                    .registrationDate(LocalDate.of(2022, 6, 7))
                    .closingDate(LocalDate.of(2020, 5, 7))
                    .status(Status.ACTIVE)
                    .build()
    );

    private static final List<Order> CLEVELAND_ORDERS = List.of(
            Order.builder()
                    .registrationDate(LocalDate.of(2021, 5, 6))
                    .closingDate(LocalDate.of(2020, 5, 7))
                    .status(Status.ACTIVE)
                    .build(),
            Order.builder()
                    .registrationDate(LocalDate.of(2022, 6, 7))
                    .closingDate(LocalDate.of(2020, 5, 7))
                    .status(Status.ACTIVE)
                    .build()
    );


    private static final List<Product> PRODUCTS = List.of(
            Product.builder()
                    .description("test")
                    .name("test")
                    .price(BigDecimal.valueOf(11))
                    .remainingQuantity(2)
                    .build(),

            Product.builder()
                    .description("test2")
                    .name("test2")
                    .price(BigDecimal.valueOf(12))
                    .remainingQuantity(2)
                    .build(),

            Product.builder()
                    .description("test3")
                    .name("test3")
                    .price(BigDecimal.valueOf(13))
                    .remainingQuantity(3)
                    .build(),

            Product.builder()
                    .description("test4")
                    .name("test4")
                    .price(BigDecimal.valueOf(14))
                    .remainingQuantity(4)
                    .build(),

            Product.builder()
                    .description("test5")
                    .name("test5")
                    .price(BigDecimal.valueOf(15))
                    .remainingQuantity(5)
                    .build(),

            Product.builder()
                    .description("test6")
                    .name("test6")
                    .price(BigDecimal.valueOf(16))
                    .remainingQuantity(6)
                    .build(),

            Product.builder()
                    .description("test7")
                    .name("test7")
                    .price(BigDecimal.valueOf(17))
                    .remainingQuantity(7)
                    .build(),

            Product.builder()
                    .description("test8")
                    .name("test8")
                    .price(BigDecimal.valueOf(18))
                    .remainingQuantity(8)
                    .build(),

            Product.builder()
                    .description("test9")
                    .name("test9")
                    .price(BigDecimal.valueOf(19))
                    .remainingQuantity(9)
                    .build(),

            Product.builder()
                    .description("test10")
                    .name("test10")
                    .price(BigDecimal.valueOf(20))
                    .remainingQuantity(10)
                    .build(),

            Product.builder()
                    .description("test11")
                    .name("test11")
                    .price(BigDecimal.valueOf(21))
                    .remainingQuantity(11)
                    .build()
    );

    public void importData(SessionFactory sessionFactory) {
        @Cleanup Session session = sessionFactory.openSession();
        saveCustomerAndPersonalInfo(session, ISOBELLE, ISOBELLE_PERSONAL_INFO, ISOBELLE_ORDERS);
        saveCustomerAndPersonalInfo(session, FINDLAY, FINDLAY_PERSONAL_INFO, FINDLAY_ORDERS);
        saveCustomerAndPersonalInfo(session, CLEVELAND, CLEVELAND_PERSONAL_INFO, CLEVELAND_ORDERS);

        saveOrderProducts(session, PRODUCTS.get(0), ISOBELLE_ORDERS.get(0), 10);
        saveOrderProducts(session, PRODUCTS.get(1), ISOBELLE_ORDERS.get(1), 5);
        saveOrderProducts(session, PRODUCTS.get(2), ISOBELLE_ORDERS.get(2), 16);
        saveOrderProducts(session, PRODUCTS.get(3), ISOBELLE_ORDERS.get(3), 12);
        saveOrderProducts(session, PRODUCTS.get(4), FINDLAY_ORDERS.get(0), 13);
        saveOrderProducts(session, PRODUCTS.get(5), FINDLAY_ORDERS.get(1), 14);
        saveOrderProducts(session, PRODUCTS.get(6), FINDLAY_ORDERS.get(2), 15);
        saveOrderProducts(session, PRODUCTS.get(7), ISOBELLE_ORDERS.get(0), 42);
        saveOrderProducts(session, PRODUCTS.get(8), CLEVELAND_ORDERS.get(0), 12);
        saveOrderProducts(session, PRODUCTS.get(9), CLEVELAND_ORDERS.get(1), 32);
        saveOrderProducts(session, PRODUCTS.get(10), ISOBELLE_ORDERS.get(3), 2);
    }


    public void saveCustomerAndPersonalInfo(Session session, Customer customer, PersonalInfo personalInfo, List<Order> order) {
        customer.setPersonalInfo(personalInfo);
        for (int i = 0; i < order.size(); i++) {
            customer.addOrder(order.get(i));
        }
        session.save(customer);
    }

    public void saveOrderProducts(Session session, Product product, Order order, Integer count) {
        OrderProduct orderProduct = OrderProduct.builder()
                .count(count)
                .createdAt(LocalDate.now())
                .build();
        orderProduct.addProduct(product);
        orderProduct.addOrder(order);
        session.save(orderProduct);
    }
}
