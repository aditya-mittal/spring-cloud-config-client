package example.stores.rest.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PropertyResponse {
  String propertyA;
  String propertyB;

  @JsonCreator
  public PropertyResponse(@JsonProperty("propertyA") String propertyA, @JsonProperty("propertyB") String propertyB) {
    this.propertyA = propertyA;
    this.propertyB = propertyB;
  }

  public String getPropertyA() {
    return propertyA;
  }

  public String getPropertyB() {
    return propertyB;
  }
}
