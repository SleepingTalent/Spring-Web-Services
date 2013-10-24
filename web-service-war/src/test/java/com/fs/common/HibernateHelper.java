package com.fs.common;


import com.fs.humanResources.model.address.entities.Address;
import com.fs.humanResources.model.employee.entities.Employee;
import com.fs.humanResources.model.holiday.entities.Holiday;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class HibernateHelper {

    private static SessionFactory sessionFactory;

    public static SessionFactory sessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = configureSessionFactory();
        }
        return sessionFactory;
    }

    public static SessionFactory configureSessionFactory() {

        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Employee.class);
        configuration.addAnnotatedClass(Address.class);
        configuration.addAnnotatedClass(Holiday.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/db");
        configuration.setProperty("hibernate.connection.username", "db");
        configuration.setProperty("hibernate.connection.password", "db");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        configuration.setProperty("hibernate.transaction.factory_class", "org.hibernate.transaction.JDBCTransactionFactory");
        configuration.setProperty("hibernate.show_sql", "true");

        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}
