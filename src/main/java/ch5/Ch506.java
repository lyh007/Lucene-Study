package ch5;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;

import java.io.File;

/**
 * @author kevin
 * @version Revision: 1.00 Date: 11-6-1下午4:18
 * @Email liuyuhui007@gmail.com
 */
public class Ch506 {
    //索引存储路径
    public final static String INDEX_STORE_PATH = "D:\\appserver\\workspace\\ideaworkspace\\LuceneStudy\\src\\main\\resources\\indexFileStore";

    public static void main(String[] args) throws Exception {
        Directory directory = new SimpleFSDirectory(new File(INDEX_STORE_PATH));
        IndexWriter writer = new IndexWriter(directory, new StandardAnalyzer(), true);
        writer.setUseCompoundFile(false);
        //创建8个文档
        Document doc1 = new Document();
        Field f1 = new Field("booknumber", "0000001", Field.Store.YES, Field.Index.UN_TOKENIZED);
        Field f2 = new Field("bookname", "钢铁是怎样炼成的", Field.Store.YES, Field.Index.TOKENIZED);
        Field f3 = new Field("publishdate", "1970-01-01", Field.Store.YES, Field.Index.TOKENIZED);
        doc1.add(f1);
        doc1.add(f2);
        doc1.add(f3);

        Document doc2 = new Document();
        f1 = new Field("booknumber", "0000002", Field.Store.YES, Field.Index.UN_TOKENIZED);
        f2 = new Field("bookname", "钢铁战士", Field.Store.YES, Field.Index.TOKENIZED);
        f3 = new Field("publishdate", "1980-01-01", Field.Store.YES, Field.Index.TOKENIZED);
        doc2.add(f1);
        doc2.add(f2);
        doc2.add(f3);

        Document doc3 = new Document();
        f1 = new Field("booknumber", "0000003", Field.Store.YES, Field.Index.UN_TOKENIZED);
        f2 = new Field("bookname", "篱笆女人和狗", Field.Store.YES, Field.Index.TOKENIZED);
        f3 = new Field("publishdate", "1990-01-01", Field.Store.YES, Field.Index.TOKENIZED);
        doc3.add(f1);
        doc3.add(f2);
        doc3.add(f3);


        Document doc4 = new Document();
        f1 = new Field("booknumber", "0000004", Field.Store.YES, Field.Index.UN_TOKENIZED);
        f2 = new Field("bookname", "女人是水做的", Field.Store.YES, Field.Index.TOKENIZED);
        f3 = new Field("publishdate", "1999-01-01", Field.Store.YES, Field.Index.TOKENIZED);
        doc4.add(f1);
        doc4.add(f2);
        doc4.add(f3);

        Document doc5 = new Document();
        f1 = new Field("booknumber", "0000005", Field.Store.YES, Field.Index.UN_TOKENIZED);
        f2 = new Field("bookname", "英雄儿女", Field.Store.YES, Field.Index.TOKENIZED);
        f3 = new Field("publishdate", "2002-01-01", Field.Store.YES, Field.Index.TOKENIZED);
        doc5.add(f1);
        doc5.add(f2);
        doc5.add(f3);

        Document doc6 = new Document();
        f1 = new Field("booknumber", "0000006", Field.Store.YES, Field.Index.UN_TOKENIZED);
        f2 = new Field("bookname", "白毛女", Field.Store.YES, Field.Index.TOKENIZED);
        f3 = new Field("publishdate", "1985-01-01", Field.Store.YES, Field.Index.TOKENIZED);
        doc6.add(f1);
        doc6.add(f2);
        doc6.add(f3);

        Document doc7 = new Document();
        f1 = new Field("booknumber", "0000007", Field.Store.YES, Field.Index.UN_TOKENIZED);
        f2 = new Field("bookname", "我的兄弟和女儿", Field.Store.YES, Field.Index.TOKENIZED);
        f3 = new Field("publishdate", "1978-01-01", Field.Store.YES, Field.Index.TOKENIZED);
        doc7.add(f1);
        doc7.add(f2);
        doc7.add(f3);

        //将书的信息加入索引
        writer.addDocument(doc1);
        writer.addDocument(doc2);
        writer.addDocument(doc3);
        writer.addDocument(doc4);
        writer.addDocument(doc5);
        writer.addDocument(doc6);
        writer.addDocument(doc7);
        writer.close();

        Directory searchDirectory = new SimpleFSDirectory(new File(INDEX_STORE_PATH));
        //根据索引位置建立 IndexSearcher
        IndexSearcher searcher = new IndexSearcher(searchDirectory, true);
        TermQuery q = new TermQuery(new Term("bookname", "女"));

        //定义一个Sort对象
        Sort sort = new Sort();
        //定义一个SortField对象
        SortField f = new SortField("booknumber", SortField.INT, false);
        sort.setSort(f);

        Hits hits = searcher.search(q, sort);
        for (int i = 0; i < hits.length(); i++) {
            Document doc = hits.doc(i);
            System.out.print("书名:");
            System.out.println(doc.get("bookname"));
            System.out.print("得分:");
            System.out.println(hits.score(i));
            System.out.print("内部ID号:");
            System.out.println(String.valueOf(hits.id(i)));

            System.out.print("书号:");
            System.out.println(doc.get("booknumber"));
            System.out.print("发行日期:");
            System.out.println(doc.get("publishdate"));
            System.out.println("得分计划：");
            System.out.println(searcher.explain(q, hits.id(i)).toString());
            System.out.println("------------------------------------------");
        }
    }
}
