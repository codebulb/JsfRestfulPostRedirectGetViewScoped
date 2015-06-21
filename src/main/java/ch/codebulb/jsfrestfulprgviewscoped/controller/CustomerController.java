package ch.codebulb.jsfrestfulprgviewscoped.controller;

import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import ch.codebulb.jsfrestfulprgviewscoped.model.Customer;
import ch.codebulb.jsfrestfulprgviewscoped.model.EmploymentStatus;
import ch.codebulb.jsfrestfulprgviewscoped.service.CustomerService;
import java.io.Serializable;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.faces.event.AjaxBehaviorEvent;

@ManagedBean
@ViewScoped
public class CustomerController extends BaseController<Customer> implements Serializable {
    @Inject
    private CustomerService service;

    @Override
    protected CustomerService getService() {
        return service;
    }

    @Override
    protected Customer createNewEntity() {
        return new Customer();
    }
    
    public void employmentStatusChanged(AjaxBehaviorEvent event) {
        if (EmploymentStatus.Unemployed == ((UIInput) event.getComponent()).getValue()) {
            getCurrentEntity().setCompanyName(null);
        }
    }
}
