package org.springframework.samples.petclinic.sfg.junit5;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.samples.petclinic.sfg.HearingInterpreter;
import org.springframework.samples.petclinic.sfg.LaurelWordProducer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.Assert.*;

@ActiveProfiles("component-scan")
@SpringJUnitConfig(classes = HearingInterpreterComponentTest.TestConfig.class)
public class HearingInterpreterComponentTest {

    @Profile("component-scan") // We did this to isolate this class
    @Configuration
    @ComponentScan("org.springframework.samples.petclinic.sfg")
    static class TestConfig{
//        Shouldn't need to use bean because of the @ComponentScan. Bean is just a quick fix
        @Bean
        HearingInterpreter hearingInterpreter(){
            return new HearingInterpreter(new LaurelWordProducer());
        }
    }

    @Autowired
    HearingInterpreter hearingInterpreter;

    @Test
    public void whatIHeard() {

        String word = hearingInterpreter.whatIHeard();
        assertEquals("Laurel", word);
    }
}