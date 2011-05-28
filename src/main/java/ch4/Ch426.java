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
public class Ch426 {
    //索引存储路径
    public final static String INDEX_STORE_PATH = "D:\\appserver\\workspace\\ideaworkspace\\LuceneStudy\\src\\main\\resources\\indexFileStore";

    public static void main(String[] args) throws Exception {
        Directory directory = new SimpleFSDirectory(new File(INDEX_STORE_PATH));
        IndexWriter writer = new IndexWriter(directory, new StandardAnalyzer(), true);
        writer.setUseCompoundFile(false);
        //创建8个文档
        Document doc1 = new Document();
        Field f1 = new Field("content", "aa bb cc dd ee ff gg hh ii jj kk", Field.Store.YES, Field.Index.TOKENIZED);
        doc1.add(f1);
        writer.addDocument(doc1);
        writer.close();

        Directory searchDirectory = new SimpleFSDirectory(new File(INDEX_STORE_PATH));
        //根据索引位置建立 IndexSearcher
        IndexSearcher searcher = new IndexSearcher(searchDirectory, true);

        //分明建4个SpanTermQuery
        Term t1 = new Term("content", "aa");
        Term t2 = new Term("content", "cc");
        SpanTermQuery s1 = new SpanTermQuery(t1);
        SpanTermQuery s2 = new SpanTermQuery(t2);

        Term t3 = new Term("content", "gg");
        Term t4 = new Term("content", "kk");
        SpanTermQuery s3 = new SpanTermQuery(t3);
        SpanTermQuery s4 = new SpanTermQuery(t4);

        //先创建两个SpanNearQuery,参数为上面的4个SpanTermQuery
        SpanNearQuery query1 = new SpanNearQuery(new SpanQuery[]{s1, s2}, 1, false);
        SpanNearQuery query2 = new SpanNearQuery(new SpanQuery[]{s3, s4}, 3, false);
        //再创建一个SpanNearQuery，参数为上面的两个SpanNearQuery
        SpanNearQuery query = new SpanNearQuery(new SpanQuery[]{query1, query2}, 3, false);

        Hits hits = searcher.search(query);

        for (int i = 0; i < hits.length(); i++) {
            System.out.println(hits.doc(i));
        }
    }
}
