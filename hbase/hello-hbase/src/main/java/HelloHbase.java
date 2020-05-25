import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lwq
 * @date 2020/4/15 0015
 */
@Slf4j
public class HelloHbase {

    public static Configuration configuration; //管理hbase的配置信息

    public static Connection connection; //管理hbase连接

    public static Admin admin; //管理hbase数据库的信息

    static {
        configuration = HBaseConfiguration.create();
        //configuration.set("hbase.zookeeper.property.clientPort", "2181");
        configuration.set("hbase.zookeeper.quorum", "172.16.20.220");
        //configuration.set("hbase.master", "172.16.20.220:16000");

        try {
            connection = ConnectionFactory.createConnection(configuration);
            admin = connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        String tableName = "user3";
        String rowkey = "zhangsan";
        String columnFamily1 = "info";
        String columnFamily2 = "extra";
        String columnFamily1_column1 = "name";
        String columnFamily1_column2 = "age";
        String columnFamily1_column3 = "sex";
        String columnFamily1_column1_value = "zhangsan";
        String columnFamily1_column2_value = "22";
        String columnFamily1_column3_value = "man";

        //BasicConfigurator.configure(); //自动快速地使用缺省Log4j环境。
        /*System.out.println("连接关闭？： " + connection.isClosed());
        System.out.println("表user是否存在？： " + tableExist(tableName));
        createTable(tableName, "info", "extra");
        System.out.println("创建表：" + tableName);
        System.out.println("表" + tableName + "是否存在？： " + tableExist(tableName));*/

        //list();

        //putOne(tableName, rowkey, columnFamily1, columnFamily1_column2, columnFamily1_column2_value);

        //get(tableName, rowkey);

        //scanTable(tableName);

        //singColumnFilter(tableName, columnFamily1, columnFamily1_column1, "zhangsan");

        //rowkeyFilter(tableName);

        //columnPrefixFilter(tableName);

        //filterSet(tableName, columnFamily1, columnFamily1_column1, columnFamily1_column1_value);

        //putMany(tableName, rowkey, columnFamily1, columnFamily1_column1, columnFamily1_column1_value);

        //putSingleRow(tableName, rowkey, columnFamily2, columnFamily1_column2, columnFamily1_column2_value);

        //update(tableName, rowkey, columnFamily2);

        delete(tableName, rowkey, columnFamily1);
        scanTable(tableName);
    }

    public static void close(Connection conn, Admin admin) throws IOException {
        if(conn != null){
            conn.close();
        }
        if(admin != null){
            admin.close();
        }
    }

    // 判断表是否存在
    public static boolean tableExist(String tableName) throws IOException {
        boolean b = admin.tableExists(TableName.valueOf(tableName));
        return b;
    }

    // 创建表
    public static void createTable(String tableNameStr, String... cfs) throws IOException {
        if(tableExist(tableNameStr)) {
            System.out.println("表已存在");
            return;
        }

        // cfs是列族，官方建议一个表一个，但可以有多个
        // 创建表描述器
        // 2.0.0版本弃用 start
       /* HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableNameStr));
        for (String cf : cfs) {
            // 创建列描述器
            HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(cf);
            // hColumnDescriptor.setMaxVersions(3);//设置版本数
            tableDescriptor.addFamily(hColumnDescriptor);
        }*/
        // 2.0.0版本弃用 end
        // 2.0.2版本以上 start
        TableName tableName = TableName.valueOf(tableNameStr);
        // 表描述器构造
        TableDescriptorBuilder builder = TableDescriptorBuilder.newBuilder(tableName);

        // 针对有多个列族
        for (String cf : cfs) {
            // 列族描述器构造
            ColumnFamilyDescriptorBuilder cfdb = ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes(cf));
            // 获得列族描述器
            ColumnFamilyDescriptor cfd = cfdb.build();
            // 添加列族
            builder.setColumnFamily(cfd);
        }
        // 获得表描述器
        TableDescriptor tableDescriptor = builder.build();

        // 2.0.2版本以上 end

        // 创建表操作
        admin.createTable(tableDescriptor);
    }

