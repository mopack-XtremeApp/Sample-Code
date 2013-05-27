package com.assignment.security;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.assignment.security.test.ForgetPasswordFunctionalityTest;
import com.assignment.security.test.UserAuthenticationTest;
import com.assignment.security.test.UserOperationsTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ ForgetPasswordFunctionalityTest.class,
		UserAuthenticationTest.class, UserOperationsTest.class })
public class UserAccountTestSuite {

}
