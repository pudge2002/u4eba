package com.example.application.views.posts;

import com.example.application.views.navbars.desktopNav;
import com.example.application.views.navbars.mobileNav;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;

@PageTitle("post")
@Menu(icon = "line-awesome/svg/pencil-ruler-solid.svg", order = 0)
@Route(value = "post")
public class PostView extends Composite<VerticalLayout>   {
    Div dv = new Div();
    Paragraph textMedium = new Paragraph();
    public PostView() {
        HorizontalLayout layoutRow = new HorizontalLayout();
        Avatar avatar = new Avatar();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        H3 h3 = new H3();
        H6 h6 = new H6();
        H5 h5 = new H5();
        H2 h2 = new H2();


        layoutRow.setWidthFull();

        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("50%");
        layoutRow.setHeight("min-content");
        layoutRow.setAlignItems(Alignment.CENTER);
        layoutRow.setJustifyContentMode(JustifyContentMode.START);
        layoutRow.getStyle().set("margin", "0px");
        avatar.setName("Firstname Lastname");
        layoutRow.setAlignSelf(FlexComponent.Alignment.CENTER, avatar);
        avatar.setWidth("70px");
        avatar.setHeight("70px");
        layoutColumn2.setHeightFull();
        layoutRow.setFlexGrow(1.0, layoutColumn2);
        layoutRow.getStyle().set("margin-top","0px");
//        layoutColumn2.setWidth("min-content");
        layoutColumn2.setHeight("min-content");
        layoutColumn2.setJustifyContentMode(JustifyContentMode.CENTER);
        layoutColumn2.setAlignItems(Alignment.START);
        layoutColumn2.getStyle().set("margin", "0px");
        layoutRow2.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow2);
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.getStyle().set("flex-grow", "1");
        h3.setText("Heading");
        h3.setWidth("max-content");
        h6.setText("Heading");
        layoutRow2.setAlignSelf(FlexComponent.Alignment.END, h6);
        h6.setWidth("max-content");
        h5.setText("Heading");
        h5.setWidth("max-content");
        h2.setText("Heading");

        h2.setWidth("max-content");
        textMedium.setText(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore " +
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.e" +
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.t dolore magna aliqua.");

        textMedium.getStyle().set("font-size", "var(--lumo-font-size-m)");
        textMedium.setHeight("min-content");
        getContent().setWidth("max-content");

        getContent().setHeight("min-content");
        getContent().setJustifyContentMode(JustifyContentMode.CENTER);
        getContent().setAlignItems(Alignment.CENTER);
        getContent().setFlexGrow(1.0, layoutRow);
        layoutRow.add(avatar);
        layoutRow.add(layoutColumn2);
        layoutColumn2.add(layoutRow2);
        layoutRow2.add(h3);
        layoutRow2.add(h6);
        layoutColumn2.add(h5);



        dv.add(layoutRow);
        dv.add(h2);
        dv.add(textMedium);
        dv.getStyle().set("background-color", "#e9e9e9").set("padding-top", "0px").set("padding-bottom", "0px").set("margin-left","0");
//        getContent().getStyle().set("justify-content", "flex-start");

//        getContent().setAlignSelf(FlexComponent.Alignment.START, h2);

        getContent().add(dv);

        setBorderRadius();
    }

    private void setBorderRadius() {
        boolean isMobile = isMobileDevice();
        if (isMobile) {
            dv.getStyle().set("border-radius", "0").set("padding-left","10px").set("margin-left", "-25%").set("margin-right", "0");
            textMedium.setWidth("100%");
            dv.getStyle().set("width","50vh");

        } else {
            dv.getStyle().set("border-radius", "15px").set("padding-left", "1%").set("margin-left", "-2%");;
            textMedium.setWidth("100%");
            dv.getStyle().set("width","100vh");
        }
    }

    private boolean isMobileDevice() {
        VaadinSession session = VaadinSession.getCurrent();
        String userAgent = session.getBrowser().getBrowserApplication();
        return userAgent.contains("Mobile") || userAgent.contains("Android") || userAgent.contains("iPhone");
    }
}