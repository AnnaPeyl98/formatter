package it.sevenbits;

import it.sevenbits.formatter.FormatterException;
import it.sevenbits.formatter.IFormatter;
import it.sevenbits.formatter.implementation.LexerFormatter;
import it.sevenbits.reader.IReader;
import it.sevenbits.reader.implementation.FileReader;
import it.sevenbits.writer.IWriter;
import it.sevenbits.writer.implementation.FileWriter;
import it.sevenbits.reader.ReaderException;
import it.sevenbits.writer.WriterException;

/**
 * Main class for running formatter
 */
public final class Main {
    /**
     * That could not call the constructor without parameters
     */
    private Main() {
    }

    /**
     * Method that run formatter
     *
     * @param args args console arguments
     */
    public static void main(final String[] args) {
        try {
            IReader testTwo = new FileReader(args[0]);

            IWriter stringWriterForTestTwo = new FileWriter(args[1]);

            IFormatter bf = new LexerFormatter();


            System.out.println("Formatter formatted code. Check file out.txt.");
            bf.format(testTwo, stringWriterForTestTwo);


        } catch (ReaderException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (FormatterException e) {
            e.printStackTrace();
        }
    }
}