/*
 *     Orbit, a versatile image analysis software for biological image-based quantification.
 *     Copyright (C) 2009 - 2017 Actelion Pharmaceuticals Ltd., Gewerbestrasse 16, CH-4123 Allschwil, Switzerland.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

/**
 * Load an image, iterate over tiles, access pixel data and compute mean intensities.
 * Change image id!
 */

import com.actelion.research.orbit.beans.RawDataFile
import com.actelion.research.orbit.imageAnalysis.components.RecognitionFrame
import com.actelion.research.orbit.imageAnalysis.dal.DALConfig
import java.awt.image.Raster

RawDataFile rdf = DALConfig.imageProvider.LoadRawDataFile(7683);
RecognitionFrame recognitionFrame = new RecognitionFrame(rdf);

long[] rgb = [0,0,0];
long cnt=0;
recognitionFrame.bimg.image.tiles.each {
    Raster r = it;
    for (int y=r.minY; y<r.minY + r.height; y++)
        for (int x=r.minX; x<r.minX + r.width; x++) {
                for (int c = 0; c < rgb.length; c++) {
                    rgb[c] += r.getSample(x, y, c);
                }
                cnt++;
        }
}

rgb = rgb.collect{it/cnt};
println "r:" +rgb[0]+" g:"+rgb[1]+" b:"+rgb[2]
DALConfig.getImageProvider().close();  // only close if not executed within Orbit