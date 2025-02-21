package com.example.application.views.account;

import com.example.application.Model.Controller;
import com.example.application.localdata.UserData;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.html.H6;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.server.VaadinSession;

import java.sql.SQLException;

//import com.vaadin.flow.component.orderedlayout.FlexLayout.Gap;

@PageTitle("edAccount")
@Menu(icon = "line-awesome/svg/pencil-ruler-solid.svg", order = 5)
@Route(value = "edaccount")
public class EditAccountView extends Composite<VerticalLayout> implements BeforeEnterObserver {
    H1 h1 = new H1();
    String user = "Menshev";
    Controller controller = new Controller();
    String aboutus = "student";
    private String selectedColor = "#ff0000"; // Переменная для сохранения цвета
    public EditAccountView() throws SQLException {
        UserData userData = VaadinSession.getCurrent().getAttribute(UserData.class);
        System.out.println("1"+userData);
        if (userData.getAvatar()!=null)
            selectedColor = userData.getAvatar();
        HorizontalLayout layoutRow = new HorizontalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        Avatar avatar = new Avatar();
        H2 h2 = new H2();
        H6 h6 = new H6();
        Button buttonSecondary = new Button();


        getContent().setWidth("90%");
        getContent().getStyle().set("flex-grow", "1");
        getContent().setJustifyContentMode(JustifyContentMode.CENTER);
        getContent().setAlignItems(Alignment.CENTER);
        layoutRow.setWidthFull();
        getContent().setFlexGrow(1.0, layoutRow);

        layoutRow.setWidth("min-content");
//        layoutRow.setHeight("min-content");
//        layoutColumn2.setHeightFull();
//        layoutRow.setFlexGrow(1.0, layoutColumn2);
        layoutColumn2.setWidth("100%");
//        layoutColumn2.getStyle().set("flex-grow", "1");
        avatar.setName(userData.getUsername());
        avatar.setWidth("150px");
        avatar.setHeight("150px");
        avatar.getStyle().set("background-color", selectedColor);

        h2.setWidth("max-content");
        h6.setText("student");
        h6.setWidth("max-content");
        buttonSecondary.setText("Редактировать");
        buttonSecondary.getStyle().set("margin-top", "20%");
        buttonSecondary.setWidth("min-content");


        getContent().setHeight("100%");


        Button returnButton = new Button(VaadinIcon.ARROW_LEFT.create());
        returnButton.addClickListener(event -> {
            UI.getCurrent().navigate(AccountView.class);
        });
        H3 red = new H3("Редактирование");
        Button saveButton = new Button(VaadinIcon.CHECK.create());

        HorizontalLayout hz = new HorizontalLayout(returnButton, red, saveButton);
        hz.setWidth("100%");
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
        hz.getStyle().set("justify-content", "space-between"); // Кнопки по краям
        hz.getStyle().set("align-items", "center"); // Вертикальное выравнивание по центру
        getContent().add(hz);

        getContent().add(avatar);

//        layoutColumn2.add(h2);
//        layoutColumn2.add(h6);
        layoutRow.getStyle().set("width", "30%");
        getContent().add(layoutRow);
        layoutRow.add(layoutColumn2);

        TextField userName = new TextField("Изменить имя");
        TextField about = new TextField("Описание профиля");
        userName.getStyle().set("width","300px");
        userName.setValue(userData.getUsername());
        about.setValue(userData.getDescription());
        about.getStyle().set("width","300px").setHeight("300px");
        about.getStyle().set("height","100px");
        getContent().add(userName);
        getContent().add(about);

        Div colorPickerContainer = new Div();
        colorPickerContainer.setId("color-picker-container");
        colorPickerContainer.getStyle().set("position", "fixed");
        colorPickerContainer.getStyle().set("top", "50%");
        colorPickerContainer.getStyle().set("left", "50%");
        colorPickerContainer.getStyle().set("transform", "translate(-50%, -50%)");
        colorPickerContainer.getStyle().set("z-index", "1000");
        colorPickerContainer.getStyle().set("display", "none"); // Скрываем пикер по умолчанию

        // Добавляем JavaScript для цветового пикера
        getElement().executeJs("""
            const avatarElement = document.querySelector('vaadin-avatar');
            const colorPickerContainer = document.getElementById('color-picker-container');
            const colorPicker = document.createElement('input');
            colorPicker.type = 'color';
            colorPicker.value = '#ff0000';
            colorPicker.style.position = 'fixed';
            colorPicker.style.top = '50%';
            colorPicker.style.left = '50%';
            colorPicker.style.transform = 'translate(-50%, -50%)';
            colorPicker.style.zIndex = '1000';
            colorPicker.style.display = 'none';
            colorPicker.addEventListener('input', (event) => {
                avatarElement.style.backgroundColor = event.target.value;
                window.selectedColor = event.target.value; // Сохраняем цвет в переменную
                $0.$server.updateSelectedColor(window.selectedColor); // Вызываем серверный метод для обновления цвета
            });
            colorPickerContainer.appendChild(colorPicker);

            avatarElement.addEventListener('click', () => {
                colorPicker.style.display = 'block';
                colorPicker.click();
            });

            colorPicker.addEventListener('change', () => {
                colorPicker.style.display = 'none';
            });

            document.addEventListener('click', (event) => {
                if (!colorPickerContainer.contains(event.target) && event.target !== avatarElement) {
                    colorPicker.style.display = 'none';
                }
            });
        """, getElement());

        layoutColumn2.add(colorPickerContainer);

        addClassName("main-content");

        Button exitButton = new Button("Выйти из аккаунта");
        exitButton.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate(""));
        });
        saveButton.addClickListener(event -> {
            System.out.println(getSelectedColor()+userName.getValue()+about.getValue());
            userData.setAvatar(getSelectedColor());
            userData.setUsername(userName.getValue());
            userData.setDescription(about.getValue());
            boolean isSave = controller.saveUser();
            if(isSave)
                UI.getCurrent().navigate(AccountView.class);
            //saveData();
        });
        exitButton.getStyle().set("background-color", "white");
        exitButton.getStyle().set("color", "red");
        exitButton.getStyle().set("border", "none");

        getContent().add(exitButton);
//        addPost.getStyle().set("border-radius", "100%");
//        addPost.getStyle().set("width", "60px");
//        addPost.getStyle().set("height", "60px");
//        addPost.getStyle().set("font-size", "50px");
//        addPost.getStyle().set("align-items", "center");
//        addPost.getStyle().set("justify-content", "center");

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {

    }
    public class DeviceUtils {
        public static boolean isMobileDevice() {
            VaadinSession session = VaadinSession.getCurrent();
            String userAgent = session.getBrowser().getBrowserApplication();
            return userAgent.contains("Mobile") || userAgent.contains("Android") || userAgent.contains("iPhone");
        }
    }
    // Метод для получения сохраненного цвета
    public String getSelectedColor() {
        return selectedColor;
    }

    // Метод для установки сохраненного цвета
    public void setSelectedColor(String color) {
        this.selectedColor = color;
    }

    // Серверный метод для обновления цвета
    @ClientCallable
    public void updateSelectedColor(String color) {
        setSelectedColor(color);
        System.out.println("Selected color updated to: " + color);
    }
}
