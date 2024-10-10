package org.NAGP.Assignment.Rest.Booker.Listeners;

import com.aventstack.extentreports.Status;
import org.NAGP.Assignment.Rest.Booker.Utils.ExtentReports.ExtentManager;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;



import static org.NAGP.Assignment.Rest.Booker.Utils.ExtentReports.ExtentTestManager.getTest;
import static org.NAGP.Assignment.Rest.Booker.Utils.Logs.Log.Log;


public class TestListener implements ITestListener {
    private static String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Override
    public void onStart(ITestContext iTestContext) {
        Log.info("I am in onStart method "+iTestContext.getName());
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        Log.info("I am in onFinish method" +iTestContext.getName());
        ExtentManager.extentReports.flush();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        Log.info(getTestMethodName(iTestResult) +"test is starting.");
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        Log.info(getTestMethodName(iTestResult) +" test is succeed.");
        getTest().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        Log.info(getTestMethodName(iTestResult) +" test is failed.");

    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        Log.info(getTestMethodName(iTestResult) +" test is skipped.");
        getTest().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        Log.info("Test failed but it is in defined success ratio "+getTestMethodName(iTestResult));
    }
}