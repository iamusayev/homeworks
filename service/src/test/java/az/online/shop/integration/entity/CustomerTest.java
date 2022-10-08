package az.online.shop.integration.entity;

import static org.assertj.core.api.Assertions.assertThat;

import az.online.shop.entity.Customer;
import az.online.shop.integration.IntegrationTestBase;
import az.online.shop.util.TestObjectUtils;
import az.online.shop.util.HibernateTestUtil;
import lombok.Cleanup;
import org.junit.jupiter.api.Test;

class CustomerTest extends IntegrationTestBase {


    @Test
    void shouldSaveCorrectEntity() {
        var customer = TestObjectUtils.getCustomer();

        @Cleanup var session = HibernateTestUtil.buildSessionFactory().openSession();
        session.beginTransaction();
        session.save(customer);
        session.getTransaction().commit();

        assertThat(customer.getId()).isNotNull();
    }

    @Test
    void shouldSaveCustomerAndInfo() {
        var customer = TestObjectUtils.getCustomer();
        var info = TestObjectUtils.getInfo();


        @Cleanup var session = HibernateTestUtil.buildSessionFactory().openSession();
        session.beginTransaction();
        customer.setInfo(info);
        session.save(customer);
        session.getTransaction().commit();


        assertThat(customer.getId()).isNotNull();
        assertThat(info.getId()).isNotNull();
    }

    @Test
    void shouldSaveCustomerAndOrder() {
        var customer = TestObjectUtils.getCustomer();
        var order = TestObjectUtils.getOrder();
        var info = TestObjectUtils.getInfo();

        @Cleanup var session = HibernateTestUtil.buildSessionFactory().openSession();
        session.beginTransaction();

        customer.setOrder(order);
        session.save(customer);
        session.getTransaction().commit();

        assertThat(customer.getId()).isNotNull();
        assertThat(order.getId()).isNotNull();
    }

    @Test
    void shouldSaveCustomerWithOrderAndInfo() {
        var customer = TestObjectUtils.getCustomer();
        var order = TestObjectUtils.getOrder();
        var info = TestObjectUtils.getInfo();

        @Cleanup var session = HibernateTestUtil.buildSessionFactory().openSession();
        session.beginTransaction();
        customer.setOrder(order);
        customer.setInfo(info);
        session.save(customer);
        session.getTransaction().commit();


        assertThat(customer.getId()).isNotNull();
        assertThat(order.getId()).isNotNull();
        assertThat(info.getId()).isNotNull();
    }

    @Test
    void shouldFindExistingEntity() {

        @Cleanup var session = HibernateTestUtil.buildSessionFactory().openSession();
        session.beginTransaction();

        Customer customer = session.get(Customer.class, TestObjectUtils.EXISTING_ID);
        assertThat(customer).isNotNull();

        session.getTransaction().commit();
    }

    @Test
    void shouldFindExistingEntityWithInfo() {

        @Cleanup var session = HibernateTestUtil.buildSessionFactory().openSession();
        session.beginTransaction();

        Customer customer = session.get(Customer.class, TestObjectUtils.EXISTING_ID);
        assertThat(customer).isNotNull();
        assertThat(customer.getInfo()).isNotNull();

        session.getTransaction().commit();
    }

    @Test
    void shouldFindExistingEntityWithInfoAndOrder() {
        var id = TestObjectUtils.EXISTING_ID;

        @Cleanup var session = HibernateTestUtil.buildSessionFactory().openSession();
        session.beginTransaction();
        Customer customer = session.get(Customer.class, id);
        session.getTransaction().commit();

        assertThat(customer).isNotNull();
        assertThat(customer.getInfo()).isNotNull();
        assertThat(customer.getOrder()).isNotNull();
    }

    @Test
    void shouldDeleteExistingEntityAndHisRelations() {
        var id = TestObjectUtils.EXISTING_ID;

        @Cleanup var session = HibernateTestUtil.buildSessionFactory().openSession();
        session.beginTransaction();
        Customer customer = session.get(Customer.class, id);
        session.delete(customer);
        session.flush();
        Customer actualCustomer = session.get(Customer.class, id);
        session.getTransaction().commit();

        assertThat(actualCustomer).isNull();
    }
}


