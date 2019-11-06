package it.sevenbits.reader.implementation;

import it.sevenbits.reader.IReader;
import it.sevenbits.reader.ReaderException;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Class for reading unformatted strings from file
 */
public class FileReader implements IReader {
    private int currentSymbol;
    private Reader reader;
private FileReader(){};
    /**
     * Constructor that create stream for reading from file
     * @param path path file
     * @throws ReaderException if was trouble with reading from file
     */
    public FileReader(final String path) throws ReaderException {
        try {
            reader = Files.newBufferedReader(Paths.get(path), Charset.forName("UTF-8"));
            currentSymbol = reader.read();
        } catch (IOException ex) {
            throw new ReaderException("Cannot create stream");
        }
    }

    /**
     * Check can read next symbol or not
     * @return true if can else false
     */
    @Override
    public boolean hasNext() {
        return currentSymbol != -1;
    }

    /**
     * read next symbol from stream
     * @return next symbol
     * @throws ReaderException if was trouble with reading
     */
    @Override
    public char read() throws ReaderException {
        if (!hasNext()) {
            throw new ReaderException("Stream is empty");
        }
        try {
            int temp = currentSymbol;
            currentSymbol = reader.read();
            return (char) temp;
        } catch (IOException ex) {
            throw new ReaderException("Cannot read from file", ex);
        }
    }

    /**
     * close stream
     * @throws ReaderException if was trouble with closes stream
     */
    @Override
    public void close() throws ReaderException {
        try {
            reader.close();
        } catch (IOException ex) {
            throw new ReaderException("Cannot close stream", ex);
        }
    }
}