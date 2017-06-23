package org.zezutom.springseries0114.part02.test;

import org.zezutom.springseries0114.part02.qualifier.ICapital;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config.xml")
public class LazyVsQualifierTests {

    @Qualifier(value = "prague")
    @Autowired
    private ICapital capital;

    private Profile profile = ProfileBuilder
            .newBuilder()
            .name("Spring logs")
            .filePath("target/spring.log").onLocalhost().build();

    @Test
    public void theFirstIsUsedByDefault() {
        assertEquals("Prague", capital.name());
    }

    @Test
    public void aLazyIsNotPreloaded() {
        assertThat(executing(grep(regularExpression("Creating instance of bean(.*)london"), on(profile))).totalLines(), is(0));
    }

    @Test
    public void aPrototypeIsNotPreloaded() {
        assertThat(executing(grep(regularExpression("Creating instance of bean(.*)stockholm"), on(profile))).totalLines(), is(0));
    }
}
