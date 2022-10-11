package az.online.shop.query;

import static az.online.shop.entity.QCustomer.customer;
import static az.online.shop.entity.QOrder.order;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

import az.online.shop.entity.Customer;
import az.online.shop.entity.Customer_;
import az.online.shop.entity.Order;
import az.online.shop.entity.Order_;
import az.online.shop.model.Status;
import az.online.shop.util.HibernateTestUtil;
import az.online.shop.util.TestDataImporter;
import com.querydsl.jpa.impl.JPAQuery;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import lombok.Cleanup;
import org.assertj.core.api.Assertions;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(PER_CLASS)
@DisplayName(value = "HQL tests")
class CustomerHqlTest {

    private final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();

    @BeforeAll
    public void initDb() {
        TestDataImporter.importData(sessionFactory);
    }

    @AfterAll
    public void finish() {
        sessionFactory.close();
    }


    @Test
    void findAll() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Customer> criteria = cb.createQuery(Customer.class);
        Root<Customer> customer = criteria.from(Customer.class);

        List<Customer> result = session.createQuery(criteria).list();
        assertThat(result).hasSize(3);

        List<String> names = result.stream().map(Customer::getFirstName).toList();
        assertThat(names).containsExactlyInAnyOrder("Isobelle", "Findlay", "Cleveland");

