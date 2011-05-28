package ch2.lucenedemo.test;

import ch2.lucenedemo.process.Search;

/**
 * @author kevin
 * @version Revision: 1.00 Date: 11-4-9上午11:40
 * @Email liuyuhui007@gmail.com
 */
public class SearchTimeCompareTest {
    public static void main(String[] args) {
        Search search=new Search();
        //通过索引搜索关键词
        search.indexSearch("content","红军");

        //通过SringAPI搜索关键词
        search.stringSearch("红军", "D:\\appserver\\workspace\\ideaworkspace\\LuceneStudy\\src\\main\\resources\\sources");
    }
}
