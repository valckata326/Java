package bg.sofia.uni.fmi.mjt.stylechecker;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static junit.framework.TestCase.assertEquals;
// if problems appear with this tests, replace "\n" with "\r\n".
public class StyleCheckerTest {
    private static final String BRACKET_ERROR = "// FIXME Opening brackets should be placed " +
            "on the same line as the declaration";
    private static final String LENGTH_ERROR_DEFAULT = "// FIXME Length of line should not exceed 100 characters";
    private static final String LENGTH_ERROR_CUSTOM = "// FIXME Length of line should not exceed 80 characters";
    private static final String SINGLE_STATEMENT_ERROR = "// FIXME Only one statement per line is allowed";
    private static final String WILDCARD_IMPORT_ERROR = "// FIXME Wildcards are not allowed in import statements";
    private static final String DEFAULT_CHECK_MODE = "true";
    private static final String LINE_LENGTH_MAX = "100";
    private static final String customProperties = "wildcard.import.check.active=false\n"
            + "line.length.limit=80\n"
            + "length.of.line.check.active=true\n"
            + "opening.bracket.check.active=true";
    private static final String defaultProperties = "wildcard.import.check.active=true\n"
            + "statements.per.line.check.active=true\n"
            + "line.length.limit=100\n"
            + "length.of.line.check.active=true\n"
            + "opening.bracket.check.active=true";
    @BeforeClass
    public static void preparation()
    {

    }
    private  void testOneLineStyleChecker(String message, String lineOfCode,
                                          StyleChecker styleChecker, String finalCode) {
        ByteArrayInputStream input = new ByteArrayInputStream(lineOfCode.getBytes());
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        styleChecker.checkStyle(input, output);
        assertEquals(message, finalCode, new String(output.toByteArray()).trim());
    }
    @Test
    public void testWildcardsRightWithDefaultStyleChecker()
    {
        StyleChecker styler = new StyleChecker();
        String importLine = "import java.util.;";
        String assertMessage = "There is not wildcards in this import and it must not produce an error";
        testOneLineStyleChecker(assertMessage, importLine, styler,importLine);
    }
    @Test
    public void testWildcardsWrongWithDefaultStyleChecker() {
        StyleChecker styler = new StyleChecker();
        String importLine = "import java.util.*;";
        String assertMessage = "Wildcards are not allowed in import statements";
        testOneLineStyleChecker(assertMessage, importLine, styler,
                String.format("%s\n%s", WILDCARD_IMPORT_ERROR, importLine.trim()));
    }

    @Test
    public void testOneStatementRightWithDefaultStyleChecker()
    {
        StyleChecker styler = new StyleChecker();
        String oneLine = "doSomething();;;;;;;;";
        String assertMessage = "There is only 1 statement so it must not produce an error";
        testOneLineStyleChecker(assertMessage,oneLine,styler,oneLine);
    }
    @Test
    public void testOneStatementWrongWithDefaultStyleChecker(){
        StyleChecker styler = new StyleChecker();
        String oneLine = "doSomething(); counter++; doSomethingElse(counter);";
        String assertMessage = "You are allowed to use only one statement at a single line of code";
        testOneLineStyleChecker(assertMessage,oneLine,styler,
                String.format("%s\n%s", SINGLE_STATEMENT_ERROR, oneLine.trim()));
    }
    @Test
    public void testBracketsOnNewLineRightWithDefaultStyleChecker()
    {
        StyleChecker styler = new StyleChecker();
        String brackets = "public static void main(String[] args) {";
        String assertMessage = "Opening bracket is on the same line as the statement so it must not produce an error";
        testOneLineStyleChecker(assertMessage,brackets,
                styler,brackets);
    }
    @Test
    public void testBracketsOnNewLineWrongWithDefaultStyleChecker(){
        StyleChecker styler = new StyleChecker();
        String brackets = "{";
        String assertMessage = "Opening brackets should not be on a separate line";
        testOneLineStyleChecker(assertMessage,brackets,
                styler,String.format("%s\n%s", BRACKET_ERROR, brackets.trim()));
    }
    @Test
    public void testLineLengthRightWithDefaultStyleChecker()
    {
        StyleChecker styler = new StyleChecker();
        String line = "This line of code is correct because its length is shorter than 100 symbols;";
        String assertMessage = "This line of code is longer than 100 symbols!";
        testOneLineStyleChecker(assertMessage,line,styler,line);
    }
    @Test
    public void testLineLengthWrongWithDefaultStyleChecker()
    {
        StyleChecker styler = new StyleChecker();
        String line = "This line of code must produce length error"
                    + " because of the fact that it must not exceed 100 symbols per a single one;";
        String assertMessage = "This line of code is longer than 100 symbols!";
        testOneLineStyleChecker(assertMessage,line,styler,String.format("%s\n%s", LENGTH_ERROR_DEFAULT, line.trim()));
    }
    @Test
    public void testAllErrorsTogetherWhenTheLineOfCodeIsFineWithDefaultStyleChecker()
    {
        StyleChecker styler = new StyleChecker();
        String line = "int a;";
        String assertMessage = "This line of code is completely fine, and should not produce any sort of error!";
        testOneLineStyleChecker(assertMessage,line,styler,line);
    }