        session.getTransaction().commit();
    }

    @Test
    void findByFirstName() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Customer> criteria = cb.createQuery(Customer.class);
        Root<Customer> customer = criteria.from(Customer.class);
        criteria.select(customer)
                .where(cb.equal(customer.get(Customer_.FIRST_NAME), "Isobelle"));

        List<Customer> result = session.createQuery(criteria).list();
        assertThat(result).hasSize(1);

        Integer id = result.get(0).getId();
        assertThat(id).isEqualTo(1);

        session.getTransaction().commit();
    }

    @Test
    void findLimitedCustomerOrderedByBirthday() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        Integer limit = 2;
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Customer> criteria = cb.createQuery(Customer.class);
        Root<Customer> customer = criteria.from(Customer.class);
        criteria.select(customer)
                .orderBy(cb.asc(customer.get(Customer_.FIRST_NAME)));

        List<Customer> result = session.createQuery(criteria)
                .setMaxResults(limit)
                .list();
        assertThat(result).hasSize(2);

        Stream<String> names = result.stream().map(Customer::getFirstName);
        assertThat(names).containsExactlyInAnyOrder("Isobelle", "Findlay", "Cleveland");

        session.getTransaction().commit();
    }

    @Test
    void findCustomerWhoHasAtLeastOneActiveOrder() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Customer> criteria = cb.createQuery(Customer.class);
        Root<Customer> customer = criteria.from(Customer.class);
        Join<Object, Object> orders = customer.join(Customer_.ORDERS);
        criteria.select(customer)
                .where(cb.equal(orders.get(Order_.STATUS), Status.ACTIVE))
                .distinct(true);

        List<Customer> result = session.createQuery(criteria).list();

        Stream<String> names = result.stream().map(Customer::getFirstName);
        assertThat(names).containsExactlyInAnyOrder("Cleveland", "Findlay","Isobelle");

        session.getTransaction().commit();
    }

    @Test
    void findAllOrdersByCustomerName() {
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        List<Order> result =
                session.createQuery("select o from Order o join o.customer c on c.firstName= :firstName order by c.firstName, c.surname", Order.class)
                        .setParameter(Customer_.FIRST_NAME, "Isobelle")
                        .list();
        assertThat(result).hasSize(4);

        List<LocalDate> registrationDates = result.stream().map(Order::getRegistrationDate).toList();
        assertThat(registrationDates).contains(LocalDate.of(2021, 5, 6), LocalDate.of(2022, 6, 7), LocalDate.of(2022, 6, 7),
                LocalDate.of(2022, 6, 7));

        session.getTransaction().commit();
    }


    @Nested
    @DisplayName(value = "CriteriaAPI tests")
    class CustomerCriteriaApiTest {

        @Test
        void findAll() {
            @Cleanup var session = sessionFactory.openSession();
            session.beginTransaction();

            List<Customer> result = session.createQuery("select c from Customer c", Customer.class).list();
            assertThat(result).hasSize(3);

            List<String> names = result.stream().map(Customer::getFirstName).toList();
            assertThat(names).containsExactlyInAnyOrder("Isobelle", "Findlay", "Cleveland");

            session.getTransaction().commit();
        }

        @Test
        void findByFirstName() {
            @Cleanup var session = sessionFactory.openSession();
            session.beginTransaction();

            List<Customer> result = session.createQuery("select c from Customer c where c.firstName= :firstName", Customer.class)
                    .setParameter(Customer_.FIRST_NAME, "Isobelle")
                    .list();
            assertThat(result).hasSize(1);

            Integer id = result.get(0).getId();
            assertThat(id).isEqualTo(1);

            session.getTransaction().commit();
        }


        @Test
        void findLimitedCustomerOrderedByBirthday() {
            @Cleanup var session = sessionFactory.openSession();
            session.beginTransaction();

            Integer limit = 2;
            List<Customer> result = session.createQuery("select c from Customer c order by c.birthDate", Customer.class)
                    .setMaxResults(limit)
                    .list();
            assertThat(result).hasSize(2);

            Stream<String> names = result.stream().map(Customer::getFirstName);
            assertThat(names).containsExactlyInAnyOrder("Cleveland", "Findlay","Isobelle");

            session.getTransaction().commit();
        }

        @Test
        void findCustomerWhoHasAtLeastOneActiveOrder() {
            @Cleanup var session = sessionFactory.openSession();
            session.beginTransaction();

            List<Customer> result = session.createQuery("select distinct c from Customer c join c.orders o on o.status= :status", Customer.class)
                    .setParameter(Order_.STATUS, Status.ACTIVE)
                    .list();
            assertThat(result).hasSize(3);

            Stream<String> names = result.stream().map(Customer::getFirstName);
            assertThat(names).containsExactlyInAnyOrder("Isobelle", "Findlay", "Cleveland");

            session.getTransaction().commit();
        }

        @Test
        void findAllOrdersByCustomerName() {
            @Cleanup var session = sessionFactory.openSession();
            session.beginTransaction();

            List<Order> result =
                    session.createQuery("select o from Order o join o.customer c on c.firstName= :firstName order by c.firstName, c.surname",
                                    Order.class)
                            .setParameter(Customer_.FIRST_NAME, "Isobelle")
                            .list();
            assertThat(result).hasSize(4);

            List<LocalDate> registrationDates = result.stream().map(Order::getRegistrationDate).toList();
            assertThat(registrationDates).contains(LocalDate.of(2021, 5, 6), LocalDate.of(2022, 6, 7), LocalDate.of(2022, 6, 7),
                    LocalDate.of(2022, 6, 7));

            session.getTransaction().commit();
        }
    }

    @Nested
    @DisplayName(value = "Querydsl tests")
    class CustomerQuerydslTest {

        @Test
        void findAll() {
            @Cleanup var session = sessionFactory.openSession();
            session.beginTransaction();

            List<Customer> result = new JPAQuery<Customer>(session)
                    .select(customer)
                    .from(customer)
                    .fetch();
            assertThat(result).hasSize(3);

            List<String> names = result.stream().map(Customer::getFirstName).toList();
            assertThat(names).containsExactlyInAnyOrder("Isobelle", "Findlay", "Cleveland");

            session.getTransaction().commit();
        }

        @Test
        void findByFirstName() {
            @Cleanup var session = sessionFactory.openSession();
            session.beginTransaction();

            List<Customer> result = new JPAQuery<Customer>(session)
                    .select(customer)
                    .from(customer)
                    .where(customer.firstName.eq("Isobelle"))
                    .fetch();
            assertThat(result).hasSize(1);

            Integer id = result.get(0).getId();
            assertThat(id).isEqualTo(1);

            session.getTransaction().commit();
        }

        @Test
        void findLimitedCustomerOrderedByBirthday() {
            @Cleanup var session = sessionFactory.openSession();
            session.beginTransaction();

            Integer limit = 2;
            List<Customer> result = new JPAQuery<Customer>(session)
                    .select(customer)
                    .from(customer)
                    .orderBy(customer.birthDate.asc())
                    .limit(limit)
                    .fetch();
            assertThat(result).hasSize(2);

            Stream<String> names = result.stream().map(Customer::getFirstName);
            assertThat(names).containsExactlyInAnyOrder("Cleveland", "Findlay","Isobelle");

            session.getTransaction().commit();
        }

        @Test
        void findCustomerWhoHasAtLeastOneActiveOrder() {
            @Cleanup var session = sessionFactory.openSession();
            session.beginTransaction();

            List<Customer> result = new JPAQuery<Customer>(session)
                    .select(customer)
                    .distinct()
                    .from(customer)
                    .join(customer.orders, order)
                    .on(order.status.eq(Status.ACTIVE))
                    .from()
                    .fetch();
            assertThat(result).hasSize(3);

            Stream<String> names = result.stream().map(Customer::getFirstName);
            assertThat(names).containsExactlyInAnyOrder("Isobelle", "Findlay", "Cleveland");

            session.getTransaction().commit();
        }

        @Test
        void findAllOrdersByCustomerName() {
            @Cleanup var session = sessionFactory.openSession();
            session.beginTransaction();

            List<Order> result = new JPAQuery<Order>(session)
                    .select(order)
                    .from(order)
                    .join(order.customer, customer)
                    .on(customer.firstName.eq("Isobelle"))
                    .orderBy(customer.firstName.asc(), customer.surname.asc())
                    .fetch();
            assertThat(result).hasSize(4);

            List<LocalDate> registrationDates = result.stream().map(Order::getRegistrationDate).toList();
            assertThat(registrationDates).contains(LocalDate.of(2021, 5, 6), LocalDate.of(2022, 6, 7), LocalDate.of(2022, 6, 7),
                    LocalDate.of(2022, 6, 7));

            session.getTransaction().commit();
        }
    }
}
