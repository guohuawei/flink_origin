package yueworld.dataStreamAnddataSetAPI;

import org.apache.flink.streaming.api.functions.source.SourceFunction;
import yueworld.POJO.Item;


/**
 *  自定义实时数据源
 */
public class MyStreamingSource implements SourceFunction<Item> {

    private boolean isRunning = true;

    /**
     * 重写run方法产生一个源源不断的数据源
     * @param sourceContext
     * @throws Exception
     */
    @Override
    public void run(SourceContext<Item> sourceContext) throws Exception {
        while (isRunning){
            Item item = Item.generateItem();
            sourceContext.collect(item);
            // 每秒产生一条数据
            Thread.sleep(1000);
        }
    }

    @Override
    public void cancel() {

        isRunning = false;
    }
}
