package ro.ubb.socket.client.tcp;


import message.Message;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class TcpClient {

    private ExecutorService executorService;

    public TcpClient(ExecutorService executorService) {
        this.executorService = executorService;
    }

    /**
     * Method used to communicate with the server
     * @param request using the Message convention to send information
     * @return (type Message) received information
     */
    public Message sendAndReceive(Message request) {
        try (var socket = new Socket(Message.HOST, Message.PORT);
             var is = socket.getInputStream();
             var os = socket.getOutputStream()) {

            System.out.println("sending request: " + request);
            request.writeTo(os);
            System.out.println("request sent");

            Message response = new Message();
            response.readFrom(is);
            System.out.println("received response: " + response);

            return response;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("exception in send and receive", e);
        }

    }
}
