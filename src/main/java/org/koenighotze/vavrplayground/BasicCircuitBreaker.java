package org.koenighotze.vavrplayground;

public class BasicCircuitBreaker {

    private final FeedFetcher feedFetcher = new FeedFetcher();

    public static Long waitForMillis(Long duration) throws InterruptedException {
//        Future.of(() -> {
//            Thread.sleep(duration);
//            return duration;
//        });
        return duration;
    }
}
