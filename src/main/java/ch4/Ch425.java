package ch4;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.spans.SpanNearQuery;
import org.apache.lucene.search.spans.SpanQuery;
import org.apache.lucene.search.spans.SpanTermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;

import java.io.File;

/**
 * @author kevin
 * @version Revision: 1.00 Date: 11-5-28下午2:50
 * @Email liuyuhui007@gmail.com
 */
public class Ch425 {
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

        Field f1 = new Field("content", "Last night David gave him every beautiful clothes", Field.Store.YES, Field.Index.TOKENIZED);
        Field f2 = new Field("content", "Last night Mary gave him very poor clothes", Field.Store.YES, Field.Index.TOKENIZED);
        Field f3 = new Field("content", "Last night Candy gave him very beautiful shoes", Field.Store.YES, Field.Index.TOKENIZED);

        doc1.add(f1);
        doc2.add(f2);
        doc3.add(f3);

        writer.addDocument(doc1);
        writer.addDocument(doc2);
        writer.addDocument(doc3);

        writer.close();

        Directory searchDirectory = new SimpleFSDirectory(new File(INDEX_STORE_PATH));
        //根据索引位置建立 IndexSearcher
        IndexSearcher searcher = new IndexSearcher(searchDirectory, true);
        //构建一个Term对象并将其作为SpanTermQuery的构造函数参数
        Term t1 = new Term("content", "mary");
        Term t2 = new Term("content", "poor");

        SpanTermQuery people = new SpanTermQuery(t1);
        SpanTermQuery how = new SpanTermQuery(t2);

        System.out.println("设置slop值为3");
        SpanNearQuery query = new SpanNearQuery(new SpanQuery[]{people, how}, 3, false);

        Hits hits = searcher.search(query);
        for (int i = 0; i < hits.length(); i++) {
            System.out.println(hits.doc(i));
        }

        System.out.println("span值设为2时");
        query = new SpanNearQuery(new SpanQuery[]{people, how}, 2, false);
        hits = searcher.search(query);
        for (int i = 0; i < hits.length(); i++) {
            System.out.println(hits.doc(i));
        }
    }
}
