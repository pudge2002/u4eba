package com.example.application.views.posts;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;

import java.util.Arrays;
import java.util.List;

@PageTitle("Posts")
@Menu(icon = "line-awesome/svg/list-solid.svg", order = 4)
@Route(value = "posts")
public class PostsView extends Composite<VerticalLayout> {

    Grid<Person> grid = new Grid<>();

    public PostsView() {

        afterNavigation();

        addClassName("posts-view");
        getContent().setSizeFull();

        getStyle().set("margin", "0");
        getStyle().set("padding", "0");
        getContent().setMinHeight("100px");

        grid.setHeight("100%");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        grid.addComponentColumn(person -> createCard(person));

        grid.getStyle().set("margin", "0");
        grid.getStyle().set("padding", "0");
        grid.getStyle().set("background-color", "#E6E9ED");

        grid.addItemClickListener(event -> {
            Person person = event.getItem();
            VaadinSession.getCurrent().setAttribute("person", person);
            getUI().ifPresent(ui -> ui.navigate("page-edit"));
        });

        getContent().add(grid);
    }

    private HorizontalLayout createCard(Person person) {
        HorizontalLayout card = new HorizontalLayout();
        card.addClassName("card");
        card.setSpacing(false);
        card.getThemeList().add("spacing-s");

        Image image = new Image();
        image.setSrc(person.getImage());
        VerticalLayout description = new VerticalLayout();
        description.addClassName("description");
        description.setSpacing(false);
        description.setPadding(false);

        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("header");
        header.setSpacing(false);
        header.getThemeList().add("spacing-s");

        Span name = new Span(person.getName());
        name.addClassName("name");
        Span date = new Span(person.getDate());
        date.addClassName("date");
        header.add(name, date);

        Span post = new Span(person.getPost());
        post.addClassName("post");

//        HorizontalLayout actions = new HorizontalLayout();
//        actions.addClassName("actions");
//        actions.setSpacing(false);
//        actions.getThemeList().add("spacing-s");
//
//        Icon likeIcon = VaadinIcon.HEART.create();
//        likeIcon.addClassName("icon");
//        Span likes = new Span(person.getLikes());
//        likes.addClassName("likes");
//        Icon commentIcon = VaadinIcon.COMMENT.create();
//        commentIcon.addClassName("icon");
//        Span comments = new Span(person.getComments());
//        comments.addClassName("comments");
//        Icon shareIcon = VaadinIcon.CONNECT.create();
//        shareIcon.addClassName("icon");
//        Span shares = new Span(person.getShares());
//        shares.addClassName("shares");
//
//        actions.add(likeIcon, likes, commentIcon, comments, shareIcon, shares);

        description.add(header, post);
        card.add(image, description);
        return card;
    }


    public void afterNavigation() {

        // Set some data when this view is displayed.
        List<Person> persons = Arrays.asList( //
                new Person("https://randomuser.me/api/portraits/men/42.jpg", "John Smith", "May 8",
                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
                        "1K", "500", "20"),
                new Person("https://randomuser.me/api/portraits/women/42.jpg", "Abagail Libbie", "May 3",
                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
                        "1K", "500", "20"),
                new Person("https://randomuser.me/api/portraits/men/24.jpg", "Alberto Raya", "May 3",
                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
                        "1K", "500", "20"),
                new Person("https://randomuser.me/api/portraits/men/24.jpg", "Alberto Raya", "May 3",
                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
                        "1K", "500", "20"),
                new Person("https://randomuser.me/api/portraits/men/24.jpg", "Alberto Raya", "May 3",
                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
                        "1K", "500", "20"),
                new Person("https://randomuser.me/api/portraits/men/24.jpg", "Alberto Raya", "May 3",
                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
                        "1K", "500", "20"),
                new Person("https://randomuser.me/api/portraits/men/24.jpg", "Alberto Raya", "May 3",
                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
                        "1K", "500", "20")
        );

        grid.setItems(persons);
    }
}