    // I know you won't like it, but sorry, it will stay this way! :D
    @Test
    public void testSingleStatementRightWithCustomStyleCheckerWhenErrorIsDefaultOn() {
        StyleChecker styler = new StyleChecker(new ByteArrayInputStream(customProperties.getBytes()));
        String oneLine = "doSomething();;;;;;;;";
        String assertMessage = "There is only 1 statement so it must not produce an error";
        testOneLineStyleChecker(assertMessage,oneLine,styler, oneLine);
    }
    @Test
    public void testSingleStatementWrongWithCustomStyleCheckerWhenErrorIsDefaultON()
    {
        StyleChecker styler = new StyleChecker(new ByteArrayInputStream(customProperties.getBytes()));
        String oneLine = "doSome;thing();;;;;;;;";
        String assertMessage = "There is more than one statement so it must produce an error";
        testOneLineStyleChecker(assertMessage,oneLine,styler, String.format("%s\n%s", SINGLE_STATEMENT_ERROR, oneLine.trim()));
    }
    @Test
    public void testWildcardsWrongWithCustomStyleCheckerWhenErrorOff()
    {
        StyleChecker styler = new StyleChecker(new ByteArrayInputStream(customProperties.getBytes()));
        String importLine = "import java.util.*;";
        String assertMessage = "Wildcards error is turned off so it must produce an error";
        testOneLineStyleChecker(assertMessage, importLine, styler,importLine);
    }
    @Test
    public void testWildCardsRightWithCustomStyleCheckerWhenErrorOff()
    {
        StyleChecker styler = new StyleChecker(new ByteArrayInputStream(customProperties.getBytes()));
        String importLine = "import java.util.;";
        String assertMessage = "Wildcards error is turned off so it must produce an error";
        testOneLineStyleChecker(assertMessage, importLine, styler,importLine);
    }
    @Test
    public void testBracketsRightWithCustomStyleCheckerWhenErrorOn()
    {
        StyleChecker styler = new StyleChecker();
        String brackets = "public static void main(String[] args) {";
        String assertMessage = "Opening bracket is on the same line as"
                + " the statement so it must not produce an error";
        testOneLineStyleChecker(assertMessage,brackets,
                styler,brackets);
    }
    @Test
    public void testBracketsWrongWithCustomStyleCheckerWhenErrorOn()
    {
        StyleChecker styler = new StyleChecker(new ByteArrayInputStream(customProperties.getBytes()));
        String brackets = "{";
        String assertMessage = "Opening bracket should not be placed on new line!";
        testOneLineStyleChecker(assertMessage,brackets,
                styler,String.format("%s\n%s",BRACKET_ERROR,brackets.trim()));
    }
    @Test
    public void testLineLengthWrongWithCustomStyleCheckerWhenErrorOn()
    {
        StyleChecker styler = new StyleChecker(new ByteArrayInputStream(customProperties.getBytes()));
        String line = "This line of code should produce line length error because it is longer than 80 symbols long!;";
        String assertMessage = "This line of code is longer than 80 symbols!";
        testOneLineStyleChecker(assertMessage,line,styler,String.format("%s\n%s", LENGTH_ERROR_CUSTOM, line.trim()));
    }
    @Test
    public void testLineLengthRightWithCustomStyleCheckerWhenErrorOn()
    {
        StyleChecker styler = new StyleChecker(new ByteArrayInputStream(customProperties.getBytes()));
        String line = "This line of code should not produce length error!;";
        String assertMessage = "This line is shorter than 80 symbols so it must not produce line length error!";
        testOneLineStyleChecker(assertMessage,line,styler, line);
    }
}
