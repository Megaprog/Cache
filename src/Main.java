/**
 * Copyright (C) 2012 Tomas Shestakov.
 */

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Runs test
 */
public class Main {

    public static String CONFIGFILENAME = "cache.xml";

    public static void main(String[] args) {
        //Check config file name command line argument
        if (args.length > 0) {
            CONFIGFILENAME = args[0];
        }

        ApplicationContext context = new FileSystemXmlApplicationContext(CONFIGFILENAME);

        @SuppressWarnings("unchecked")
        TestObjectFactory<Integer, Employee> testObjectFactory = context.getBean("testObjectFactory", TestObjectFactory.class);

        testObjectFactory.run();

        System.out.println("Total calls: " + testObjectFactory.totalCalls());
        System.out.println("Mismatches number: " + testObjectFactory.getMismatches().size());
        for (TestObjectFactory.Mismatch<Integer, Employee> mismatch : testObjectFactory.getMismatches()) {
            System.out.println(mismatch);
        }

    }
}
