package com.example.application.views.account;

import com.example.application.views.navbars.desktopNav;
import com.example.application.views.navbars.mobileNav;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.server.VaadinSession;
//import com.vaadin.flow.component.orderedlayout.FlexLayout.Gap;

@PageTitle("edAccount")
@Menu(icon = "line-awesome/svg/pencil-ruler-solid.svg", order = 5)
@Route(value = "edaccount")
public class EditAccountView extends Composite<VerticalLayout> implements BeforeEnterObserver {
    H1 h1 = new H1();
    String user = "Menshev";
    String aboutus = "student";
    public EditAccountView() {
        HorizontalLayout layoutRow = new HorizontalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        Avatar avatar = new Avatar();
        H2 h2 = new H2();
        H6 h6 = new H6();
        Button buttonSecondary = new Button();
        TabSheet tabSheet = new TabSheet();

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.CENTER);
        getContent().setAlignItems(Alignment.CENTER);
        h1.setText("Обложка");
        h1.setWidth("max-content");
        layoutRow.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow);

        layoutRow.setWidth("min-content");
        layoutRow.setHeight("min-content");
        layoutColumn2.setHeightFull();
        layoutRow.setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        avatar.setName(user);
        avatar.setWidth("150px");
        avatar.setHeight("150px");
        h2.setText("Menshev");
        h2.setWidth("max-content");
        h6.setText("student");
        h6.setWidth("max-content");
        buttonSecondary.setText("Редактировать");
        buttonSecondary.getStyle().set("margin-top", "20%");
        buttonSecondary.setWidth("min-content");

        getContent().setAlignSelf(Alignment.CENTER, tabSheet);
        tabSheet.setWidth("min-content");
        tabSheet.getStyle().set("flex-grow", "1").set("width", "100%");
        tabSheet.setHeight("100%");

        getContent().setHeight("100%");

        getContent().add(layoutRow);
        layoutRow.add(layoutColumn2);
        Button returnButton = new Button(VaadinIcon.ARROW_LEFT.create());
        returnButton.addClickListener(event -> {
            UI.getCurrent().navigate(AccountView.class);
        });
        H3 red = new H3("Редактирование");
        Button saveButton = new Button(VaadinIcon.CHECK.create());
        saveButton.addClickListener(event -> {
            UI.getCurrent().navigate(AccountView.class);
            //saveData();
        });
        HorizontalLayout hz = new HorizontalLayout(returnButton, red, saveButton);
        saveButton.getStyle().set("margin-top", "0");
        saveButton.getStyle().set("padding-top", "0");

        saveButton.getStyle().set("background", "none");
        saveButton.getStyle().set("border", "none");
        saveButton.getStyle().set("padding", "0");
        saveButton.getStyle().set("font-size", "20px");
        saveButton.getStyle().set("color", "inherit");
        saveButton.getStyle().set("cursor", "pointer");
        saveButton.getStyle().set("text-decoration", "none");
        returnButton.getStyle().set("margin-top", "0");
        returnButton.getStyle().set("padding-top", "0");

        returnButton.getStyle().set("background", "none");
        returnButton.getStyle().set("border", "none");
        returnButton.getStyle().set("padding", "0");
        returnButton.getStyle().set("font-size", "20px");
        returnButton.getStyle().set("color", "inherit");
        returnButton.getStyle().set("cursor", "pointer");
        returnButton.getStyle().set("text-decoration", "none");
        layoutColumn2.add(hz);
        layoutColumn2.add(avatar);
//        layoutColumn2.add(h2);
//        layoutColumn2.add(h6);
        layoutRow.getStyle().set("width", "90%");


        TextField userName = new TextField("Изменить имя");
        TextField about = new TextField("Описание профиля");
        userName.getStyle().set("width","300px");
        userName.setPlaceholder(user);
        about.setPlaceholder(aboutus);
        about.getStyle().set("width","300px").setHeight("300px");
        layoutColumn2.add(userName);
        layoutColumn2.add(about);

        // Добавляем класс для стилизации
        getContent().addClassName("main-content");
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        boolean isMobile = AccountView.DeviceUtils.isMobileDevice();
        if (isMobile) {
            getContent().add(new mobileNav());
        } else {
            getContent().add(new desktopNav());
        }
    }
    public class DeviceUtils {
        public static boolean isMobileDevice() {
            VaadinSession session = VaadinSession.getCurrent();
            String userAgent = session.getBrowser().getBrowserApplication();
            return userAgent.contains("Mobile") || userAgent.contains("Android") || userAgent.contains("iPhone");
        }
    }
}
