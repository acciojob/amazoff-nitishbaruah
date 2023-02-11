package com.driver;

import org.springframework.stereotype.Repository;

import java.lang.reflect.Parameter;
import java.util.*;


@Repository
public class OrderRepository {
    Map<String,Order> orders=new HashMap<>();
    Set<DeliveryPartner> partnerSet= new HashSet<>();
    Map<String,HashSet<String>> partnerWithDelivery= new HashMap<>();

    public void addOrder(Order order){
        orders.put(order.getId(),order);
       // return "Success";
    }
    public void addPartner(String id){
        partnerSet.add(new DeliveryPartner(id));

    }

    public void addOrderPartner(String orderId, String deliverPartner){
        boolean flag=false;
        for (DeliveryPartner partner:partnerSet){
            if(partner.getId().equals(deliverPartner)) {
                flag=true;
                break;
            }
        }
        if(!flag) return ;
        if(!orders.containsKey(orderId)) return ;
        if(partnerWithDelivery.containsKey(deliverPartner)){
            partnerWithDelivery.get(deliverPartner).add(orderId);
            for (DeliveryPartner partner:partnerSet){
                if(partner.getId().equals(deliverPartner)) {
                    partner.setNumberOfOrders(partnerWithDelivery.get(deliverPartner).size());
                    break;
                }
            }
            return ;
        }
        HashSet<String> set= new HashSet<>();
        set.add(orderId);
        partnerWithDelivery.put(deliverPartner,set);
        for (DeliveryPartner partner:partnerSet){
            if(partner.getId().equals(deliverPartner)) {
                partner.setNumberOfOrders(partnerWithDelivery.get(deliverPartner).size());
                break;
            }
        }

    }

    public Order getOrder(String orderId){
        return orders.get(orderId);
    }

    public DeliveryPartner getPartner(String Id){
        DeliveryPartner newPartner= new DeliveryPartner();
        for (DeliveryPartner partner:partnerSet){
            if(partner.getId().equals(Id)) {
                newPartner.setNumberOfOrders(partner.getNumberOfOrders());
                newPartner.setId(partner.getId());
                break;
            }
        }
        return newPartner;
    }

    public int getOrderCount(String Id){
        return partnerWithDelivery.get(Id).size();
    }

    public List<String> getOrderByPartner(String partnerId){
        List<String> list=new ArrayList<>();
        for(String OrderId:partnerWithDelivery.get(partnerId)){
            list.add(OrderId);
        }
        return list;
    }

    public List<String> getAllOrder(){
        List<String> list=new ArrayList<>();
        for(String OrderId: orders.keySet()){
            list.add(OrderId);
        }
        return list;
    }

    public int getNotAssignedOrder(){
        HashSet<String> set= new HashSet<>();
        for(String id: partnerWithDelivery.keySet()){
            for(String orderId: partnerWithDelivery.get(id)){
                set.add(orderId);
            }
        }
        int count=0;
        for(String st: orders.keySet()){
            if(!set.contains(st)) count++;
        }
        return count;
    }

    public int getLeftOrders(String partnerId,String time){
        int HH = Integer.valueOf(time.substring(0, 2));
        int MM= Integer.valueOf(time.substring(3));
        int timeOut = HH*60 + MM;

        int countOfOrders = 0;
        if(partnerWithDelivery.containsKey(partnerId)){
            for(String st:partnerWithDelivery.get(partnerId)){
                if (orders.get(st).getDeliveryTime()>timeOut)
                    countOfOrders++;
            }

        }
        return countOfOrders;
    }

    public String getLastOrder(String partnerId){
        int time=0;
        if(partnerWithDelivery.containsKey(partnerId)){
            for(String st:partnerWithDelivery.get(partnerId)){
                time=Math.max(time,orders.get(st).getDeliveryTime());
            }

        }
        Integer HH=time/60;
        Integer MM=time%60;
        String hour = String.valueOf(HH);
        String min= String.valueOf(MM);
        if(hour.length() == 1){
            hour = "0" + hour;
        }
        if(min.length() == 1){
            min= "0" + min;
        }

        return  hour+ ":" + min;
    }

    public void deletePartner(String partnerId){
        partnerWithDelivery.remove(partnerId);
        for(DeliveryPartner partner:partnerSet){
            if(partner.getId().equals(partnerId)){
                partnerSet.remove(partner);
                break;
            }
        }

    }
    public void deleteOrder(String orderId){
        orders.remove(orderId);
        for(String partner: partnerWithDelivery.keySet()){
            for (String order: partnerWithDelivery.get(partner)){
                if(order.equals(orderId)){
                    partnerWithDelivery.get(partner).remove(order);
                }
            }
        }

    }


}
