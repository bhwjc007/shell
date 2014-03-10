package shell;

import java.util.*;
import java.util.concurrent.*;


/**
 * <p> 线程池示例 - 获取memcached服务器key </p>
 *
 * @author changming.Y
 * @version 1.0 14-3-10 下午9:16
 */
public class ExecutorUtil {

    public Map<String,Integer> dumps = new HashMap<String,Integer>();
    private static MemCachedClient mcc;
    private MemcachedCache memcachedCache;
    public Map slabs;

    private List<Future> tasks = new ArrayList<Future>();
    private ExecutorService executorService;


    /**
     * Constructor
     *
     * @param mcc   客户端
     */
    public MemcachedKeyUtil(MemcachedCache memcachedCache,MemCachedClient mcc){

        this.memcachedCache = memcachedCache;
        MemcachedKeyUtil.mcc = mcc;
    }


    /**
     * 获取所有缓存服务器的key
     *
     * @param limit
     * @param fast
     * @return
     */
    public Set keySet(int limit,boolean fast){

        int threadPoolSize = 0;
        this.slabs = mcc.statsItems();

        if (slabs != null && slabs.keySet() != null) {
            Iterator itemsItr = slabs.keySet().iterator();

            while(itemsItr.hasNext()) {
                String server = itemsItr.next().toString();
                Map itemNames = (Map) slabs.get(server);
                Iterator itemNameItr = itemNames.keySet().iterator();

                //每个缓存服务器对应的信息
                while(itemNameItr.hasNext()) {
                    String itemName = itemNameItr.next().toString();

                    //itemAtt[0] = itemname | itemAtt[1] = number | itemAtt[2] = field
                    String[] itemAtt = itemName.split(":");
                    //获取cacheDump号
                    if (itemAtt[2].startsWith("number"))
                        dumps.put(itemAtt[1], Integer.parseInt(itemAtt[1]));
                }
            }

            if(dumps!=null && dumps.size()>0){
                threadPoolSize = dumps.values().size();
            }

            //创建固定线程数量的线程池
            executorService = Executors.newFixedThreadPool(threadPoolSize);

            if (!dumps.values().isEmpty()) {
                Iterator<Integer> dumpIter = dumps.values().iterator();

                //遍历所有的cacheDump,每个cacheDump启一个新线程去进行遍历
                while(dumpIter.hasNext()) {
                    int dumpNumber = dumpIter.next();
                    DumpExecutor dumpExecutor = new DumpExecutor(dumpNumber,limit,fast);

                    FutureTask task = new FutureTask(dumpExecutor);
                    tasks.add(task);
                    if(!executorService.isShutdown()){
                        executorService.submit(task);
                    }
                }
            }
        }

        return getResult();
    }


    public Set getResult(){

        Set<String> keys = new HashSet<String>();

        for(Future task : tasks) {
            Set<String> _keys = new HashSet<String>();
            try {
                _keys = (Set)task.get();
                keys.addAll(_keys);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return keys;
    }


    /**
     * 有序的关闭先前提交的所有task
     */
    public void close(){
        executorService.shutdown();
    }


    /**
     * 私有内部类，对cacheDump的原子操作，有返回值
     */
    private class DumpExecutor implements Callable {

        private  int dumpNumber;
        private int limit;
        private boolean fast;

        public DumpExecutor(int dumpNumber,int limit,boolean fast){
            this.dumpNumber = dumpNumber;
            this.limit = limit;
            this.fast = fast;
        }


        public Object call() throws Exception {

            Set<String> keys = new HashSet<String>();
            Map cacheDump = mcc.statsCacheDump(dumpNumber, limit);
            Iterator entryIter = cacheDump.values().iterator();

            while (entryIter.hasNext()) {
                Map items = (Map)entryIter.next();
                Iterator ks = items.keySet().iterator();

                while(ks.hasNext()) {
                    String k = (String)ks.next();
                    try {
                        k = URLDecoder.decode(k, "UTF-8");
                    }catch(Exception ex){
                        //Logger.error(ex);
                    }

                    if (k != null && !k.trim().equals("")) {
                        if (fast)
                            keys.add(k);
                        else
                            //去除已经被移除的keys，速度较慢
                            if (memcachedCache.keyExists(k)) {
                                keys.add(k);
                            }
                    }
                }
            }

            return keys;
        }

    }

}