    // 获取所有表名 list
    public static void list() throws IOException {
        TableName[] tableNames = admin.listTableNames();
        for (TableName tableName : tableNames) {
            System.out.println(tableName.toString());
        }
    }

    // 单条插入 put
    public static void putOne(String tableNameStr, String rowkey, String columnFamily, String column, String value) throws IOException {
        // new 一个列
        Put put = new Put(Bytes.toBytes(rowkey));
        // 下面三个分别为：列族，列名，列值
        put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value));
        TableName tableName = TableName.valueOf(tableNameStr);
        // 得到table
        Table table = connection.getTable(tableName);
        // 执行插入
        table.put(put);
    }

    // 插入多个列
    public static void putMany(String tableNameStr, String rowkey, String columnFamily, String column, String value) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableNameStr));
        ArrayList<Put> puts = new ArrayList<Put>();
        Put put1 = new Put(Bytes.toBytes(rowkey));
        put1.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes("name"), Bytes.toBytes("lwq"));
        Put put2 = new Put(Bytes.toBytes(rowkey));
        put2.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes("age"), Bytes.toBytes("15"));
        Put put3 = new Put(Bytes.toBytes(rowkey));
        put3.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes("sex"), Bytes.toBytes("women"));

        Put put4 = new Put(Bytes.toBytes(rowkey));
        put4.addColumn(Bytes.toBytes("extra"), Bytes.toBytes("addr"), Bytes.toBytes("bj tz"));

        puts.add(put1);
        puts.add(put2);
        puts.add(put3);
        puts.add(put4);

        table.put(puts);
        table.close();
    }

    // 同一条数据的插入
    public static void putSingleRow(String tableNameStr, String rowkey, String columnFamily, String column, String value) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableNameStr));
        Put put = new Put(Bytes.toBytes(rowkey));
        put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes("name"), Bytes.toBytes("jack"));
        put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes("age"), Bytes.toBytes("40"));
        put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes("weight"), Bytes.toBytes("70kg"));
        put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes("sex"), Bytes.toBytes("男"));

        table.put(put);
        table.close();
    }

    // 数据的更新，hbase对数据只有追加，没有更新，但是查询的时候会把最新的数据（时间戳）返回给我们
    public static void update(String tableNameStr, String rowkey, String columnFamily) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableNameStr));
        Put put = new Put(Bytes.toBytes(rowkey));
        put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes("weight") , Bytes.toBytes("63kg"));
        table.put(put);
        table.close();
    }

    // 删除数据
    public static void delete(String tableNameStr, String rowkey, String columnFamily) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableNameStr));
        // 参数为 rowkey
        //删除一列
        Delete delete = new Delete(Bytes.toBytes(rowkey));
        delete.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes("weight"));

        // 删除多列
        Delete delete2 = new Delete(Bytes.toBytes(rowkey));
        delete2.addColumns(Bytes.toBytes(columnFamily), Bytes.toBytes("age"));
        delete2.addColumns(Bytes.toBytes(columnFamily), Bytes.toBytes("sex"));

        // 删除某一行的列族内容
        Delete delete3 = new Delete(Bytes.toBytes(rowkey));
        delete3.addFamily(Bytes.toBytes("extra"));

        // 删除一整行
        Delete delete4 = new Delete(Bytes.toBytes(rowkey));
        table.delete(delete);
        table.delete(delete2);
        table.delete(delete3);
        table.delete(delete4);
        table.close();
    }

    // 查询 get
    public static void get(String tableNameStr, String rowkey) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableNameStr));
        // 获取一行
        Get get = new Get(Bytes.toBytes(rowkey));
        Result result = table.get(get);
        Cell[] cells = result.rawCells();
        for (Cell cell : cells) {
            System.out.println(Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(),
                    cell.getQualifierLength()) + "::" + Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
            table.close();
        }
    }

    // 全表扫描 scan
    public static void scanTable(String tableNameStr) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableNameStr));
        Scan scan = new Scan();
        //scan.addFamily();
        //scan.addColumn();
        //scan.withStartRow());
        //scan.withStopRow();
        ResultScanner scanner = table.getScanner(scan);
        for (Result result : scanner) {
            String rowkey = Bytes.toString(result.getRow());
            System.out.println("rowkey: " + rowkey);
            Cell[] cells = result.rawCells();
            for (Cell cell : cells) {
                System.out.println(Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(),
                        cell.getQualifierLength()) + "::" + Bytes.toString(cell.getValueArray(), cell.getValueOffset(),
                        cell.getValueLength()));
            }
            System.out.println("------------------------------------------");
        }
    }

    // 过滤器
    // 列值过滤器
    public static void singColumnFilter(String tableNameStr, String columnFamily, String column, String value) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableNameStr));
        Scan scan = new Scan();
        // 下列参数分别为，列族，列名，比较符号，值
        SingleColumnValueFilter singleColumnValueFilter = new SingleColumnValueFilter(Bytes.toBytes(columnFamily),
                Bytes.toBytes(column), CompareOperator.EQUAL, Bytes.toBytes(value));
        scan.setFilter(singleColumnValueFilter);
        ResultScanner scanner = table.getScanner(scan);
        for (Result result : scanner) {
            String rowkey = Bytes.toString(result.getRow());
            System.out.println("rowkey: " + rowkey);
            Cell[] cells = result.rawCells();
            for (Cell cell : cells) {
                System.out.println(Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength())+"::"+
                        Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
            }
            System.out.println("-----------------------------------------");
        }
    }

    // rowkey过滤器
    public static void rowkeyFilter(String tableNameStr) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableNameStr));
        Scan scan = new Scan();
        RowFilter rowFilter = new RowFilter(CompareOperator.EQUAL, new RegexStringComparator("^zhang"));
        scan.setFilter(rowFilter);
        ResultScanner scanner = table.getScanner(scan);
        for (Result result : scanner) {
            String rowkey = Bytes.toString(result.getRow());
            System.out.println("rowkey: " + rowkey);
            Cell[] cells = result.rawCells();
            for (Cell cell : cells) {
                System.out.println(Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength())+"::"+
                        Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
            }
            System.out.println("-----------------------------------------");
        }
    }

    public static void columnPrefixFilter(String tableNameStr) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableNameStr));
        Scan scan = new Scan();
        ColumnPrefixFilter filter = new ColumnPrefixFilter(Bytes.toBytes("ag"));
        scan.setFilter(filter);
        ResultScanner scanner = table.getScanner(scan);
        for (Result result : scanner) {
            String rowkey = Bytes.toString(result.getRow());
            System.out.println("rowkey: " + rowkey);
            Cell[] cells = result.rawCells();
            for (Cell cell : cells) {
                System.out.println(Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength())+"::"+
                        Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
            }
            System.out.println("-----------------------------------------");
        }
    }

    // 过滤器集合
    public static void filterSet(String tableNameStr, String columnFamily, String column, String value) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableNameStr));
        Scan scan = new Scan();
        FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        SingleColumnValueFilter singleColumnValueFilter = new SingleColumnValueFilter(Bytes.toBytes(columnFamily), Bytes.toBytes(column), CompareOperator.EQUAL, Bytes.toBytes(value));
        ColumnPrefixFilter filter = new ColumnPrefixFilter(Bytes.toBytes("na"));
        filterList.addFilter(singleColumnValueFilter);
        filterList.addFilter(filter);

        scan.setFilter(filterList);
        ResultScanner scanner = table.getScanner(scan);
        for (Result result : scanner) {
            String rowkey = Bytes.toString(result.getRow());
            System.out.println("rowkey: " + rowkey);
            Cell[] cells = result.rawCells();
            for (Cell cell : cells) {
                System.out.println(Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength())
                    + "::" + Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength()));
            }
            System.out.println("----------------------------------");

        }
    }

}
