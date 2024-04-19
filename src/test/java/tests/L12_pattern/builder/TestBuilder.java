package tests.L12_pattern.builder;

import org.testng.annotations.Test;

public class TestBuilder {
    @Test
    public void testManualBuilder(){
        AddressManualBuilder addressManualBuilder = new AddressManualBuilder.Builder()
                .setCity("Kyiv")
                .setStreet("dsfsdf")
                .setApartmentNumber("5")
                .build();

        AddressManualBuilder addressManualBuilder2 = new AddressManualBuilder.Builder()
                .setCity("Kyiv")
                .setStreet("dsfsdf")
                .setApartmentNumber("5")
                .setState("sdfsdf")
                .build();

        System.out.println(addressManualBuilder.getState());
        System.out.println(addressManualBuilder2.getState());

    }

    @Test
    public void testLombokBuilder(){
        CarLombokBuilder car = CarLombokBuilder.builder()
                .speed(200)
                .acceleration(22)
                .build();

        CarLombokBuilder car2 = CarLombokBuilder.builder()
                .speed(200)
                .acceleration(22)
                .name("Honda")
                .build();

        System.out.println(car.getName());
        System.out.println(car2.getName());

    }
}
