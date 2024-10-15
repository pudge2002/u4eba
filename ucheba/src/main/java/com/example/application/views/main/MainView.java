package com.example.application.views.main;

import com.example.application.views.account.AccountView;
import com.example.application.views.navbars.desktopNav;
import com.example.application.views.navbars.mobileNav;
import com.example.application.views.posts.PostsView;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;


@PageTitle("Main")
@Menu(icon = "line-awesome/svg/pencil-ruler-solid.svg", order = 0)
@Route(value = "main")
public class MainView extends Composite<VerticalLayout> implements BeforeEnterObserver {

    TabSheet tabSheet = new TabSheet();

    public MainView() {
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setHeight("100%");

        tabSheet.setHeight("100%");
        tabSheet.getStyle().set("background-color", "#E6E9ED");

        tabSheet.getChildren().forEach(child -> {
            child.getStyle().set("background-color", "white"); // Устанавливаем фон для внутренних элементов
        });

        getStyle().set("margin", "0");
        getStyle().set("padding", "0");

        setTabSheetSampleData(tabSheet);
        getContent().add(tabSheet);
    }

    private void setTabSheetSampleData(TabSheet tabSheet) {
        PostsView post = new PostsView();
        AccountView ac = new AccountView();

        tabSheet.add("Популярное", post);
        tabSheet.add("Вопрос/ответ", ac);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        boolean isMobile = isMobileDevice();
        if (isMobile) {
            tabSheet.setWidth("100%");
            tabSheet.getStyle().set("margin-left", "0");
            tabSheet.addClassName("left-aligned");
            getContent().add(new mobileNav());

        } else {
            tabSheet.getStyle().set("margin-top", "20px");
            tabSheet.setWidth("60%");
            tabSheet.getStyle().set("margin-left", "auto");
            tabSheet.getStyle().set("margin-right", "auto");
            getContent().add(new desktopNav());
        }
    }

    private boolean isMobileDevice() {
        VaadinSession session = VaadinSession.getCurrent();
        String userAgent = session.getBrowser().getBrowserApplication();
        return userAgent.contains("Mobile") || userAgent.contains("Android") || userAgent.contains("iPhone");
    }
}