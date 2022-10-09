package az.online.shop.dao;


import static az.online.shop.entity.QCustomer.customer;
import static az.online.shop.entity.QOrder.order;

import az.online.shop.entity.Customer;
import az.online.shop.entity.Order;
import az.online.shop.model.Status;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerDao {

    private static final CustomerDao INSTANCE = new CustomerDao();

    //Возвращает всех покупателей
    public List<Customer> findAll(Session session) {
        return new JPAQuery<Customer>(session)
                .select(customer)
                .from(customer)
                .fetch();
    }

    //Возвращает всех покупателей с указанным именем
    public List<Customer> findAllByName(Session session, String firstName) {
        return new JPAQuery<Customer>(session)
                .select(customer)
                .from(customer)
                .where(customer.firstName.eq(firstName))
                .fetch();
    }

    //Возвращает всех покупателей limit упорядоченных по дате рождения
    public List<Customer> findLimitedCustomerOrderedByBirthday(Session session, int limit) {
        return new JPAQuery<Customer>(session)
                .select(customer)
                .from(customer)
                .orderBy(customer.birthDate.asc())
                .limit(limit)
                .fetch();
    }

    //Возвращает всех покупателей компании с активными заказами
    public List<Customer> findAllByStatus(Session session, Status status) {
        return new JPAQuery<Customer>(session)
                .select(customer)
                .from(customer)
                .join(customer.orders, order)
                .on(order.status.eq(status))
                .distinct()
                .fetch();
    }

    //Возвращает все заказы сделанные покупателем указанным именем, упорядоченные по имени покупателя, а затем
    //по размеру фамилии покупателя
    public List<Order> findAllOrdersByCustomerName(Session session, String firstName) {
        return new JPAQuery<Order>(session)
                .select(order)
                .from(order)
                .join(order.customer, customer)
                .on(customer.firstName.eq(firstName))
                .orderBy(customer.firstName.asc(), customer.surname.asc())
                .fetch();
    }


    public static CustomerDao getInstance() {
        return INSTANCE;
    }
}
