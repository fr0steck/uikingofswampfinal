package com.example.uikingofswampfinal.views.play;

import com.example.uikingofswampfinal.views.MainLayout;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.html.OrderedList;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;

@PageTitle("Occupations")
@Route(value = "occupations", layout = MainLayout.class)
public class OccupationView extends Main implements HasComponents, HasStyle {
	
	private OrderedList imageContainer;
	
	public OccupationView() {
		constructUI();
		setCardImages();
	}
	
	private void setCardImages() {
		imageContainer.add(new OccupationViewCard("Czarodziej",
				"https://i.ibb.co/ZNxrkLh/czarodziej-karta.png"));
		imageContainer.add(new OccupationViewCard("Kapłan",
				"https://i.ibb.co/kM45f62/Kap-an.png"));
		imageContainer.add(new OccupationViewCard("Łotr",
				"https://i.ibb.co/c30jp64/otrzyk-karta.png"));
		imageContainer.add(new OccupationViewCard("Nekromanta",
				"https://i.ibb.co/j87C8Y5/Nekromanta.png"));
		imageContainer.add(new OccupationViewCard("Paladyn",
				"https://i.ibb.co/2cw1GYy/Paladyn.png"));
		imageContainer.add(new OccupationViewCard("Zwiadowca",
				"https://i.ibb.co/348YNs3/zwiadowca-karta.png"));
		imageContainer.add(new OccupationViewCard("Rycerz",
				"https://i.ibb.co/StzLkYt/rycerz-karta.png"));
	}
	
	private void constructUI() {
		addClassNames("play-view");
		addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);
		
		HorizontalLayout container = new HorizontalLayout();
		container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);
		
		VerticalLayout headerContainer = new VerticalLayout();
		H2 header = new H2("Lista klas");
		header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
		Paragraph description = new Paragraph("Sprawdz statystki klas bohaterow ponizej!");
		description.addClassNames(Margin.Bottom.XLARGE, Margin.Top.NONE, TextColor.SECONDARY);
		headerContainer.add(header, description);
		
		Select<String> sortBy = new Select<>();
		sortBy.setLabel("Sort by");
		sortBy.setItems("Popularity", "Newest first", "Oldest first");
		sortBy.setValue("Popularity");
		
		imageContainer = new OrderedList();
		imageContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);
		
		container.add(headerContainer, sortBy);
		add(container, imageContainer);
		
	}
}
