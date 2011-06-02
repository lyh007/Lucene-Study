package ch5;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;

import java.io.IOException;
import java.util.BitSet;

/**
 * @author kevin
 * @version Revision: 1.00 Date: 11-6-2上午11:30
 * @Email liuyuhui007@gmail.com
 * 安全级别与过滤器代码
 */
public class Ch514 extends Filter {
    //安全级别常量
    public final static int SECURITY_ADVANCED = 0;

    @Override      //继承自Filter的抽像基类
    public BitSet bits(IndexReader reader) throws IOException {
        //首先初始化一个BitSet对象
        final BitSet bits = new BitSet(reader.maxDoc());
        //先将整个集合置为True
        //表示当前集合内的所有文档都是可以被检索到的
        bits.set(0, bits.size() - 1);
        //构造一个Term对象，代表最高安全级别
        Term term = new Term("securitylevel", SECURITY_ADVANCED + "");

        //初始化一个IndexSearcher对象
        //查找SecurityLever这个Field的值为SECURITY_ADVANCED的文档
        IndexSearcher searcher = new IndexSearcher(reader);
        Hits hits = searcher.search(new TermQuery(term));
        for (int i = 0; i < hits.length(); i++) {
            //遍历结果,并将bits中相应的值赋成false
            bits.set(hits.id(i), false);
        }
        return bits;
    }
}
