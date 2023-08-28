package kz.aha.bot.util;

import java.util.Objects;

/**
 * @author Akhmet.Sulemenov
 * createdate 6/30/2023
 */
public class AppUtils {
    public  static String checkFormatPhone(String phone) {
        if (Objects.nonNull(phone) && !phone.startsWith("+"))
            phone = "+" + phone;
        return phone.replaceAll(" ", "").replaceAll("\\(", "").replaceAll("\\)", "");
    }

    public static String formatAmount(Integer sum) {
//        DecimalFormat df = new DecimalFormat();
//        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
//        symbols.setGroupingSeparator(' ');
//        df.setDecimalFormatSymbols(symbols);
//        return df.format(sum);
        String strFormatSum = "";
        try {
            int j = 0;
            char[] result = sum.toString().toCharArray();
            for (int i = result.length - 1; i >= 0; i--) {
                if (i % 3 == 0)
                    strFormatSum += result[j] + " ";
                else
                    strFormatSum += result[j] + "";
                j++;
            }
            strFormatSum = strFormatSum.trim();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return strFormatSum;
    }

}
