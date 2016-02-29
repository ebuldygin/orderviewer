package beans.services;

import beans.debug.Logged;
import entities.Order;
import entities.OrderItem;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import resources.DataRepository;

@Transactional
@Logged
public class OrderDAO implements Serializable {

    @Inject
    @DataRepository
    private EntityManager em;

    public List<Order> getOrders() {
        return em.createQuery("select o from OrderEntity o").getResultList();
    }

    public List<OrderItem> getOrderItems(Order o) {
        return em.createNamedQuery(OrderItem.Q_ITEMS_FOR_ORDER)
                .setParameter("orderId", o.getId())
                .getResultList();
    }

    public Order find(Long id, boolean fetchItems) {
        Map<String, Object> properties = new HashMap<>();
        if (fetchItems) {
            EntityGraph<?> eg = em.createEntityGraph(Order.EG_ITEMS);
            properties.put("javax.persistence.loadgraph", eg);
        }
        return em.find(Order.class, id, properties);
    }

    public void create(Order order) {
        em.persist(order);
    }

    public void update(Order order) {
        em.merge(order);
    }

    public void remove(Long orderId) {
        Order o = em.getReference(Order.class, orderId);
        if (o != null) {
            em.remove(o);
        }
    }

}
