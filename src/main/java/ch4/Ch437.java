package ch4;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.RangeQuery;

/**
 * @author kevin
 * @version Revision: 1.00 Date: 11-5-28下午2:50
 * @Email liuyuhui007@gmail.com
 */
public class Ch437 {
    public static void main(String[] args) throws Exception {
        Term t1 = new Term("publishdate", "1990-01-01");
        Term t2 = new Term("publishdate", "1998-12-31");
        RangeQuery query = new RangeQuery(t1, t2, true);
        System.out.println(query.toString());

        System.out.println("=========================================");
        String queryStr = "[1990-01-01 TO 1998-12-31]";
        QueryParser parser = new QueryParser("publishdate", new StandardAnalyzer());
        Query query1 = parser.parse(queryStr);
        System.out.println(query1.toString());
    }
}
