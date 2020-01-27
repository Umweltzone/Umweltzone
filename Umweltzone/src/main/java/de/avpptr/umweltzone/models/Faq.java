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

package de.avpptr.umweltzone.models;

import androidx.annotation.NonNull;

public class Faq {

    public int position;

    public String question;

    public String answer;

    public String sourceUrl;

    public String label;

    @NonNull
    @Override
    public String toString() {
        return "Position: " + position +
                ", Question: " + question +
                ", Answer: " + answer +
                ", SourceUrl: " + sourceUrl +
                ", Label: " + label;
    }

    public String toStringShort() {
        return position + "_" + label;
    }
}
