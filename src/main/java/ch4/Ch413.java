package ch4;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;

import java.io.File;

/**
 * @author kevin
 * @version Revision: 1.00 Date: 11-5-26下午2:42
 * @Email liuyuhui007@gmail.com
 */
public class Ch413 {
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

        Field f1 = new Field("bookname", "钢铁是怎样炼成的", Field.Store.YES, Field.Index.TOKENIZED);
        Field f2 = new Field("bookname", "英雄儿女", Field.Store.YES, Field.Index.TOKENIZED);
        Field f3 = new Field("bookname", "篱笆女人和狗", Field.Store.YES, Field.Index.TOKENIZED);
        Field f4 = new Field("bookname", "女人是水做的", Field.Store.YES, Field.Index.TOKENIZED);
        Field f5 = new Field("bookname", "我的兄弟和女儿", Field.Store.YES, Field.Index.TOKENIZED);
        Field f6 = new Field("bookname", "白毛女", Field.Store.YES, Field.Index.TOKENIZED);
        Field f7 = new Field("bookname", "钢的世界", Field.Store.YES, Field.Index.TOKENIZED);
        Field f8 = new Field("bookname", "钢铁战士", Field.Store.YES, Field.Index.TOKENIZED);

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
        //构建一个Term,用于表示要查找的前缀
        Term prefix = new Term("bookname", "钢");

        //PrefixQuery的构造函数只有一个参数
        PrefixQuery prefixQuery = new PrefixQuery(prefix);
        //打印查询结果
        Hits hits = searcher.search(prefixQuery);
        for (int i = 0; i < hits.length(); i++) {
            System.out.println(hits.doc(i));
        }
    }
}
