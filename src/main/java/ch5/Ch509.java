package ch5;

import java.text.Collator;
import java.util.Locale;

/**
 * @author kevin
 * @version Revision: 1.00 Date: 11-6-2上午10:47
 * @Email liuyuhui007@gmail.com
 */
public class Ch509 {
    //Locale信息会影响到排序结果
    public static void main(String[] args) {
        String text1 = "我";
        String text2 = "你";
        Collator co1 = Collator.getInstance(Locale.CHINA);
        Collator co2 = Collator.getInstance(Locale.JAPAN);
        System.out.println(Locale.CHINA + ":" + co1.compare(text1, text2));
        System.out.println(Locale.JAPAN + ":" + co2.compare(text1, text2));
    }
}
