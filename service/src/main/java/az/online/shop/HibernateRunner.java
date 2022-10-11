package az.online.shop;

import az.online.shop.entity.Customer;
import az.online.shop.util.HibernateUtil;
import javax.persistence.LockModeType;
import org.hibernate.Session;

public class HibernateRunner {
    public static void main(String[] args) {
        Session session = HibernateUtil.buildSession();
        session.find(Customer.class, 1, LockModeType.OPTIMISTIC);
    }
}

