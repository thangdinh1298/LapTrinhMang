package Bai1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thang
 */
public class Server {
    private DatagramSocket socket;
    private DatagramPacket sendPacket;
    private DatagramPacket recvPacket;
    private String serverIP = "localhost";
    private int serverPort = 5678;
    
    public Server(){
        try {
            socket = new DatagramSocket(serverPort, InetAddress.getByName("localhost"));
        } catch (SocketException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void recvInit() {
        try {
            recvPacket = new DatagramPacket(new byte[1024], 1024);
            socket.receive(recvPacket);
            
            String recvStr = new String(recvPacket.getData()).trim();
            System.out.println(recvStr);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void sendParams(){
        try {
            int requestID = 6969;
            int a = 6;
            int b = 9;
            
            String sendStr = String.format("%d;%d;%d", requestID, a, b);
            byte[] bytes = sendStr.getBytes();
            
            sendPacket = new DatagramPacket(bytes, bytes.length, recvPacket.getAddress(),
                    recvPacket.getPort());
            socket.send(sendPacket);
            
            recvPacket = new DatagramPacket(new byte[1024], 1024);
            socket.receive(recvPacket);
            
            System.out.println(new String(recvPacket.getData()).trim());
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void main(String[] args) {
        Server server = new Server();
        server.recvInit();
        server.sendParams();
    }
}
