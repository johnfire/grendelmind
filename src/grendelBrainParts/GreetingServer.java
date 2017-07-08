/*
 * Copyright (C) 2017 christopherrehm.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package grendelBrainParts;

/**
 *
 * @author christopherrehm
 */

// File Name GreetingServer.java
import basicstuff.*;
import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/** greeting server contains all the server functionality
 * 
 * @author christopherrehm
 */

public class GreetingServer extends BasicObject  {
    
    allLinkedLists theLinkedListObject = new allLinkedLists();
    int port = 5000; // default
    private final ServerSocketChannel ssc;
    private final Selector selector;
    private final ByteBuffer buf = ByteBuffer.allocate(5000);
    private final ByteBuffer welcomeBuf = ByteBuffer.wrap("blank data for test".getBytes());

    GreetingServer(allLinkedLists myLinkedLists) throws IOException {
         
        theLinkedListObject = myLinkedLists;

        this.ssc = ServerSocketChannel.open();
        this.ssc.socket().bind(new InetSocketAddress(port));
        this.ssc.configureBlocking(false);
        this.selector = Selector.open();
        this.ssc.register(selector, SelectionKey.OP_ACCEPT); 
    }
   
    /**Creates and starts the server socket
     *
     */
    
    @Override
    public void run() {

        try {
            
            this.systemMessage("GrendelGreetingServer starting on port " + this.port);

            Iterator<SelectionKey> iter;
            SelectionKey key;
            while(this.ssc.isOpen()) {
                selector.select();
                iter=this.selector.selectedKeys().iterator();
                while(iter.hasNext()) {
                    key = iter.next();
                    iter.remove();

                    if(key.isAcceptable()) this.handleAccept(key);
                    if(key.isReadable()) this.handleRead(key);
                    if(key.isWritable()) this.handleWrite(key);
                    
                }
            }
        } catch(IOException e) {
            System.out.println("IOException, server of port " +this.port+ " terminating");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GreetingServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    private void handleAccept(SelectionKey key) throws IOException {
	SocketChannel sc = ((ServerSocketChannel) key.channel()).accept();
	String address = (new StringBuilder( sc.socket().getInetAddress().toString() )).append(":").append( sc.socket().getPort() ).toString();
	sc.configureBlocking(false);
	sc.register(selector, SelectionKey.OP_READ, address);
	System.out.println("accepted connection from: "+address);
    }

    private void handleRead(SelectionKey key) throws IOException, ClassNotFoundException {
	SocketChannel ch = (SocketChannel) key.channel();
        try (ObjectInputStream oIS = new <Message> ObjectInputStream(Channels.newInputStream(ch))) {
            while(oIS.available() > 0 ) {
                ch.configureBlocking(true);
                Message queueMessage = (Message) oIS.readObject();
                this.theLinkedListObject.unProcessedMessages.addLast(queueMessage);
                ch.configureBlocking(false);
            }   
        }
    }
    
    private void handleWrite(SelectionKey key) throws IOException{
        SocketChannel ch = (SocketChannel) key.channel();
        try (ObjectOutputStream oOS = new <Message> ObjectOutputStream(Channels.newOutputStream(ch))) {
            while(this.theLinkedListObject.internetMessages.size() > 1) {
                Message tempMessage = this.theLinkedListObject.internetMessages.removeFirst();
                oOS.writeObject(tempMessage);
            }     
        }
    }
}
