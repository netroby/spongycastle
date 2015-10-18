package org.spongycastle.cert.test;

import java.security.Security;

import junit.extensions.TestSetup;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.util.test.SimpleTestResult;

public class AllTests
    extends TestCase
{
    public void testSimpleTests()
    {
        org.spongycastle.util.test.Test[] tests = new org.spongycastle.util.test.Test[] { new CertTest(), new DANETest(), new PKCS10Test(), new AttrCertSelectorTest(), new AttrCertTest(), new X509ExtensionUtilsTest(), new CertPathLoopTest() };

        for (int i = 0; i != tests.length; i++)
        {
            SimpleTestResult  result = (SimpleTestResult)tests[i].perform();

            if (!result.isSuccessful())
            {
                if (result.getException() != null)
                {
                    result.getException().printStackTrace();
                }
                fail(result.toString());
            }
        }
    }

    public static void main (String[] args)
    {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite()
    {
        TestSuite suite = new TestSuite("Cert Tests");

        if (Security.getProvider("SC") == null)
        {
            Security.addProvider(new BouncyCastleProvider());
        }

        suite.addTestSuite(AllTests.class);
        suite.addTestSuite(BcAttrCertSelectorTest.class);
        suite.addTestSuite(BcAttrCertSelectorTest.class);
        suite.addTestSuite(BcAttrCertTest.class);
        suite.addTestSuite(BcCertTest.class);
        suite.addTestSuite(BcPKCS10Test.class);
        suite.addTest(ConverterTest.suite());

        return new BCTestSetup(suite);
    }

    static class BCTestSetup
        extends TestSetup
    {
        public BCTestSetup(Test test)
        {
            super(test);
        }

        protected void setUp()
        {
            Security.addProvider(new BouncyCastleProvider());
        }

        protected void tearDown()
        {
            Security.removeProvider("SC");
        }
    }

}