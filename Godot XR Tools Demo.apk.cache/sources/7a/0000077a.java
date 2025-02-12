package kotlin.time;

import kotlin.Metadata;
import kotlin.jvm.internal.LongCompanionObject;
import kotlin.time.Duration;

/* compiled from: longSaturatedMath.kt */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\u001a*\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0001H\u0002ø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0007\u001a\"\u0010\b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0000ø\u0001\u0000¢\u0006\u0004\b\t\u0010\n\u001a\"\u0010\u000b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0002ø\u0001\u0000¢\u0006\u0004\b\f\u0010\n\u001a \u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0001H\u0000ø\u0001\u0000¢\u0006\u0002\u0010\n\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0010"}, d2 = {"checkInfiniteSumDefined", "", "longNs", "duration", "Lkotlin/time/Duration;", "durationNs", "checkInfiniteSumDefined-PjuGub4", "(JJJ)J", "saturatingAdd", "saturatingAdd-pTJri5U", "(JJ)J", "saturatingAddInHalves", "saturatingAddInHalves-pTJri5U", "saturatingDiff", "valueNs", "originNs", "kotlin-stdlib"}, k = 2, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes.dex */
public final class LongSaturatedMathKt {
    /* renamed from: saturatingAdd-pTJri5U  reason: not valid java name */
    public static final long m1467saturatingAddpTJri5U(long longNs, long duration) {
        long durationNs = Duration.m1359getInWholeNanosecondsimpl(duration);
        if (((longNs - 1) | 1) != LongCompanionObject.MAX_VALUE) {
            if ((1 | (durationNs - 1)) == LongCompanionObject.MAX_VALUE) {
                return m1468saturatingAddInHalvespTJri5U(longNs, duration);
            }
            long result = longNs + durationNs;
            if (((longNs ^ result) & (durationNs ^ result)) < 0) {
                if (longNs < 0) {
                    return Long.MIN_VALUE;
                }
                return LongCompanionObject.MAX_VALUE;
            }
            return result;
        }
        return m1466checkInfiniteSumDefinedPjuGub4(longNs, duration, durationNs);
    }

    /* renamed from: checkInfiniteSumDefined-PjuGub4  reason: not valid java name */
    private static final long m1466checkInfiniteSumDefinedPjuGub4(long longNs, long duration, long durationNs) {
        if (!Duration.m1371isInfiniteimpl(duration) || (longNs ^ durationNs) >= 0) {
            return longNs;
        }
        throw new IllegalArgumentException("Summing infinities of different signs");
    }

    /* renamed from: saturatingAddInHalves-pTJri5U  reason: not valid java name */
    private static final long m1468saturatingAddInHalvespTJri5U(long longNs, long duration) {
        long half = Duration.m1342divUwyO8pc(duration, 2);
        if (((Duration.m1359getInWholeNanosecondsimpl(half) - 1) | 1) == LongCompanionObject.MAX_VALUE) {
            return (long) (longNs + Duration.m1382toDoubleimpl(duration, DurationUnit.NANOSECONDS));
        }
        return m1467saturatingAddpTJri5U(m1467saturatingAddpTJri5U(longNs, half), half);
    }

    public static final long saturatingDiff(long valueNs, long originNs) {
        if ((1 | (originNs - 1)) == LongCompanionObject.MAX_VALUE) {
            return Duration.m1391unaryMinusUwyO8pc(DurationKt.toDuration(originNs, DurationUnit.DAYS));
        }
        long result = valueNs - originNs;
        if (((result ^ valueNs) & (~(result ^ originNs))) < 0) {
            long j = (long) DurationKt.NANOS_IN_MILLIS;
            long resultMs = (valueNs / j) - (originNs / j);
            long resultNs = (valueNs % j) - (originNs % j);
            Duration.Companion companion = Duration.Companion;
            long duration = DurationKt.toDuration(resultMs, DurationUnit.MILLISECONDS);
            Duration.Companion companion2 = Duration.Companion;
            return Duration.m1375plusLRDsOJo(duration, DurationKt.toDuration(resultNs, DurationUnit.NANOSECONDS));
        }
        Duration.Companion companion3 = Duration.Companion;
        return DurationKt.toDuration(result, DurationUnit.NANOSECONDS);
    }
}