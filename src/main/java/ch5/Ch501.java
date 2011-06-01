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
public class Ch501 {
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
        Document doc7 = new Document();
        Document doc8 = new Document();

        Field f1 = new Field("bookname", "ab", Field.Store.YES, Field.Index.TOKENIZED);
        Field f2 = new Field("bookname", "ab bc", Field.Store.YES, Field.Index.TOKENIZED);
        Field f3 = new Field("bookname", "ab bc cd", Field.Store.YES, Field.Index.TOKENIZED);
        Field f4 = new Field("bookname", "ab bc cd de ef", Field.Store.YES, Field.Index.TOKENIZED);
        Field f5 = new Field("bookname", "ab bc cd de ef fg", Field.Store.YES, Field.Index.TOKENIZED);
        Field f6 = new Field("bookname", "ab bc de", Field.Store.YES, Field.Index.TOKENIZED);
        Field f7 = new Field("bookname", "ab bc cd de ef fg gh hi", Field.Store.YES, Field.Index.TOKENIZED);
        Field f8 = new Field("bookname", "ab cd", Field.Store.YES, Field.Index.TOKENIZED);

        doc1.add(f1);
        doc2.add(f2);
        doc3.add(f3);
        doc4.add(f4);
        doc5.add(f5);
        doc6.add(f6);
        doc7.add(f7);
        doc8.add(f8);

        writer.addDocument(doc1);
        writer.addDocument(doc2);
        writer.addDocument(doc3);
        writer.addDocument(doc4);
        writer.addDocument(doc5);
        writer.addDocument(doc6);
        writer.addDocument(doc7);
        writer.addDocument(doc8);
        writer.close();

        Directory searchDirectory = new SimpleFSDirectory(new File(INDEX_STORE_PATH));
        //根据索引位置建立 IndexSearcher
        IndexSearcher searcher = new IndexSearcher(searchDirectory, true);
        TermQuery q = new TermQuery(new Term("bookname", "bc"));

        //自然排序
        Hits hits = searcher.search(q);
        for (int i = 0; i < hits.length(); i++) {
            Document doc=hits.doc(i);
            System.out.print(doc.get("bookname")+"\t\t");
            System.out.println(hits.score(i));
        }
    }
}
