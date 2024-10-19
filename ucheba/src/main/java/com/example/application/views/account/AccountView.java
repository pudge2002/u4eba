package com.example.application.views.account;

import com.example.application.views.main.MainView;
import com.example.application.views.navbars.desktopNav;
import com.example.application.views.navbars.mobileNav;
import com.example.application.views.posts.PostsView;
import com.example.application.views.registration.RegistrationView;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

import java.sql.SQLException;

@PageTitle("Account")
@Menu(icon = "line-awesome/svg/pencil-ruler-solid.svg", order = 5)
@Route(value = "account")
public class AccountView extends AppLayout implements BeforeEnterObserver {
    H1 h1 = new H1();
    HorizontalLayout Navbar;

    public AccountView() throws SQLException {
        VerticalLayout content = CreateContent();
        content.setSizeFull();
        setContent(content);

        CreateNavbar();
//        Navbar = new mobileNav();
//        addToNavbar(true, Navbar);
    }

    private VerticalLayout CreateContent() throws SQLException {
        VerticalLayout content = new VerticalLayout();

        HorizontalLayout layoutRow = new HorizontalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        Avatar avatar = new Avatar();
        H2 h2 = new H2();
        H6 h6 = new H6();
        Button buttonSecondary = new Button();
        TabSheet tabSheet = new TabSheet();

        content.setWidth("100%");
        content.getStyle().set("flex-grow", "1");
        content.setJustifyContentMode(JustifyContentMode.CENTER);
        content.setAlignItems(Alignment.CENTER);
        h1.setText("Обложка");
        h1.setWidth("max-content");
        layoutRow.setWidthFull();
        content.setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("min-content");
        layoutRow.setHeight("min-content");
        layoutColumn2.setHeightFull();
        layoutRow.setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        avatar.setName("Menshev");
        avatar.setWidth("150px");
        avatar.setHeight("150px");
        h2.setText("Menshev");
        h2.setWidth("max-content");
        h6.setText("student");
        h6.setWidth("max-content");
        buttonSecondary.setText("Редактировать");
        buttonSecondary.getStyle().set("margin-top", "20%");
        buttonSecondary.setWidth("min-content");

        content.setAlignSelf(FlexComponent.Alignment.CENTER, tabSheet);
        tabSheet.setWidth("min-content");
        tabSheet.getStyle().set("flex-grow", "1").set("width", "100%");
        tabSheet.setHeight("100%");
        setTabSheetSampleData(tabSheet);
        content.setHeight("100%");

        content.add(layoutRow);
        layoutRow.add(layoutColumn2);
        layoutColumn2.add(avatar);
        layoutColumn2.add(h2);
        layoutColumn2.add(h6);
        layoutRow.getStyle().set("width", "90%");
        layoutRow.add(buttonSecondary);
        content.add(tabSheet);

        buttonSecondary.addClickListener(e -> {
            UI.getCurrent().navigate(EditAccountView.class);
        });

        return content;
    }

    public void CreateNavbar() {
        boolean isMobile = isMobileDevice();
        if (isMobile) {

            Navbar = new mobileNav();
            addToNavbar(true, Navbar);

        } else {

            DrawerToggle toggle = new DrawerToggle();

            H1 title = new H1("ucheba");
            title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                    .set("margin", "0");

            Navbar = new desktopNav();

            Scroller scroller = new Scroller(Navbar);
            scroller.setClassName(LumoUtility.Padding.SMALL);

            addToDrawer(scroller);
            addToNavbar(toggle, title);
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
          boolean isMobile = isMobileDevice();
//        if (isMobile) {
//            getContent().add(new mobileNav());
//        } else {
//            getContent().add(new desktopNav());
//        }
    }

    public static boolean isMobileDevice() {
        VaadinSession session = VaadinSession.getCurrent();
        String userAgent = session.getBrowser().getBrowserApplication();
        return userAgent.contains("Mobile") || userAgent.contains("Android") || userAgent.contains("iPhone");
    }


    private void setTabSheetSampleData(TabSheet tabSheet) throws SQLException {
        PostsView postsDiv = new PostsView();
        postsDiv.addClassName("custom-div");
        tabSheet.add("Посты", postsDiv);

        Div questionsDiv = new Div(new Text("Пока пусто"));
        questionsDiv.addClassName("custom-div");
        tabSheet.add("Вопросы", questionsDiv);
    }
}
