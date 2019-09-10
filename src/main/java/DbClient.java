import com.thoughtworks.qdox.model.expression.Add;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import javax.smartcardio.CardTerminal;
import java.io.File;
import java.util.List;

public class DbClient {

    public final static Logger logger = Logger.getLogger(DbClient.class);


    public static SessionFactory getSessionFactory() {
        // Creating Configuration Instance & Passing Hibernate Configuration File
        Configuration configObj = new Configuration();
        configObj.configure("hibernate.cfg.xml");

        // add classes which are annotated
        configObj.addAnnotatedClass(AddressItem.class);
        configObj.addAnnotatedClass(CategoryItem.class);
        configObj.addAnnotatedClass(ImageItem.class);

        // Since Hibernate Version 4.x, Service Registry Is Being Used
        ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build();

        // Creating Hibernate Session Factory Instance
        SessionFactory factoryObj = configObj.buildSessionFactory(serviceRegistryObj);
        return factoryObj;
    }

    public static AddressItem findAddressRecordById(int addressID) throws HibernateException {
        Session sessionObj = getSessionFactory().openSession();
        sessionObj.beginTransaction();
        AddressItem address = null;
        try {
            // fetch address row from data base with given id
            address = sessionObj.load(AddressItem.class, addressID);
        } catch (HibernateException e) {
            logger.info(e.getMessage());
        } finally {
            sessionObj.close();
        }
        return address;
    }

    public static boolean checkIfItemExistsAddress(int ID) {
        Session sessionObj = getSessionFactory().openSession();
        sessionObj.beginTransaction();
        AddressItem address = null;
        try {
            address = sessionObj.get(AddressItem.class, ID);
        } catch (HibernateException e) {
            logger.info(e.getMessage());
        } finally {
            sessionObj.close();
        }
        return address != null;
    }

    public static CategoryItem findCategoryRecordById(int ID) {
        Session sessionObj = getSessionFactory().openSession();
        sessionObj.beginTransaction();
        CategoryItem category = null;
        try {
            category = sessionObj.load(CategoryItem.class, ID);
        } catch (HibernateException e) {
            logger.info(e.getMessage());
        } finally {
            sessionObj.close();
        }
        return category;
    }

    // return true if exists
    public static boolean checkIfItemExistsCategory(int ID) {
        Session sessionObj = getSessionFactory().openSession();
        sessionObj.beginTransaction();
        CategoryItem category = null;
        try {
            category = sessionObj.get(CategoryItem.class, ID);
        } catch (HibernateException e) {
            logger.info(e.getMessage());
        } finally {
            sessionObj.close();
        }
        return category != null;
    }


    public static ImageItem findImageRecordById(int ID) {
        Session sessionObj = getSessionFactory().openSession();
        sessionObj.beginTransaction();
        ImageItem image = null;
        try {
            // fetch category row from data base with given id
            image = sessionObj.load(ImageItem.class, ID);
            System.out.println("Fetched data with id =" + ID + ": " + image.toString());
        } catch (HibernateException e) {
            logger.info(e.getMessage());
        } finally {
            sessionObj.close();
        }
        return image;
    }

    public static boolean checkIfItemExistsImage(int ID) {
        Session sessionObj = getSessionFactory().openSession();
        sessionObj.beginTransaction();
        ImageItem image = null;
        try {
            image = sessionObj.get(ImageItem.class, ID);
        } catch (HibernateException e) {
            logger.info(e.getMessage());
        } finally {
            sessionObj.close();
        }
        return image != null;
    }

}
