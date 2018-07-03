package plugins.warnings.assertions;

import org.jenkinsci.test.acceptance.plugins.warnings.white_mountains.DetailsTableRow;

/**
 * {@link DetailsTableRow} specific assertions - Generated by CustomAssertionGenerator.
 * <p>
 * Although this class is not final to allow Soft assertions proxy, if you wish to extend it, extend {@link
 * AbstractDetailsTableRowAssert} instead.
 */
@javax.annotation.Generated(value = "assertj-assertions-generator")
public class DetailsTableRowAssert extends
        AbstractDetailsTableRowAssert<DetailsTableRowAssert, DetailsTableRow> {
    /**
     * Creates a new {@link DetailsTableRowAssert} to make assertions on actual DetailsTableRow.
     *
     * @param actual
     *         the DetailsTableRow we want to make assertions on.
     */
    public DetailsTableRowAssert(DetailsTableRow actual) {
        super(actual, DetailsTableRowAssert.class);
    }

    /**
     * An entry point for DetailsTableRowAssert to follow AssertJ standard <code>assertThat()</code> statements.<br>
     * With a static import, one can write directly: <code>assertThat(myIssuesDetailsTableRow)</code> and get specific
     * assertion with code completion.
     *
     * @param actual
     *         the DetailsTableRow we want to make assertions on.
     *
     * @return a new {@link DetailsTableRowAssert}
     */
    @org.assertj.core.util.CheckReturnValue
    public static DetailsTableRowAssert assertThat(DetailsTableRow actual) {
        return new DetailsTableRowAssert(actual);
    }
}
