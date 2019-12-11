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
public class Client {
    private DatagramSocket socket;
    private DatagramPacket sendPacket;
    private DatagramPacket recvPacket;
    private String serverIP = "192.168.0.103";
    private int serverPort = 5678;

    public Client() {
        try {
            socket = new DatagramSocket();
        } catch (SocketException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Student initCommunication(){
        Student student = new Student("B16DCCN317");
        
        byte[] bytes = Utils.serialize(student);
        byte[] recvBytes = new byte[1024];
        try {
            sendPacket = new DatagramPacket(bytes, bytes.length,
                    InetAddress.getByName(serverIP), serverPort);
            socket.send(sendPacket);
            
            recvPacket = new DatagramPacket(recvBytes, 1024);
            
            socket.receive(recvPacket);
            student = Utils.deserialize(recvPacket.getData());
            
            System.out.println(student);
            return student;
        } catch (UnknownHostException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void convertScore(Student student){
        float gpa = student.getGpa();
        String letterGPA = null;
        
        if(gpa <= 1.0f && gpa > 0) {
            letterGPA = "F";
        } else if(gpa <= 2.0f && gpa > 1.0f ){
            letterGPA = "D";
        } else if(gpa <= 3.0f && gpa > 2.0f) {
            letterGPA = "C";
        } else if(gpa <= 3.7f && gpa > 3.0f) {
            letterGPA = "B";
        } else {
            letterGPA = "A";
        }
        
        student.setGpaLetter(letterGPA);
    }
    
    public void sendResult(Student student) {
        byte[] bytes = Utils.serialize(student);
        
        try { 
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
        Student student = client.initCommunication();
        client.convertScore(student);
        client.sendResult(student);
    }
}
