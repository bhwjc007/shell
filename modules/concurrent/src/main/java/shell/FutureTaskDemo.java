package shell;

import java.sql.Connection;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.FutureTask;

/**
 * FutureTaskDemo
 * Not create duplicately connection again and again
 *
 * @author changming.Y <changming.yang.ah@gmail.com>
 * @since 2014-07-14
 */
public class FutureTaskDemo {

    private ConcurrentMap<String,FutureTask<Connection>> connectionPool = new ConcurrentHashMap<String, FutureTask<Connection>>();

    private Connection connection = null;

    private FutureTask<Connection> connectionTask;


    public Connection getConnection(String key) throws Exception{

        if(connectionPool.containsKey(key)){

            connectionTask = connectionPool.get(key);

            if(connectionTask!=null){
                return connectionTask.get();
            }

        }else {

            Callable<Connection> callable = new Callable<Connection>() {
                @Override
                public Connection call() throws Exception {
                    System.out.println("create connection ........");
                    return null;
                }
            };

            FutureTask<Connection> newConnectionTask = new FutureTask<Connection>(callable);
            //important this. It is must be returnning previous value associated with the specified key.
            connectionTask = connectionPool.putIfAbsent(key,newConnectionTask);

            //previous value associated with the key is null
            if(connectionTask==null){
                connectionTask = newConnectionTask;
                connectionTask.run();
            }
            return connectionTask.get();
        }
        return connectionTask.get();
    }

}
