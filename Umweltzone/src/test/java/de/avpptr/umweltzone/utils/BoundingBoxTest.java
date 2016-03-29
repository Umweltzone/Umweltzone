package de.avpptr.umweltzone.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.parceler.Parcels;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class BoundingBoxTest {

    @Test
    public void wrapUnwrapParcelable() {
        BoundingBox boundingBox = Parcels.unwrap(Parcels.wrap(getBoundingBox()));
        assertThat(boundingBox).isEqualTo(getBoundingBox());
    }

    private BoundingBox getBoundingBox() {
        GeoPoint southWest = new GeoPoint(52.464504, 13.282079);
        GeoPoint northEast = new GeoPoint(52.549808, 13.475550);
        return new BoundingBox(southWest, northEast);
    }

}
