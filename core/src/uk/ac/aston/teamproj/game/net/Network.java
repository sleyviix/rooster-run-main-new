package uk.ac.aston.teamproj.game.net;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import uk.ac.aston.teamproj.game.net.packet.ChosenMap;
import uk.ac.aston.teamproj.game.net.packet.Login;
import uk.ac.aston.teamproj.game.net.packet.MovementJump;
import uk.ac.aston.teamproj.game.net.packet.MovementLeft;
import uk.ac.aston.teamproj.game.net.packet.MovementP2Jump;
import uk.ac.aston.teamproj.game.net.packet.MovementP2Left;
import uk.ac.aston.teamproj.game.net.packet.MovementP2Right;
import uk.ac.aston.teamproj.game.net.packet.MovementRight;

public class Network {

	public static final int TCP_PORT = 54555;
	public static final int UDP_PORT = 54556;
	
	public static void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(Login.class);
		kryo.register(MovementJump.class);
		kryo.register(MovementRight.class);
		kryo.register(MovementLeft.class);
		kryo.register(MovementP2Left.class);
		kryo.register(MovementP2Right.class);
		kryo.register(MovementP2Jump.class);
		
		kryo.register(ChosenMap.class);
	}
	
}
