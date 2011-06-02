package ch5;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.search.Filter;

import java.io.IOException;
import java.util.BitSet;

/**
 * @author kevin
 * @version Revision: 1.00 Date: 11-6-2上午11:30
 * @Email liuyuhui007@gmail.com
 * 安全级别与过滤器代码
 */
public class AdvancedSecurityFilterUpdate extends Filter {
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

        //从索引中取出具有最高安全级别的文档
        TermDocs termDocs = reader.termDocs(term);

        //遍历每个文档
        while (termDocs.next()) {
            //设置它们相应的BitSet中的值为false
            bits.set(termDocs.doc(), false);
        }
        return bits;
    }

//    @Override
//    public DocIdSet getDocIdSet(IndexReader reader) throws IOException {
//        DocIdSet docIdSet = new DocIdSet() {
//            @Override
//            public DocIdSetIterator iterator() throws IOException {
//                 //TODO:
//                return null;
//            }
//        };
//        return docIdSet;
//    }
}
