package shell;

/**
 * TimeServerDemo
 *
 * @author changming.Y <changming.yang.ah@gmail.com>
 * @since 2014-08-09
 */
public class TimeServerDemo {

    public TimeServerDemo(){}

    public static void main(String[] args) {

        int port = 8080;
        if(args!=null && args.length>0) {
            port = Integer.valueOf(args[0]);
        }
    }


}
