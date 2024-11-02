package com.example.application.views.main;

import com.example.application.localdata.Post;
import com.example.application.localdata.UserData;
import com.example.application.views.navbars.desktopNav;
import com.example.application.views.navbars.mobileNav;
import com.example.application.views.posts.PostView;
import com.example.application.views.posts.PostsView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.servlet.http.Cookie;

import java.sql.SQLException;

@PageTitle("Main")
@Menu(icon = "line-awesome/svg/pencil-ruler-solid.svg", order = 0)
@Route(value = "main")
public class MainView extends AppLayout implements BeforeEnterObserver {

    TabSheet tabSheet = new TabSheet();
    HorizontalLayout Navbar;

    public MainView() throws SQLException {
        UserData userData = loadUserDataFromCookies();
        VerticalLayout content = CreateContent();
        content.setSizeFull();
        setContent(content);

        CreateNavbar();
    }

    private VerticalLayout CreateContent() throws SQLException {
        VerticalLayout content = new VerticalLayout();

        setTabSheetSampleData();
        content.add(tabSheet);

        tabSheet.setSizeFull();
        tabSheet.getStyle().set("margin-left", "0").set("padding-left","0");
        tabSheet.addClassName("left-aligned");

        Button addPost = new Button(VaadinIcon.PLUS.create());
        addPost.addClickListener(event -> {
            Post post = new Post(0,"","");
            VaadinSession.getCurrent().setAttribute("post", post);
            getUI().ifPresent(ui -> ui.navigate("page-edit"));
        });

        addPost.getStyle().set("position", "fixed");
        addPost.getStyle().set("bottom", "80px");
        addPost.getStyle().set("right", "30px");
        addPost.getStyle().set("z-index", "1000");
        addPost.getStyle().set("background-color", "#e35d84");
        addPost.getStyle().set("color", "white");
        addPost.getStyle().set("border", "none");
        addPost.getStyle().set("border-radius", "100%");
        addPost.getStyle().set("width", "60px");
        addPost.getStyle().set("height", "60px");
        addPost.getStyle().set("font-size", "50px");
        addPost.getStyle().set("align-items", "center");
        addPost.getStyle().set("justify-content", "center");

        content.add(addPost);

        return content;
    }

    private void setTabSheetSampleData() throws SQLException {
        PostsView post = new PostsView("post-open");
       // AccountView ac = new AccountView();
        PostView post2 = new PostView();
        PostView post3 = new PostView();
        PostView post4 = new PostView();
        PostView post5 = new PostView();
        PostView post6 = new PostView();

        post.getContent().setHeight("100%");

        VerticalLayout layout = new VerticalLayout(post2,post3,post4,post5,post6);
        layout.setSizeFull();

        tabSheet.add("Популярное", post);
        tabSheet.add("Вопрос/ответ", layout);
    }

    public void CreateNavbar(){
        boolean isMobile = isMobileDevice();
        if (isMobile) {
            tabSheet.setWidth("100%");
            tabSheet.getStyle().set("margin-left", "0").set("padding-left","0");
            tabSheet.addClassName("left-aligned");

            Navbar = new mobileNav();
            addToNavbar(true, Navbar);

        } else {
            tabSheet.getStyle().set("margin-top", "0px");
            tabSheet.setWidth("1000px");
            tabSheet.getStyle().set("margin-left", "10px");
            tabSheet.getStyle().set("margin-right", "auto");

            DrawerToggle toggle = new DrawerToggle();

            H1 title = new H1("ucheba");
            title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                    .set("margin", "0");

            Navbar = new desktopNav();

            Scroller scroller = new Scroller(Navbar);
            scroller.setClassName(LumoUtility.Padding.SMALL);

            addToDrawer(scroller);
            addToNavbar(toggle, title);
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

    private UserData loadUserDataFromCookies() {
        VaadinRequest request = VaadinService.getCurrentRequest();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {

            String username = getCookieValue(cookies, "username");
            String email = getCookieValue(cookies, "email");
            String avatar = getCookieValue(cookies, "avatar");
            String description = getCookieValue(cookies, "description");

            if (username != null && email != null) {
                return new UserData(1,username, email, description, avatar);
            }
        }
        return null;
    }

    private String getCookieValue(Cookie[] cookies, String name) {
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}


