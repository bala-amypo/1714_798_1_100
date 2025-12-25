package com.example.demo;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestResultListener implements ITestListener {
    
    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + " - PASS");
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + " - FAIL");
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println(result.getMethod().getMethodName() + " - SKIP");
    }
    
    // Add empty implementations for all other methods to avoid errors
    @Override
    public void onTestStart(ITestResult result) {}
    
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}
    
    @Override
    public void onStart(org.testng.ITestContext context) {}
    
    @Override
    public void onFinish(org.testng.ITestContext context) {}
}