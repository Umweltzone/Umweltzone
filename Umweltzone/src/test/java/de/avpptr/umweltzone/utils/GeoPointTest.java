package de.avpptr.umweltzone.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.parceler.Parcels;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class GeoPointTest {

    @Test
    public void wrapUnwrapParcelable() {
        GeoPoint geoPoint = Parcels.unwrap(Parcels.wrap(getGeoPoint()));
        assertThat(geoPoint).isEqualTo(getGeoPoint());
    }

    private GeoPoint getGeoPoint() {
        return new GeoPoint(52.464504, 13.282079);
    }

}
