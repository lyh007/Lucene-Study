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


//
//ab bc		0.7084572
//0.7084572 = (MATCH) fieldWeight(bookname:bc in 1), product of:
//  1.0 = tf(termFreq(bookname:bc)=1)
//  1.1335315 = idf(docFreq=6, maxDocs=8)
//  0.625 = fieldNorm(field=bookname, doc=1)
//
//ab bc cd		0.5667657
//0.5667657 = (MATCH) fieldWeight(bookname:bc in 2), product of:
//  1.0 = tf(termFreq(bookname:bc)=1)
//  1.1335315 = idf(docFreq=6, maxDocs=8)
//  0.5 = fieldNorm(field=bookname, doc=2)
//
//ab bc de		0.5667657
//0.5667657 = (MATCH) fieldWeight(bookname:bc in 5), product of:
//  1.0 = tf(termFreq(bookname:bc)=1)
//  1.1335315 = idf(docFreq=6, maxDocs=8)
//  0.5 = fieldNorm(field=bookname, doc=5)
//
//ab bc cd de ef		0.49592
//0.49592 = (MATCH) fieldWeight(bookname:bc in 3), product of:
//  1.0 = tf(termFreq(bookname:bc)=1)
//  1.1335315 = idf(docFreq=6, maxDocs=8)
//  0.4375 = fieldNorm(field=bookname, doc=3)
//
//ab bc cd de ef fg		0.42507428
//0.42507428 = (MATCH) fieldWeight(bookname:bc in 4), product of:
//  1.0 = tf(termFreq(bookname:bc)=1)
//  1.1335315 = idf(docFreq=6, maxDocs=8)
//  0.375 = fieldNorm(field=bookname, doc=4)
//
//ab bc cd de ef fg gh hi		0.3542286
//0.3542286 = (MATCH) fieldWeight(bookname:bc in 6), product of:
//  1.0 = tf(termFreq(bookname:bc)=1)
//  1.1335315 = idf(docFreq=6, maxDocs=8)
//  0.3125 = fieldNorm(field=bookname, doc=6)

/**
 * 文档的得分主要由四部分内容决定,tf(词条频率），idf(反转文档频率),boost(Field的激励因子)和lenghtNorm(长度因子)
 * @see http://baike.baidu.com/view/1228847.htm
 *
 */
public class Ch502 {
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
            Document doc = hits.doc(i);
            System.out.print(doc.get("bookname") + "\t\t");
            System.out.println(hits.score(i));
            System.out.println(searcher.explain(q, hits.id(i)).toString());
        }
    }
}
