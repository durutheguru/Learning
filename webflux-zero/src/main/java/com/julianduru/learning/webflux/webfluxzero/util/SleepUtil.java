package com.julianduru.learning.webflux.webfluxzero.util;

import lombok.extern.slf4j.Slf4j;

/**
 * created by julian on 08/03/2022
 */
@Slf4j
public class SleepUtil {


    public static void sleepSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            log.error("Error occurred while sleeping", e);
        }
    }


}
