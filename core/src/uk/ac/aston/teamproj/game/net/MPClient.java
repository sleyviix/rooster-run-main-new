package uk.ac.aston.teamproj.game.net;

import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;

import uk.ac.aston.teamproj.game.MainGame;
import uk.ac.aston.teamproj.game.net.packet.ChosenMap;
import uk.ac.aston.teamproj.game.net.packet.Login;
import uk.ac.aston.teamproj.game.net.packet.Movement;
import uk.ac.aston.teamproj.game.screens.LoadingScreen;
import uk.ac.aston.teamproj.game.screens.PlayScreen;

public class MPClient {

	public static Client client;
	public MainGame game;
	private String name;
	
	private String mapPath;

	public MPClient(String ip, String name, MainGame game) {
		this.name = name;
		client = new Client();
		client.start();
		
		Network.register(client);

		

		client.addListener(new ThreadedListener(new Listener() {
			// What to do with the packets.
			public void connected(Connection connection) {

			}

			public void received(Connection connection, Object object) {

				if (object instanceof ChosenMap) {
					ChosenMap packet = (ChosenMap) object;
					mapPath = packet.path;					
				}
				
				if(object instanceof Movement) {
					Movement packet = (Movement) object;
					
					if(packet.clientID == 0) {
						
						Body body = PlayScreen.player.b2body;
						float forceX = 0.8f; // replace with packet.impulse
						float forceY = body.getLinearVelocity().y;
						
						switch(packet.direction) {
						case 0:
							forceX *= -1;
							break;
						case 1:
							forceY = 3f;
							forceX = body.getLinearVelocity().x;
							break;
						case 2:
							break;
						}
	                    body.setLinearVelocity(forceX, forceY);
	                    
					} else {
						
						Body body = PlayScreen.player2.b2body;
						float forceX = 0.8f; // replace with packet.impulse
						float forceY = body.getLinearVelocity().y;
						
						switch(packet.direction) {
						case 0:
							forceX *= -1;
							break;
						case 1:
							forceY = 3f;
							forceX = body.getLinearVelocity().x;
							break;
						case 2:
							break;
						}
	                    body.setLinearVelocity(forceX, forceY);

					}
				}
				
			}

		}));
		
		try {
			client.connect(50000, ip, Network.TCP_PORT, Network.UDP_PORT);
			requestLogin();
			//TimeUnit.SECONDS.sleep(10);
			game.setScreen(new LoadingScreen(game, client.getID(), mapPath));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void requestLogin() {
		Login login = new Login();
		login.name = name;
		login.id = client.getID();
		client.sendTCP(login);
	}

}
