package UnitTestCases;

import UnitTestCases.JunitIsPathCorrect;
import java.io.IOException;
import java.text.ParseException;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestPathToSaveData {

    public void checkPath() throws IOException, ParseException {
        // TODO Auto-generated method stub

        Result result = JUnitCore.runClasses(JunitIsPathCorrect.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println("Test case Result is : " + result.wasSuccessful());

    }

}
