package com.orasystems.tyrus.ws;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/teste")
public class ServerEndPoint {
	
	private ConcurrentHashMap<String, Session> connections = new ConcurrentHashMap<String, Session>();
	
	private void enviaMensagemPraTodoMundo(String string) {
		//Set<String> keySet = connections.keySet();
        for (Session nextSession : connections.values()) {
        	if(nextSession.isOpen()){
	            RemoteEndpoint.Basic remote = nextSession.getBasicRemote();
	            try {
	                remote.sendText(string);
	            } catch (IOException ioe) {
	                System.out.println("Error updating a client " + remote + " : " + ioe.getMessage());
	            }
        	}
        }
	}

	@OnOpen
    public void hi(Session remote) {
        System.out.println("Someone connected...ID: "+remote.getId());
        adicionaNaLista(remote.getId(), remote);
    }

    @OnMessage
    public void handleMessage(String message, Session session) {
        System.out.println("Someone sent me this message: " + message);
        try {
            session.getBasicRemote().sendText("Olá você enviou a mensagem: "+message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        enviaMensagemPraTodoMundo(message);
    }
    
    private String adicionaNaLista(String newUsername, Session session) {
        /*if (connections.containsKey(newUsername)) {
            return this.registerNewUsername(newUsername + "1", session);
        }*/

        connections.put(newUsername, session);
        return newUsername;
    }

    @OnClose
    public void bye(Session remote) {
        System.out.println("Someone is disconnecting...");
    }
}
