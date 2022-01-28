package commons;

import java.io.File;

public class BaseClass {
    public static String environment;
    public static String baseUrl;
    public static String basePathMember;
    public static String basePathQuote;
    public static String absPath = new File("").getAbsolutePath();
    public static String separator = System.getProperty(("file.separator"));

}
