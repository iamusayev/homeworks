package az.online.shop.dao;

import az.online.shop.entity.Customer;
import az.online.shop.entity.Customer_;
import az.online.shop.entity.Order;
import az.online.shop.entity.Order_;
import az.online.shop.model.Status;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerDao {

    private static final CustomerDao INSTANCE = new CustomerDao();

    //Возвращает всех покупателей
    public List<Customer> findAll(Session session) {
        return session.createQuery("select c from Customer c", Customer.class)
                .list();
    }

    //Возвращает всех покупателей с указанным именем
    public List<Customer> findAllByName(Session session, String firstName) {
        return session.createQuery("select c from Customer c where c.firstName = :firstName", Customer.class)
                .setParameter(Customer_.FIRST_NAME, firstName)
                .list();
    }

    //Возвращает всех покупателей limit упорядоченных по дате рождения
    public List<Customer> findLimitedCustomerOrderedByBirthday(Session session, int limit) {
        return session.createQuery("select c from Customer c order by c.birthDate", Customer.class)
                .setMaxResults(limit)
                .list();
    }

    //Возвращает всех покупателей компании с активными заказами
    public List<Customer> findAllByStatus(Session session, Status status) {
        return session.createQuery("select distinct c from Customer c join c.orders o on o.status = :status ", Customer.class)
                .setParameter(Order_.STATUS, status)
                .list();
    }

    //Возвращает все заказы сделанные покупателем указанным именем, упорядоченные по имени покупателя, а затем
    //по размеру фамилии покупателя
    public List<Order> findAllOrdersByCustomerName(Session session, String firstName) {
        return session.createQuery("select o from Order  o join o.customer c on c.firstName= :firstName order by c.firstName,c.surname", Order.class)
                .setParameter(Customer_.FIRST_NAME, firstName)
                .list();
    }


    public static CustomerDao getInstance() {
        return INSTANCE;
    }

}
