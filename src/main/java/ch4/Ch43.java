package ch4;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;

import java.io.File;

/**
 * @author kevin
 * @version Revision: 1.00 Date: 11-5-25下午4:30
 * @Email liuyuhui007@gmail.com
 */
public class Ch43 {
    //索引存储路径
    public final static String INDEX_STORE_PATH = "D:\\appserver\\workspace\\ideaworkspace\\LuceneStudy\\src\\main\\resources\\indexFileStore";

    public static void main(String[] args) throws Exception {
        Directory directory = new SimpleFSDirectory(new File(INDEX_STORE_PATH));
        IndexWriter writer = new IndexWriter(directory, new StandardAnalyzer(), true);
        Document doc = null;
        Field field = new Field("bookname", "美女与野兽", Field.Store.YES, Field.Index.TOKENIZED);
        for (int i = 1; i < 100000; i++) {
            doc = new Document();
            doc.add(field);
            writer.addDocument(doc);
        }
        writer.close();
    }
}
