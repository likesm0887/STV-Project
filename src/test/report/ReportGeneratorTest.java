package report;

import org.junit.Before;
import org.junit.Test;
import useCase.Script;
import useCase.ScriptInformation;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

public class ReportGeneratorTest {
    ScriptReportGenerator rg;
    ScriptInformation scriptInformation;
    @Before
    public void setUp() {
        rg = new ScriptReportGenerator();
        scriptInformation = new ScriptInformation("Test Name");
        scriptInformation.setExecutionTime(10000);
        scriptInformation.executionComplete();
    }

    @Test
    public void generateScriptInfoHeader() {
        System.out.println(rg.generateScriptInfoHeader(scriptInformation));
        assertThat(rg.generateScriptInfoHeader(scriptInformation),
                allOf(containsString("Starting"),
                        containsString("Test Name")));
    }

    @Test
    public void generateScriptInfoFooter() {
        System.out.println(rg.generateScriptInfoFooter(scriptInformation));
        assertThat(rg.generateScriptInfoFooter(scriptInformation),
                allOf(containsString("Spending Time"),
                        containsString("10.0s"),
                        containsString("Status"),
                        containsString("PASS")));
    }

    @Test
    public void generateScriptInfoBody() {
        scriptInformation.executionFailed("This is a error.");
        System.out.println(rg.generateScriptInfoBody(scriptInformation));
        assertThat(rg.generateScriptInfoBody(scriptInformation),
                allOf(containsString("Assert Error"),
                        containsString("This is a error")));
    }

    @Test
    public void generateScriptSummaryHeader() {
        ScriptInformation scriptInformation1 = new ScriptInformation("Test2");
        scriptInformation1.setExecutionTime(10000);
        List<ScriptInformation> scriptInformations = Arrays.asList(scriptInformation, scriptInformation1);
        System.out.println(rg.generateScriptSummary(scriptInformations));
        assertThat(rg.generateScriptSummary(scriptInformations),
                allOf(containsString("ScriptName"),
                        containsString("Times"),
                        containsString("Status")));
    }

    @Test
    public void generateScriptSummaryBody() {
        ScriptInformation scriptInformation1 = new ScriptInformation("Test2");
        scriptInformation1.setExecutionTime(10000);
        List<ScriptInformation> scriptInformations = Arrays.asList(scriptInformation, scriptInformation1);
        System.out.println(rg.generateScriptSummary(scriptInformations));
        assertThat(rg.generateScriptSummary(scriptInformations),
                allOf(containsString("Total"),
                        containsString("333.33 min"),
                        containsString("1 passed"),
                        containsString("1 failed")));
    }

    @Test
    public void generateScriptSummaryFooter() {
        ScriptInformation scriptInformation1 = new ScriptInformation("Test2");
        scriptInformation1.setExecutionTime(10000);
        List<ScriptInformation> scriptInformations = Arrays.asList(scriptInformation, scriptInformation1);
        List<String> result = rg.getScriptBodyList(scriptInformations);

        for(int i = 0; i < scriptInformations.size(); i++) {
            System.out.print(result.get(i));
            assertThat(result.get(i),
                    allOf(containsString(scriptInformations.get(i).getScriptName()),
                            containsString(String.valueOf(scriptInformations.get(i).getExecutionTime())),
                            containsString(scriptInformations.get(i).resultStatus())));
        }
    }

    @Test
    public void test() {
    }

}