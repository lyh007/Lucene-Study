package ch4;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;

import java.io.File;
import java.util.List;

/**
 * @author kevin
 * @version Revision: 1.00 Date: 11-5-25下午4:09
 * @Email liuyuhui007@gmail.com
 */
public class Ch42 {
    //索引存储路径
    public final static String INDEX_STORE_PATH = "D:\\appserver\\workspace\\ideaworkspace\\LuceneStudy\\src\\main\\resources\\indexFileStore";

    public static void main(String[] args) throws Exception {
        Directory directory = new SimpleFSDirectory(new File(INDEX_STORE_PATH));
        //根据索引位置建立 IndexSearcher
        IndexSearcher searcher = new IndexSearcher(directory, true);
        //建立索引单元,searchType代表索引的Field, searchKey代表关键字
        Term t = new Term("content", "五");
        //构建一个Query对象
        Query query = new TermQuery(t);
        //检索
        Hits hits=searcher.search(query);
        //TopDocs topDocs = searcher.search(query, 1);
        //System.out.println(topDocs.getMaxScore());
        //显示查询结果
        for(int i=0;i<hits.length();i++){
            System.out.println(hits.doc(i));
            System.out.println(hits.score(i));
            System.out.println(hits.id(i));
            System.out.println("==========================");
            Document docResult=hits.doc(i);
            List<Field> fields=docResult.getFields();
            for(Field field:fields){
                System.out.println(field.name());
            }
        }
    }
}
