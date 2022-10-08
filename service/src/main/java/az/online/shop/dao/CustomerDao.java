package az.online.shop.dao;


import static az.online.shop.entity.Customer_.FIRST_NAME;
import static az.online.shop.entity.Customer_.SURNAME;

import az.online.shop.entity.Customer;
import az.online.shop.entity.Customer_;
import az.online.shop.entity.Order;
import az.online.shop.entity.Order_;
import az.online.shop.model.Status;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerDao {

    private static final CustomerDao INSTANCE = new CustomerDao();

    //Возвращает всех покупателей
    public List<Customer> findAll(Session session) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Customer> criteria = cb.createQuery(Customer.class);
        Root<Customer> customer = criteria.from(Customer.class);

        return session.createQuery(criteria)
                .list();
    }

    //Возвращает всех покупателей с указанным именем
    public List<Customer> findAllByName(Session session, String firstName) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Customer> criteria = cb.createQuery(Customer.class);
        Root<Customer> customer = criteria.from(Customer.class);
        criteria.where(cb.equal(customer.get(FIRST_NAME), firstName));

        return session.createQuery(criteria)
                .list();
    }

    //Возвращает всех покупателей limit упорядоченных по дате рождения
    public List<Customer> findLimitedCustomerOrderedByBirthday(Session session, int limit) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Customer> criteria = cb.createQuery(Customer.class);
        Root<Customer> customer = criteria.from(Customer.class);
        criteria.orderBy(cb.asc(customer.get(Customer_.BIRTH_DATE)));

        return session.createQuery(criteria)
                .setMaxResults(limit)
                .list();
    }

    //Возвращает всех покупателей компании с активными заказами
    public List<Customer> findAllByStatus(Session session, Status status) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Customer> criteria = cb.createQuery(Customer.class);
        Root<Customer> customer = criteria.from(Customer.class);
        Join<Object, Object> orders = customer.join(Customer_.ORDERS);
        criteria.select(customer)
                .where(cb.equal(orders.get(Order_.STATUS), status))
                .distinct(true);

        return session.createQuery(criteria)
                .list();
    }

    //Возвращает все заказы сделанные покупателем указанным именем, упорядоченные по имени покупателя, а затем
    //по размеру фамилии покупателя
    public List<Order> findAllOrdersByCustomerName(Session session, String firstName) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteria = cb.createQuery(Order.class);
        Root<Order> order = criteria.from(Order.class);
        Join<Object, Object> customer = order.join(Order_.CUSTOMER);
        criteria.select(order).where(cb.equal(customer.get(FIRST_NAME), firstName))
                .orderBy(cb.asc(customer.get(FIRST_NAME)), cb.asc(customer.get(SURNAME)));

        return session.createQuery(criteria)
                .list();
    }


    public static CustomerDao getInstance() {
        return INSTANCE;
    }
}
