package ch4;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.regex.RegexQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;

import java.io.File;

/**
 * @author kevin
 * @version Revision: 1.00 Date: 11-5-28下午2:50
 * @Email liuyuhui007@gmail.com
 */
public class Ch429 {
    //索引存储路径
    public final static String INDEX_STORE_PATH = "D:\\appserver\\workspace\\ideaworkspace\\LuceneStudy\\src\\main\\resources\\indexFileStore";

    public static void main(String[] args) throws Exception {
        Directory directory = new SimpleFSDirectory(new File(INDEX_STORE_PATH));
        IndexWriter writer = new IndexWriter(directory, new StandardAnalyzer(), true);
        writer.setUseCompoundFile(false);
        //创建8个文档
        Document doc1 = new Document();
        Document doc2 = new Document();
        Document doc3 = new Document();
        Field f1 = new Field("url", "http://www.abc.com/product?typeid=1&category=10&item=71", Field.Store.YES, Field.Index.UN_TOKENIZED);
        Field f2 = new Field("url", "http://www.def.com/product/show?typeid=3&category=10&item=58", Field.Store.YES, Field.Index.UN_TOKENIZED);
        Field f3 = new Field("url", "http://www.ghi.com/product/list?category=4&type=10&order=32", Field.Store.YES, Field.Index.UN_TOKENIZED);
        doc1.add(f1);
        doc2.add(f2);
        doc2.add(f3);
        writer.addDocument(doc1);
        writer.addDocument(doc2);
        writer.addDocument(doc3);
        writer.close();

        Directory searchDirectory = new SimpleFSDirectory(new File(INDEX_STORE_PATH));
        //根据索引位置建立 IndexSearcher
        IndexSearcher searcher = new IndexSearcher(searchDirectory, true);
        //创建一个正则表达式，用于匹配所有域名为abc.com的地址
        String regex = "http://[a-z]{1,3}\\.abc\\.com/.*";
        //构建Term
        Term t = new Term("url", regex);
        //创建正则查询
        RegexQuery query = new RegexQuery(t);
        Hits hits = searcher.search(query);

        for (int i = 0; i < hits.length(); i++) {
            System.out.println(hits.doc(i));
        }
    }
}
