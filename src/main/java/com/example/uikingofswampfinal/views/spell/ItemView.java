package com.example.uikingofswampfinal.views.spell;

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

@PageTitle("Items")
@Route(value = "items", layout = MainLayout.class)
public class ItemView extends Main implements HasComponents, HasStyle {
	
	private OrderedList imageContainer;
	
	public ItemView() {
		constructUI();
		setCardImages();
	}
	
	private void setCardImages() {
		imageContainer.add(new ItemViewCard("Amulet zdrowia",
				"https://i.ibb.co/RyxCHS7/Amulet-zdrowia.png"));
		imageContainer.add(new ItemViewCard("Bomba",
				"https://i.ibb.co/Y2bkfq5/Bomba.png"));
		imageContainer.add(new ItemViewCard("Dekret",
				"https://i.ibb.co/xh3VmC3/Dekret-o-poborze.png"));
		imageContainer.add(new ItemViewCard("Hełm",
				"https://i.ibb.co/3fPf6X9/He-m.png"));
		imageContainer.add(new ItemViewCard("Kamień",
				"https://i.ibb.co/NTtXLhh/Kamie-przeniesienia.png"));
		imageContainer.add(new ItemViewCard("Kamień2",
				"https://i.ibb.co/XxyC6nb/Kamie-ycia.png"));
		imageContainer.add(new ItemViewCard("Konie",
				"https://i.ibb.co/9w5Pp41/Konie.png"));
		imageContainer.add(new ItemViewCard("Pierscien",
				"https://i.ibb.co/4KW0hrq/Magiczny-pier-cie.png"));
		imageContainer.add(new ItemViewCard("Miecz",
				"https://i.ibb.co/rk4f996/Miecz.png"));
		imageContainer.add(new ItemViewCard("Mikstura",
				"https://i.ibb.co/820gKpj/Mikstura-lecznicza.png"));
		imageContainer.add(new ItemViewCard("Noz",
				"https://i.ibb.co/m6v3DJD/N-rytualny.png"));
		imageContainer.add(new ItemViewCard("Pierscien",
				"https://i.ibb.co/xS7BcRh/Pier-cie-ycia.png"));
		imageContainer.add(new ItemViewCard("Przeklety miecz",
				"https://i.ibb.co/BNZ22yt/Przekl-ty-miecz.png"));
		imageContainer.add(new ItemViewCard("Rog wojenny",
				"https://i.ibb.co/D4pD4jQ/R-g-wojenny.png"));
		imageContainer.add(new ItemViewCard("Smocze jajo",
				"https://i.ibb.co/bQCKHwW/Smocze-jajo.png"));
		imageContainer.add(new ItemViewCard("Moneta",
				"https://i.ibb.co/WBC6RGm/Szcz-liwa-moneta.png"));
		imageContainer.add(new ItemViewCard("Sztandar",
				"https://i.ibb.co/rc2N5FS/Sztandar.png"));
		imageContainer.add(new ItemViewCard("Tarcza",
				"https://i.ibb.co/NZ2TtVX/Tarcza.png"));
		imageContainer.add(new ItemViewCard("Topór",
				"https://i.ibb.co/qJGQQbb/Top-r.png"));
		imageContainer.add(new ItemViewCard("Młot",
				"https://i.ibb.co/2Z5ckRQ/Wielki-m-ot.png"));
		imageContainer.add(new ItemViewCard("Ksiega",
				"https://i.ibb.co/PNTrXnt/Zakazana-ksi-ga.png"));
		imageContainer.add(new ItemViewCard("Klepsydra",
				"https://i.ibb.co/n8j7Wg2/Zakl-ta-klepsydra.png"));
		imageContainer.add(new ItemViewCard("Karawasze",
				"https://i.ibb.co/BZWh6Qf/Zakl-te-karwasze.png"));
		imageContainer.add(new ItemViewCard("Zwój",
				"https://i.ibb.co/GxshP68/Zw-j-teleportacji.png"));
	}
	
	private void constructUI() {
		addClassNames("play-view");
		addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);
		
		HorizontalLayout container = new HorizontalLayout();
		container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);
		
		VerticalLayout headerContainer = new VerticalLayout();
		H2 header = new H2("Lista przedmiotow");
		header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
		Paragraph description = new Paragraph("Sprawdz aktualne statystki przedmiotow ponizej!");
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
