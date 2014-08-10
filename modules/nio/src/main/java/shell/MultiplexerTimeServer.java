package shell;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * MultiplexerTimeServer
 *
 * @author changming.Y <changming.yang.ah@gmail.com>
 * @since 2014-08-10
 */
public class MultiplexerTimeServer implements Runnable {

    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    private volatile boolean stop;

    public MultiplexerTimeServer(int port) {

        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port),1024);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println(".............The time server is starting in port : " + port );

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }


    public void stop() {
        this.stop = true;
    }


    @Override
    public void run() {

        while(!stop) {

            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey key = null;

                while(iterator.hasNext()) {
                    key = iterator.next();
                    iterator.remove();

                    doProcess(key);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void doProcess(SelectionKey key) throws IOException {

        if(key.isValid()) {

            if(key.isAcceptable()) {
                ServerSocketChannel serverSocketChannel1 = (ServerSocketChannel)key.channel();
                SocketChannel socketChannel = serverSocketChannel1.accept();
                socketChannel.configureBlocking(false);
                socketChannel.register(selector,SelectionKey.OP_READ);
            }

            

        }
    }

}
