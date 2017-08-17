package UnitTestCases;

import UnitTestCases.JunitIsloginCorrect;
import java.io.IOException;
import java.text.ParseException;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestLoginDetails {

    public void isLoginCorrect() throws IOException, ParseException {
        // TODO Auto-generated method stub

        //Result result = JUnitCore.this.run(jt);
        Result result = JUnitCore.runClasses(JunitIsloginCorrect.class);
        //Result result1 = JUnitCore.runClasses()
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println("Test case Result is : " + result.wasSuccessful());

    }
}
