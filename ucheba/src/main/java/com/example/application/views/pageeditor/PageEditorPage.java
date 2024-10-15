package com.example.application.views.pageeditor;

import com.example.application.views.account.AccountView;
import com.example.application.views.posts.Person;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.server.VaadinSession;


@PageTitle("Page Editor")
@Menu(icon = "line-awesome/svg/edit.svg", order = 1)
@Route("page-edit")
public class PageEditorPage extends Composite<VerticalLayout> {

    Person person = new Person();
    Span personName = new Span("");

    TextField title = new TextField();
    TextArea text = new TextArea();

    PageEditorPage(){
        HorizontalLayout header = getHeader();
        getContent().add(header);
        //getContent().setHeightFull();
        //getContent().getStyle().set("resize", "none");
       // getContent().getStyle().set("overflow", "hidden");

        VerticalLayout postEditing = new VerticalLayout();
        //postEditing.setHeightFull();
        //postEditing.setSpacing(true);
       // postEditing.setHeight("auto");
        postEditing.getStyle().set("overflow-y", "auto");
        //postEditing.getStyle().set("resize", "none");
        //postEditing.getStyle().set("overflow", "hidden");

        title.setPlaceholder("Заголовок");
        title.setWidthFull();
        title.getStyle().set("font-size", "30px");
        postEditing.add(title);

        text.setPlaceholder("Начните писать ваш пост...");
        text.setSizeFull();
        //text.setHeight("auto"); // Отключаем фиксированную высоту
        text.getStyle().set("overflow", "hidden");
        //text.getStyle().set("resize", "none");
        postEditing.add(text);

        //getContent().add(postEditing);

        HorizontalLayout header2 = getHeader();
        getContent().add(header2);

        // Добавляем элементы в основной контейнер
        getContent().add(header, postEditing, header);
        getContent().setFlexGrow(1, postEditing);

        getContent().setSizeFull();
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        person = (Person) VaadinSession.getCurrent().getAttribute("person");

        personName.setText(person.getName());
        title.setValue(person.getDate());
        text.setValue(person.getPost());
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
        personName.getStyle().set("margin-right", "0");
        saveButton.getStyle().set("margin-left", "auto");

        return header;
    }
}




