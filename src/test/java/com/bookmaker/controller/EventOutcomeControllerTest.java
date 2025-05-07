package com.bookmaker.controller;

import com.bookmaker.model.dto.EventOutcome;
import com.bookmaker.service.EventService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EventOutcomeController.class)
@AutoConfigureMockMvc(addFilters = false) // disables Spring Security filters
class EventOutcomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @Test
    void shouldPublishEventOutcomeSuccessfully() throws Exception {
        String requestBody = """
            {
                "eventId": "1230",
                "eventName": "HOME_WIN",
                "eventWinnerId": 501
            }
        """;

        mockMvc.perform(post("/api/events/publish")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("Event processed successfully"));

        // Verify service was called with proper object
        EventOutcome expected = new EventOutcome();
        expected.setEventId(1230L);
        expected.setEventName("HOME_WIN");
        expected.setEventWinnerId(501L);

        verify(eventService).sendEventOutcome(Mockito.refEq(expected));
    }
}
