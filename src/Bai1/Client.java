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
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author thang
 */
public class Client {
    private DatagramSocket socket;
    private DatagramPacket sendPacket;
    private DatagramPacket recvPacket;
    private String serverIP = "localhost";
    private int serverPort = 5678;

    public Client() {
        try {
            this.socket = new DatagramSocket();
        } catch (SocketException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String initCommuication() {
        try {
            String sendStr = ";B16DCCN317;100";
            byte[] bytes = sendStr.getBytes();
            sendPacket = new DatagramPacket(bytes, bytes.length,
                    InetAddress.getByName(serverIP), serverPort);
            byte[] recvBuffer = new byte[1024];
            recvPacket = new DatagramPacket(recvBuffer, recvBuffer.length);
                    
            socket.send(sendPacket);
            socket.receive(recvPacket);
            
            return new String(recvPacket.getData()).trim();
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    
    public void send(int a, int b, int requestID) {
        try {
            String sendStr = String.format("%d;%d", requestID, a*b);
            byte[] bytes = sendStr.getBytes();
            sendPacket = new DatagramPacket(bytes, bytes.length,
                    InetAddress.getByName(serverIP), serverPort);
            
            socket.send(sendPacket);
            socket.close();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void main(String[] args) {
        Client client = new Client();
        String init = client.initCommuication();
        String[] params = init.split(";");
        
        client.send(Integer.parseInt(params[1]), Integer.parseInt(params[2])
                , Integer.parseInt(params[0])); 
    }
            
}
