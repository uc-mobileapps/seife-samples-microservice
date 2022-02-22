package com.uc_mobileapps.examples.cloud.ui.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

import javax.annotation.PostConstruct;

@Route("")
public class MainView extends VerticalLayout implements BeforeEnterObserver {

    private static final long serialVersionUID = -119363637799067567L;

    public void beforeEnter(BeforeEnterEvent event) {
        event.rerouteTo(SimpleEditor.class);
    }

}
