package az.online.shop.integration;

import az.online.shop.util.TestObjectUtils;
import az.online.shop.util.HibernateTestUtil;
import lombok.Cleanup;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeAll;

public class IntegrationTestBase {

    @BeforeAll
    static void prepareDatabase() {
        @Cleanup Session session = HibernateTestUtil.buildSessionFactory().openSession();

        session.beginTransaction();

        session.save(TestObjectUtils.CUSTOMER);
        session.save(TestObjectUtils.INFO);
        session.save(TestObjectUtils.ORDER);
        session.save(TestObjectUtils.PRODUCT);

        session.getTransaction().commit();
    }
}
