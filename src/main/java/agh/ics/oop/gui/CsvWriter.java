package agh.ics.oop.gui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvWriter {

    public CsvWriter(){ }

    //https://mkyong.com/java/how-to-export-data-to-csv-file-java/
    private String convertToCsvFormat(final String[] line) {
        return convertToCsvFormat(line, ",");
    }

    private String convertToCsvFormat(final String[] line, final String separator) {
        return convertToCsvFormat(line, separator, true);
    }


    private String convertToCsvFormat(
            final String[] line,
            final String separator,
            final boolean quote) {

        return Stream.of(line)
                .map(l -> formatCsvField(l, quote))
                .collect(Collectors.joining(separator));
    }

    private String formatCsvField(final String field, final boolean quote) {

        String result = field;

        if (result.contains(",")
                || result.contains("\"")
                || result.contains("\n")
                || result.contains("\r\n")) {

            result = result.replace("\"", "\"\"");

            result = "\"" + result + "\"";

        } else {
            if (quote) {
                result ="\""  + result + "\"";
            }
        }
        return result;
    }
    public void writeToCsvFile(List<String[]> list, File file) throws IOException {

        List<String> collect = list.stream()
                .map(this::convertToCsvFormat)
                .collect(Collectors.toList());

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (String line : collect) {
                bw.write(line);
                bw.newLine();
            }
        }
    }
}
