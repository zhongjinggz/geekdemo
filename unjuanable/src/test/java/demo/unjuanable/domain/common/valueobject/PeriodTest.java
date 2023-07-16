package demo.unjuanable.domain.common.valueobject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PeriodTest {
    @Test
    void should_throw_exception_when_other_is_null() {
        Period period = Period.of(2021, 1, 1, 2021, 1, 2);
        assertThrows(IllegalArgumentException.class, () -> period.overlap(null));
    }

    static Stream<Arguments> overlapPeriods() {
        return Stream.of(
                Arguments.of(
                        Period.of(2021, 1, 1
                                , 2021, 1, 3)
                        , Period.of(2021, 1, 2
                                , 2021, 1, 4)
                )
                , Arguments.of(
                        Period.of(2021, 1, 1
                                , 2022, 1, 1)
                        , Period.of(2021, 6, 1
                                , 2022, 7, 1)
                )
                , Arguments.of(
                        Period.of(2021, 6, 1
                                , 2022, 7, 1)
                        , Period.of(2021, 1, 1
                                , 2022, 1, 1)
                )
                , Arguments.of(
                        Period.of(2021, 1, 1
                                , 2021, 1, 10)
                        , Period.of(2021, 1, 1
                                , 2021, 1, 10)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("overlapPeriods")
    void overlap_shouldReturnTrue_whenOtherIsOverlap(Period period, Period other) {
        assertTrue(period.overlap(other));
    }

    static Stream<Arguments> notOverlapPeriods() {
        return Stream.of(
                Arguments.of(
                        Period.of(2021, 1, 1
                                , 2021, 1, 2)
                        , Period.of(2021, 1, 3
                                , 2021, 1, 4)
                ), Arguments.of(
                        Period.of(2021, 1, 3
                                , 2021, 1, 4)
                        , Period.of(2021, 1, 1
                                , 2021, 1, 2)
                ), Arguments.of(
                        Period.of(2021, 1, 1
                                , 2021, 1, 2)
                        , Period.of(2021, 1, 2
                                , 2021, 1, 3)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("notOverlapPeriods")
    void overlap_shouldReturnFalse_whenOtherIsNotOverlap(Period period, Period other) {
        assertFalse(period.overlap(other));
    }


}