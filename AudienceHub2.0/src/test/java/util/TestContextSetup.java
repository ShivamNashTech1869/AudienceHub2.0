package util;

import pages.PageObjectManager;
import resources.Base;
import util.Common;

import java.io.IOException;

public class TestContextSetup {
public Base base;
public PageObjectManager pom;
public Common common;
public TestContextSetup() throws IOException {
    base=new Base();
    pom=new PageObjectManager(base.initializeDriver());
    common = new Common(base.initializeDriver());
}
}
