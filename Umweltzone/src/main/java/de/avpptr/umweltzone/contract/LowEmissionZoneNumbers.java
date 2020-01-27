/*
 *  Copyright (C) 2020  Tobias Preuss
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.avpptr.umweltzone.contract;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class LowEmissionZoneNumbers {

    @IntDef(value = {NONE, RED, YELLOW, GREEN, LIGHT_BLUE, DARK_BLUE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Color {

    }

    public static final int NONE = -1;

    public static final int RED = 2;

    public static final int YELLOW = 3;

    public static final int GREEN = 4;

    public static final int LIGHT_BLUE = 5;

    public static final int DARK_BLUE = 6;

    @Color
    public static int getNext(@Color int zoneNumber) {
        switch (zoneNumber) {
            case RED:
                return YELLOW;
            case YELLOW:
                return GREEN;
            case GREEN:
                // continue
            case LIGHT_BLUE:
                // continue
            case DARK_BLUE:
                // continue
            case NONE:
                // continue
        }
        throw new IllegalArgumentException("Cannot return next zone number after: " + zoneNumber);
    }

}
