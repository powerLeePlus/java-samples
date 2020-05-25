import java.net.InetSocketAddress;
import java.util.List;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * @author lwq
 * @date 2020/4/16 0016
 */
public class CanalClient {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        initConnector("example");

    }

    public static void  initConnector(String destination) throws InvalidProtocolBufferException {
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("172.16.20.220", 11111), destination, "", "");
        try {
            connector.connect();
            connector.subscribe(".*\\..*");
            connector.rollback();
            System.out.println("canal 连接成功， instance: " + destination);
            System.out.println("--------------------------------------------");
            while (true) {
                Message message = connector.getWithoutAck(1000); // 获取指定数量的数据
                if(message.getId() != -1 && message.getEntries().size() > 0) {
                    printEntry(message.getEntries());
                }
                connector.ack(message.getId()); // 提交确认，消费成功，通知server删除数据
                //connector.rollback(message.getId());// 处理失败, 回滚数据，后续重新获取数据
            }
        } finally {
            connector.disconnect();
        }
    }

    public static void printEntry(List<Entry> entries) throws InvalidProtocolBufferException {
        for (Entry entry : entries) {
            System.out.println("entryType === " + entry.getEntryType().name());
            System.out.println("--------------------------------------------");

            if(entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }

            System.out.println("entry.toString === " + entry.toString());
            System.out.println("--------------------------------------------");

            CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            System.out.println("rowChange.toString === " + rowChange.toString());
            System.out.println("--------------------------------------------");

            System.out.println("hasSql??? === " + rowChange.hasSql());
            System.out.println("rowChange.getSql() === " + rowChange.getSql());
            System.out.println("--------------------------------------------");

            for (RowData rowData : rowChange.getRowDatasList()) {
                switch (rowChange.getEventType()) {
                    case INSERT:
                        System.out.println("INSERT ");
                        printColumns(rowData.getAfterColumnsList());
                        break;
                    case UPDATE:
                        System.out.println("UPDATE ");
                        printColumns(rowData.getAfterColumnsList());
                        break;
                    case DELETE:
                        System.out.println("DELETE ");
                        printColumns(rowData.getBeforeColumnsList());
                        break;

                    default:
                        break;
                }
                System.out.println("--------------------------------------------");

            }

        }
    }

    private static void printColumns(List<Column> columns) {
        for(Column column : columns) {
            System.out.println("行 =>name:value: === " + column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
            //System.out.println(column.toString());
        }
    }
}
