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
import uk.ac.aston.teamproj.game.net.packet.MovementJump;
import uk.ac.aston.teamproj.game.net.packet.MovementLeft;
import uk.ac.aston.teamproj.game.net.packet.MovementP2Jump;
import uk.ac.aston.teamproj.game.net.packet.MovementP2Left;
import uk.ac.aston.teamproj.game.net.packet.MovementP2Right;
import uk.ac.aston.teamproj.game.net.packet.MovementRight;
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
				
				if (object instanceof MovementJump) {
					MovementJump packet = (MovementJump) object;

					Body body = PlayScreen.player.b2body;
                    body.setLinearVelocity(body.getLinearVelocity().x, 3f);
				}

				if (object instanceof MovementRight) {
					MovementRight packet = (MovementRight) object;

					Body body = PlayScreen.player.b2body;
                    body.setLinearVelocity(packet.impulse, body.getLinearVelocity().y);
				}

				if (object instanceof MovementLeft) {
					MovementLeft packet = (MovementLeft) object;

					Body body = PlayScreen.player.b2body;
                    body.setLinearVelocity(-packet.impulse, body.getLinearVelocity().y);
				}

				if (object instanceof MovementP2Jump) {
					MovementP2Jump packet = (MovementP2Jump) object;
					
					Body body = PlayScreen.player2.b2body;
                    body.setLinearVelocity(body.getLinearVelocity().x, 3f);
				}

				if (object instanceof MovementP2Right) {
					MovementP2Right packet = (MovementP2Right) object;
					
					Body body = PlayScreen.player2.b2body;
                    body.setLinearVelocity(packet.impulse, body.getLinearVelocity().y);
				}

				if (object instanceof MovementP2Left) {
					MovementP2Left packet = (MovementP2Left) object;
					
					Body body = PlayScreen.player2.b2body;
                    body.setLinearVelocity(-packet.impulse, body.getLinearVelocity().y);
				}
			}

		}));
		
		try {
			client.connect(50000, ip, Network.TCP_PORT, Network.UDP_PORT);
			requestLogin();
			TimeUnit.SECONDS.sleep(10);
			game.setScreen(new PlayScreen(game, client.getID(), mapPath));
			
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
