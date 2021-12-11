package day10

import org.junit.Test
import kotlin.test.assertEquals
import com.github.stefanbirkner.systemlambda.SystemLambda.*

class SyntaxScoringTests {

    @Test
    fun testAutocmpleteScorer() {
        assertEquals( 288957, calculateAutocomplete("}}]])})]"))
        assertEquals( 5566, calculateAutocomplete(")}>]})"))
        assertEquals(1480781, calculateAutocomplete("}}>}>))))"))
        assertEquals(995444, calculateAutocomplete("]]}}]}]}>"))
        assertEquals(294, calculateAutocomplete("])}>"))
    }

    @Test
    fun breakfastPrintsResult() {
        val output = tapSystemOut {
            breakfast()
        }
        assertEquals(
            "168417",
            output.trim()
        )
    }
}