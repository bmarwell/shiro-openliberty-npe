/*
 *  Copyright 2018 The shiro-openliberty-npe contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.github.bmhm.shiro.servlet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloServiceIT {
  private static final Logger LOG = LoggerFactory.getLogger(HelloServiceIT.class);

  private static String URL;

  @BeforeAll
  public static void init() {
    String port = System.getProperty("http.port");
    String war = System.getProperty("war.name");
    URL = "http://localhost:" + port + "/" + war + "/" + "api/v1/hello";
  }

  @Test
  public void testServlet() throws Exception {
    HttpClient client = new HttpClient();
    GetMethod method = new GetMethod(URL);

    try {
      LOG.debug("Executing query: GET to [{}}].", method.getURI());
      int statusCode = client.executeMethod(method);

      assertEquals(HttpStatus.SC_OK, statusCode, "HTTP GET failed");

      String response = method.getResponseBodyAsString(1000);

      assertTrue(response.contains("Hello! How are you today?"),
          "Unexpected response body");
    } finally {
      method.releaseConnection();
    }
  }
}
