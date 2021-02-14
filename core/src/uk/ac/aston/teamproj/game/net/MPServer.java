package uk.ac.aston.teamproj.game.net;

import java.io.IOException;
import java.util.ArrayList;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import uk.ac.aston.teamproj.game.net.packet.Login;
import uk.ac.aston.teamproj.game.net.packet.MovementJump;
import uk.ac.aston.teamproj.game.net.packet.MovementLeft;
import uk.ac.aston.teamproj.game.net.packet.MovementP2Jump;
import uk.ac.aston.teamproj.game.net.packet.MovementP2Left;
import uk.ac.aston.teamproj.game.net.packet.MovementP2Right;
import uk.ac.aston.teamproj.game.net.packet.MovementRight;


public class MPServer {
	
	public static Server server;
	public static ArrayList<Integer> playerCount;
	public static boolean online;
	public static float impulse;
	public static float impulse2;
	public static float timer;
	
	public static final float DEFAULT_SPEED = 0.8f;
	public static final float SLOW_SPEED = 0.4f;
	public static final float FAST_SPEED = 1.6f;
	/**
	 * Constructor
	 * @param args
	 */
	public MPServer() throws IOException {
		playerCount = new ArrayList<>();
		timer = 0;
		playerCount.add(1);
		playerCount.add(2);
		server = new Server() {
			protected Connection newConnection() {
				return new PlayerConnection();
			}
		};
		online = true;
		impulse = DEFAULT_SPEED;
		impulse2 = DEFAULT_SPEED;
		
		Network.register(server);

		server.addListener(new Listener() {

			public void received(Connection c, Object object) {
				
				PlayerConnection connection = (PlayerConnection) c;
				
				if(object instanceof Login) {
					
					/*
					 * Please keep in mind we are assuming two player's here hence we are adding the integer
					 * values 1 and 2 prematurely. In the future we should be parsing in client.getID(). 
					 * 
					 * For now we avoided a NullPointerException. 
					 * Consult Chanveer if changing this method.
					 */
					
					Login packet = (Login) object;
					System.out.println("[" + packet.id + "] " + packet.name + " has entered the game.");
				}
				
				if(object instanceof MovementJump) {
					MovementJump pos = (MovementJump) object;
					server.sendToAllTCP(pos);
				}
				
				if(object instanceof MovementLeft) {
					MovementLeft pos = (MovementLeft) object;
					pos.impulse = impulse;
					server.sendToAllTCP(pos);
				}
				
				if(object instanceof MovementRight) {
					MovementRight pos = (MovementRight) object;
					pos.impulse = impulse;
					server.sendToAllTCP(pos);
				}
				
				if(object instanceof MovementP2Jump) {
					MovementP2Jump pos = (MovementP2Jump) object;
					server.sendToAllTCP(pos);
				}
				
				if(object instanceof MovementP2Right) {
					MovementP2Right pos = (MovementP2Right) object;
					pos.impulse = impulse2;
					server.sendToAllTCP(pos);
				}
				
				if(object instanceof MovementP2Left) {
					MovementP2Left pos = (MovementP2Left) object;
					pos.impulse = impulse2;
					server.sendToAllTCP(pos);
				}
			}
			
		});
		
		server.bind(Network.TCP_PORT, Network.UDP_PORT);
		server.start();
	}
	
	public static class PlayerConnection extends Connection {
		public String name;
	}
}
