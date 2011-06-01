package ch2.lucenedemo.process;

import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author kevin
 * @version Revision: 1.00 Date: 11-4-9上午11:06
 * @Email liuyuhui007@gmail.com
 */
public class Search {
    //索引存储路径
    private String INDEX_STORE_PATH = "D:\\appserver\\workspace\\ideaworkspace\\LuceneStudy\\src\\main\\resources\\indexFileStore";

    /**
     * 利用Lucene搜索
     *
     * @param searchType 搜索类型
     * @param searchKey  关键字
     */
    public void indexSearch(String searchType, String searchKey) {
        try {
            //根据索引位置建立 IndexSearcher
            IndexSearcher searcher = new IndexSearcher(INDEX_STORE_PATH);

            //建立索引单元,searchType代表索引的Field, searchKey代表关键字
            Term t = new Term(searchType, searchKey);

            //由Term生成一个Query
          //  Query q = new TermQuery(t);

            //搜索开始时间
            Date beginTime = new Date();
            //获取一个<Document,frequency>的枚举对旬TermDoc
            TermDocs termDocs = searcher.getIndexReader().termDocs(t);
            while (termDocs.next()) {
                //输出文档中出现关键词的次数
                System.out.println(termDocs.freq());

                //输出搜索到关键词的文档
                System.out.println(searcher.getIndexReader().document(termDocs.doc()));
            }
            //搜索完成时间
            Date endTime = new Date();

            //搜索所耗时间
            long timeOfSearch = endTime.getTime() - beginTime.getTime();
            System.out.println("The time For indexSearch is " + timeOfSearch + " ms");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 利用String的搜索
     *
     * @param keyword   关键字
     * @param searchDir 搜索路径
     */
    public void stringSearch(String keyword, String searchDir) {
        File fileDir = new File(searchDir);

        //返回目录文件夹所有的文件列表
        File files[] = fileDir.listFiles();

        //HashMap保存文件名和匹配次数对
        Map rs = new HashMap();

        //搜索开始时间
        Date beginTime = new Date();

        //遍历所有文件
        for (int i = 0; i < files.length; i++) {
            //初始化匹配次数
            int hits = 0;

            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(files[i]));
                StringBuffer sb = new StringBuffer();
                String line = br.readLine();
                while (line != null) {
                    sb.append(line);
                    line = br.readLine();
                }
                br.close();

                //将StringBuffer转化成String,便于搜索
                String stringToSearch = sb.toString();

                //初始化formIndex
                int fromIndex = -keyword.length();

                //逐个匹配关键词
                while ((fromIndex = stringToSearch.indexOf(keyword, fromIndex + keyword.length())) != -1) {
                    hits++;
                }
                //将文件名和匹配次数加入HashMap
                rs.put(files[i].getName(), Integer.valueOf(hits));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //输出查询结果
        Iterator it = rs.keySet().iterator();
        while (it.hasNext()) {
            String fileName = (String) it.next();
            Integer hits = (Integer) rs.get(fileName);
            System.out.println("find " + hits.intValue() + " matches in " + fileName);
        }
        //搜索完成时间
        Date endTime = new Date();
        //搜索所耗时间
        long timeOfSearch = endTime.getTime() - beginTime.getTime();
        System.out.println("The time For indexSearch is " + timeOfSearch + " ms");
    }
}
