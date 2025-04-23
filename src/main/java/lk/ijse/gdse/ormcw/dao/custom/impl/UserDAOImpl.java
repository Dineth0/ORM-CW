package lk.ijse.gdse.ormcw.dao.custom.impl;

import lk.ijse.gdse.ormcw.config.FactoryConfiguration;
import lk.ijse.gdse.ormcw.dao.custom.UserDAO;
import lk.ijse.gdse.ormcw.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public String getUser(String name) throws SQLException, IOException {

      /*try (Session session = FactoryConfiguration.getInstance().getSession()) {
            String sql = "SELECT CAST(password AS CHAR) FROM User WHERE userId = :userId";
            //SELECT CAST(password AS CHAR) FROM User WHERE userId = :userId
            //SELECT CAST(password AS CHAR) FROM Admin WHERE userName = :userName
            return session.createQuery(sql, User.class)
                    .setParameter("userID", userId)
                    .uniqueResult();
        } catch (Exception e) {
            throw new Exception("Error fetching user: " + e.getMessage());
        }*/
        System.out.println("aawaaaa " + name);
        String password = null;


        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();


            String sql = "SELECT CAST(password AS CHAR) FROM User WHERE UserName = :UserName";
            NativeQuery query = session.createNativeQuery(sql);
            query.setParameter("UserName", name);


            password = (String) query.getSingleResult();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        System.out.println(password+"awaaaaaaaaaaaaaaaaaaaaaa");
        return password;

    }

    @Override
    public String getNextId() throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
              Transaction transaction = session.beginTransaction();
            String hql = "SELECT l.Id FROM User l ORDER BY l.Id DESC";
            Query<String> query = session.createQuery(hql);
            query.setMaxResults(1);
            String lastId = query.uniqueResult();
        System.out.println("Last ID from DB: " + lastId);
            transaction.commit();
            session.close();

            if (lastId != null) {
                String substring = lastId.substring(1);
                int i = Integer.parseInt(substring);
                int newIdIndex = i + 1;
                return String.format("U%03d", newIdIndex);
            }

        return "U001";

    }

    @Override
    public List<User> getAll() throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query<User> query = session.createQuery("SELECT c FROM User c", User.class);
            List<User> customers = query.list();

            transaction.commit();
            session.close();
            return customers;
    }

    @Override
    public boolean save(User user) throws IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(User user) throws SQLException, IOException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(user);

        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public boolean delete(String pk) throws SQLException {
        return false;
    }
}
