package shell;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

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

    private volatile boolean stop;


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
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }


    public void stop(){
        this.stop = true;
    }


    @Override
    public void run() {

        try {
            doConnect();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        while (!stop) {

            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey key = null;

                while(iterator.hasNext()) {
                    key = iterator.next();
                    iterator.remove();
                    handleInput(key);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        if(selector!=null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void handleInput(SelectionKey key) throws IOException {

        if(key.isValid()) {

            SocketChannel socketChannel1 = (SocketChannel)key.channel();

            if(key.isConnectable()){
                if(socketChannel1.finishConnect()) {
                    socketChannel1.register(selector,SelectionKey.OP_READ);
                    doWrite(socketChannel1);
                }else {
                    System.exit(1);
                }
            }

            if(key.isReadable()) {
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = socketChannel1.read(readBuffer);

                if(readBytes>0) {
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes,"UTF-8");
                    System.out.println("Now is : " + body);
                    stop();
                }else if(readBytes<0) {
                    key.cancel();
                    socketChannel1.close();
                }
            }
        }
    }


    private void doConnect() throws IOException{

        if(socketChannel.connect(new InetSocketAddress(host,port))) {
            socketChannel.register(selector, SelectionKey.OP_READ);
            doWrite(socketChannel);
        }else {
            socketChannel.register(selector,SelectionKey.OP_CONNECT);
        }
    }


    private void doWrite(SocketChannel socketChannel) throws IOException{

        byte[] request = "QUERY TIME ORDER".getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(request.length);
        writeBuffer.put(request);
        writeBuffer.flip();
        socketChannel.write(writeBuffer);
        if(!writeBuffer.hasRemaining()) {
            System.out.println("It is succeed to send order to Time server!");
        }
    }

}
