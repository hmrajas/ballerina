/*
 * Copyright (c) 2017, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 * <p>
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.wso2.ballerina.nativeimpl.convertors;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.wso2.ballerina.core.interpreter.SymScope;
import org.wso2.ballerina.core.model.BallerinaFile;
import org.wso2.ballerina.core.model.SymbolName;
import org.wso2.ballerina.core.model.values.BJSON;
import org.wso2.ballerina.core.model.values.BString;
import org.wso2.ballerina.core.model.values.BValue;
import org.wso2.ballerina.core.model.values.BXML;
import org.wso2.ballerina.core.runtime.internal.BuiltInNativeConstructLoader;
import org.wso2.ballerina.core.runtime.internal.GlobalScopeHolder;
import org.wso2.ballerina.core.utils.ParserUtils;
import org.wso2.ballerina.lang.util.Functions;

public class NativeTypeConvertersTest {
    private BallerinaFile bFile;

    @BeforeClass
    public void setup() {
        // Add Native functions.
        SymScope symScope = GlobalScopeHolder.getInstance().getScope();
        if (symScope.lookup(new SymbolName("ballerina.lang.convertors:_xml->_json")) == null) {
            BuiltInNativeConstructLoader.loadConstructs();
        }
        bFile = ParserUtils.parseBalFile("lang/expressions/type-conversion.bal", symScope);
    }

    @Test
    public void testXMLToJSON() {
        BValue[] args = {new BXML("<name>chanaka</name>")};
        BValue[] returns = Functions.invoke(bFile, "xmltojson", args);
        Assert.assertTrue(returns[0] instanceof BJSON);
        final String expected = "{\"name\":\"chanaka\"}";
        Assert.assertEquals(returns[0].stringValue(), expected);
    }

    @Test
    public void testJSONToXML() {
        BValue[] args = {new BJSON("{\"name\":\"chanaka\"}")};
        BValue[] returns = Functions.invoke(bFile, "jsontoxml", args);
        Assert.assertTrue(returns[0] instanceof BXML);
        final String expected = "<name>chanaka</name>";
        Assert.assertEquals(returns[0].stringValue(), expected);
    }

    @Test
    public void testStringToJSON() {
        BValue[] args = {new BString("{\"name\":\"chanaka\"}")};
        BValue[] returns = Functions.invoke(bFile, "stringtojson", args);
        Assert.assertTrue(returns[0] instanceof BJSON);
        final String expected = "{\"name\":\"chanaka\"}";
        Assert.assertEquals(returns[0].stringValue(), expected);
    }

    @Test
    public void testStringToXML() {
        BValue[] args = {new BString("<name>chanaka</name>")};
        BValue[] returns = Functions.invoke(bFile, "stringtoxml", args);
        Assert.assertTrue(returns[0] instanceof BXML);
        final String expected = "<name>chanaka</name>";
        Assert.assertEquals(returns[0].stringValue(), expected);
    }

    @Test
    public void testXMLToString() {
        BValue[] args = {new BXML("<name>chanaka</name>")};
        BValue[] returns = Functions.invoke(bFile, "xmltostring", args);
        Assert.assertTrue(returns[0] instanceof BString);
        final String expected = "<name>chanaka</name>";
        Assert.assertEquals(returns[0].stringValue(), expected);
    }

    @Test
    public void testJSONToString() {
        BValue[] args = {new BJSON("{\"name\":\"chanaka\"}")};
        BValue[] returns = Functions.invoke(bFile, "jsontostring", args);
        Assert.assertTrue(returns[0] instanceof BString);
        final String expected = "{\"name\":\"chanaka\"}";
        Assert.assertEquals(returns[0].stringValue(), expected);
    }
}
