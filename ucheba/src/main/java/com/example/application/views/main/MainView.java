package com.example.application.views.main;

import com.example.application.views.account.AccountView;
import com.example.application.views.navbars.desktopNav;
import com.example.application.views.navbars.mobileNav;
import com.example.application.views.posts.PostsView;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;


@PageTitle("Main")
@Menu(icon = "line-awesome/svg/pencil-ruler-solid.svg", order = 0)
@Route(value = "main")
public class MainView extends Composite<VerticalLayout> implements BeforeEnterObserver {
    boolean mobile = false;
    TabSheet tabSheet = new TabSheet();
    public MainView() {
        VerticalLayout layoutColumn2 = new VerticalLayout();

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");

//        tabSheet.getStyle().set("flex-grow", "1");


        setTabSheetSampleData(tabSheet);
        getContent().add(layoutColumn2);
        layoutColumn2.add(tabSheet);
    }

    private void setTabSheetSampleData(TabSheet tabSheet) {
        PostsView post = new PostsView();
        AccountView ac = new AccountView();
        H1 test = new H1("text");
        H1 test1 = new H1("text1");
        H1 test2 = new H1("text2");
       VerticalLayout hz = new VerticalLayout(test,test1,test2);
//        hz.getStyle().set("margin-left","10%");
        tabSheet.add("Популярное", hz);
        tabSheet.add("Вопрос/ответ", ac);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        boolean isMobile = isMobileDevice();
        if (isMobile) {
            tabSheet.setWidth("110%");
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