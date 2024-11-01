package com.example.application.views.posts;

import com.example.application.Model.Controller;
import com.example.application.localdata.Post;
import com.example.application.views.main.MainView;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import java.sql.SQLException;

@PageTitle("Post Open")
@Menu(icon = "line-awesome/svg/edit.svg", order = 1)
@Route("post-open")
public class PostOpenView extends Composite<VerticalLayout> {

    Post post = new Post();
    Span personName = new Span("");
    Controller db = new Controller();

    H2 title = new H2();
    Paragraph text = new Paragraph();

    PostOpenView() throws SQLException {
        getContent().setWidthFull();
        getContent().getStyle().set("overflow-y", "auto");

        HorizontalLayout header = getHeader();
        getContent().add(header);

        title.setWidthFull();
        title.getStyle().set("font-size", "30px");
        // title.getStyle().set("background-color", "#E6E9ED");
        getContent().add(title);

        text.setMinHeight("100px");
        text.setWidthFull();
      //  text.getStyle().set("background-color", "#E6E9ED");
        getContent().add(text);

//        String fileName = "example.txt";
//        String fileContent = "This is an example file.";
//        InputStream inputStream = new ByteArrayInputStream(fileContent.getBytes());
//
//        // Создаем StreamResource для файла
//        StreamResource resource = new StreamResource(fileName, () -> inputStream);
//
//        // Создаем компонент Anchor для скачивания файла
//        Anchor downloadLink = new Anchor(resource, "Download " + fileName);
//
//        // Добавляем компонент Anchor в макет
//        getContent().add(downloadLink);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        post = (Post) VaadinSession.getCurrent().getAttribute("post");

        personName.setText(post.getUserName());
        title.setText(post.getHeading());
        text.setText(post.getContent());
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
            UI.getCurrent().navigate(MainView.class);
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

        returnButton.getStyle().set("margin-right", "0");
        personName.getStyle().set("margin-right", "0");

        return header;
    }
}


