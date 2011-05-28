package ch4;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;

import java.io.File;

/**
 * @author kevin
 * @version Revision: 1.00 Date: 11-5-28下午2:50
 * @Email liuyuhui007@gmail.com
 */
public class Ch436 {
    //索引存储路径
    public final static String INDEX_STORE_PATH = "D:\\appserver\\workspace\\ideaworkspace\\LuceneStudy\\src\\main\\resources\\indexFileStore";

    public static void main(String[] args) throws Exception {
        Directory directory = new SimpleFSDirectory(new File(INDEX_STORE_PATH));
        IndexWriter writer = new IndexWriter(directory, new StandardAnalyzer(), true);
        writer.setUseCompoundFile(false);
        //创建8个文档
        Document bookdoc1 = new Document();
        Field bookNo = new Field("booknumber", "FB309663100", Field.Store.YES, Field.Index.UN_TOKENIZED);
        Field bookName = new Field("bookname", "钢铁是怎么样练成的", Field.Store.YES, Field.Index.TOKENIZED);
        Field author = new Field("author", "匿名", Field.Store.YES, Field.Index.TOKENIZED);
        Field publishdate = new Field("publishdate", "1970-01-01", Field.Store.YES, Field.Index.UN_TOKENIZED);
        Field bookabstract = new Field("abstract", "钢铁是怎么样炼成的，这部小说非常不错，是前苏联的小说", Field.Store.NO, Field.Index.UN_TOKENIZED);
        Field price = new Field("price", "25.00", Field.Store.YES, Field.Index.UN_TOKENIZED);

        bookdoc1.add(bookNo);
        bookdoc1.add(bookName);
        bookdoc1.add(author);
        bookdoc1.add(publishdate);
        bookdoc1.add(bookabstract);
        bookdoc1.add(price);

        //构建第二个Document对象，书名为"钢铁战士"
        Document bookdoc2 = new Document();
        bookNo = new Field("booknumber", "KM407338593", Field.Store.YES, Field.Index.UN_TOKENIZED);
        bookName = new Field("bookname", "钢铁战士", Field.Store.YES, Field.Index.TOKENIZED);
        author = new Field("author", "匿名", Field.Store.YES, Field.Index.TOKENIZED);
        publishdate = new Field("publishdate", "1970-01-01", Field.Store.YES, Field.Index.UN_TOKENIZED);
        bookabstract = new Field("abstract", "这是一部科幻小说", Field.Store.NO, Field.Index.UN_TOKENIZED);
        price = new Field("price", "28.00", Field.Store.YES, Field.Index.UN_TOKENIZED);

        bookdoc2.add(bookNo);
        bookdoc2.add(bookName);
        bookdoc2.add(author);
        bookdoc2.add(publishdate);
        bookdoc2.add(bookabstract);
        bookdoc2.add(price);

        writer.addDocument(bookdoc1);
        writer.addDocument(bookdoc2);
        writer.close();
        //将两个Document加入到索引中去
        Directory searchDirectory = new SimpleFSDirectory(new File(INDEX_STORE_PATH));
        //根据索引位置建立 IndexSearcher
        IndexSearcher searcher = new IndexSearcher(searchDirectory, true);
        //4个查询字符串
        String queryStr1 = "钢 publishdate:1970-01-01";
        String queryStr2 = "战";
        String queryStr3 = "price:25.00";
        String queryStr4 = "publishdate:1970-01-01";

        //下面构建了QueryParser对象，分别对每个查询字符串进行分析并生成Query对象
        //然后利用IndexSearcher进行查找
        QueryParser parser = new QueryParser("bookname", new StandardAnalyzer());
        parser.setDefaultOperator(QueryParser.AND_OPERATOR);
        Query query = parser.parse(queryStr1);
        System.out.println(query.toString());
        Hits hits = searcher.search(query);
        for (int i = 0; i < hits.length(); i++) {
            System.out.println(hits.doc(i));
        }
        System.out.println("========================================================");
        query = parser.parse(queryStr2);
        System.out.println(query.toString());
        hits = searcher.search(query);
        for (int i = 0; i < hits.length(); i++) {
            System.out.println(hits.doc(i));
        }

        System.out.println("========================================================");
        query = parser.parse(queryStr3);
        System.out.println(query.toString());
        hits = searcher.search(query);
        for (int i = 0; i < hits.length(); i++) {
            System.out.println(hits.doc(i));
        }

        System.out.println("========================================================");
        query = parser.parse(queryStr4);
        System.out.println(query.toString());
        hits = searcher.search(query);
        for (int i = 0; i < hits.length(); i++) {
            System.out.println(hits.doc(i));
        }
    }
}
