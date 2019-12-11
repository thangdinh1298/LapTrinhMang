/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bai3;

import UDP.Student;
import UDP.Utils;
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
public class Server {
    private DatagramSocket socket;
    private DatagramPacket sendPacket;
    private DatagramPacket recvPacket;
    private String serverIP = "192.168.0.103";
    private int serverPort = 5678;

    public Server() {
        try {
            socket = new DatagramSocket(serverPort, InetAddress.getByName(serverIP));
        } catch (UnknownHostException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void recvInit(){
        try {
            byte[] bytes = new byte[1024];
            byte[] sendBytes;
            
            recvPacket = new DatagramPacket(bytes, bytes.length);
            socket.receive(recvPacket);
            System.out.println("Received something");
            Student student = Utils.deserialize(recvPacket.getData());
            
            student.setGpa(3.6f);
            sendBytes = Utils.serialize(student);
            
            sendPacket = new DatagramPacket(sendBytes, sendBytes.length, 
                    recvPacket.getAddress(), recvPacket.getPort());
            
            socket.send(sendPacket);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void recvResult(){
        try {
            byte[] recvBytes = new byte[1024];
            
            recvPacket = new DatagramPacket(recvBytes,recvBytes.length);
            socket.receive(recvPacket);
            
            Student student = Utils.deserialize(recvPacket.getData());
            
            System.out.println(student);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        Server server = new Server();
        server.recvInit();
        server.recvResult();
    }
}
