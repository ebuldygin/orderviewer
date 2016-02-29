package beans.view;

import beans.debug.Logged;
import beans.services.OrderDAO;
import entities.Order;
import java.io.Serializable;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("orderEdit")
@ConversationScoped
@Logged
public class OrderEditBean implements Serializable {

    @Inject
    private Conversation conversation;
    @Inject
    private OrderDAO orderDAO;
    private Long id;
    private Order instance;

    public Order getInstance() {
        if (instance == null) {
            if (id != null) {
                instance = orderDAO.find(id, true);
            } else {
                instance = new Order();
            }
        }
        return instance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isManaged() {
        return getInstance().getId() != null;
    }

    public String save() {
        if (isManaged()) {
            orderDAO.update(getInstance());
            conversation.end();
            return "saved";
        } else {
            orderDAO.create(getInstance());
            this.setId(getInstance().getId());
            return "created";
        }
    }

    public String cancel() {
        conversation.end();
        return "cancelled";
    }

    public String delete() {
        if (id != null) {
            orderDAO.remove(id);
        }
        conversation.end();
        return "deleted";
    }

    public void initConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
        }
    }

}
