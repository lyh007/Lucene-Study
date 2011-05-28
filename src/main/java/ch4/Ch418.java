package ch4;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MultiPhraseQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;

import java.io.File;

/**
 * @author kevin
 * @version Revision: 1.00 Date: 11-5-26下午5:19
 * @Email liuyuhui007@gmail.com
 */
public class Ch418 {
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
        Document doc4 = new Document();
        Document doc5 = new Document();
        Document doc6 = new Document();

        Field f1 = new Field("bookname", "钢铁是怎样炼成的", Field.Store.YES, Field.Index.TOKENIZED);
        Field f2 = new Field("bookname", "钢铁战士", Field.Store.YES, Field.Index.TOKENIZED);
        Field f3 = new Field("bookname", "钢和铁是两种金属元素", Field.Store.YES, Field.Index.TOKENIZED);
        Field f4 = new Field("bookname", "钢要比铁有更多的碳元素", Field.Store.YES, Field.Index.TOKENIZED);
        Field f5 = new Field("bookname", "钢和铁是两种重要的金属", Field.Store.YES, Field.Index.TOKENIZED);
        Field f6 = new Field("bookname", "铁钢是两种重要的金属", Field.Store.YES, Field.Index.TOKENIZED);

        doc1.add(f1);
        doc2.add(f2);
        doc3.add(f3);
        doc4.add(f4);
        doc5.add(f5);
        doc6.add(f6);


        writer.addDocument(doc1);
        writer.addDocument(doc2);
        writer.addDocument(doc3);
        writer.addDocument(doc4);
        writer.addDocument(doc5);
        writer.addDocument(doc6);

        writer.close();

        Directory searchDirectory = new SimpleFSDirectory(new File(INDEX_STORE_PATH));
        //根据索引位置建立 IndexSearcher
        IndexSearcher searcher = new IndexSearcher(searchDirectory, true);
        //创建一个MultiPhraseQuery
        MultiPhraseQuery query = new MultiPhraseQuery();
        //构建两个Term,作为短语的前缀
        Term t1 = new Term("bookname", "钢");
        Term t2 = new Term("bookname", "和");

        //首先向Query中加入所有的前缀，与后缀一起，它们将组成两个短语
        query.add(new Term[]{t1, t2});
        //向其中加入要查找的短语的后缀
        query.add(new Term("bookname", "铁"));
        Hits hits = searcher.search(query);
        for (int i = 0; i < hits.length(); i++) {
            System.out.println(hits.doc(i));
        }
    }
}
