package shizuwei.test.java;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        Arrays.asList(1L, 2L, 3L);
        List<Object> list = Lists.newArrayList();
        list.indexOf(new Object());
        list.toArray();
        list.isEmpty();
        Iterator<Object> i0 = list.iterator();

        ListIterator<Object> li = list.listIterator();
        li.next();
        i0.next();

        LinkedList<String> linkedList = new LinkedList<String>();
        Collections.reverseOrder();
        Set<String> set = Sets.newHashSet();
        set.add("teacher");
        Random rand = new Random(47);
        SortedSet<Integer> intset = new TreeSet<Integer>();
        for (int i = 0; i < 10000; i++)
            intset.add(rand.nextInt(30));
        assertTrue(true);
    }
}
