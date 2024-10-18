package com.example.application.views.account;

import com.example.application.views.navbars.desktopNav;
import com.example.application.views.navbars.mobileNav;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextArea;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;


@PageTitle("AccountEdit")
@Menu(icon = "line-awesome/svg/pencil-ruler-solid.svg", order = 5)
@Route(value = "account-edit")

public class AccountEditView extends Composite<VerticalLayout> implements BeforeEnterObserver {

    Avatar avatar = new Avatar();

    public AccountEditView() {
        HorizontalLayout header = getHeader();
        getContent().add(header);

        Div avatarContainer = createAvatarContainer();
        getContent().add(avatarContainer);

        TextField name = new TextField();
        name.setPlaceholder("Имя пользователя");
        name.setWidthFull();
        getContent().add(name);

        TextArea description = new TextArea();
        description.setPlaceholder("Описание профиля");
        description.setWidthFull();
        getContent().add(description);

    }

    private HorizontalLayout getHeader() {
        HorizontalLayout header = new HorizontalLayout();

        header.setWidthFull();

        //RouterLink returnLink = new RouterLink();
        //returnLink.add(VaadinIcon.ARROW_LEFT.create());
        //returnLink.setRoute(AccountView.class);
        //header.add(returnLink);

        Button returnButton = new Button(VaadinIcon.ARROW_LEFT.create());
        returnButton.addClickListener(event -> {
            UI.getCurrent().navigate(AccountView.class);
        });

        returnButton.getStyle().set("margin-top", "0");
        returnButton.getStyle().set("padding-top", "0");

        returnButton.getStyle().set("background", "none");
        returnButton.getStyle().set("border", "none");
        returnButton.getStyle().set("padding", "0");
        returnButton.getStyle().set("font-size", "20px");
        returnButton.getStyle().set("color", "inherit");
        returnButton.getStyle().set("cursor", "pointer");
        returnButton.getStyle().set("text-decoration", "none");
        header.add(returnButton);

        Span text = new Span("Редактирование");
        text.getStyle().set("font-size", "20px");
        header.add(text);

        //RouterLink saveLink = new RouterLink();
        //saveLink.add(VaadinIcon.CHECK.create());
        //saveLink.setRoute(AccountView.class);
        //header.add(saveLink);

        Button saveButton = new Button(VaadinIcon.CHECK.create());
        saveButton.addClickListener(event -> {
            UI.getCurrent().navigate(AccountView.class);
            //saveData();
        });

        saveButton.getStyle().set("margin-top", "0");
        saveButton.getStyle().set("padding-top", "0");

        saveButton.getStyle().set("background", "none");
        saveButton.getStyle().set("border", "none");
        saveButton.getStyle().set("padding", "0");
        saveButton.getStyle().set("font-size", "20px");
        saveButton.getStyle().set("color", "inherit");
        saveButton.getStyle().set("cursor", "pointer");
        saveButton.getStyle().set("text-decoration", "none");
        header.add(saveButton);

        returnButton.getStyle().set("margin-right", "0");
        text.getStyle().set("margin-right", "0");
        saveButton.getStyle().set("margin-left", "auto");

        return header;
    }

    private Div createAvatarContainer() {
        Div avatarContainer = new Div();
        avatarContainer.getStyle().set("position", "relative");
        avatarContainer.getStyle().set("display", "flex");
        avatarContainer.getStyle().set("justify-content", "center");
        avatarContainer.getStyle().set("align-items", "center");

        avatar.setName("Firstname Lastname");
        avatar.setWidth("200px");
        avatar.setHeight("200px");

        avatarContainer.add(avatar);

        Div overlay = new Div();
        overlay.getStyle().set("position", "absolute");
        overlay.getStyle().set("top", "0");
        overlay.getStyle().set("left", "0");
        overlay.getStyle().set("width", "100%");
        overlay.getStyle().set("height", "100%");
        overlay.getStyle().set("background", "rgba(0, 0, 0, 0.5)"); // 50% затемнение
        overlay.getStyle().set("pointer-events", "none"); // Чтобы слой не блокировал клики
        overlay.getStyle().set("border-radius", "50%");

        avatarContainer.add(overlay);

        Button button = new Button(VaadinIcon.PICTURE.create());

        button.getStyle().set("background", "none");
        button.getStyle().set("border", "none");
        button.getStyle().set("padding", "0");
        button.getStyle().set("color", "white");
        button.getStyle().set("font-size", "40px");

        //button.getStyle().set("position", "absolute");
        //button.getStyle().set("top", "50%");
        //button.getStyle().set("left", "50%");
        //button.getStyle().set("transform", "translate(-50%, -50%)");

        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setDropAllowed(false);
        upload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        upload.setMaxFiles(1);
        upload.setUploadButton(button);
        upload.getStyle().set("position", "absolute");

        avatarContainer.add(upload);

        upload.addSucceededListener(event -> {
            InputStream inputStream = null;
            try {
                inputStream = new ByteArrayInputStream(buffer.getInputStream().readAllBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            InputStream finalInputStream = inputStream;
            StreamResource resource = new StreamResource("avatar.png", () -> finalInputStream);
            avatar.setImageResource(resource);
            Notification.show("Image uploaded successfully!");
        });

        upload.addFailedListener(event -> {
            Notification.show("Image upload failed!");
        });

        return avatarContainer;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        boolean isMobile = AccountView.isMobileDevice();
        if (isMobile) {

            getContent().add(new mobileNav());
        } else {
            //h1.getStyle().set("margin-top","5%");
            getContent().add(new desktopNav());
        }
    }
}

