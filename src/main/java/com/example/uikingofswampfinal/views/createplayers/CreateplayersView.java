package com.example.uikingofswampfinal.views.createplayers;

import com.example.uikingofswampfinal.data.entity.SamplePlayer;
import com.example.uikingofswampfinal.data.service.SamplePlayerService;
import com.example.uikingofswampfinal.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import org.springframework.data.domain.PageRequest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.Optional;

@PageTitle("Create players")
@Route(value = "hello/:samplePersonID?/:action?(edit)", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@Uses(Icon.class)
public class CreateplayersView extends Div implements BeforeEnterObserver {
	
	private final String SAMPLEPERSON_ID = "samplePersonID";
	private final String SAMPLEPERSON_EDIT_ROUTE_TEMPLATE = "hello/%s/edit";
	
	private final Grid<SamplePlayer> grid = new Grid<>(SamplePlayer.class, false);
	
	private TextField nickname;
	private TextField occupation;
	private TextField goldamount;
	private TextField troopsamount;
	private TextField movementspeed;
	private TextField attackpoints;
	private TextField defensepoints;
	private TextField health;
	private Checkbox active;
	
	private final Button cancel = new Button("Cancel");
	private final Button save = new Button("Save");
	
	private final BeanValidationBinder<SamplePlayer> binder;
	
	private SamplePlayer samplePlayer;
	
	private final SamplePlayerService samplePlayerService;
	
	public CreateplayersView(SamplePlayerService samplePlayerService) {
		this.samplePlayerService = samplePlayerService;
		addClassNames("createplayers-view");
		
		// Create UI
		SplitLayout splitLayout = new SplitLayout();
		
		createGridLayout(splitLayout);
		createEditorLayout(splitLayout);
		
		add(splitLayout);
		
		// Configure Grid
		grid.addColumn("nickname").setAutoWidth(true);
		grid.addColumn("occupation").setAutoWidth(true);
		grid.addColumn("goldamount").setAutoWidth(true);
		grid.addColumn("troopsamount").setAutoWidth(true);
		grid.addColumn("movementspeed").setAutoWidth(true);
		grid.addColumn("health").setAutoWidth(true);
		grid.addColumn("attackpoints").setAutoWidth(true);
		grid.addColumn("defensepoints").setAutoWidth(true);
		grid.addColumn("active").setAutoWidth(true);
		LitRenderer<SamplePlayer> importantRenderer = LitRenderer.<SamplePlayer>of(
						"<vaadin-icon icon='vaadin:${item.icon}' style='width: var(--lumo-icon-size-s); height: var(--lumo-icon-size-s); color: ${item.color};'></vaadin-icon>")
				.withProperty("icon", important -> important.getActive() ? "check" : "minus").withProperty("color",
						important -> important.getActive()
								? "var(--lumo-primary-text-color)"
								: "var(--lumo-disabled-text-color)");
		
		grid.addColumn(importantRenderer).setHeader("Active").setAutoWidth(true);
		
		grid.setItems(query -> samplePlayerService.list(
						PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
				.stream());
		grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
		
		// when a row is selected or deselected, populate form
		grid.asSingleSelect().addValueChangeListener(event -> {
			if (event.getValue() != null) {
				UI.getCurrent().navigate(String.format(SAMPLEPERSON_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
			} else {
				clearForm();
				UI.getCurrent().navigate(CreateplayersView.class);
			}
		});
		
		// Configure Form
		binder = new BeanValidationBinder<>(SamplePlayer.class);
		
		// Bind fields. This is where you'd define e.g. validation rules
		
		binder.bindInstanceFields(this);
		
		cancel.addClickListener(e -> {
			clearForm();
			refreshGrid();
		});
		
		save.addClickListener(e -> {
			try {
				if (this.samplePlayer == null) {
					this.samplePlayer = new SamplePlayer();
				}
				binder.writeBean(this.samplePlayer);
				samplePlayerService.update(this.samplePlayer);
				clearForm();
				refreshGrid();
				Notification.show("Data updated");
				UI.getCurrent().navigate(CreateplayersView.class);
			} catch (ObjectOptimisticLockingFailureException exception) {
				Notification n = Notification.show(
						"Error updating the data. Somebody else has updated the record while you were making changes.");
				n.setPosition(Position.MIDDLE);
				n.addThemeVariants(NotificationVariant.LUMO_ERROR);
			} catch (ValidationException validationException) {
				Notification.show("Failed to update the data. Check again that all values are valid");
			}
		});
	}
	
	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		Optional<Long> samplePersonId = event.getRouteParameters().get(SAMPLEPERSON_ID).map(Long::parseLong);
		if (samplePersonId.isPresent()) {
			Optional<SamplePlayer> samplePersonFromBackend = samplePlayerService.get(samplePersonId.get());
			if (samplePersonFromBackend.isPresent()) {
				populateForm(samplePersonFromBackend.get());
			} else {
				Notification.show(
						String.format("The requested samplePlayer was not found, ID = %s", samplePersonId.get()), 3000,
						Notification.Position.BOTTOM_START);
				// when a row is selected but the data is no longer available,
				// refresh grid
				refreshGrid();
				event.forwardTo(CreateplayersView.class);
			}
		}
	}
	
	private void createEditorLayout(SplitLayout splitLayout) {
		Div editorLayoutDiv = new Div();
		editorLayoutDiv.setClassName("editor-layout");
		
		Div editorDiv = new Div();
		editorDiv.setClassName("editor");
		editorLayoutDiv.add(editorDiv);
		
		FormLayout formLayout = new FormLayout();
		nickname = new TextField("Nickname");
		occupation = new TextField("Occupation");

//		Select<String> occupation = new Select<>();
//		occupation.setLabel("Choose occupation");
//		occupation.setItems("Mage", "Knight",
//		        "Vampire", "Thief");
//		occupation.setValue("Mage");
		
		
		goldamount = new TextField("Gold amount");
		troopsamount = new TextField("Troops amount");
		movementspeed = new TextField("Movement speed");
		attackpoints = new TextField("Attack points");
		defensepoints = new TextField("Defense points");
		health = new TextField("Health");
		active = new Checkbox("Active");
		formLayout.add(nickname, occupation, goldamount, troopsamount, movementspeed, active, attackpoints, defensepoints, health);
		
		editorDiv.add(formLayout);
		createButtonLayout(editorLayoutDiv);
		
		splitLayout.addToSecondary(editorLayoutDiv);
	}
	
	private void createButtonLayout(Div editorLayoutDiv) {
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setClassName("button-layout");
		cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
		save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		buttonLayout.add(save, cancel);
		editorLayoutDiv.add(buttonLayout);
	}
	
	private void createGridLayout(SplitLayout splitLayout) {
		Div wrapper = new Div();
		wrapper.setClassName("grid-wrapper");
		splitLayout.addToPrimary(wrapper);
		wrapper.add(grid);
	}
	
	private void refreshGrid() {
		grid.select(null);
		grid.getDataProvider().refreshAll();
	}
	
	private void clearForm() {
		populateForm(null);
	}
	
	private void populateForm(SamplePlayer value) {
		this.samplePlayer = value;
		binder.readBean(this.samplePlayer);
		
	}
}
