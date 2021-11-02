package com.cy;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class LogTests {
//    private static final Logger log=
//            LoggerFactory.getLogger(LogTests.class);

    @Test
    void testLogLevel(){
        //日志级别 trace<debug<info<warn<error
        log.trace("==trace==");
        log.debug("==debug==");
        log.info("==info==");
        log.warn("==warn==");
        log.error("==error==");
    }

}
