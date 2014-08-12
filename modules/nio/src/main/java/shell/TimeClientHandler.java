package shell;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * TimeClientHandler
 *
 * @author changming.Y <changming.yang.ah@gmail.com>
 * @since 2014-08-12
 */
public class TimeClientHandler implements Runnable {

    private String host;

    private int port;

    private SocketChannel socketChannel;

    private Selector selector;

    /**
     * Constructor
     *
     * @param host
     * @param port
     */
    public TimeClientHandler(String host, int port){

        this.host = host;
        this.port = port;

        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress(host,port));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {

        


    }



}
