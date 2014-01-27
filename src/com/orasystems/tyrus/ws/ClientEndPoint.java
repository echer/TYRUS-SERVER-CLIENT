package com.orasystems.tyrus.ws;

import java.io.IOException;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@ClientEndpoint()
public class ClientEndPoint {

	@OnOpen
	public void onOpen(Session p) {
		try {
			p.getBasicRemote().sendText("Hello!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@OnMessage
	public void onMessage(String message) {
		System.out.println(String
				.format("%s %s", "Received message: ", message));
	}
}