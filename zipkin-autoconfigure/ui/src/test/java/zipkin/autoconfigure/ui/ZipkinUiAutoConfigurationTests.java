/**
 * Copyright 2015-2016 The OpenZipkin Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package zipkin.autoconfigure.ui;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Test;
import org.springframework.boot.autoconfigure.PropertyPlaceholderAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.EnvironmentTestUtils.addEnvironment;

public class ZipkinUiAutoConfigurationTests {

  AnnotationConfigApplicationContext context;

  @After
  public void close() {
    if (context != null) {
      context.close();
    }
  }

  @Test
  public void indexHtmlFromClasspath() {
    context = new AnnotationConfigApplicationContext();
    context.register(PropertyPlaceholderAutoConfiguration.class, ZipkinUiAutoConfiguration.class);
    context.refresh();

    assertThat(context.getBean(ZipkinUiAutoConfiguration.class).indexHtml)
        .isNotNull();
  }

  @Test
  public void canOverridesProperty_defaultLookback() {
    context = new AnnotationConfigApplicationContext();
    addEnvironment(context, "zipkin.ui.defaultLookback:100");
    context.register(PropertyPlaceholderAutoConfiguration.class, ZipkinUiAutoConfiguration.class);
    context.refresh();

    assertThat(context.getBean(ZipkinUiProperties.class).getDefaultLookback())
        .isEqualTo(100);
  }
}
