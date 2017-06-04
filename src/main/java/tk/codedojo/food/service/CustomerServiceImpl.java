package tk.codedojo.food.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.codedojo.food.beans.Customer;
import tk.codedojo.food.dao.CustomerDao;
import tk.codedojo.food.exception.UserNameAlreadyInUseException;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private CustomerDao dao;

    public boolean usernameInUse(String username){
        return dao.getCustomerByUserName(username)!=null;
    }

    public void addCustomer(Customer c) throws Exception{
        if(c.getUserName()==null || "".equals(c.getUserName()) ){
            throw new NullPointerException("You must provide a username!");
        } else{
            //username not already in use
            if(!this.usernameInUse(c.getUserName())){
                dao.save(c);
            } else{
                throw new UserNameAlreadyInUseException("This username is already in use!");
            }
        }
    }
}
