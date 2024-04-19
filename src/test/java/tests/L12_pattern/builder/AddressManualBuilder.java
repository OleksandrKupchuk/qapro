package tests.L12_pattern.builder;

public class AddressManualBuilder {
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String district;
    private String apartmentNumber;

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getDistrict() {
        return district;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    private AddressManualBuilder(){

    }

    public static class Builder{
        private AddressManualBuilder address;
        private String street;
        private String city;
        private String state;
        private String postalCode;
        private String district;
        private String apartmentNumber;
        public Builder(){
            address = new AddressManualBuilder();
        }

        public Builder setStreet(String street) {
            address.street = street;
            return this;
        }

        public Builder setCity(String city) {
            address.city = city;
            return this;
        }

        public Builder setState(String state) {
            address.state = state;
            return this;
        }

        public Builder setPostalCode(String postalCode) {
            address.postalCode = postalCode;
            return this;
        }

        public Builder setDistrict(String district) {
            address.district = district;
            return this;
        }

        public Builder setApartmentNumber(String apartmentNumber) {
            address.apartmentNumber = apartmentNumber;
            return this;
        }

        public AddressManualBuilder build(){
            return address;
        }
    }
}
