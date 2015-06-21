package ch.codebulb.jsfrestfulprgviewscoped.controller;

import ch.codebulb.jsfrestfulprgviewscoped.model.Customer;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import ch.codebulb.jsfrestfulprgviewscoped.model.Payment;
import ch.codebulb.jsfrestfulprgviewscoped.service.CustomerService;
import ch.codebulb.jsfrestfulprgviewscoped.service.PaymentService;
import javax.faces.bean.ViewScoped;
import org.omnifaces.util.Faces;

@ManagedBean
@ViewScoped
public class PaymentController extends BaseController<Payment> {
    @Inject
    private PaymentService service;
    @Inject
    private CustomerService customerService;
    
    private Long currentEntityCustomerId;

    @Override
    protected PaymentService getService() {
        return service;
    }

    @Override
    protected Payment createNewEntity() {
        // if called by preValidate <f:event>
        if (currentEntityCustomerId == null) {
            String idParam = Faces.getRequestParameterMap().get("customer");
            currentEntityCustomerId = Long.parseLong(idParam);
        }
        // if called by <f:viewAction>
        Customer customer = customerService.findById(currentEntityCustomerId);
        return new Payment(customer);
    }

    @Override
    protected Url resolveUrl(NavigationOutcome outcome) {
        switch (outcome) {
            case THIS: return new Url(null);
            case BACK:
                if (getCurrentEntity() == null || getCurrentEntity().getCustomer() == null) {
                    String idParam = Faces.getRequestParameterMap().get("customer");
                    if (idParam != null) {
                        currentEntityCustomerId = Long.parseLong(idParam);
                        Customer customer = customerService.findById(currentEntityCustomerId);
                        if (customer != null) {
                            return new Url("/pages/payments/list.xhtml", false).param("customer", customer.getId());
                        }
                    }
                    // else let the client handle this error
                    return new Url("/pages/customers/list.xhtml");
                }
                return new Url("/pages/payments/list.xhtml", false).param("customer", getCurrentEntity().getCustomer().getId());
            default: throw new IllegalArgumentException("Navigation outcome not recognized: " + outcome);
        }
    }

    public Long getCurrentEntityCustomerId() {
        return currentEntityCustomerId;
    }

    public void setCurrentEntityCustomerId(Long currentEntityCustomerId) {
        this.currentEntityCustomerId = currentEntityCustomerId;
    }
}
