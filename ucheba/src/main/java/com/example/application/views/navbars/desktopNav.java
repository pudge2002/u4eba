package com.example.application.views.navbars;

import com.example.application.views.account.AccountView;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.router.RouterLink;

import javax.swing.*;

@Route("app-layout-navbar-placement")
public class desktopNav extends HorizontalLayout {

    public desktopNav() {
        add(getNavigation());

    }

    private VerticalLayout getNavigation() {
        VerticalLayout navigation = new VerticalLayout();
        navigation.addClassNames(LumoUtility.Width.FULL,
                LumoUtility.JustifyContent.EVENLY,
                LumoUtility.AlignSelf.STRETCH);
        navigation.setPadding(false);
        navigation.setSpacing(true); // Установите spacing в true для добавления расстояния между кнопками

        navigation.add(createLink(VaadinIcon.HOME, "Главная"),

                createLink(VaadinIcon.USER, "Аккаунт"));
        navigation.getStyle().set("margin-top", "10%");
        return navigation;
    }

    private Component createLink(VaadinIcon icon, String label) {
        HorizontalLayout linkLayout = new HorizontalLayout();
        linkLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        linkLayout.setSpacing(true);

        Icon linkIcon = icon.create();
        Span linkLabel = new Span(label);

        RouterLink routerLink = new RouterLink();
        routerLink.addClassNames(LumoUtility.Display.FLEX,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.Padding.Horizontal.LARGE,
                LumoUtility.TextColor.SECONDARY);
        if(label == "Главная")
            routerLink.setRoute(MainView.class);
        if (label == "Аккаунт")
            routerLink.setRoute(AccountView.class);
        linkLabel.getStyle().set("font-size", "var(--lumo-font-size-m)")
                .set("margin-left", "10px").set("font-weight","bold");
        routerLink.add(linkIcon, linkLabel);
        routerLink.addClassName("nav-link");

        linkLayout.add(routerLink);

        return linkLayout;
    }
}