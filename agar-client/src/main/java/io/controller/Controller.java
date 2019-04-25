package io.controller;

import java.io.InputStream;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import io.connection.Client;
import io.connection.IObserver;
import io.view.Main;

/**
 * Controller
 */
public class Controller extends Application implements IObserver{

    private Main main;
    private Client client;

    @Override
    public void start(Stage stage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader();
        
        InputStream lobbyViewStream = getClass().getResourceAsStream("../view/lobby.fxml");
        
        AnchorPane pane = loader.load(lobbyViewStream);
        main=new Main();
        main.init(pane);
        main.setPlayButtonListener(e->{
            play();
        });

        
        
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
        
        main.setStage(stage);
    }
    
    private EventHandler<KeyEvent> getGameKeyHandler() {
    	return e->{
    		
    		String c =e.getCharacter();
    		String mov = "";
			switch (c.toLowerCase()) {
			case "w":
				mov="up";
				break;
			case "s":
				mov="down";
				break;
			case "a":
				mov="left";
				break;				
			case "d":
				mov="right";
				break;
			}
			
			if(!mov.isEmpty()) {
				client.move(mov);
			}
			
    		
    	};
    	
		
	}
    
    

    public void play(){
        String userName = main.getNameTxt();
        //Using default client, view the several class constructos
        client = new Client();
        client.setUserName(userName);
        client.setObserver(this);
        try {
            client.connect();
		} catch (Exception e) {
			main.setMessage("No se puede conectar con el servidor");
		}
        
    }

    @Override
    public void callback(){
    	switch (client.getGame().getState()) {
    	case Join:
    		Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
		    		main.hide();	
		    		main.showGameView(client.getGame());
		    		main.getGameView().setKeyHandler(getGameKeyHandler());
		    		main.drawGame();
				}

				
			});
    		break;
		case Playing:
			
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					main.drawGame();					
				}
			});
			
			break;

		case Lost:
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					main.getGameView().hide();
					main.show();
					String message = client.getGame().getState().getMessage();
					main.setMessage(message);
					
				}
			});
			break;

		default:
			
			break;
		}
    }
    
    public static void main(String[] args) {
		launch(args);
	}
}