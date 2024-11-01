package com.example.application.views.authorization;

import com.example.application.Model.*;
import com.example.application.views.main.MainView;
import com.example.application.views.registration.RegistrationView;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.component.UI;

import java.sql.SQLException;

@PageTitle("Authorization")
@Menu(icon = "line-awesome/svg/pencil-ruler-solid.svg", order = 2)
@Route(value = "")
public class AuthorizationView extends Composite<VerticalLayout> {
    Controller controller = new Controller();

    public AuthorizationView() throws SQLException {
        HorizontalLayout layoutRow = new HorizontalLayout();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        H1 h1 = new H1();        TextField textField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button buttonPrimary = new Button();
        Button buttonTertiary = new Button();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutRow.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        layoutRow2.setHeightFull();
        layoutRow.setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.getStyle().set("flex-grow", "1");
        layoutRow2.setAlignItems(Alignment.CENTER);
        layoutRow2.setJustifyContentMode(JustifyContentMode.CENTER);

        layoutColumn2.setHeightFull();
        layoutRow2.setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        layoutColumn2.setJustifyContentMode(JustifyContentMode.CENTER);
        layoutColumn2.setAlignItems(Alignment.CENTER);

        h1.setText("ucheba");
        h1.setWidth("max-content");
        h1.addClassName("grayText");

        textField.setLabel("Логин");
        textField.setWidth("min-content");

        passwordField.setLabel("Пароль");
        passwordField.setWidth("min-content");

        buttonPrimary.setText("Войти");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary.addClassName("custom-button");

        buttonTertiary.setText("Регистрация");
        buttonTertiary.setWidth("min-content");
        buttonTertiary.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        buttonTertiary.addClassName("second-button");

        StreamResource image = new StreamResource("logo.png",
                () -> getClass().getResourceAsStream("/images/logo.png"));
        Image logo = new Image(image,"ucheba logo");
        logo.setWidth("200px");
        logo.setHeight("200px");

        H4 errorMessage = new H4("Неверный логин или пароль");
        errorMessage.getStyle().set("color", "red");
        errorMessage.setVisible(false);
        getContent().add(layoutRow);
        layoutRow.add(layoutRow2);
        layoutRow.getStyle().set("margin-top","15vh");
        layoutRow2.add(layoutColumn2);
        layoutColumn2.add(logo);
        layoutColumn2.add(textField);
        layoutColumn2.add(passwordField);
        layoutColumn2.add(errorMessage);
        layoutColumn2.add(buttonPrimary);
        layoutColumn2.add(buttonTertiary);

        buttonPrimary.addClickListener(e -> {

            String username = textField.getValue();
            String password = passwordField.getValue();
            boolean isAuthenticated = controller.authenticateUser(username, password);
            if (isAuthenticated) {
                UI.getCurrent().navigate(MainView.class);
                System.out.println("norm");
            }
            else {
                    errorMessage.setVisible(true);
                System.out.println("error");
            }

        });
        buttonTertiary.addClickListener(e -> {

            UI.getCurrent().navigate(RegistrationView.class);
        });
    }
}
