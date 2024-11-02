package com.example.application.views.pageeditor;

import com.example.application.Model.*;
import com.example.application.localdata.Post;
import com.example.application.localdata.UserData;
import com.example.application.views.account.AccountView;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.server.VaadinSession;

import java.sql.SQLException;

@PageTitle("Page Editor")
@Menu(icon = "line-awesome/svg/edit.svg", order = 1)
@Route("page-edit")
public class PageEditorPage extends Composite<VerticalLayout> {

    Post post = new Post();
    UserData userData = VaadinSession.getCurrent().getAttribute(UserData.class);

    Span personName = new Span("");
    Controller db = new Controller();
    TextField title = new TextField();
    TextArea text = new TextArea();

    PageEditorPage() throws SQLException {
        getContent().setWidthFull();
        getContent().getStyle().set("overflow-y", "auto");

        HorizontalLayout header = getHeader();
        getContent().add(header);

        title.setPlaceholder("Заголовок");
        title.setWidthFull();
        title.getStyle().set("font-size", "30px");
        getContent().add(title);

        text.setPlaceholder("Начните писать ваш пост...");
        text.setMinHeight("100px");
        text.setWidthFull();
        //text.setHeight("auto"); // Отключаем фиксированную высоту
        //text.getStyle().set("resize", "none");
        getContent().add(text);

//        MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
//
//        // Создаем компонент загрузки файлов
//        Upload upload = new Upload(buffer);
//        upload.setMaxFiles(10); // Устанавливаем максимальное количество файлов
//        //upload.setAcceptedFileTypes("image/*", ".doc", ".docx", ".pdf"); // Устанавливаем допустимые типы файлов
//
//        getContent().add(upload);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        post = (Post) VaadinSession.getCurrent().getAttribute("post");

        personName.setText(post.getUserName());
        title.setValue(post.getHeading());
        text.setValue(post.getContent());
//        if (person != null) {
//            // Отобразите данные Person в форме редактирования
//            getContent().add(new Div("Editing: " + person.getName()));
//            // Добавьте остальные элементы формы здесь
//        } else {
//            getContent().add(new Div("Person not found"));
//        }
    }

    private HorizontalLayout getHeader() {
        HorizontalLayout header = new HorizontalLayout();

        header.setWidthFull();

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

        personName.getStyle().set("font-size", "20px");
        header.add(personName);

        Button saveButton = new Button(VaadinIcon.CHECK.create());
        saveButton.addClickListener(event -> {
            UI.getCurrent().navigate(AccountView.class);

            Post pt = new Post(userData.getUserId(), text.getValue(), title.getValue());
            db.savePost(pt, null);
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
        personName.getStyle().set("margin-right", "0");
        saveButton.getStyle().set("margin-left", "auto");

        return header;
    }
}



