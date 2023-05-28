package bg.rumen.Bookstore.util;
import bg.rumen.Bookstore.params.PageParams;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class PageManagerTest {

    private List<Integer> testList;
    private List<int[]> testCases;
    private PageParams pageParams;

    @Before
    public void setUp() {
        this.testList = new ArrayList<>();
        int [] testCaseOne = {1, 2, 3, 4 ,5};
        int [] testCaseTwo = {11, 12, 13, 14, 15};
        int [] testCaseThree = {46, 47, 48, 49, 50};
        int [] testCaseFour = {91, 92, 93, 94, 95};
        int [] testCaseFive = {96, 97, 98, 99, 100};
        this.testCases = Arrays.asList(testCaseOne, testCaseTwo, testCaseThree, testCaseFour, testCaseFive);
        this.pageParams = new PageParams();
    }


    @Test
    public void testGetPagesShouldReturnCorrectData() {

        for (int num = 1; num <= 100; num++) {
            this.testList.add(num);
        }

        this.pageParams.setPage(1);
        List<Integer> newList = PageManager.getPages(this.testList, this.pageParams).getItems();
        this.pageParams.setPage(3);
        List<Integer> secondList = PageManager.getPages(this.testList, this.pageParams).getItems();
        this.pageParams.setPage(10);
        List<Integer> thirdList = PageManager.getPages(this.testList, this.pageParams).getItems();
        this.pageParams.setPage(19);
        List<Integer> fourthList = PageManager.getPages(this.testList, this.pageParams).getItems();
        this.pageParams.setPage(20);
        List<Integer> fifthList = PageManager.getPages(this.testList, this.pageParams).getItems();

        int[] firstArr = new int[5];
        int[] secondArr = new int[5];
        int[] thirdArr = new int[5];
        int[] fourthArr = new int[5];
        int[] fifthArr = new int[5];


        for (int index = 0; index < 5; index++) {

            int numToAdd = newList.get(index);
            int secondNumToAdd = secondList.get(index);
            int thirdNumToAdd = thirdList.get(index);
            int fourthNumToAdd = fourthList.get(index);
            int fifthNumToAdd = fifthList.get(index);
            firstArr[index] = numToAdd;
            secondArr[index] = secondNumToAdd;
            thirdArr[index] = thirdNumToAdd;
            fourthArr[index] = fourthNumToAdd;
            fifthArr[index] = fifthNumToAdd;

        }

        List<int[]> actual = Arrays.asList(firstArr, secondArr, thirdArr, fourthArr, fifthArr);


        for (int index = 0; index < 5; index++) {
            assertArrayEquals(this.testCases.get(index), actual.get(index));
        }

    }
}