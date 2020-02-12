package bg.sofia.uni.fmi.mjt.stylechecker;

import bg.sofia.uni.fmi.mjt.stylechecker.checkers.*;

import java.io.*;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class StyleChecker {
    private static final String WILDCARD_IMPORT_CHECK = "wildcard.import.check.active";
    private static final String BRACKET_CHECK = "opening.bracket.check.active";
    private static final String LENGTH_CHECK = "length.of.line.check.active";
    private static final String SIMPLE_STATEMENT_CHECK = "statements.per.line.check.active";
    private static final String LINE_LENGTH_LIMIT = "line.length.limit";

    private static final String BRACKET_ERROR = "// FIXME Opening brackets should be placed " +
            "on the same line as the declaration";
    private static final String LENGTH_ERROR = "// FIXME Length of line should not exceed %s characters";
    private static final String SINGLE_STATEMENT_ERROR = "// FIXME Only one statement per line is allowed";
    private static final String WILDCARD_IMPORT_ERROR = "// FIXME Wildcards are not allowed in import statements";

    private static final String DEFAULT_CHECK_MODE = "true";
    private static final String LINE_LENGTH_MAX = "100";

    private Properties properties;
    private Set<CodeChecker> checks;

    public StyleChecker() {
        DefaultProperties();
        checks = new HashSet<>();
        CheckProperties();
    }
    public StyleChecker(InputStream input)
    {
        DefaultProperties();
        checks = new HashSet<>();
        if(input != null){
            try {
                getProperties().load(input);
            }
            catch(IOException exception) {
                System.out.println("No stream given!");
            }
            CheckProperties();
        }

    }
    public void checkStyle(InputStream source, OutputStream output)
    {
        try {
            BufferedReader read = new BufferedReader(new InputStreamReader(source));
            PrintWriter writer = new PrintWriter(output);
            String singleLine = read.readLine();
            while (singleLine != null) {
                if (singleLine.trim().equals("")) {
                    writer.println(singleLine);
                    continue;
                } else {
                    Set<String> errors = new HashSet<>();
                    for (CodeChecker codeChecker : checks) {
                        if (codeChecker.checkErrors(singleLine)) {
                            errors.add(codeChecker.getErrorMessage());
                        }
                    }
                    if (!errors.isEmpty()) {
                        for (String error : errors) {
                            writer.println(error);
                        }
                    }
                    writer.println(singleLine);
                }
                singleLine = read.readLine();
            }
            writer.flush();
        }
        catch (IOException exception)
        {
            throw new RuntimeException("Cannot read from source stream", exception);
        }
    }
    public void DefaultProperties()
    {
        properties = new Properties();
        String[] checks = {WILDCARD_IMPORT_CHECK,BRACKET_CHECK, LENGTH_CHECK,SIMPLE_STATEMENT_CHECK};
        for(String check : checks)
        {
            getProperties().setProperty(check, DEFAULT_CHECK_MODE); // property - true;
        }
        getProperties().setProperty(LINE_LENGTH_LIMIT, LINE_LENGTH_MAX);
    }
    public void CheckProperties()
    {
        if(Boolean.parseBoolean(getProperties().getProperty(BRACKET_CHECK)))
        {
            checks.add(new BracketCheck(BRACKET_ERROR));
        }
        if(Boolean.parseBoolean(getProperties().getProperty(WILDCARD_IMPORT_CHECK)))
        {
            checks.add(new WildcardImportCheck(WILDCARD_IMPORT_ERROR));
        }
        if(Boolean.parseBoolean(getProperties().getProperty(LENGTH_CHECK)))
        {
            int lineLength = Integer.parseInt(getProperties().getProperty(LINE_LENGTH_LIMIT));
            checks.add(new LengthCheck(LENGTH_ERROR, lineLength));
        }
        if(Boolean.parseBoolean(getProperties().getProperty(SIMPLE_STATEMENT_CHECK)))
        {
            checks.add(new SingleStatementCheck(SINGLE_STATEMENT_ERROR));
        }
    }

    public Properties getProperties() {
        return properties;
    }
}
