/*
 *  Copyright (C) 2014  Tobias Preuss
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

package de.avpptr.umweltzone.models;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Arrays;

import de.avpptr.umweltzone.utils.GeoPoint;

public class CircuitDeserializer extends JsonDeserializer<Circuit> {

    @Override
    public Circuit deserialize(JsonParser parser, DeserializationContext context)
            throws IOException {
        Circuit circuit = new Circuit();
        ObjectCodec codec = parser.getCodec();
        GeoPoint[] coordinates = codec.readValue(parser, GeoPoint[].class);
        circuit.setCoordinates(Arrays.asList(coordinates));
        return circuit;
    }

}
