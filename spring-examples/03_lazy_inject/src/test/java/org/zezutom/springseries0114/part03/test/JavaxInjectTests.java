package org.zezutom.springseries0114.part03.test;

import org.zezutom.springseries0114.part03.inject.Car;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ="classpath:spring-config.xml")
public class JavaxInjectTests {

    @Autowired
    private Car car;

    @Test
    public void injectionPointWorksAsExpected() {
        assertNotNull(car.getDriver());
        assertNotNull(car.getPassenger());
    }

    @Test
    public void injectionRespectsThePrototypeScope() {
         assertFalse(car.getDriver().equals(car.getPassenger()));
    }
}
