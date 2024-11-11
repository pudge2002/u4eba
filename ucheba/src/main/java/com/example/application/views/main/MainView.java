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
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.servlet.http.Cookie;

import java.sql.SQLException;

@PageTitle("Main")
@Menu(icon = "line-awesome/svg/pencil-ruler-solid.svg", order = 0)
@Route(value = "main")
@CssImport("./themes/ucheba-app/styles.css")
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
            Post post = new Post(); // тут поправить !!!
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
        PostsView post2 = new PostsView("post-open");

        tabSheet.add("Популярное", post);
        tabSheet.add("Вопрос/ответ", post2);
    }

    public void CreateNavbar(){
        boolean isMobile = isMobileDevice();
        if (isMobile) {
            tabSheet.setWidthFull();
            tabSheet.getStyle().set("margin-left", "0").set("padding-left","0");
            tabSheet.addClassName("left-aligned");

            Navbar = new mobileNav();
            addToNavbar(true, Navbar);

        } else {
            tabSheet.getStyle().set("margin-top", "0px");
            tabSheet.setWidth("80%");
            tabSheet.getStyle().set("margin-left", "10px");
            tabSheet.getStyle().set("margin-right", "auto");

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


