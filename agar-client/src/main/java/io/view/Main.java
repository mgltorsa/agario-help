package io.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

import io.model.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Main
 */
public class Main {

	private AnchorPane root;

	private TextField nameTxt;

	private Label titleNick;

	private Button playBtn;

	private Stage stage;

	private GameView gameView;

	/**
	 * @return the nameTxt
	 */
	public String getNameTxt() {
		return nameTxt.getText();
	}

	/**
	 * @return the root
	 */
	public AnchorPane getRoot() {
		return root;
	}
	
	public Label getTitleNick() {
		return titleNick;
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(AnchorPane root) {
		this.root = root;
	}

	public void init(AnchorPane root) {
		setRoot(root);

		
		//THE AnchorPane content
		AnchorPane content = (AnchorPane) root.getChildren().get(root.getChildren().size()-1);
		
		for (int i = 0; i < content.getChildren().size(); i++) {
			Node node = content.getChildren().get(i);
			if (node != null && node.getId() != null) {
				if (node.getId().equals("titleNick")) {
					titleNick = (Label) node;
				} else if (node.getId().equals("nameTxt")) {
					nameTxt = (TextField) node;
				} else if (node.getId().equals("playBtn")) {
					playBtn = (Button) node;
				}

			}
		}

		Label notifications = new Label("notifiaciones");

		Font font = new Font(20);
		notifications.setFont(font);
		root.getChildren().add(notifications);

		notifications.setLayoutY(340);
		notifications.setLayoutX(200);
	}

	public void setPlayButtonListener() {
		// playBtn.setOnAction(handler)
	}

	public void setPlayButtonListener(EventHandler<ActionEvent> handler) {
		// TODO Auto-generated method stub
		playBtn.setOnAction(handler);
	}

	public void setMessage(String message) {
		((Label) root.getChildren().get(root.getChildren().size() - 1)).setText(message);

	}

	public void hide() {
		stage.close();
	}

	public void setStage(Stage stage) {
		this.stage = stage;

	}

	public Stage getStage() {
		return stage;
	}

	public void showGameView(Game game) {
		FXMLLoader loader = new FXMLLoader();
		try {

			AnchorPane pane = loader.load(getClass().getResourceAsStream("../view/game.fxml"));
			gameView = new GameView();
			gameView.init(pane, game);
			Stage stage = new Stage();
			Scene scene = new Scene(pane);
			stage.setScene(scene);
			gameView.setStage(stage);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void drawGame() {
		gameView.dibujar();

	}

	public GameView getGameView() {
		// TODO Auto-generated method stub
		return gameView;
	}

	public void show() {
		this.stage.show();

	}

}