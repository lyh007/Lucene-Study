package ch4;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;

import java.io.File;
import java.util.Date;

/**
 * @author kevin
 * @version Revision: 1.00 Date: 11-5-25下午4:36
 * @Email liuyuhui007@gmail.com
 */
public class Ch44 {
    //索引存储路径
    public final static String INDEX_STORE_PATH = "D:\\appserver\\workspace\\ideaworkspace\\LuceneStudy\\src\\main\\resources\\indexFileStore";

    public static void main(String[] args) throws Exception {
        Directory directory = new SimpleFSDirectory(new File(INDEX_STORE_PATH));
        //根据索引位置建立 IndexSearcher
        IndexSearcher searcher = new IndexSearcher(directory, true);
        //建立索引单元,searchType代表索引的Field, searchKey代表关键字
        Term t = new Term("bookname", "女");
        //构建一个Query对象
        Query query = new TermQuery(t);
        //检索
        Hits hits = searcher.search(query);
        //计算取出第10个结果所花费的时间
//        Date start = new Date();
//        Document d1 = hits.doc(10);
//        System.out.println(d1);
//        System.out.println(hits.id(10));
//        System.out.println(d1.get("bookname"));
//        Date end = new Date();
//        System.out.println("cost time:" + (end.getTime() - start.getTime()) + "ms");

        //计算取出第100个结果所花费的时间
//        Date start = new Date();
//        Document d1 = hits.doc(100);
//        System.out.println(d1);
//        System.out.println(hits.id(100));
//        System.out.println(d1.get("bookname"));
//        Date end = new Date();
//        System.out.println("cost time:" + (end.getTime() - start.getTime()) + "ms");

        //计算取出第1000个结果所花费的时间
//        Date start = new Date();
//        Document d1 = hits.doc(1000);
//        System.out.println(d1);
//        System.out.println(hits.id(1000));
//        System.out.println(d1.get("bookname"));
//        Date end = new Date();
//        System.out.println("cost time:" + (end.getTime() - start.getTime()) + "ms");

//        //计算取出第10000个结果所花费的时间
//        Date start = new Date();
//        Document d1 = hits.doc(10000);
//        System.out.println(d1);
//        System.out.println(hits.id(10000));
//        System.out.println(d1.get("bookname"));
//        Date end = new Date();
//        System.out.println("cost time:" + (end.getTime() - start.getTime()) + "ms");

        //计算取出第99998个结果所花费的时间
        Date start = new Date();
        Document d1 = hits.doc(99998);
        System.out.println(d1);
        System.out.println(hits.id(99998));
        System.out.println(d1.get("bookname"));
        Date end = new Date();
        System.out.println("cost time:" + (end.getTime() - start.getTime()) + "ms");
        try{

        }catch(Exception e){

        }

    }
}
