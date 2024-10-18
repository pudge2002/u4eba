package com.example.application.views.navbars;


import com.example.application.views.account.AccountView;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;


// tag::snippet[]
public class mobileNav extends HorizontalLayout {

    public mobileNav() {
        setWidthFull();
//        getStyle().set("bottom", "0");
//        getStyle().set("position", "fixed");
        add(getNavigation());
    }

    private HorizontalLayout getNavigation() {
        HorizontalLayout navigation = new HorizontalLayout();
        navigation.addClassNames(LumoUtility.Width.FULL,
                LumoUtility.JustifyContent.EVENLY,
                LumoUtility.AlignSelf.STRETCH);
        navigation.setPadding(false);
        navigation.setSpacing(false);
        navigation.add(createLink(VaadinIcon.HOME, "Home"),
                createLink(VaadinIcon.SEARCH, "Search"),
                createLink(VaadinIcon.USER, "Account"));

        return navigation;
    }

    private RouterLink createLink(VaadinIcon icon, String viewName) {
        RouterLink link = new RouterLink();
        // Demo has no routes
        if (viewName=="Home")
            link.setRoute(MainView.class);
        if (viewName == "Account")
            link.setRoute(AccountView.class);

        link.addClassNames(LumoUtility.Display.FLEX,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.Padding.Horizontal.LARGE,
                LumoUtility.TextColor.SECONDARY);
        link.add(icon.create());
        link.getElement().setAttribute("aria-label", viewName);
        return link;
    }


}

