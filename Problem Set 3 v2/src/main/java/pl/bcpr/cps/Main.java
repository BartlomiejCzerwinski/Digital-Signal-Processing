package pl.bcpr.cps;

import pl.bcpr.cps.executionmode.graphical.GraphicalMode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    /**
     * Params for cmd mode
     * <p>
     * Generate - generate, filename to save, signal type, params for signal
     * Signal type abbreviation:
     * uni_noise - rangeStart, rangeLength, amplitude
     * gauss_noise -rangeStart, rangeLength, amplitude
     * sin - rangeStart, rangeLength, amplitude, term
     * sin_one_half - rangeStart, rangeLength, amplitude, term
     * sin_two_half - rangeStart, rangeLength, amplitude, term
     * rect - rangeStart, rangeLength, amplitude, term, fulfillment
     * rect_symm - rangeStart, rangeLength, amplitude, term, fulfillment
     * triang - rangeStart, rangeLength, amplitude, term, fulfillment
     * unit_jump - rangeStart, rangeLength, amplitude, jumpMoment
     * unit_impulse - rangeStart, rangeLength, sampleRate, amplitude, jumpMoment.intValue()
     * impulse_noise - rangeStart, rangeLength, sampleRate, amplitude,probability
     * --
     * low_fil - sampleRate, filterRow, cuttingFrequency,
     * windowType: win_rect || win_ham || win_han || win_bla
     * --
     * band_fil - sampleRate, filterRow, cuttingFrequency,
     * windowType: win_rect || win_ham || win_han || win_bla
     * --
     * high_fil - sampleRate, filterRow, cuttingFrequency,
     * windowType: win_rect || win_ham || win_han || win_bla
     * <p>
     * Sampling - sampl, filename to read, filename to save, sampleRate
     * <p>
     * Quantization - quant, filename to read, filename to save, type, quantization level
     * Type abbreviation :
     * qu_trun, qu_roud
     * <p>
     * Reconstruction - recon, filename to read, filename to save, type, sinc param(only for sinc)
     * Type abbreviation:
     * zero_order, first_order, sinc
     * <p>
     * Comparison - comp, first filename to read, second filename to read
     * <p>
     * Draw charts - draw, filenames to read...
     * <p>
     * Convolution - conv, first filename to read, second filename to read, filename to save
     *
     * Correlation - corr, first filename to read, second filename to read, filename to save
     */

    /*------------------------ FIELDS REGION ------------------------*/
    private static List<String> mainArgs;

    /*------------------------ METHODS REGION ------------------------*/
    public static List<String> getMainArgs() {
        return Collections.unmodifiableList(mainArgs);
    }

    public static void main(String[] args) {
        mainArgs = Arrays.asList(args);

        if (args.length == 0) {
            new GraphicalMode().main();
        }
    }
}

