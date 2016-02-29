package beans.services;

import beans.debug.Logged;
import entities.OrderItem;
import entities.OrderItemPK;
import java.io.Serializable;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import resources.DataRepository;

@Transactional
@Logged
public class OrderItemDAO implements Serializable {

    @Inject
    @DataRepository
    private EntityManager em;

    public OrderItem find(Long orderId, Long itemId) {
        OrderItemPK id = new OrderItemPK(orderId, itemId);
        return em.find(OrderItem.class, id);
    }

    public void create(OrderItem orderItem) {
        OrderItemPK oldId = new OrderItemPK(orderItem.getId().getOrderId(), null);
        saveOrUpdate(orderItem, oldId);
    }

    public void update(OrderItem orderItem) {
        saveOrUpdate(orderItem, orderItem.getId());
    }

    public void saveOrUpdate(OrderItem orderItem, OrderItemPK oldId) {
        if (oldId.getItemId() != null && !oldId.equals(orderItem.getId())) {
            remove(oldId.getOrderId(), oldId.getItemId());
        }
        em.merge(orderItem);
    }

    public void remove(Long orderId, Long itemId) {
        OrderItemPK id = new OrderItemPK(orderId, itemId);
        remove(id);
    }

    public void remove(OrderItemPK id) {
        OrderItem oi = em.getReference(OrderItem.class, id);
        if (oi != null) {
            em.remove(oi);
        }
    }

}
