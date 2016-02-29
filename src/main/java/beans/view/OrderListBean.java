package beans.view;

import beans.debug.Logged;
import beans.services.TimeServiceBean;
import beans.services.OrderDAO;
import entities.Order;
import entities.OrderItem;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named("orderList")
@ConversationScoped
@Logged
public class OrderListBean implements Serializable {

    @Inject
    private OrderDAO orderDAO;
    @Inject
    private TimeServiceBean timeServiceBean;
    @Inject
    private Conversation conversation;
    private Date reportDate;
    private List<Order> orders;

    public void initConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
            refreshData();
        }
    }

    public void refreshData() {
        orders = orderDAO.getOrders();
        try {
            reportDate = timeServiceBean.requestDate();
        } catch (Exception ex) {
            Logger.getLogger(OrderListBean.class.getName()).log(Level.SEVERE, "Time service error", ex);
        }
    }

    public Date getReportDate() {
        return reportDate;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<OrderItem> getOrderItems(Order o) {
        return orderDAO.getOrderItems(o);
    }

    public void remove() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        if (params.containsKey("orderId")) {
            Long orderId = Long.parseLong(params.get("orderId"));
            orderDAO.remove(orderId);
            conversation.end();
        }
    }

    public void remove(Order order) {
        orderDAO.remove(order.getId());
        conversation.end();
    }

}
