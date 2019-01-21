package pl.sagiton.example.impl.parsing

import pl.sagiton.example.impl.model.Visitation
import spock.lang.Specification
import spock.lang.Unroll

import static pl.sagiton.example.impl.parsing.FormatParsers.parseFormat1
import static pl.sagiton.example.impl.parsing.FormatParsers.parseFormat2

/**
 * Developer note:
 * Obviously, we should also cover negative scenarios, where the line is not in expected format. In such cases,
 * there should be some dedicated exception thrown, which we would check with
 * {@code when: ... then: thrown DedicatedException}.
 * <p>
 * Because this is just an "exercise" task, I've assumed that input files are well formatted and just covered
 * format differences that boil down to separators and additional hyphen in ID. Because of that simplification,
 * I only cover positive scenarios here.
 */
class FormatParsersTest extends Specification {
    @Unroll
    def "Line #line is parsed to #expected for 1st format"(){
        expect:
        parseFormat1(line) == expected

        where:
        line                                        | expected
        "Lucy Mcgee,LONDON,51011156P"               | Visitation.of("Lucy Mcgee", "LONDON", "51011156P")
        "Alexander Arnold,SAN FRANCISCO,21743514G"  | Visitation.of("Alexander Arnold", "SAN FRANCISCO", "21743514G")
        "Rodolfo West,LAS VEGAS,74176315G"          | Visitation.of("Rodolfo West", "LAS VEGAS", "74176315G")
    }

    @Unroll
    def "Line #line is parsed to #expected for 2nd format"(){
        expect:
        parseFormat2(line) == expected

        where:
        line                                        | expected
        "Mitchell Newton ; LAS VEGAS ; 25384390-A"  | Visitation.of("Mitchell Newton", "LAS VEGAS", "25384390A")
        "Taylor Matthews ; LONDRES ; 58202263-G"    | Visitation.of("Taylor Matthews", "LONDRES", "58202263G")
        "Russell Pope ; CARTAGENA ; 69429384-C"     | Visitation.of("Russell Pope", "CARTAGENA", "69429384C")
    }
}
