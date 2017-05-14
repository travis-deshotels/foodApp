package tk.codedojo.food.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.codedojo.food.beans.MenuItem;
import tk.codedojo.food.beans.Order;
import tk.codedojo.food.beans.Restaurant;
import tk.codedojo.food.dao.CustomerDao;
import tk.codedojo.food.dao.OrderDao;
import tk.codedojo.food.dao.RestaurantDao;
import tk.codedojo.food.exception.CustomerNotFoundException;
import tk.codedojo.food.exception.OrderNotFoundException;
import tk.codedojo.food.exception.RestaurantNotFoundException;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    CustomerDao customerDao;

    @Autowired
    RestaurantDao restaurantDao;

    @Autowired
    OrderDao orderDao;

    public void completeOrder(String orderID) throws Exception{
        Order order = orderDao.findOne(orderID);
        if (order == null){
            throw new OrderNotFoundException("Cannot complete order, order id invalid!");
        }
        order.setComplete(true);
        orderDao.save(order);
    }

    public void addOrder(Order order) throws Exception{
        Restaurant restaurant = restaurantDao.findOne(order.getRestaurantID());
        if(restaurant == null){
            throw new RestaurantNotFoundException("Order does not have a valid restaurant!");
        }
        if(customerDao.findOne(order.getCustomerID()) == null){
            throw new CustomerNotFoundException("Order does not have a valid customer!");
        }
        if(order.getItems() == null){
            throw new NullPointerException();
        }
        order.setComplete(false);
        //TODO verify that the items being ordered are on the menu
        orderDao.save(order);
    }

    private boolean itemIsOnMenu(String item, List<MenuItem> menuItems){
        //TODO implement this
        return false;
    }
}