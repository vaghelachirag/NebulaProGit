package com.nebulacompanies.ibo.util;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;

/**
 * Created by Palak Mehta on 17-Feb-18.
 */

public class RankValueFormatter implements YAxisValueFormatter {

    @Override
    public String getFormattedValue(float value, YAxis axis) {
        //return mFormat.format(value) + " $";
        //int rankId = Config.rankValues.length;
        String appendix = "Member";
        appendix = Config.rankValues[(int) value];

        return appendix;

    }
}
