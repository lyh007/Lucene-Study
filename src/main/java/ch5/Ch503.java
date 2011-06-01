package ch5;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;

import java.io.File;

/**
 * @author kevin
 * @version Revision: 1.00 Date: 11-6-1下午4:18
 * @Email liuyuhui007@gmail.com
 */

/**
 * 文档的得分主要由四部分内容决定,tf(词条频率），idf(反转文档频率),boost(Field的激励因子)和lenghtNorm(长度因子)
 *
 * @see http://baike.baidu.com/view/1228847.htm
 *      使用boost值来改变文档的得分
 */
public class Ch503 {
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

        Field f1 = new Field("bookname", "bc", Field.Store.YES, Field.Index.TOKENIZED);
        Field f2 = new Field("bookname", "ab bc cd", Field.Store.YES, Field.Index.TOKENIZED);
        Field f3 = new Field("bookname", "ab bc cd de ef", Field.Store.YES, Field.Index.TOKENIZED);

        doc1.add(f1);
        doc2.add(f2);
        doc3.add(f3);


        writer.addDocument(doc1);
        writer.addDocument(doc2);
        doc3.setBoost(4f);
        writer.addDocument(doc3);

        writer.close();

        Directory searchDirectory = new SimpleFSDirectory(new File(INDEX_STORE_PATH));
        //根据索引位置建立 IndexSearcher
        IndexSearcher searcher = new IndexSearcher(searchDirectory, true);
        TermQuery q = new TermQuery(new Term("bookname", "bc"));

        //自然排序
        Hits hits = searcher.search(q);
        for (int i = 0; i < hits.length(); i++) {
            Document doc = hits.doc(i);
            System.out.print(doc.get("bookname") + "\t\t");
            System.out.println(hits.score(i));
            System.out.println(searcher.explain(q, hits.id(i)).toString());
        }
    }
}
