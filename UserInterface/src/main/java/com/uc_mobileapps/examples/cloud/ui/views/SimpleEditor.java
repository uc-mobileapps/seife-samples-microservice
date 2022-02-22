package com.uc_mobileapps.examples.cloud.ui.views;

import com.uc_mobileapps.examples.cloud.ui.forms.TripForm;
import com.uc_mobileapps.examples.cloud.ui.forms.TripGrid;
import com.uc_mobileapps.examples.entities.Trip;
import com.uc_mobileapps.examples.filters.TripFilterVO;
import com.uc_mobileapps.examples.restclient.TripClient;
import com.uc_mobileapps.spring.QueryUtil;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.GridSelectionModel;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;

import javax.annotation.PostConstruct;
import java.util.Optional;

/**
 * Master-detail view.
 */
@UIScope
@SpringComponent
@Route(value = "trip-overview")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class SimpleEditor extends VerticalLayout {

    private final TripGrid tripGrid;
    private final TripForm tripForm;

    private Button button = new Button("click");
    private Button newButton = new Button("New");
    private Button delButton = new Button("Delete");

    /**
     * The Spring REST endpoint for trips. In real scenarios an additional ui service may be feasible.
     */
    private TripClient tripClient;


    public SimpleEditor(TripClient tripClient, TripGrid tripGrid, TripForm tripForm) {
        this.tripClient = tripClient;
        this.tripGrid = tripGrid;
        this.tripForm = tripForm;
    }

    @PostConstruct
    public void setup() {

        // bind the REST service endpoint with optional filtering capability to the user interface
        ConfigurableFilterDataProvider<Trip, String, TripFilterVO> filteredDataProvider = tripGrid.createDataProvider(
                query -> query.getFilter()
                        .map(filter -> tripClient.listFilteredTrips(filter, QueryUtil.createPageRequest(query)))
                        .orElse(tripClient.listTrips(QueryUtil.createPageRequest(query)))
                        .stream(),
                query -> Math.toIntExact(query.getFilter()
                        .map(filter -> tripClient.countFilteredBy(filter))
                        .orElse(tripClient.count())));

        tripGrid.setDataProvider(filteredDataProvider);

        button.addClickListener(l -> {
            tripGrid.getDataProvider().refreshAll();
        });

        newButton.addClickListener(e -> {
            tripGrid.getSelectionModel().deselectAll();
            tripForm.setEntity(new Trip());
        });

        delButton.addClickListener(e -> {
            GridSelectionModel<Trip> selection = tripGrid.getSelectionModel();
            Optional<Trip> item = selection.getFirstSelectedItem();
            if (item.isPresent()) {
                tripClient.delete(item.get().getId());
                tripGrid.getDataProvider().refreshAll();
                selection.deselectAll();

            }
        });

        tripGrid.addSelectionListener(e -> {
           e.getFirstSelectedItem().ifPresent(tripForm::setEntity);
        });

        tripForm.addSaveListener(this::onTripSaved);

        add(tripGrid, tripForm, newButton, delButton, button);

    }

    private void onTripSaved(Trip trip, boolean asNew) {
        tripGrid.getDataProvider().refreshAll();
        tripGrid.select(trip);

        String source = trip.getSource();
        if (source != null) {
            tripGrid.filterSource(trip.getSource().substring(0, Math.min(2, source.length())));
        }
        tripGrid.scrollToEnd();
    }

}
