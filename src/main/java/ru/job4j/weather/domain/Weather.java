package ru.job4j.weather.domain;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "of")
public class Weather {

    private int id;
    private String city;
    private int temperature;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Weather weather = (Weather) o;
        return id == weather.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
