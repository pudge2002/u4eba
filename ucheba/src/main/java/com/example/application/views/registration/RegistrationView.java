package com.example.application.views.registration;

import com.example.application.Model.Controller;
import com.example.application.views.authorization.AuthorizationView;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
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

import java.sql.SQLException;
import java.util.Objects;

@PageTitle("Registration")
@Menu(icon = "line-awesome/svg/pencil-ruler-solid.svg", order = 3)
@Route(value = "registration")
public class RegistrationView extends Composite<VerticalLayout> {
    Controller controller = new Controller();
    public RegistrationView() throws SQLException {
        HorizontalLayout layoutRow = new HorizontalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        VerticalLayout layoutColumn3 = new VerticalLayout();
        H2 h1 = new H2();
        TextField textField = new TextField();
        TextField textField2 = new TextField();
        PasswordField passwordField = new PasswordField();
        PasswordField passwordField2 = new PasswordField();
        Button buttonPrimary = new Button();
        getContent().setHeightFull();
        getContent().setWidthFull();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutRow.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow);
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        layoutColumn2.setHeightFull();
        layoutRow.setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setWidth("100%");
        layoutColumn2.setHeight("100%");
        layoutColumn2.setJustifyContentMode(JustifyContentMode.CENTER);
        layoutColumn2.setAlignItems(Alignment.CENTER);
        layoutColumn3.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutColumn3);
        layoutColumn3.setWidth("min-content");
        layoutColumn3.getStyle().set("flex-grow", "1");
        layoutColumn3.setJustifyContentMode(JustifyContentMode.CENTER);
        layoutColumn3.setAlignItems(Alignment.CENTER);
        h1.setText("Регистрация");
        h1.addClassName("pinkText");
        layoutColumn3.setAlignSelf(FlexComponent.Alignment.CENTER, h1);
        h1.setWidth("max-content");
        textField.setLabel("Никнейм");
        layoutColumn3.setAlignSelf(FlexComponent.Alignment.CENTER, textField);
        textField.setWidth("min-content");
        textField2.setLabel("Логин");
        layoutColumn3.setAlignSelf(FlexComponent.Alignment.CENTER, textField2);
        textField2.setWidth("min-content");
        passwordField.setLabel("Пароль");
        layoutColumn3.setAlignSelf(FlexComponent.Alignment.CENTER, passwordField);
        passwordField.setWidth("min-content");
        passwordField2.setLabel("Подтверждение пароля");
        passwordField2.setWidth("min-content");
        buttonPrimary.setText("Подтвердить");
        layoutColumn3.setAlignSelf(FlexComponent.Alignment.CENTER, buttonPrimary);
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrimary.addClassName("custom-button");
        H4 errorMessage = new H4("Пароли не совпадают");
        errorMessage.getStyle().set("color", "red");
        errorMessage.setVisible(false);

        getContent().add(layoutRow);
        layoutRow.add(layoutColumn2);
        layoutColumn2.add(layoutColumn3);
        StreamResource image = new StreamResource("logo.png",
                () -> getClass().getResourceAsStream("/images/logo.png"));
        Image logo = new Image(image,"ucheba logo");
        logo.setWidth("150px");
        logo.setHeight("150px");
        layoutColumn3.add(logo);
        layoutColumn3.add(h1);
        layoutColumn3.add(textField);
        layoutColumn3.add(textField2);
        layoutColumn3.add(passwordField);
        layoutColumn3.add(passwordField2);
        layoutColumn3.add(errorMessage);
        layoutColumn3.add(buttonPrimary);

        buttonPrimary.addClickListener(e -> {
            System.out.println("funk reg");
            if(passwordField.getValue()!= null &&passwordField2.getValue()!= null &&textField.getValue()!=null&&textField2.getValue()!=null) {
                System.out.println(passwordField.getValue() +" "+ passwordField2.getValue());
                if (Objects.equals(passwordField.getValue(), passwordField2.getValue())) {
                    boolean isRegistered = controller.registerUser(textField.getValue(), passwordField.getValue(), textField2.getValue());
                    System.out.println(isRegistered);
                    if (isRegistered) {
                        UI.getCurrent().navigate(AuthorizationView.class);
                    } else {
                        errorMessage.setText("Ошибка подключения");
                        errorMessage.setVisible(true);
                        System.out.println("error");
                    }
                } else {
                    errorMessage.setVisible(true);
                }
            }
            else {
                errorMessage.setText("Введите все данные");
                errorMessage.setVisible(true);
                System.out.println("error2");
            }


        });
    }
}
