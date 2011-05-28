package ch2.lucenedemo.process;

import jeasy.analysis.MMAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author kevin
 * @version Revision: 1.00 Date: 11-4-9上午9:27
 * @Email liuyuhui007@gmail.com
 */
public class IndexProcesser {
    //索引存储路径
    private String INDEX_STORE_PATH = "D:\\appserver\\workspace\\ideaworkspace\\LuceneStudy\\src\\main\\resources\\indexFileStore";


    public static void main(String[] args) {
        IndexProcesser processer = new IndexProcesser();
        processer.createIndex("D:\\appserver\\workspace\\ideaworkspace\\LuceneStudy\\src\\main\\resources\\sources");
    }

    //创建索引
    public void createIndex(String inputDir) {
        try {
            IndexWriter writer = new IndexWriter(INDEX_STORE_PATH, new MMAnalyzer(), true);
            File fileDir = new File(inputDir);
            //取得所有需要建立索引的文件数组
            File files[] = fileDir.listFiles();
            //遍历数组
            for (int i = 0; i < files.length; i++) {
                //获取文件名
                String fileName = files[i].getName();
                //判断是否为文本文件
                if (fileName.substring(fileName.lastIndexOf(".")).equals(".txt")) {
                    //创建Document
                    Document doc = new Document();
                    //为文件名创建一个Field
                    Field field = new Field("filename", files[i].getName(), Field.Store.YES, Field.Index.TOKENIZED);
                    doc.add(field);

                    //为文件内容创建一个Field
                    field = new Field("content", loadFileToString(files[i]), Field.Store.NO, Field.Index.TOKENIZED);
                    doc.add(field);

                    //把Document加入IndexWriter
                    writer.addDocument(doc);
                }
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从文件中把内容读出，所有的内容放在一个String中返回
     *
     * @param file 文件
     * @return 内容字符串
     */
    public String loadFileToString(File file) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            StringBuffer sb = new StringBuffer();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            br.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
