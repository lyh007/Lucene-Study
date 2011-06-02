package ch5;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;

import java.io.File;

/**
 * @author kevin
 * @version Revision: 1.00 Date: 11-6-1下午4:18
 * @Email liuyuhui007@gmail.com
 */
public class Ch511 {
    //索引存储路径
    public final static String INDEX_STORE_PATH = "D:\\appserver\\workspace\\ideaworkspace\\LuceneStudy\\src\\main\\resources\\indexFileStore";
    public final static int SECURITY_ADVANCED = 0;
    public final static int SECURITY_MIDDLE = 1;
    public final static int SECURITY_NORMAL = 2;

    public static void main(String[] args) throws Exception {
        Directory directory = new SimpleFSDirectory(new File(INDEX_STORE_PATH));
        IndexWriter writer = new IndexWriter(directory, new StandardAnalyzer(), true);
        writer.setUseCompoundFile(false);
        //创建8个文档
        Document doc1 = new Document();
        Field f1 = new Field("booknumber", "0000003", Field.Store.YES, Field.Index.UN_TOKENIZED);
        Field f2 = new Field("bookname", "论宇称非对称模型", Field.Store.YES, Field.Index.TOKENIZED);
        Field f3 = new Field("publishdate", "1999-01-01", Field.Store.YES, Field.Index.UN_TOKENIZED);
        Field f4 = new Field("securitylevel", SECURITY_ADVANCED + "", Field.Store.YES, Field.Index.UN_TOKENIZED);
        doc1.add(f1);
        doc1.add(f2);
        doc1.add(f3);
        doc1.add(f4);

        Document doc2 = new Document();
        f1 = new Field("booknumber", "0000005", Field.Store.YES, Field.Index.UN_TOKENIZED);
        f2 = new Field("bookname", "钢铁战士", Field.Store.YES, Field.Index.TOKENIZED);
        f3 = new Field("publishdate", "1995-07-15", Field.Store.YES, Field.Index.UN_TOKENIZED);
        f4 = new Field("securitylevel", SECURITY_MIDDLE + "", Field.Store.YES, Field.Index.UN_TOKENIZED);
        doc2.add(f1);
        doc2.add(f2);
        doc2.add(f3);
        doc2.add(f4);

        Document doc3 = new Document();
        f1 = new Field("booknumber", "0000001", Field.Store.YES, Field.Index.UN_TOKENIZED);
        f2 = new Field("bookname", "论相对论", Field.Store.YES, Field.Index.TOKENIZED);
        f3 = new Field("publishdate", "1963-02-14", Field.Store.YES, Field.Index.UN_TOKENIZED);
        f4 = new Field("securitylevel", SECURITY_ADVANCED + "", Field.Store.YES, Field.Index.UN_TOKENIZED);
        doc3.add(f1);
        doc3.add(f2);
        doc3.add(f3);
        doc3.add(f4);

        Document doc4 = new Document();
        f1 = new Field("booknumber", "0000006", Field.Store.YES, Field.Index.UN_TOKENIZED);
        f2 = new Field("bookname", "黑猫警长", Field.Store.YES, Field.Index.TOKENIZED);
        f3 = new Field("publishdate", "1988-05-01", Field.Store.YES, Field.Index.UN_TOKENIZED);
        f4 = new Field("securitylevel", SECURITY_NORMAL + "", Field.Store.YES, Field.Index.UN_TOKENIZED);
        doc4.add(f1);
        doc4.add(f2);
        doc4.add(f3);
        doc4.add(f4);

        Document doc5 = new Document();
        f1 = new Field("booknumber", "0000004", Field.Store.YES, Field.Index.UN_TOKENIZED);
        f2 = new Field("bookname", "原子弹的爆破模拟", Field.Store.YES, Field.Index.TOKENIZED);
        f3 = new Field("publishdate", "1959-10-21", Field.Store.YES, Field.Index.UN_TOKENIZED);
        f4 = new Field("securitylevel", SECURITY_ADVANCED + "", Field.Store.YES, Field.Index.UN_TOKENIZED);
        doc5.add(f1);
        doc5.add(f2);
        doc5.add(f3);
        doc5.add(f4);

        Document doc6 = new Document();
        f1 = new Field("booknumber", "0000007", Field.Store.YES, Field.Index.UN_TOKENIZED);
        f2 = new Field("bookname", "钢铁是怎样炼成的", Field.Store.YES, Field.Index.TOKENIZED);
        f3 = new Field("publishdate", "1970-01-11", Field.Store.YES, Field.Index.UN_TOKENIZED);
        f4 = new Field("securitylevel", SECURITY_MIDDLE + "", Field.Store.YES, Field.Index.UN_TOKENIZED);
        doc6.add(f1);
        doc6.add(f2);
        doc6.add(f3);
        doc6.add(f4);

        Document doc7 = new Document();
        f1 = new Field("booknumber", "0000002", Field.Store.YES, Field.Index.UN_TOKENIZED);
        f2 = new Field("bookname", "白毛女", Field.Store.YES, Field.Index.TOKENIZED);
        f3 = new Field("publishdate", "1977-09-07", Field.Store.YES, Field.Index.UN_TOKENIZED);
        f4 = new Field("securitylevel", SECURITY_NORMAL + "", Field.Store.YES, Field.Index.UN_TOKENIZED);
        doc7.add(f1);
        doc7.add(f2);
        doc7.add(f3);
        doc7.add(f4);

        //将书的信息加入索引
        writer.addDocument(doc1);
        writer.addDocument(doc2);
        writer.addDocument(doc3);
        writer.addDocument(doc4);
        writer.addDocument(doc5);
        writer.addDocument(doc6);
        writer.addDocument(doc7);
        writer.close();

        Directory searchDirectory = new SimpleFSDirectory(new File(INDEX_STORE_PATH));
        IndexReader reader = IndexReader.open(searchDirectory, true);
        //打印索引文档信息
        for (int i = 0; i < reader.numDocs(); i++) {
            Document doc = reader.document(i);
            System.out.print("书号：");
            System.out.println(doc.get("booknumber"));
            System.out.print("书名：");
            System.out.println(doc.get("bookname"));
            System.out.print("发布日期：");
            System.out.println(doc.get("publishdate"));
            System.out.print("安全级别：");
            int level = Integer.parseInt(doc.get("securitylevel"));
            switch (level) {
                case SECURITY_ADVANCED:
                    System.out.println("高级");
                    break;
                case SECURITY_MIDDLE:
                    System.out.println("中级");
                    break;
                case SECURITY_NORMAL:
                    System.out.println("一般");
                    break;
            }
            System.out.println("=================================");
        }
    }
}
