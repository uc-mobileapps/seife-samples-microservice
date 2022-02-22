package com.uc_mobileapps.examples.cloud.ui.views;

import com.uc_mobileapps.examples.entities.Trip;
import com.uc_mobileapps.examples.cloud.ui.forms.TripGrid;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@UIScope
@SpringComponent
@Route(value = SimpleGrid.PATH)
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class SimpleGrid extends VerticalLayout {

    public static final String PATH = "customers";
    private final TripGrid customerGrid;


    @Autowired
    public SimpleGrid(TripGrid customerGrid) {
        this.customerGrid = customerGrid;
        ///this.customerRepository = customerRepository;

        RouterLink addCustomer = new RouterLink("Add Customer", SimpleEditor.class);

        add(new Label("List of customers"));

        add(customerGrid);
        add(addCustomer);

        addAttachListener(e -> refreshView());

    }

    private void refreshView() {
        /*CallbackDataProvider<Trip, ?> dataProvider = DataProvider.fromCallbacks(
                query -> customerRepository.findAll(PageRequest.of(query.getOffset() / query.getLimit(),
                                query.getLimit(), Sort.by("lastName"))).stream(),
                query -> Math.toIntExact(customerRepository.count()));

        customerGrid.setDataProvider(dataProvider);*/
    }
}
