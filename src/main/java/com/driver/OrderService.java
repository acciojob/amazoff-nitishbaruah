package com.driver;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    OrderRepository orderRepository=new OrderRepository();

    public void addOrder(Order order){
        orderRepository.addOrder(order);
    }

    public void addPartner(String id){
         orderRepository.addPartner(id);
    }

    public void addOrderPartner(String order, String partner){
       orderRepository.addOrderPartner(order,partner);
    }

    public Order getOrder(String id){
        return orderRepository.getOrder(id);
    }

    public DeliveryPartner getPartner(String id){
        return orderRepository.getPartner(id);
    }

    public int getOrderCount(String id){
        return orderRepository.getOrderCount(id);
    }

    public List<String> getOrderByPartner(String id){
        return orderRepository.getOrderByPartner(id);
    }

    public List<String> getAllOrder(){
        return orderRepository.getAllOrder();
    }

    public int getNotAssignedOrder(){
        return orderRepository.getNotAssignedOrder();
    }

    public int getLeftOrder(String id, String time){
        return orderRepository.getLeftOrders(id,time);
    }

    public String lastOrder(String Id){
        return orderRepository.getLastOrder(Id);
    }

    public void deletePartner(String id){
        orderRepository.deletePartner(id);
    }

    public void deleteOrder(String id){
        orderRepository.deleteOrder(id);
    }
}
