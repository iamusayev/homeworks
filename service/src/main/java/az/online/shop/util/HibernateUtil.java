package az.online.shop.util;

import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {

    public static Session buildSession() {
        Configuration configuration = buildConfiguration();
        return configuration.buildSessionFactory().openSession();
    }

    public static Configuration buildConfiguration() {
        Configuration configuration = new Configuration();
        new Configuration().configure().setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        return configuration;
    }
}
