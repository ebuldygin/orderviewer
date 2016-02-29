package beans.view;

import beans.debug.Logged;
import beans.services.OrderItemDAO;
import entities.Order;
import entities.OrderItem;
import entities.OrderItemPK;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Instance;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.jboss.logging.Logger;

@Named("orderItemEdit")
@ConversationScoped
@Logged
public class OrderItemEditBean implements Serializable {

    @Inject
    private OrderItemDAO dao;
    private Long orderId;
    private Long itemId;
    private OrderItem instance;

    public void setNewInstance(Order order) {
        OrderItem newItem = new OrderItem();
        OrderItemPK newId = new OrderItemPK(order.getId(), null);
        newItem.setId(newId);
        newItem.setOrder(order);
        setInstance(newItem);
    }

    public OrderItem getInstance() {
        return instance;
    }

    public void setInstance(OrderItem instance) {
        this.instance = instance;
        if (instance == null) {
            this.orderId = null;
            this.itemId = null;
        } else {
            this.orderId = instance.getId().getOrderId();
            this.itemId = instance.getId().getItemId();
        }
    }

    public void save() {
        List<OrderItem> items = getInstance().getOrder().getItems();
        OrderItemPK oldId = new OrderItemPK(orderId, itemId);
        if (checkUniqueConstraint(oldId, getInstance().getId(), items)) {
            save(oldId, items);
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Error", "Item id already exists in the order"));
        }
    }

    private boolean checkUniqueConstraint(OrderItemPK oldId, OrderItemPK newId,
            List<OrderItem> items) {
        if (!oldId.equals(newId)) {
            for (OrderItem item : items) {
                if (newId.equals(item.getId())) {
                    return false;
                }
            }
        }
        return true;
    }

    private void save(OrderItemPK oldId, List<OrderItem> items) {
        try {
            dao.saveOrUpdate(getInstance(), oldId);
            items.remove(getInstance());
            items.add(getInstance());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Item saved"));
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Logger.Level.ERROR,
                    "Save OrderItem operation failed", ex);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Item not saved"));
        }
    }

    public void delete() {
        try {
            dao.remove(getInstance().getId());
            getInstance().getOrder().getItems().remove(getInstance());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Item deleted"));
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Logger.Level.ERROR,
                    "Delete item from the order operation failed", ex);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Item not deleted"));
        }
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

}
