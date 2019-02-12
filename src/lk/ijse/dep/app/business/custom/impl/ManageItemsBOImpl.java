package lk.ijse.dep.app.business.custom.impl;

import lk.ijse.dep.app.business.Converter;
import lk.ijse.dep.app.business.custom.ManageItemsBO;
import lk.ijse.dep.app.dao.DAOFactory;
import lk.ijse.dep.app.dao.custom.CustomerDAO;
import lk.ijse.dep.app.dto.CustomerDTO;
import lk.ijse.dep.app.dto.ItemDTO;
import lk.ijse.dep.app.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class ManageItemsBOImpl implements ManageItemsBO {

    private CustomerDAO customerDAO;

    public ManageItemsBOImpl() {
        customerDAO = DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    }


    @Override
    public List<ItemDTO> getItems() throws Exception {
        Session mySession = HibernateUtil.getSessionFactory().openSession();
        try(Session session = mySession){
            customerDAO.setSession(session);
            session.beginTransaction();
            List<CustomerDTO> customerDTOS = customerDAO.findAll().map(Converter::<CustomerDTO>getDTOList).get();
            session.getTransaction().commit();
            return customerDTOS;
        }catch(Exception ex){
            mySession.getTransaction().rollback();
            throw ex;
        }
    }

    @Override
    public void createItem(ItemDTO dto) throws Exception {
        Session mySession = HibernateUtil.getSessionFactory().openSession();
        try(Session session = mySession){
            customerDAO.setSession(session);
            session.beginTransaction();
            customerDAO.save(Converter.getEntity(dto));
            session.getTransaction().commit();
        }catch(Exception ex){
            mySession.getTransaction().rollback();
            throw ex;
        }
    }

    @Override
    public void updateItem(ItemDTO dto) throws Exception {
        Session mySession = HibernateUtil.getSessionFactory().openSession();
        try(Session session = mySession){
            customerDAO.setSession(session);
            session.beginTransaction();
            customerDAO.update(Converter.getEntity(dto));
            session.getTransaction().commit();
        }catch(Exception ex){
            mySession.getTransaction().rollback();
            throw ex;
        }
    }

    @Override
    public void deleteItem(String code) throws Exception {
        Session mySession = HibernateUtil.getSessionFactory().openSession();
        try(Session session = mySession){
            customerDAO.setSession(session);
            session.beginTransaction();
            customerDAO.delete(customerID);
            session.getTransaction().commit();
        }catch(Exception ex){
            mySession.getTransaction().rollback();
            throw ex;
        }
    }

    @Override
    public ItemDTO findItem(String itemCode) throws Exception {
        Session mySession = HibernateUtil.getSessionFactory().openSession();
        try(Session session = mySession){
            customerDAO.setSession(session);
            session.beginTransaction();
            CustomerDTO customerDTO = customerDAO.find(id).map(Converter::<CustomerDTO>getDTO).orElse(null);
            session.getTransaction().commit();
            return customerDTO;
        }catch(Exception ex){
            mySession.getTransaction().rollback();
            throw ex;
        }
    }
}
