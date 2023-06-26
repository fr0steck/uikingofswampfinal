package com.example.uikingofswampfinal.views.fight;

import com.example.uikingofswampfinal.data.entity.SamplePlayer;
import com.example.uikingofswampfinal.data.service.SamplePlayerService;
import com.example.uikingofswampfinal.views.MainLayout;
import com.example.uikingofswampfinal.views.createplayers.CreateplayersView;
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

@PageTitle("Fight PVP")
@Route(value = "fight-pvp", layout = MainLayout.class)
public class PvPView extends Composite<VerticalLayout> {
	private final ComboBox<SamplePlayer> playerA = new ComboBox<>("Player A");
	private final ComboBox<SamplePlayer> playerB = new ComboBox<>("Player B");
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

	private double calculateAttackPoints(SamplePlayer player) {
		return player.getTroopsamount().multiply(BigDecimal.valueOf(ATTACK_POINT_OF_TROOPS))
				.add(player.getAttackpoints()).doubleValue();
	}

	private int calculateDestroyedTroops(double attackPoints, double defendingTroops) {
		return (int) Math.round(attackPoints / HEALTH_OF_TROOPS);
	}

	private double calculateHeroAttackPoint(SamplePlayer player) {
		return player.getAttackpoints().doubleValue();
	}


	private void calculateFight(ClickEvent<Button> buttonClickEvent) {
		var playerA = this.playerA.getValue();
		var playerB = this.playerB.getValue();

		var attackPointA = calculateAttackPoints(playerA);
		var attackPointB = calculateAttackPoints(playerB);

		var destroyedTroopsA = calculateDestroyedTroops(attackPointA, playerB.getTroopsamount().doubleValue());
		var destroyedTroopsB = calculateDestroyedTroops(attackPointB, playerA.getTroopsamount().doubleValue());

		var newTroopsAmountA = playerA.getTroopsamount().doubleValue() - destroyedTroopsA;
		var newTroopsAmountB = playerB.getTroopsamount().doubleValue() - destroyedTroopsB;

		if (newTroopsAmountA < 0) {
			newTroopsAmountA = 0;
		}

		if (newTroopsAmountB < 0) {
			newTroopsAmountB = 0;
		}

		Notification.show("Player: " + playerA.getNickname() + " lost troops: " + destroyedTroopsA, 1000 * 10, Notification.Position.MIDDLE);
		Notification.show("Player: " + playerB.getNickname() + " lost troops: " + destroyedTroopsB, 1000 * 10, Notification.Position.MIDDLE);

		playerA.setTroopsamount(BigDecimal.valueOf(newTroopsAmountA));
		playerB.setTroopsamount(BigDecimal.valueOf(newTroopsAmountB));

		if (newTroopsAmountA <= 0) {
			performHeroAttack(playerA, playerB, attackPointB);
		}

		if (newTroopsAmountB <= 0) {
			performHeroAttack(playerB, playerA, attackPointA);
		}

		samplePlayerService.update(playerA);
		samplePlayerService.update(playerB);

		this.playerA.setValue(playerA);
		this.playerB.setValue(playerB);
	}

	private void performHeroAttack(SamplePlayer attackingPlayer, SamplePlayer defendingPlayer, double opponentAttackPoint) {
		var heroAttackPoint = calculateHeroAttackPoint(attackingPlayer);
		var newHeroLivePoint = defendingPlayer.getHealth().doubleValue() - Math.abs(heroAttackPoint - opponentAttackPoint);

		if (newHeroLivePoint <= 0) {
			endGame(defendingPlayer);
			return;
		}

		defendingPlayer.setHealth(BigDecimal.valueOf(newHeroLivePoint));
		Notification.show("Hero attack: " + defendingPlayer.getNickname(), 1000 * 10, Notification.Position.MIDDLE);
	}

	private void endGame(SamplePlayer player) {
		player.setActive(false);
		samplePlayerService.update(player);
		Notification.show("Player: " + player.getNickname() + " has lost the game.", 1000 * 10, Notification.Position.MIDDLE);
	}

}