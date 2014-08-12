package shell;

/**
 * TimeClientDemo
 *
 * @author changming.Y <changming.yang.ah@gmail.com>
 * @since 2014-08-09
 */
public class TimeClientDemo {


    public static void main(String[] args) {

        int port = 8080;

        String host = "127.0.0.1";

        if(args!=null && args.length>0) {
            host = (args[0]!=null&&args[0].trim().length()>0) ? args[0] : "127.0.0.1" ;
            port = Integer.valueOf((args[1]!=null&&args[1].trim().length()>0) ? args[1] : "8080");
        }

        TimeClientHandler timeClientHandler = new TimeClientHandler(host,port);
        new Thread(timeClientHandler).start();
    }

}
