package com.example.application.views.posts;

import com.example.application.Model.Controller;
import com.example.application.localdata.Post;
import com.example.application.localdata.Reaction;
import com.example.application.localdata.UserData;
import com.example.application.views.main.MainView;
import com.example.application.views.navbars.desktopNav;
import com.example.application.views.navbars.mobileNav;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinSession;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@PageTitle("Post Open")
@Menu(icon = "line-awesome/svg/edit.svg", order = 1)
@Route("post-open")
public class PostOpenView extends AppLayout implements BeforeEnterObserver {

    Post post = new Post();
    H4 personName = new H4();
    Controller controller = new Controller();
    Controller db = new Controller();
    HorizontalLayout Navbar;
    H2 title = new H2();
    UserData userData;
    Paragraph text = new Paragraph();

    PostOpenView() throws SQLException {
        VerticalLayout content = CreateContent();
        content.setWidthFull();
        content.getStyle().set("overflow-y", "auto");
        setContent(content);
        CreateNavbar();

    }
    private VerticalLayout CreateContent() throws SQLException {
        VerticalLayout content = new VerticalLayout();
        HorizontalLayout header = getHeader();

        title.setWidthFull();
        title.getStyle().set("font-size", "30px");
        // title.getStyle().set("background-color", "#E6E9ED");

        text.setMinHeight("100px");
        text.setWidthFull();
        //  text.getStyle().set("background-color", "#E6E9ED");
        Button like = new Button("Понравилось");
        List<Reaction> filteredPeople = null;
        if( post.getReaction() != null)
            filteredPeople = post.getReaction().stream().filter(p -> p.getUser_id() == userData.getUserId() && p.getPost_id() == post.getId())
                .collect(Collectors.toList());
        if (filteredPeople != null) {
            like.addClassName("post-open-view-button-2");
            like.isDisableOnClick();
        }
        else {
            like.addClassName("post-open-view-button-1");
        }
        like.addClickListener(event -> {
            System.out.println("ahahah");
            Reaction reaction = new Reaction(post.getId(), userData.getUserId());
            controller.saveReaction(reaction);

                like.addClassName("post-open-view-button-2");
                like.isDisableOnClick();



        });
        content.add(header, title, text, like);
        return content;
    }
    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        post = (Post) VaadinSession.getCurrent().getAttribute("post");
        if (post != null) {
            personName.setText(post.getAuthor());
            title.setText(post.getHeading());
            text.setText(post.getContent());
        }
        userData = VaadinSession.getCurrent().getAttribute(UserData.class);
//        personName.setText(post.getAuthor());
//        title.setText(post.getHeading());
//        text.setText(post.getContent());
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
        Avatar avatar = new Avatar();
        avatar.setName(post.getAuthor());
        avatar.getStyle().set("background-color", post.getAvatar());
        header.add(avatar,personName);

        returnButton.getStyle().set("margin-right", "0");
        personName.getStyle().set("margin-right", "0");

        return header;
    }
    public void CreateNavbar(){
        boolean isMobile = isMobileDevice();
        if (isMobile) {


        } else {


            DrawerToggle toggle = new DrawerToggle();


            StreamResource image = new StreamResource("logo3.png",
                    () -> getClass().getResourceAsStream("/images/logo3.png"));
            Image logo = new Image(image,"ucheba logo");
            logo.setWidth("40px");
            logo.setHeight("40px");
            logo.getStyle().set("margin-left", "13%");
//            title.getStyle().set("font-size", "var(--lumo-font-size-xl)");

            desktopNav Navbar = new desktopNav();


            Scroller scroller = new Scroller(Navbar);
//            scroller.setClassName(LumoUtility.Padding.XLARGE);

            setDrawerOpened(true);
            addToDrawer(scroller);
            addToNavbar(logo);
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {

    }

    private boolean isMobileDevice() {
        VaadinSession session = VaadinSession.getCurrent();
        String userAgent = session.getBrowser().getBrowserApplication();
        return userAgent.contains("Mobile") || userAgent.contains("Android") || userAgent.contains("iPhone");
    }
}


