package az.online.shop.integration.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import az.online.shop.dao.CustomerDao;
import az.online.shop.entity.Customer;
import az.online.shop.entity.Order;
import az.online.shop.model.Status;
import az.online.shop.util.HibernateUtil;
import java.time.LocalDate;
import java.util.List;
import lombok.Cleanup;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(PER_CLASS)
class CustomerDaoIT {

    private final SessionFactory sessionFactory = HibernateUtil.buildSession().getSessionFactory();
    private final CustomerDao customerDao = CustomerDao.getInstance();


    @BeforeAll
    public void initDb() {
        System.out.println("Инициализируем нашей сущности.");
    }

    @AfterAll
    public void finish() {
        sessionFactory.close();
    }


    @Test
    void findAll() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        List<Customer> result = customerDao.findAll(session);
        assertThat(result).hasSize(10);

        List<String> names = result.stream().map(Customer::getFirstName).toList();
        assertThat(names)
                .containsExactlyInAnyOrder("Isobelle", "Findlay", "Cleveland", "Herman", "Brandy", "Kirby", "Donald", "Holman", "Woolley", "Riya");

        session.getTransaction().commit();
    }

    @Test
    void findAllByFirstName() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        List<Customer> results = customerDao.findAllByName(session, "Isobelle");

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getFirstName()).isEqualTo("Isobelle");

        session.getTransaction().commit();
    }

    @Test
    void findLimitedCustomerOrderedByBirthday() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        int limit = 3;
        List<Customer> results = customerDao.findLimitedCustomerOrderedByBirthday(session, limit);
        assertThat(results).hasSize(3);

        List<String> names = results.stream().map(Customer::getFirstName).toList();
        assertThat(names).contains("Holman", "Kirby", "Woolley");

        session.getTransaction().commit();
    }

    @Test
    void findAllByOrderStatus() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        List<Customer> results = customerDao.findAllByStatus(session, Status.ACTIVE);
        assertThat(results).hasSize(9);

        List<String> names = results.stream().map(Customer::getFirstName).toList();
        assertThat(names).containsExactlyInAnyOrder("Findlay", "Cleveland", "Herman", "Brandy", "Kirby", "Donald", "Holman", "Woolley", "Riya");

        session.getTransaction().commit();
    }

    @Test
    void findAllOrdersByCustomerName(){
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        List<Order> results = customerDao.findAllOrdersByCustomerName(session, "Findlay");
        assertThat(results).hasSize(1);

        List<LocalDate> registrationDates = results.stream().map(Order::getRegistrationDate).toList();
        assertThat(registrationDates.get(0)).isEqualTo(LocalDate.of(2020,1,3));

        session.getTransaction().commit();
    }

}
