package stringcalculator;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StringCalculatorTest {

    @Test
    public void test_add_with_only_comma() {
        assertThat(StringCalculator.add(""), is(0));
        assertThat(StringCalculator.add("0"), is(0));
        assertThat(StringCalculator.add("1,2,5"), is(8));
        assertThat(StringCalculator.add("12,15"), is(27));
        assertThat(StringCalculator.add(","), is(0));
    }

    @Test
    public void test_add_with_newline_and_comma() {
        assertThat(StringCalculator.add("1\n2"), is(3));
        assertThat(StringCalculator.add("1\n,2,3"), is(6));
        assertThat(StringCalculator.add("12\n2,3\n\n4,6"), is(27));
    }

    @Test
    public void test_add_with_single_custom_delimiter() {
        assertThat(StringCalculator.add("//;\n1;3;4"), is(8));
        assertThat(StringCalculator.add("//$\n1$2$3"), is(6));
        assertThat(StringCalculator.add("//@\n2@3@8"), is(13));
        assertThat(StringCalculator.add("//@\n"), is(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_add_with_negative_with_comma() {
        StringCalculator.add("-1,2,3,-7,-8,9");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_add_with_negative_with_newline() {
        StringCalculator.add("1\n,2,-60");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_add_with_negative_with_custom_delimiter() {
        StringCalculator.add("//;\n1;-2;23;-4");
    }

    @Test
    public void test_add_ignore_bigger_than_1000() {
        assertThat(StringCalculator.add("2,1001"), is(2));
        assertThat(StringCalculator.add("2,1000"), is(1002));
        assertThat(StringCalculator.add("2,1000,2000,9999"), is(1002));
    }

    @Test
    public void test_add_with_delimiter_that_has_arbitrary_length() {
        assertThat(StringCalculator.add("//***\n1***2***3"), is(6));
        assertThat(StringCalculator.add("//@@@\n1@@@2@@@3"), is(6));
    }

    @Test
    public void test_add_with_multiple_delimiter() {
        assertThat(StringCalculator.add("//$,@\n1$2@3"), is(6));
        assertThat(StringCalculator.add("//$,@@@\n1$2@@@3"), is(6));
        assertThat(StringCalculator.add("//$,@@@\n1$2@@@3"), is(6));
    }
}