package com.example.uikingofswampfinal.views.fight;

import com.example.uikingofswampfinal.data.entity.SamplePlayer;
import com.example.uikingofswampfinal.data.service.SamplePlayerService;
import com.example.uikingofswampfinal.views.MainLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

@PageTitle("Fight PVR")
@Route(value = "fight-pve", layout = MainLayout.class)
public class PvPView extends Composite<VerticalLayout> {
	private final ComboBox<SamplePlayer> playerA = new ComboBox<>("Attacking player.");
	private final ComboBox<SamplePlayer> playerB = new ComboBox<>("Defending player.");
	private SamplePlayerService samplePlayerService;
	
	private final int HEALTH_OF_TROOPS = 2;
	private final int ATTACK_POINT_OF_TROOPS = 4;
	
	public PvPView(SamplePlayerService samplePlayerService) {
		this.samplePlayerService = samplePlayerService;
		Button endOfRoundButton = new Button("Calculate battle turn.");
		endOfRoundButton.addClickListener(
				this::calculateFight
		);
		var q = samplePlayerService.list(Pageable.unpaged()).stream().toList();
		playerA.setItems(q);
		playerA.setItemLabelGenerator(SamplePlayer::getNickname);
		playerB.setItems(q);
		playerB.setItemLabelGenerator(SamplePlayer::getNickname);
		getContent().add(playerA);
		getContent().add(playerB);
		
		getContent().add(endOfRoundButton);
	}
	
	private void calculateFight(ClickEvent<Button> buttonClickEvent) {
		var player_a = this.playerA.getValue();
		var player_b = this.playerB.getValue();
		// TODO: Jakoś zamieniać
		
		var attacking_player = player_a;
		var defending_player = player_b;
		
		var attack_point = attacking_player.getTroopsamount()
				.multiply(BigDecimal.valueOf(ATTACK_POINT_OF_TROOPS))
				.add(attacking_player.getAttackpoints()).doubleValue();
		
		var defending_player_troops = defending_player.getTroopsamount().doubleValue();
		var new_defending_player_troops = defending_player_troops;
		var new_hero_live_point = defending_player.getHealth().doubleValue();
		
		if (defending_player_troops != 0) {
			// Troops attack
			int destroyedTroops = (int) Math.round(attack_point / HEALTH_OF_TROOPS);
			new_defending_player_troops = defending_player_troops - destroyedTroops;
			Notification.show("Player:" + defending_player.getNickname()
							+ ", lost troops: " +
							destroyedTroops,
					1000 * 10, Notification.Position.MIDDLE);
		} else {
			Notification.show("Hero attack:" + defending_player.getNickname(),
					1000 * 10, Notification.Position.MIDDLE);
			//Hero attack
			var hero_live_point = defending_player.getHealth().doubleValue();
			var hero_defend_point = defending_player.getDefensepoints().doubleValue();
			
			new_hero_live_point = hero_live_point - (hero_defend_point - attack_point);
			
			if (new_hero_live_point <= 0) {
				//Die
				Notification.show("Player:" + defending_player.getNickname()
								+ " die",
						1000 * 10, Notification.Position.MIDDLE);
				defending_player.setActive(false);
				
			}
			
		}
		defending_player.setTroopsamount(BigDecimal.valueOf(new_defending_player_troops));
		defending_player.setHealth(BigDecimal.valueOf(new_hero_live_point));
		
		samplePlayerService.update(defending_player);
		playerB.setValue(defending_player);
		
		
	}
}