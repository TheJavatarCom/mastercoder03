package com.thejavatar.mastercoder03

import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by Lukasz Janicki on 15.03.14.
 * theJavatar.com
 * contact@thejavatar.com
 */
class ApplicationTest extends Specification {

    private Application objectUnderTest = new Application()
    private String anyNumber = 2
    @Shared systemHigherThanSupportedScope = 63
    @Shared systemLowerThanSupportedScope = 1
    @Shared systemInsideSupportedScope = 6

    @Unroll
    def "should convert #numberToConvert to #expectedResult, for input system: #inputSystem and output system: #outputSystem"() {
        when:
            String convertedNumber = objectUnderTest.Convert(inputSystem, outputSystem, numberToConvert)
        then:
            convertedNumber == expectedResult
        where:
            inputSystem | outputSystem | numberToConvert || expectedResult
            2           | 16           | "11010"         || "1A"
            10          | 5            | "10"            || "20"
            4           | 3            | "3"             || "10"
            16          | 10           | "A"             || "10"
            62          | 10           | "z"             || "61"
            20          | 2            | "12"            || "10110"
            20          | 12           | "12"            || "1A"
            7           | 3            | "1554"          || "212020"
            7           | 20           | "1554"          || "1B7"
            62          | 62           | "zasdabsds"     || "zasdabsds"
    }

    @Unroll
    def "should convert rational #numberToConvert to #expectedResult, for input system: #inputSystem and output system: #outputSystem"() {
        when:
            String convertedNumber = objectUnderTest.Convert(inputSystem, outputSystem, numberToConvert)
        then:
            convertedNumber == expectedResult
        where:
            inputSystem | outputSystem | numberToConvert          || expectedResult
            2           | 10           | "10000,1"                || "16,5"
            2           | 10           | "0,11"                   || "0,75"
            10          | 3            | "0,5"                    || "0,1111"
            10          | 2            | "6,1"                    || "110,0001"
            16          | 10           | "AA,B"                   || "170,6875"
            16          | 16           | "AA,B"                   || "AA,B"
            16          | 36           | "AA,B"                   || "4Q,OR00"
            10          | 3            | "0,5"                    || "0,1111"
            10          | 2            | "1234567,5678"           || "100101101011010000111,1001"
            10          | 12           | "1234567,5678"           || "4B6547,6991"
            62          | 62           | "zzzzzzzzzzzzzzzzz,zzzz" || "zzzzzzzzzzzzzzzzz,zzzz"
    }


    @Unroll
    @Ignore("used to evaluate if BigDecimalPowSupport#optimizedPow() works; test produces valid results only when optimization for same input/output system is disabled in Converter class")
    def "performance test inputSystem [#inputSystem], outputSystem [#outputSystem], numberToConvert [#numberToConvert]"() {
        when:
            String convertedNumber = objectUnderTest.Convert(inputSystem, outputSystem, numberToConvert)
        then:
            convertedNumber == expectedResult
        where:
            inputSystem | outputSystem | numberToConvert                                            || expectedResult
            12          | 12           | "903203921039120390123131,AAAA"                            || "903203921039120390123131,AAAA"
            2           | 25           | "1011110101010,0001"                                       || "9H8,1E1E"
            4           | 13           | "123312321,12"                                             || "3CC24,4B4B"
            16          | 36           | "AA2131312431241312,B123"                                  || "IEBMEYIYBHNH1U,OWR6"
            62          | 62           | "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz,zzzz" || "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz,zzzz"
            58          | 58           | "vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv,vvvv" || "vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv,vvvv"
            57          | 57           | "uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu,uuuu" || "uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu,uuuu"
            51          | 51           | "ooooooooooooooooooooooooooooooooooooooooooooooooooo,oooo" || "ooooooooooooooooooooooooooooooooooooooooooooooooooo,oooo"
            45          | 45           | "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii,iiii" || "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii,iiii"
            39          | 39           | "ccccccccccccccccccccccccccccccccccccccccccccccccccc,cccc" || "ccccccccccccccccccccccccccccccccccccccccccccccccccc,cccc"
            33          | 33           | "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW,WWWW" || "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW,WWWW"
            25          | 25           | "OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO,OOOO" || "OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO,OOOO"
            20          | 20           | "JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ,JJJJ" || "JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ,JJJJ"
            14          | 14           | "DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD,DDDD" || "DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD,DDDD"
            8           | 8            | "777777777777777777777777777777777777777777777777777,7777" || "777777777777777777777777777777777777777777777777777,7777"
    }

    @Unroll
    def "should convert 0 to 0, for input system: #inputSystem and output system: #outputSystem"() {
        expect:
            "0" == objectUnderTest.Convert(inputSystem, outputSystem, "0")
        where:
            inputSystem | outputSystem
            2           | 4
            4           | 2
            16          | 12
    }

    @Unroll
    def "should throw an error when one of the numerical systems is out of scope (inputSystem: #inputSystem, outputSystem: #outputSystem)"() {
        when:
            objectUnderTest.Convert(inputSystem, outputSystem, anyNumber)
        then:
            thrown(IllegalArgumentException)
        where:
            inputSystem                    | outputSystem
            systemHigherThanSupportedScope | systemInsideSupportedScope
            systemInsideSupportedScope     | systemHigherThanSupportedScope
            systemLowerThanSupportedScope  | systemInsideSupportedScope
            systemInsideSupportedScope     | systemLowerThanSupportedScope
    }

    @Unroll
    def "should throw an error when number is equal to #number (contains not allowed character)"() {
        when:
            objectUnderTest.Convert(systemInsideSupportedScope, systemInsideSupportedScope, number)
        then:
            thrown(IllegalArgumentException)
        where:
            number << ["45312-5", ".", ";", "a.b", "1.3", "-", "a=", "+", "!", "@"]
    }

    def "should throw an error if number to convert has too many decimal points"() {
        when:
            objectUnderTest.Convert(systemInsideSupportedScope, systemInsideSupportedScope, "32,32,12")
        then:
            thrown(IllegalArgumentException)
    }

    def "should throw an error if number has more than 4 fractional digits"() {
        when:
            objectUnderTest.Convert(systemInsideSupportedScope, systemInsideSupportedScope, "124312,12345")
        then:
            thrown(IllegalArgumentException)
    }

    def "should throw an error if number is negative"() {
        when:
            objectUnderTest.Convert(systemInsideSupportedScope, systemInsideSupportedScope, "-124312,12")
        then:
            thrown(IllegalArgumentException)
    }

    @Unroll
    def "should throw an error if number contains #number which is not permitted in input system #inputSystem"() {
        when:
            objectUnderTest.Convert(inputSystem, systemInsideSupportedScope, number)
        then:
            thrown(IllegalArgumentException)
        where:
            inputSystem << [2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,
                    35,36,37,38,39,40,41,42,43,43,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61]
            number << ["2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
                    "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c",
                    "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
                    "x", "y", "z"]
    }

    @Unroll
    def "should throw an error if number contains #number which is digit higher or equal to input system 2"() {
        when:
            objectUnderTest.Convert(2, systemInsideSupportedScope, number)
        then:
            thrown(IllegalArgumentException)
        where:
            number << ["2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
                "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c",
                "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
                "x", "y", "z"]
    }

}
